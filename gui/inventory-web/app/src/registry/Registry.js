import EurekaClient from 'registry/EurekaClient'
import RegistryService from 'registry/RegistryService'

import { ExternalLogger } from 'utils/LogDecorator';

let $log = new ExternalLogger();
$log = $log.getInstance("BOOTSTRAP");
$log.debug("Configuring 'registry' module");

// Define the Angular 'registry' module

let moduleName = angular
    .module("registry", [])
    .service("EurekaClient", EurekaClient)
    .service("RegistryService", RegistryService)
    .name;

export default moduleName;
