function EntitiesEditController(EntitiesRepository, $log, $rootScope, $scope, $state, $translate, $stateParams, uiGridConstants) {

    $log = $log.getInstance("EntitiesEditController(" + JSON.stringify($stateParams) + ")");
    $log.debug("instanceOf() ");

    $scope.$state = $state;
    $scope.entityName = $stateParams.entityName;
    $scope.entityId = $stateParams.id;
    $scope.resources = $rootScope.resources;

    if ($stateParams.id) {
        EntitiesRepository.find($scope.entityName, $scope.entityId, entityLoaded);
    } else {
        newEntity();
    }

    function newEntity() {
        prepareEditForm({});
    }

    function entityLoaded(entity) {
        console.log("Entity returned: " + JSON.stringify(entity));
        prepareEditForm(entity);
    }

    function prepareEditForm(model) {
        console.log("Schema: " + JSON.stringify($rootScope.schemata[$scope.entityName]));
        $scope.schema = $rootScope.schemata[$scope.entityName];

        if(FORM_FIELDS[$scope.entityName]) {
            $scope.form = FORM_FIELDS[$scope.entityName];
        } else {
            $scope.form = FORM_FIELDS['default'];
        }

        angular.forEach($scope.schema.properties,function(property,key){
            $translate(property.title).then(function(translation){
                $scope.schema.properties[key].title=translation;
            })
        });

        $scope.model = model;
    }
}

var FORM_FIELDS = {defaults: ["*"], users: ["name"], unitOfMeasurements: ["code", "description"], valueLists: ["key", "values"]};

export default [
    'EntitiesRepository', '$log', '$rootScope', '$scope', '$state', '$translate', '$stateParams', 'uiGridConstants',
    EntitiesEditController
];
