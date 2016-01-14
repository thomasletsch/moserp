import LoginController from 'authentication/LoginController'
import AuthenticationService    from 'authentication/AuthenticationService'
import 'angular-cookies'
import 'angular-ui-router'

let moduleName = angular
    .module("authentication", ['structure', 'ngCookies', 'ui.router'])
    .service("AuthenticationService", AuthenticationService)
    .controller("LoginController", LoginController)
    .name;

export default moduleName;
