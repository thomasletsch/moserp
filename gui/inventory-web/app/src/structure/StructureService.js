function StructureService(EurekaClient, $http, $rootScope) {
    return {
        loadResources: function () {
            $rootScope.resources = {};
            $rootScope.schemata = {};
            var allServices = EurekaClient.getInstancesWithPostfix("MOSERP.ORG");
            for (let serviceUrl of allServices) {
                console.log("URL " + serviceUrl);
                $http.get(serviceUrl + '/structure').then(function success(response) {
                    for (let group in response.data) {
                        console.log("Group: " + group);
                        $rootScope.resourceGroups = {};
                        $rootScope.resourceGroups[group] = {};
                        for (let link of response.data[group].links) {
                            console.log("Link: " + JSON.stringify(link));
                            var resourceUri = link['href'];
                            var resourceName = link['rel'];
                            $rootScope.resources[resourceName] = resourceUri;
                            $rootScope.resourceGroups[group][resourceName] = resourceUri;
                            loadSchema($http, $rootScope, serviceUrl, resourceName);
                        }
                    }
                });
            }
        },
        listResources: function () {
            var resources = [];
            for (let singleResource in $rootScope.resources) {
                var resource = {};
                resource.name = singleResource;
                resource.uri = $rootScope.resources[singleResource];
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

export default ['EurekaClient', '$http', '$rootScope', StructureService];
