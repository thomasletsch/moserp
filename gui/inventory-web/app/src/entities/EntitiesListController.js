var IGNORE_PROPERTIES = ["links", "displayName", "version"];
function EntitiesListController($log, $rootScope, $scope, $state, i18nService, $translate, $stateParams, EntitiesRepository) {

    $log.debug("EntitiesListController(" + JSON.stringify($stateParams) + ")");

    $scope.$state = $state;
    $scope.entityName = $stateParams.entityName;
    $scope.resources = $rootScope.resources;
    i18nService.setCurrentLang($translate.proposedLanguage());

    $scope.gridOptions = {
        enableSorting: true,
        enableFullRowSelection: true,
        multiSelect: false
    };

    $scope.gridOptions.onRegisterApi = function (gridApi) {
        $scope.gridApi = gridApi;
        $log.debug("Registered gridApi");
        gridApi.selection.on.rowSelectionChanged($scope, function (row) {
            var selection = gridApi.selection.getSelectedRows();
            if (selection.length > 0) {
                $scope.selectedObject = selection[0];
            } else {
                $scope.selectedObject = null;
            }
            $log.debug("Selected object: " + JSON.stringify($scope.selectedObject));
        });
    };

    $scope.createNew = function () {
        $state.go("entities.edit", {entityName: $scope.entityName});
    };

    $scope.edit = function () {
        if ($scope.selectedObject) {
            $log.debug("Edit: " + $scope.entityName + " - " + $scope.selectedObject.id);
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
        $log.debug("Entites returned: " + JSON.stringify(entities));
        $scope.entities = entities;
        $scope.gridOptions.columnDefs = createColumns($scope.entityName);
        $scope.gridOptions.data = entities;
    }

    function createColumns(entityName) {
        var columns = [];
        for (let propertyName in $rootScope.schemata[entityName].properties) {
            var column = {};
            column.displayName = propertyName;
            column.field = propertyName;
            column.headerCellFilter = 'translate';
            if (IGNORE_PROPERTIES.indexOf(propertyName) < 0) {
                columns.push(column);
            }
        }
        return columns;
    }

}


export default [
   '$log', '$rootScope', '$scope', '$state', 'i18nService', '$translate', '$stateParams',  'EntitiesRepository',
    EntitiesListController
];
