#!/usr/bin/env bash

LDAP_PASSWORD=Ldap!
EUREKA_PASSWORD=Eureka!

echo Starting Containers
# MONGO
docker run --name mos_erp_mongo -P -d mongo:3.1
MONGO_URL=`docker inspect --format='{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' mos_erp_mongo`:27017
echo MONGO_URL: ${MONGO_URL}

sleep 10

# Open LDAP
# Access the LDAP through: base DN="dc=moserp,dc=org", user DN="cn=admin,dc=moserp, dc=org", password="Ldap!"
docker run --name mos_erp_directory -P -e LDAP_ORGANISATION="Mos ERP" -e LDAP_DOMAIN="moserp.org" -e LDAP_ADMIN_PASSWORD=${LDAP_PASSWORD} -d osixia/openldap
LDAP_URL=ldap://`docker inspect --format='{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' mos_erp_directory`
echo LDAP_URL: ${LDAP_URL}
# Add initial groups and structures
ldapadd -x -H ${LDAP_URL} -D cn=admin,dc=moserp,dc=org -w ${LDAP_PASSWORD} -f directory-server/src/main/ldif/setup-ldap.ldif
# Check with: ldapsearch -x -H ${LDAP_URL} -D "cn=admin,dc=moserp,dc=org" -w ${LDAP_PASSWORD}

sleep 10

# Eureka Service Registry (from https://github.com/spring-cloud-samples/eureka)
docker run --name mos_erp_service_registry -P --net=host -e EUREKA_PASSWORD=${EUREKA_PASSWORD} -e SPRING_PROFILES_ACTIVE="docker" -d moserp/eureka-server
#EUREKA_URL=`docker inspect --format='{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' mos_erp_service_registry`:8761
EUREKA_URL=http://eureka-client:${EUREKA_PASSWORD}@`hostname`:8761
echo EUREKA_URL: ${EUREKA_URL}

sleep 10

# Spring Cloud Config Server
docker run --name mos_erp_config_service -P --net=host -e EUREKA_URL=${EUREKA_URL} -e SPRING_PROFILES_ACTIVE="docker" -d moserp/config-server

# Wait for LEASE_TIMEOUT + CONFIG_SERVER Start Time to be sure that it is registered at EUREKA
sleep 45

docker run --name mos_erp_environment -P --net=host -e LDAP_URL=${LDAP_URL} -e LDAP_PASSWORD=${LDAP_PASSWORD} -e EUREKA_URL=${EUREKA_URL} -e SPRING_PROFILES_ACTIVE="docker" -d moserp/environment
docker run --name mos_erp_facility -P --net=host -e LDAP_URL=${LDAP_URL} -e LDAP_PASSWORD=${LDAP_PASSWORD} -e EUREKA_URL=${EUREKA_URL} -e SPRING_PROFILES_ACTIVE="docker" -d moserp/facility
docker run --name mos_erp_product -P --net=host -e LDAP_URL=${LDAP_URL} -e LDAP_PASSWORD=${LDAP_PASSWORD} -e EUREKA_URL=${EUREKA_URL} -e SPRING_PROFILES_ACTIVE="docker" -d moserp/product
docker run --name mos_erp_inventory -P --net=host -e LDAP_URL=${LDAP_URL} -e LDAP_PASSWORD=${LDAP_PASSWORD} -e EUREKA_URL=${EUREKA_URL} -e SPRING_PROFILES_ACTIVE="docker" -d moserp/inventory
