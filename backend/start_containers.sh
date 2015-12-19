#!/usr/bin/env bash

LDAP_PASSWORD=Nosfols6
EUREKA_PASSWORD=Eureka!

echo Starting Containers
# MONGO
docker start mos_erp_mongo
MONGO_URL=`docker inspect --format='{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' mos_erp_mongo`:27017
echo MONGO_URL: ${MONGO_URL}

sleep 10

# Open LDAP
# Access the LDAP through: base DN="dc=moserp,dc=org", user DN="cn=admin,dc=moserp,dc=org", password="Nosfols6"
docker start mos_erp_directory
LDAP_URL=ldap://`docker inspect --format='{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' mos_erp_directory`:389
echo LDAP_URL: ${LDAP_URL}
sleep 10

# Eureka Service Registry (from https://github.com/spring-cloud-samples/eureka)
docker start mos_erp_service_registry
#EUREKA_URL=`docker inspect --format='{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' mos_erp_service_registry`:8761
EUREKA_URL=http://eureka-client:${EUREKA_PASSWORD}@`hostname`:8761
echo EUREKA_URL: ${EUREKA_URL}

sleep 10

# Spring Cloud Config Server
docker start mos_erp_config_service

# Wait for LEASE_TIMEOUT + CONFIG_SERVER Start Time to be sure that it is registered at EUREKA
sleep 45

docker start mos_erp_environment
docker start mos_erp_facility
docker start mos_erp_product
docker start mos_erp_inventory

