var FORM_FIELDS = {
    default: ["*"],
    users: ["name"],
    unitOfMeasurements: ["code", "description"],
    valueLists: ["key", {"key": "values", "add": "new", "items": ["values[].value"]}],
    products: ["id", "name", "externalId", "validFrom", "ean", "description", {"key": "attributes", "add": "new", "items": ["attributes[].code", "attributes[].name", "attributes[].description"]}]
};



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
        $scope.schema = $scope.preparedSchema;
        $scope.form = $scope.preparedForm;
        $scope.model = {};
    }

    function entityLoaded(entity) {
        $log.debug("Entity " + (typeof entity) + " returned: " + JSON.stringify(entity));
        $scope.schema = $scope.preparedSchema;
        $scope.form = $scope.preparedForm;
        $scope.model = entity;
        $log.debug("Entity array: " + Object.prototype.toString.call(entity.values));
    }

    function prepareEditForm() {
        $log.debug("Schema: " + JSON.stringify($rootScope.schemata[$scope.entityName]));
        $scope.preparedSchema = $rootScope.schemata[$scope.entityName];
        $scope.preparedForm = {};

        if (FORM_FIELDS[$scope.entityName]) {
            $scope.preparedForm = FORM_FIELDS[$scope.entityName];
        } else {
            $scope.preparedForm = FORM_FIELDS['default'];
        }
        $scope.preparedForm = $scope.preparedForm.concat({
            type: "actions",
            items: [{type: "submit", style: 'btn-success', title: "save"},
                {type: 'button', style: 'btn-info', title: 'Cancel', onClick: "cancel()"}]
        });

        angular.forEach($scope.preparedForm, function (formEntry, index) {
            translateTitle(formEntry);
        });

        angular.forEach($scope.preparedSchema.properties, function (property, key) {
            translateTitle(property);
        });

        $scope.cancel = function(form, model) {
            $log.debug("Cancel...");
            window.history.back();
        };

        $scope.onSubmit = function (form, model) {
            $log.debug("Submitting...");
            // First we broadcast an event so all fields validate themselves
            $scope.$broadcast('schemaFormValidate');

            // Then we check if the form is valid
            //if (form.$valid) {
            $log.debug("Form: " + JSON.stringify(form));
            $log.debug("Model: " + JSON.stringify(model, undefined, 2));
            EntitiesRepository.save($scope.entityName, $scope.entityId, model, function success() {
                $log.debug("Successful saved");
                $state.go("entities.list", {entityName: $scope.entityName})
            });
            //} else {
            //    $log.debug("Form not valid");
            //    $log.debug("Model: " + JSON.stringify($scope.model));
            //}
        }
    }

    function translateTitle(property) {
        $translate(property.title).then(function (translation) {
            property.title = translation;
        });
        if (property.type == 'array') {
            $translate(property.items.title).then(function (translation) {
                property.items.title = translation;
            });
            angular.forEach(property.items.properties, function (innerProperty, innerKey) {
                translateTitle(innerProperty);
            })
        }
    }
}


export default [
    'EntitiesRepository', '$log', '$rootScope', '$scope', '$state', '$translate', '$stateParams', 'uiGridConstants',
    EntitiesEditController
];
