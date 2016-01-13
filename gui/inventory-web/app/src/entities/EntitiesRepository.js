/**
 * Entities DataService
 * Uses embedded, hard-coded data model; acts asynchronously to simulate
 * remote data service call(s).
 *
 * @returns {{loadAll: Function}}
 * @constructor
 */
function EntitiesRepository($log, $rootScope, $http) {
    $log = $log.getInstance("EntitiesRepository");
    $log.debug("instanceOf() ");

    // Promise-based API
    return {
        loadAll: function (entityName, successCallback) {
            $log.debug("loadAll()");
            var url = $rootScope.resources[entityName];
            $http.get(url).then(function success(response) {
                successCallback(response.data["_embedded"][entityName]);
            });
        }
    };
}

export default ['$log', '$rootScope', '$http', EntitiesRepository];
