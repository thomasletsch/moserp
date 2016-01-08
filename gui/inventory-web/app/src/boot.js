// Load the Angular Material CSS associated with ngMaterial
// then load the main.css to provide overrides, etc.

import 'angular-material/angular-material.css!'
import 'assets/app.css!'

// Load Angular libraries

import angular from 'angular'
import 'angular-ui-router'
import 'angular-ui-bootstrap'
import LoginController from 'authentication/LoginController'
import HomeController from 'home/HomeController'

// Load custom application modules

import main from 'app/main'

// Load loggers for injection and pre-angular debugging

import { LogDecorator, ExternalLogger } from 'utils/LogDecorator';


/**
 * Manually bootstrap the application when AngularJS and
 * the application classes have been loaded.
 */
angular
    .element(document)
    .ready(function () {

        let appName = 'inventory-web';
        let $log = new ExternalLogger();

        $log = $log.getInstance("BOOTSTRAP");
        $log.debug("Initializing '{0}'", [appName]);

        let body = document.getElementsByTagName("body")[0];
        let app = angular
            .module(appName, ['ui.router', 'ui.bootstrap', main])
            .config(['$provide', LogDecorator])
            .config(function ($stateProvider, $urlRouterProvider) {
                console.log("init $urlRouterProvider");
                $urlRouterProvider.otherwise("/login");
                $stateProvider
                    .state('login', {
                        url: "/login",
                        templateUrl: "src/authentication/view/login.html",
                        controller: LoginController
                    })
                    .state('home', {
                        url: "/",
                        templateUrl: "src/home/view/home.html",
                        controller: HomeController
                    })
            })
            //.run(['$rootScope', '$location', '$cookieStore', '$http', 'RegistryService',
            //    function ($rootScope, $location, $cookieStore, $http) {
            //        // keep user logged in after page refresh
            //        $rootScope.globals = $cookieStore.get('globals') || {};
            //        if ($rootScope.globals.currentUser) {
            //            $http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.globals.currentUser.authdata; // jshint ignore:line
            //        }
            //
            //    }])
            ;

        angular.bootstrap(body, [appName], {strictDi: false})
    });


