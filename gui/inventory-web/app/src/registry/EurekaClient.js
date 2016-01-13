var eurekaUrl = "http://" + window.location.hostname + ":" + 8761 + "/eureka/apps/";

function EurekaClient($http, $rootScope) {
    $http.get(eurekaUrl, {
        headers: {
            'Accept': 'application/json'
        }
    }).then(function success(response) {
        console.log("Successful retrieved registry ");
        var applications = response.data.applications.application;
        console.log("Successful retrieved registry " + JSON.stringify(applications));
        $rootScope.registryContent = [];
        if (applications instanceof Array) {
            for (let application of applications) {
                $rootScope.registryContent[application.name] = 'http://' + application.instance.hostName + ':' + application.instance.port.$;
                console.log("Application: " + application.name + " url: " + $rootScope.registryContent[application.name]);
            }
        } else {
            $rootScope.registryContent[applications.name] = 'http://' + applications.instance.hostName + ':' + applications.instance.port.$;
            console.log("Application: " + applications.name + " url: " + $rootScope.registryContent[applications.name]);
        }

    }, function error(response) {
        console.log("Error in retrieved registry: " + response.data);
    });
    return {
        getInstanceById: function (applicationId) {
            return $rootScope.registryContent[applicationId];
        },
        getInstancesWithPostfix: function (postfix) {
            var instances = [];
            for (let application in $rootScope.registryContent) {
                if (application.endsWith(postfix)) {
                    console.log("Adding application " + application);
                    instances.push($rootScope.registryContent[application]);
                }
            }
            return instances;
        }
    }
}

export default ['$http', '$rootScope', EurekaClient];
