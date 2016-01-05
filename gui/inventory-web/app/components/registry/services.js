'use strict';

var eurekaUrl = "http://" + window.location.hostname + ":" + 8761 + "/eureka/apps/";

angular.module('Registry', [])
    .factory('RegistryService', ['EurekaClient', function (EurekaClient) {
        return {
            /**
             * @return {string}
             */
            LoginUrl: function () {
                return (EurekaClient.getInstanceById('ENVIRONMENT.MOSERP.ORG') + '/login');
            }
        }
    }])
    .factory("EurekaClient", ['$http', '$rootScope', function ($http, $rootScope) {
        $http.get(eurekaUrl, {
            headers: {
                'Accept': 'application/json'
            }
        }).then(function success(response) {
            console.log("Successful retrieved registry ");
            $rootScope.registryContent = [];
            for (let application of response.data.applications.application) {
                $rootScope.registryContent[application.name] = 'http://' + application.instance.hostName + ':' + application.instance.port.$;
                console.log("Application: " + application.name + " url: " + $rootScope.registryContent[application.name]);
            }

        }, function error(response) {
            console.log("Error in retrieved registry: " + response.data);
        });
        return {
            getInstanceById: function (applicationId) {
                return $rootScope.registryContent[applicationId];
            }
        }
    }]);
