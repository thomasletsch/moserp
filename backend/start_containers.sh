echo Creating images

docker create --name mos_erp_mongo -P mongo:3.1
docker create --name mos_erp_directory -P -e LDAP_ORGANISATION="Mos Erp" -e LDAP_DOMAIN="moserp.org" -e LDAP_ADMIN_PASSWORD="Nosfols6" osixia/openldap
docker create --name mos_erp_service_registry -P springcloud/eureka
docker create --name mos_erp_config_service -P --link mos_erp_service_registry:service_registry springcloud/configserver
docker create --name mos_erp_environment -P --link mos_erp_service_registry:service_registry --link mos_erp_mongo:mongo --link mos_erp_directory:directory --link mos_erp_config_service:config_service moserp/environment

echo Starting images
docker start mos_erp_mongo
docker start mos_erp_directory
docker start mos_erp_service_registry
docker start mos_erp_config_service
docker start mos_erp_environment
