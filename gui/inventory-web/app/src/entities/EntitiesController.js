/**
 * Main App Controller for the Angular Material Starter App
 * @constructor
 */
function EntitiesController(StructureService, $log, $rootScope, $scope) {

    $log = $log.getInstance("SessionController");
    $log.debug("instanceOf() ");

    showForm();


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
    'StructureService', '$log', '$rootScope', '$scope',
    EntitiesController
];
