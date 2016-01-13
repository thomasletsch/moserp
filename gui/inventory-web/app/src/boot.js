import 'assets/app.css!'

// Load Angular libraries

import angular from 'angular'
import 'angular-ui-router'
import 'angular-ui-bootstrap'
import 'angular-schema-form'
import 'angular-schema-form-bootstrap'
import registry from 'registry/Registry';
import structure from 'structure/Structure';
import authentication from 'authentication/Authentication';
import entities from 'entities/Entities';
import menu from 'menu/Menu';
import LoginController from 'authentication/LoginController'
import AuthenticationService from 'authentication/AuthenticationService'
import HomeController from 'home/HomeController'
import MenuController from 'menu/MenuController'
import EntitiesController from 'entities/EntitiesController'

import { LogDecorator, ExternalLogger } from 'utils/LogDecorator';
let $log = new ExternalLogger();
$log = $log.getInstance("BOOTSTRAP");
$log.debug("Configuring 'main' module");


angular
    .element(document)
    .ready(function () {

        let appName = 'inventory-web';
        let $log = new ExternalLogger();

        $log = $log.getInstance("BOOTSTRAP");
        $log.debug("Initializing '{0}'", [appName]);

        let body = document.getElementsByTagName("body")[0];
        let app = angular
            .module(appName, ['ui.router', 'ui.bootstrap', 'schemaForm', registry, structure, authentication, entities, menu])
            .config(['$provide', LogDecorator])
            .config(function ($stateProvider, $urlRouterProvider) {
                console.log("init $urlRouterProvider");
                $urlRouterProvider.otherwise("/login");
                $stateProvider
                    .state('default', {
                        url: "/login",
                        templateUrl: 'src/home/view/home.html'
                    })
                    .state('entities', {
                        url: "/entities",
                        templateUrl: "src/entities/view/entities.html",
                        controller: EntitiesController
                    })
                    .state('entities.list', {
                        url: "/:entity",
                        templateUrl: "src/entities/view/entities.list.html",
                        controller: EntitiesController
                    })
                    .state('entities.edit', {
                        url: "/:entity/:id",
                        templateUrl: "src/entities/view/entities.edit.html",
                        controller: EntitiesController
                    })

            })
            .directive('appMenu', function() {
                return {
                    templateUrl: 'src/menu/view/menu.html',
                    controller: MenuController
                };
            })
            .directive('appLogin', function() {
                return {
                    templateUrl: "src/authentication/view/login.html",
                    controller: LoginController
                };
            })
            .run(function ($rootScope, $state, AuthenticationService) {
                $state.go("default");
                $rootScope.$on('$stateChangeStart', function (event, toState) {
                    console.log('Transition to ' + JSON.stringify(toState));
                    if (!AuthenticationService.isLoggedIn()) {
                        console.log('Not logged in');
                        if (toState.name !== "default") {
                            $state.go("default");
                        }
                    }
                })
            });
        angular.bootstrap(body, [appName], {strictDi: false})
    });


