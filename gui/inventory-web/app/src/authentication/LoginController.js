/**
 * Login Controller
 * @constructor
 */
function LoginController($log, $scope, $rootScope, $state, AuthenticationService, StructureService) {

    $scope.logout = function () {
        $http.post('/logout', {}).success(function() {
            AuthenticationService.logout();
            $location.path("/");
        }).error(function(data) {
            console.log("Logout failed");
            AuthenticationService.logout();
        });
    }
}

export default [
    '$log', '$scope', '$rootScope', '$state', 'AuthenticationService', 'StructureService',
    LoginController
];
