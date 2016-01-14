function EntitiesEditController(EntitiesRepository, $log, $rootScope, $scope, $state, $translate, $stateParams, uiGridConstants) {
    $log.debug("EntitiesEditController(" + JSON.stringify($stateParams) + ")");

    $scope.$state = $state;
    $scope.entityName = $stateParams.entityName;
    $scope.entityId = $stateParams.id;
    $scope.resources = $rootScope.resources;
    prepareEditForm();

    if ($stateParams.id) {
        EntitiesRepository.find($scope.entityName, $scope.entityId, entityLoaded);
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
            $translate(property.title).then(function (translation) {
                $scope.schema.properties[key].title = translation;
            })
        });

        $scope.onSubmit = function (form) {
            $log.debug("Submitting...");
            // First we broadcast an event so all fields validate themselves
            $scope.$broadcast('schemaFormValidate');

            // Then we check if the form is valid
            //if (form.$valid) {
            $log.debug("Form valid");
            EntitiesRepository.save($scope.entityName, $scope.entityId, $scope.model, function success() {
                $log.debug("Successful saved");
            });
            //} else {
            //    $log.debug("Form not valid");
            //    $log.debug("Model: " + JSON.stringify($scope.model));
            //}
        }
    }
}

var FORM_FIELDS = {defaults: ["*"], users: ["name"], unitOfMeasurements: ["code", "description"], valueLists: ["key", "values"]};

export default [
    'EntitiesRepository', '$log', '$rootScope', '$scope', '$state', '$translate', '$stateParams', 'uiGridConstants',
    EntitiesEditController
];
