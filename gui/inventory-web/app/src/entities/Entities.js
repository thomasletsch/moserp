import EntitiesController from 'entities/EntitiesController'
import EntitiesEditController from 'entities/EntitiesEditController'
import EntitiesListController from 'entities/EntitiesListController'
import EntitiesRepository    from 'entities/EntitiesRepository'

let moduleName = angular
    .module("entities", [])
    .service("EntitiesRepository", EntitiesRepository)
    .controller("EntitiesController", EntitiesController)
    .controller("EntitiesEditController", EntitiesEditController)
    .controller("EntitiesListController", EntitiesListController)
    .name;

export default moduleName;
