/**
 * Login Controller
 * @constructor
 */
function LoginController($log, $scope, $http, $state, AuthenticationService, StructureService) {

    $scope.logout = function () {
        $http.post('/logout', {}).success(function() {
            AuthenticationService.logout();
            $location.path("/");
        }).error(function(data) {
            console.log("Logout failed");
            AuthenticationService.logout();
        });
        $state.go("default");
    }
}

export default [
    '$log', '$scope', '$http', '$state', 'AuthenticationService', 'StructureService',
    LoginController
];
