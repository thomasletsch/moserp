import StructureService    from 'structure/StructureService'

import { ExternalLogger } from 'utils/LogDecorator';

let $log = new ExternalLogger();
$log = $log.getInstance("BOOTSTRAP");
$log.debug("Configuring 'structure' module");

// Define the Angular 'entities' module

let moduleName = angular
    .module("structure", [])
    .service("StructureService", StructureService)
    .name;

export default moduleName;
