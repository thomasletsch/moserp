function StructureService($log, $http, $rootScope) {
    return {
        loadResources: function () {
            $rootScope.resources = {};
            $rootScope.schemata = {};
            $rootScope.resourceGroups = {};
            var allServices = listServices();
            for (let serviceUrl of allServices) {
                $log.debug("URL " + serviceUrl);
                $http.get(serviceUrl + '/structure').then(function success(response) {
                    for (let group in response.data) {
                        $log.debug("Group: " + group);
                        $rootScope.resourceGroups[group] = {};
                        for (let link of response.data[group].links) {
                            $log.debug("Link: " + JSON.stringify(link));
                            var resourceUri = link['href'];
                            var resourceName = link['rel'];
                            loadSchema(serviceUrl, resourceName);
                            loadResourceUris(group, resourceName, resourceUri);
                        }
                    }
                });
            }
        },
        clearResources: function () {
            $rootScope.resources = {};
            $rootScope.schemata = {};
            $rootScope.resourceGroups = {};
            $rootScope.$broadcast('resourcesChanged');
        },
        listResources: function () {
            var resources = [];
            for (let resourceName in $rootScope.resources) {
                var resource = {};
                resource.name = resourceName;
                resource.uri = $rootScope.resources[resourceName];
                resources.push(resource);
            }
            $log.debug("listResources: " + resources);
            return resources;
        },
        getUriForResource: function (resourceId) {
            return $rootScope.resources[resourceId];
        }
    };

    function loadSchema(serviceUrl, resourceName) {
        $http.get(serviceUrl + '/schema/' + resourceName).then(function success(response) {
            $log.debug("Adding schema to " + resourceName);
            $rootScope.schemata[resourceName] = response.data;
            $log.debug("Added schema to " + resourceName + ": " + JSON.stringify($rootScope.schemata[resourceName]));
        });
    }

    function loadResourceUris(group, resourceName, resourceUri) {
        $http.get(resourceUri).then(function success(response) {
            $log.debug("Adding uri to " + resourceName);
            $rootScope.resources[resourceName] = response.data.uri;
            $rootScope.resourceGroups[group][resourceName] = response.data.uri;
            $log.debug("Added uri to " + resourceName + ": " + $rootScope.resources[resourceName]);
            $rootScope.$broadcast('resourcesChanged');
        });
    }
}

function listServices() {
    var services = [];
    var serviceNames = ["environment", "facility", "product", "inventory", "customer", "order", "sales"];
    for(let name of serviceNames) {
        services.push(window.location.protocol + "//" + window.location.hostname + ":" + 8765 + "/api/" + name);
    }
    return services;
}


export default ['$log', '$http', '$rootScope', StructureService];
