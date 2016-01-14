import EurekaClient from 'registry/EurekaClient'
import RegistryService from 'registry/RegistryService'

let moduleName = angular
    .module("registry", [])
    .service("EurekaClient", EurekaClient)
    .service("RegistryService", RegistryService)
    .name;

export default moduleName;
