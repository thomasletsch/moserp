import LoginController from 'authentication/LoginController'
import AuthenticationService    from 'authentication/AuthenticationService'
import 'angular-cookies'

import { ExternalLogger } from 'utils/LogDecorator';

let $log = new ExternalLogger();
$log = $log.getInstance("BOOTSTRAP");
$log.debug("Configuring 'authentication' module");

// Define the Angular 'entities' module

let moduleName = angular
    .module("authentication", ['structure', 'ngCookies'])
    .service("AuthenticationService", AuthenticationService)
    .controller("LoginController", LoginController)
    .name;

export default moduleName;
