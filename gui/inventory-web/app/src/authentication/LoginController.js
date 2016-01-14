/**
 * Login Controller
 * @constructor
 */
function LoginController($log, $scope, $rootScope, $state, AuthenticationService) {
    // reset login status
    AuthenticationService.ClearCredentials();

    $scope.login = function () {
        $scope.dataLoading = true;
        AuthenticationService.Login($scope.username, $scope.password, function (response) {
            $scope.user = $rootScope.globals.userName;
            $state.go("entities");
            $scope.dataLoading = false;
        }, function (response) {
            $log.debug("setting error message");
            $scope.error = response.message;
            $scope.dataLoading = false;
        });
    };
    $scope.logout = function () {
        AuthenticationService.Logout();
    }
}

export default [
    '$log', '$scope', '$rootScope', '$state', 'AuthenticationService',
    LoginController
];
