var IGNORE_PROPERTIES = ["links", "displayName"];
function EntitiesListController(EntitiesRepository, $log, $rootScope, $scope, $state, $location, $stateParams, uiGridConstants) {

    $log = $log.getInstance("EntitiesListController(" + JSON.stringify($stateParams) + ")");
    $log.debug("instanceOf() ");

    $scope.$state = $state;
    $scope.entityName = $stateParams.entityName;
    $scope.resources = $rootScope.resources;

    $scope.gridOptions = {
        enableSorting: true,
        enableFullRowSelection: true,
        multiSelect: false
    };

    $scope.gridOptions.onRegisterApi = function (gridApi) {
        $scope.gridApi = gridApi;
        console.log("Registered gridApi");
        gridApi.selection.on.rowSelectionChanged($scope, function (row) {
            var selection = gridApi.selection.getSelectedRows();
            if (selection.length > 0) {
                $scope.selectedObject = selection[0];
            } else {
                $scope.selectedObject = null;
            }
            console.log("Selected object: " + JSON.stringify($scope.selectedObject));
        });
    };

    $scope.createNew = function () {
        $state.go("entities.edit", {entityName: $scope.entityName});
    };

    $scope.edit = function () {
        if ($scope.selectedObject) {
            console.log("Edit: " + $scope.entityName + " - " + $scope.selectedObject.id);
            $state.go("entities.edit", {entityName: $scope.entityName, id: $scope.selectedObject.id});
        }
    };

    var paginationOptions = {
        pageNumber: 1,
        pageSize: 25,
        sort: null
    };


    EntitiesRepository.loadAll($scope.entityName, entitiesLoaded);


    function entitiesLoaded(entities) {
        console.log("Entites returned: " + JSON.stringify(entities));
        $scope.entities = entities;
        $scope.gridOptions.columnDefs = createColumnDefs($scope.entityName);
        $scope.gridOptions.data = entities;
    }

    function createColumnDefs(entityName) {
        var columnDefs = [];
        for (let propertyName in $rootScope.schemata[entityName].properties) {
            var column = {};
            column.name = propertyName;
            if (IGNORE_PROPERTIES.indexOf(entityName) < 0) {
                columnDefs.push(column);
            }
        }
        return columnDefs;
    }

}


export default [
    'EntitiesRepository', '$log', '$rootScope', '$scope', '$state', '$location', '$stateParams', 'uiGridConstants',
    EntitiesListController
];
