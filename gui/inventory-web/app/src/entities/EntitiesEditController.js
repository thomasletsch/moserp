function EntitiesEditController(EntitiesRepository, $log, $rootScope, $scope, $state, $translate, $stateParams, uiGridConstants) {

    $log = $log.getInstance("EntitiesEditController(" + JSON.stringify($stateParams) + ")");
    $log.debug("instanceOf() ");

    $scope.$state = $state;
    $scope.entityName = $stateParams.entityName;
    $scope.entityId = $stateParams.id;
    $scope.resources = $rootScope.resources;
    prepareEditForm();

    if ($stateParams.id) {
        EntitiesRepository.find($scope.entityName, $scope.entityId, entityLoaded);
    }

    function entityLoaded(entity) {
        console.log("Entity returned: " + JSON.stringify(entity));
        $scope.model = entity;
    }

     function prepareEditForm() {
        console.log("Schema: " + JSON.stringify($rootScope.schemata[$scope.entityName]));
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
            console.log("Submitting...");
            // First we broadcast an event so all fields validate themselves
            $scope.$broadcast('schemaFormValidate');

            // Then we check if the form is valid
            //if (form.$valid) {
            console.log("Form valid");
            EntitiesRepository.save($scope.entityName, $scope.entityId, $scope.model, function success() {
                console.log("Successful saved");
            });
            //} else {
            //    console.log("Form not valid");
            //    console.log("Model: " + JSON.stringify($scope.model));
            //}
        }
    }
}

var FORM_FIELDS = {defaults: ["*"], users: ["name"], unitOfMeasurements: ["code", "description"], valueLists: ["key", "values"]};

export default [
    'EntitiesRepository', '$log', '$rootScope', '$scope', '$state', '$translate', '$stateParams', 'uiGridConstants',
    EntitiesEditController
];
