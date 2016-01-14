function StructureService(EurekaClient, $http, $rootScope) {
    return {
        loadResources: function () {
            $rootScope.resources = {};
            $rootScope.schemata = {};
            $rootScope.resourceGroups = {};
            var allServices = EurekaClient.getInstancesWithPostfix("MOSERP.ORG");
            for (let serviceUrl of allServices) {
                console.log("URL " + serviceUrl);
                $http.get(serviceUrl + '/structure').then(function success(response) {
                    for (let group in response.data) {
                        console.log("Group: " + group);
                        $rootScope.resourceGroups[group] = {};
                        for (let link of response.data[group].links) {
                            console.log("Link: " + JSON.stringify(link));
                            var resourceUri = link['href'];
                            var resourceName = link['rel'];
                            loadSchema($http, $rootScope, serviceUrl, resourceName);
                            loadResourceUris($http, $rootScope, group, resourceName, resourceUri);
                        }
                    }
                });
            }
        },
        listResources: function () {
            var resources = [];
            for (let resourceName in $rootScope.resources) {
                var resource = {};
                resource.name = resourceName;
                resource.uri = $rootScope.resources[resourceName];
                resources.push(resource);
            }
            console.log("listResources: " + resources);
            return resources;
        },
        getUriForResource: function (resourceId) {
            return $rootScope.resources[resourceId];
        }
    }
};

function loadSchema($http, $rootScope, serviceUrl, resourceName) {
    $http.get(serviceUrl + '/schema/' + resourceName).then(function success(response) {
        console.log("Adding schema to " + resourceName);
        $rootScope.schemata[resourceName] = response.data;
        console.log("Added schema to " + resourceName + ": " + JSON.stringify($rootScope.schemata[resourceName]));
    });
}

function loadResourceUris($http, $rootScope, group, resourceName, resourceUri) {
    $http.get(resourceUri).then(function success(response) {
        console.log("Adding uri to " + resourceName);
        $rootScope.resources[resourceName] = response.data.uri;
        $rootScope.resourceGroups[group][resourceName] = response.data.uri;
        console.log("Added uri to " + resourceName + ": " + $rootScope.resources[resourceName]);
        $rootScope.$broadcast('resourcesChanged');
    });
}

export default ['EurekaClient', '$http', '$rootScope', StructureService];
