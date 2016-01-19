function EntitiesEditController(EntitiesRepository, $log, $rootScope, $scope, $state, $translate, $stateParams, uiGridConstants) {
    $log.debug("EntitiesEditController(" + JSON.stringify($stateParams) + ")");

    $scope.$state = $state;
    $scope.entityName = $stateParams.entityName;
    $scope.entityId = $stateParams.id;
    $scope.resources = $rootScope.resources;
    prepareEditForm();

    if ($stateParams.id) {
        EntitiesRepository.find($scope.entityName, $scope.entityId, entityLoaded);
    } else {
        $scope.model = {};
    }

    function entityLoaded(entity) {
        $log.debug("Entity returned: " + JSON.stringify(entity));
        $scope.model = entity;
    }

     function prepareEditForm() {
        $log.debug("Schema: " + JSON.stringify($rootScope.schemata[$scope.entityName]));
        $scope.schema = $rootScope.schemata[$scope.entityName];

        if (FORM_FIELDS[$scope.entityName]) {
            $scope.form = FORM_FIELDS[$scope.entityName];
        } else {
            $scope.form = FORM_FIELDS['default'];
        }
        $translate("save").then(function (translation) {
            $scope.form = $scope.form.concat([{type: "submit", title: translation}]);
        });

        angular.forEach($scope.schema.properties, function (property, key) {
            translateProperty(property, key);
        });

        $scope.onSubmit = function (form) {
            $log.debug("Submitting...");
            // First we broadcast an event so all fields validate themselves
            $scope.$broadcast('schemaFormValidate');

            // Then we check if the form is valid
            //if (form.$valid) {
            $log.debug("Form: " + JSON.stringify(form));
            $log.debug("Model: " + JSON.stringify($scope.model));
            EntitiesRepository.save($scope.entityName, $scope.entityId, $scope.model, function success() {
                $log.debug("Successful saved");
                $state.go("entities.list", {entityName: $scope.entityName})
            });
            //} else {
            //    $log.debug("Form not valid");
            //    $log.debug("Model: " + JSON.stringify($scope.model));
            //}
        }
    }
    function translateProperty(property, key) {
        $translate(property.title).then(function (translation) {
            property.title = translation;
        });
        if(property.type == 'array') {
            angular.forEach(property.items.properties, function(innerProperty, innerKey) {
                translateProperty(innerProperty, innerKey);
            })
        }
    }
}

var FORM_FIELDS = {defaults: ["*"], users: ["name"], unitOfMeasurements: ["code", "description"], valueLists: ["key", "values"]};

export default [
    'EntitiesRepository', '$log', '$rootScope', '$scope', '$state', '$translate', '$stateParams', 'uiGridConstants',
    EntitiesEditController
];
