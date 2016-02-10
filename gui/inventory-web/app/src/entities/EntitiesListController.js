var IGNORE_PROPERTIES = ["links", "displayName", "version"];
var CUSTOM_ORDER = {"id": 100, "createdDate": -100, "createdBy": -101, "lastModifiedDate": -102, "lastModifiedBy": -103};

function EntitiesListController($log, $rootScope, $scope, $state, i18nService, $translate, $stateParams, EntitiesRepository) {

    $log.debug("EntitiesListController(" + JSON.stringify($stateParams) + ")");

    $scope.$state = $state;
    $scope.entityName = $stateParams.entityName;
    $scope.resources = $rootScope.resources;
    i18nService.setCurrentLang($translate.proposedLanguage());

    $scope.gridOptions = {
        enableSorting: true,
        enableColumnResizing: true,
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

    $scope.gridOptions.columnDefs = createColumns($scope.entityName);

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
        $scope.gridOptions.data = entities;
    }

    function createColumns(entityName) {
        var columns = [];
        for (let propertyName in $rootScope.schemata[entityName].properties) {
            var column = {};
            var property = $rootScope.schemata[entityName].properties[propertyName];
            column.displayName = property.title;
            column.field = propertyName;
            column.headerCellFilter = 'translate';
            column.width = "*";
            if (property.type === 'array') {
                column.cellTemplate = '<div class="ui-grid-cell-contents" title="TOOLTIP">{{grid.appScope.printArray(grid, row, col)}}</div>';
            }
            if (IGNORE_PROPERTIES.indexOf(propertyName) < 0) {
                columns.push(column);
            }
        }
        columns.sort(columnsCompareFunction);
        columns.reverse();
        return columns;
    }

    $scope.printArray = function (grid, row, column) {
        //$log.debug("grid: " + grid + " row: " + row + " column: " + column);
        var value = row.entity[column.field];
        var result = "";
        //$log.debug("Field: " + JSON.stringify(value));
        angular.forEach(value, function(entry, index) {
            result += entry["displayName"] + ", ";
        });
        return result;
    };

    function columnsCompareFunction(a, b) {
        var orderA = 0;
        var orderB = 0;
        var fieldA = a.field;
        var fieldB = b.field;
        if (CUSTOM_ORDER[fieldA]) {
            orderA = CUSTOM_ORDER[fieldA];
        }
        if (CUSTOM_ORDER[fieldB]) {
            orderB = CUSTOM_ORDER[fieldB];
        }
        if ((orderA - orderB) != 0) {
            $log.debug("fieldA: " + fieldA + " fieldB: " + fieldB + " orderA: " + orderA + " orderB: " + orderB)
            return orderA - orderB;
        } else {
            $log.debug("fieldA: " + fieldA + " fieldB: " + fieldB + " compare: " + fieldA.localeCompare(fieldB))
            return fieldA.localeCompare(fieldB);
        }
    }
}


export default [
    '$log', '$rootScope', '$scope', '$state', 'i18nService', '$translate', '$stateParams', 'EntitiesRepository',
    EntitiesListController
];
