const URL_AVATAR_ICONS = 'assets/svg/avatars.svg';
const URL_ICON_MENU = 'assets/svg/menu.svg';

// Load the custom app ES6 modules

import EntitiesController from 'entities/EntitiesController'
import EntitiesService    from 'entities/EntitiesDataservice'

import { ExternalLogger } from 'utils/LogDecorator';

let $log = new ExternalLogger();
$log = $log.getInstance("BOOTSTRAP");
$log.debug("Configuring 'entities' module");

// Define the Angular 'entities' module

let moduleName = angular
    .module("entities", [])
    .service("entitiesService", EntitiesService)
    .controller("EntitiesController", EntitiesController)
    .config(($mdIconProvider) => {


        $log.debug("Configuring $mdIconProvider");

        // Register `dashboard` iconset & icons for $mdIcon service lookups

        $mdIconProvider
            .defaultIconSet(URL_AVATAR_ICONS, 128)
            .icon('menu', URL_ICON_MENU, 24);

    })
    .name;

export default moduleName;
