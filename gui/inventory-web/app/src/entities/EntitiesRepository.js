/**
 * Entities DataService
 */
function EntitiesRepository($log, $rootScope, $http) {
    $log.debug("EntitiesRepository");

    // Promise-based API
    return {
        find: function (entityName, id, successCallback) {
            $log.debug("find()");
            if (!$rootScope.resources) {
                return;
            }
            var url = $rootScope.resources[entityName] + "/" + id;
            $http.get(url).then(function success(response) {
                var data = response.data;
                data.version = response.headers("ETag").replace(/"/g, '');
                $log.debug("Version " + data.version);
                successCallback(data);
            });
        },
        loadAll: function (entityName, successCallback) {
            $log.debug("loadAll()");
            if (!$rootScope.resources) {
                return;
            }
            var url = $rootScope.resources[entityName];
            $http.get(url).then(function success(response) {
                successCallback(response.data["_embedded"][entityName]);
            });
        },
        save: function (entityName, id, entity, successCallback) {
            $log.debug("save()");
            if (!$rootScope.resources) {
                return;
            }
            if (id) {
                var url = $rootScope.resources[entityName] + "/" + id;
                if (entity.hasOwnProperty("_links")) {
                    entity["_links"] = null;
                }
                $http.put(url, entity, {headers: {"If-Match": entity.version}}).then(function success(response) {
                    successCallback(response.data);
                });
            } else {
                var url = $rootScope.resources[entityName];
                $http.post(url, entity).then(function success(response) {
                    successCallback(response.data);
                });
            }
        }

    };
}

export default ['$log', '$rootScope', '$http', EntitiesRepository];
