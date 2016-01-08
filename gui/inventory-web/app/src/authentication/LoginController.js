/**
 * Login Controller
 * @constructor
 */
function LoginController($scope, $rootScope, $location, AuthenticationService) {
    // reset login status
    AuthenticationService.ClearCredentials();

    $scope.login = function () {
        $scope.dataLoading = true;
        AuthenticationService.Login($scope.username, $scope.password, function (response) {
            $location.path('/');
            $scope.dataLoading = false;
        }, function (response) {
            console.log("setting error message");
            $scope.error = response.message;
            $scope.dataLoading = false;
        });
    };
}

export default [
    '$scope', '$rootScope', '$location', 'AuthenticationService',
    LoginController
];
