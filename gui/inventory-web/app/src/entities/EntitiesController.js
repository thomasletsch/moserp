function EntitiesController(EntitiesRepository, $log, $rootScope, $scope, $state, $stateParams) {

    $log = $log.getInstance("SessionController");
    $log.debug("instanceOf() ");

    //showForm();

    $scope.$state = $state;

    $scope.entity = $stateParams.entity;

    $scope.resources = $rootScope.resources;

    if($stateParams.entity) {
        if($stateParams.id) {

        } else {
            EntitiesRepository.loadAll($stateParams.entity, function success(entities) {
                console.log("Entites returned: " + JSON.stringify(entities));
                $scope.entities = entities;
            });
        }

    }


    function showForm() {
        console.log("Schema: " + JSON.stringify($rootScope.schemata['users']));
        $scope.schema = $rootScope.schemata['users'];

        $scope.form = [
            "*",
            {
                type: "submit",
                title: "Save"
            }
        ];

        $scope.model = {};
    }

}

export default [
    'EntitiesRepository', '$log', '$rootScope', '$scope', '$state', '$stateParams',
    EntitiesController
];
