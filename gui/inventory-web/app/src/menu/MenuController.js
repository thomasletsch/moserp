function MenuController($rootScope, $scope, $state, $translate, $stateParams) {
    $scope.$state = $state;
    $scope.entity = $stateParams.entity;

    recreateMenu();

    $rootScope.$on('resourcesChanged', function (newVal, oldVal, scope) {
        recreateMenu();
    });

    function recreateMenu() {
        $scope.menu = [];
        for (var groupName in $rootScope.resourceGroups) {
            console.log("Menu group: " + groupName);
            var menuEntry = {};
            menuEntry.label = groupName;
            menuEntry.items = [];
            for (var resourceName in $rootScope.resourceGroups[groupName]) {
                console.log("Menu item: " + resourceName);
                var menuItem = {};
                menuItem.key = resourceName;
                menuItem.label = resourceName;
                menuEntry.items.push(menuItem);
            }
            $scope.menu.push(menuEntry);
        }
        angular.forEach($scope.menu, function (menuEntry) {
            $translate(menuEntry.label).then(function (translation) {
                menuEntry.label = translation;
            });
            angular.forEach(menuEntry.items, function (menuItem) {
                $translate(menuItem.label).then(function (translation) {
                    menuItem.label = translation;
                });
            });
        });
        console.log("Menu: " + JSON.stringify($scope.menu));
    }
}


export default ['$rootScope', '$scope', '$state', '$translate', '$stateParams',
    MenuController
];
