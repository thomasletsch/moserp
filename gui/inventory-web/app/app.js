'use strict';

angular.module('Registry', []);
angular.module('Entities', []);
angular.module('Authentication', ['Registry']);

// Declare app level module which depends on views, and components
angular.module('InventoryWeb', [
    'Authentication',
    'Entities',
    'Registry',
    'ngRoute',
    'ngCookies'
]).
    config(['$routeProvider', function ($routeProvider) {
        $routeProvider
            .when('/login', {
                controller: 'LoginController',
                templateUrl: 'components/authentication/login.html',
                hideMenus: true
            })

            .when('/', {
                controller: 'EntitiesController',
                templateUrl: 'components/entities/entities.html'
            })

            .otherwise({redirectTo: '/login'});
    }])

    .run(['$rootScope', '$location', '$cookieStore', '$http', 'RegistryService',
        function ($rootScope, $location, $cookieStore, $http) {
            // keep user logged in after page refresh
            $rootScope.globals = $cookieStore.get('globals') || {};
            if ($rootScope.globals.currentUser) {
                $http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.globals.currentUser.authdata; // jshint ignore:line
            }

            $rootScope.$on('$locationChangeStart', function (event, next, current) {
                // redirect to login page if not logged in
                if ($location.path() !== '/login' && !$rootScope.globals.currentUser) {
                    console.log("not logged in");
                    $location.path('/login');
                }
            });
        }]);
