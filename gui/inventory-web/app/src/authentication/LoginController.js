/**
 * Login Controller
 * @constructor
 */
function LoginController($log, $http, $state, $scope, StructureService, AuthenticationService) {

    $scope.logout = function () {
        $http.post('/logout', {}).success(function() {
            AuthenticationService.logout();
        }).error(function(data) {
            console.log("Logout failed");
            AuthenticationService.logout();
        });
        $state.go("default");
    };

    $scope.$on('oauth:login', function(event, token) {
        $log.debug('Authorized third party app with token', token.access_token);
    });

    $scope.$on('oauth:logout', function(event) {
        $log.debug('The user has signed out');
    });

    $scope.$on('oauth:loggedOut', function(event) {
        $log.debug('The user is not signed in');
    });

    $scope.$on('oauth:denied', function(event) {
        $log.debug('The user did not authorize the third party app');
        AuthenticationService.logout();
        var baseUrl = window.location.protocol + "//" + window.location.hostname + ":" + window.location.port;
        window.location = baseUrl + "/uaa/oauth/authorize?response_type=token&client_id=web&redirect_uri=" + window.location;
    });

    $scope.$on('oauth:expired', function(event) {
        $log.debug('The access token is expired. Please refresh.');
    });

    $scope.$on('oauth:profile', function(profile) {
        $log.debug('User profile data retrieved: ', profile);
        StructureService.loadResources();
    });

}

export default [
    '$log', '$http', '$state', '$scope', 'StructureService', 'AuthenticationService',
    LoginController
];
