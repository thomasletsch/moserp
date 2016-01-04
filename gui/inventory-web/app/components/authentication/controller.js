'use strict';

angular.module('Authentication')

    .controller('LoginController',
        ['$scope', '$rootScope', '$location', 'AuthenticationService',
            function ($scope, $rootScope, $location, AuthenticationService) {
                // reset login status
                AuthenticationService.ClearCredentials();

                $scope.login = function () {
                    $scope.dataLoading = true;
                    AuthenticationService.Login($scope.username, $scope.password, function (response) {
                        console.log("setting credentials");
                        AuthenticationService.SetCredentials(username, password);
                        $location.path('/');
                    }, function (response) {
                        console.log("setting error message");
                        $scope.error = response.message;
                        $scope.dataLoading = false;
                    });
                };
            }]);
