#!/usr/bin/env bash

echo Starting Containers
# MONGO
docker run --name mos_erp_mongo -P -d mongo:3.1
MONGO_URL=`docker inspect --format='{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' mos_erp_mongo`:27017
echo MONGO_URL: $MONGO_URL

# Open LDAP
# Access the LDAP through: base DN="dc=moserp,dc=org", user DN="cn=admin,dc=moserp,dc=org", password="Nosfols6"
docker run --name mos_erp_directory -P -e LDAP_ORGANISATION="Mos Erp" -e LDAP_DOMAIN="moserp.org" -e LDAP_ADMIN_PASSWORD="Nosfols6" -d osixia/openldap
LDAP_URL=`docker inspect --format='{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' mos_erp_directory`:389
echo LDAP_URL: $LDAP_URL

# Eureka Service Registry
docker run --name mos_erp_service_registry -P -e SPRING_PROFILES_ACTIVE="docker" -d springcloud/eureka
EUREKA_URL=`docker inspect --format='{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' mos_erp_service_registry`:8761
echo EUREKA_URL: $EUREKA_URL

# Spring Cloud Config Server
docker run --name mos_erp_config_service -P -e EUREKA_URL=${EUREKA_URL} -e SPRING_PROFILES_ACTIVE="docker" -d moserp/config-server


docker run --name mos_erp_environment -P -e EUREKA_URL=${EUREKA_URL} -e SPRING_PROFILES_ACTIVE="docker" -d moserp/environment
