import 'assets/app.css!'

// Load Angular libraries

import angular from 'angular'
import 'angular-ui-router'
import 'angular-ui-bootstrap'
import 'angular-schema-form'
import 'angular-schema-form-bootstrap'
import 'angular-ui-grid'
import 'angular-translate'
import 'angular-translate-loader-static-files'
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
import EntitiesListController from 'entities/EntitiesListController'
import EntitiesEditController from 'entities/EntitiesEditController'

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
            .module(appName, ['ui.router', 'ui.bootstrap',
                'ui.grid', 'ui.grid.pagination', 'ui.grid.selection',
                'schemaForm',
                'pascalprecht.translate',
                registry, structure, authentication, entities, menu])
            .config(['$provide', LogDecorator])
            .config(['$translateProvider', function($translateProvider) {
                $translateProvider.useStaticFilesLoader({
                    prefix: 'src/translations_',
                    suffix: '.json'
                });
                $translateProvider.preferredLanguage('de');
            }])
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
                        url: "/:entityName",
                        templateUrl: "src/entities/view/entities.list.html",
                        controller: EntitiesListController
                    })
                    .state('entities.edit', {
                        url: "/:entityName/:id",
                        templateUrl: "src/entities/view/entities.edit.html",
                        controller: EntitiesEditController
                    })

            })
            .directive('appMenu', function () {
                return {
                    templateUrl: 'src/menu/view/menu.html',
                    controller: MenuController
                };
            })
            .directive('appLogin', function () {
                return {
                    templateUrl: "src/authentication/view/login.html",
                    controller: LoginController
                };
            })
            .run(function ($rootScope, $location, AuthenticationService) {
                $rootScope.$on('$stateChangeStart', function(event, toState, toParams, fromState, fromParams) {
                    console.log('Transition to ' + JSON.stringify(toState));
                    if (!AuthenticationService.isLoggedIn() && toState.url != '/login') {
                        console.log('Not logged in');
                        $location.path('/login');
                        event.preventDefault();
                    }
                })
            });
        angular.bootstrap(body, [appName], {strictDi: false})
    });


