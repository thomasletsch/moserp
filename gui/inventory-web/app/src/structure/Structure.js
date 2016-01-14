import StructureService    from 'structure/StructureService'

let moduleName = angular
    .module("structure", [])
    .service("StructureService", StructureService)
    .name;

export default moduleName;
