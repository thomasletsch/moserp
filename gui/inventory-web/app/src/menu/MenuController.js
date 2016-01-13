function MenuController($rootScope, $scope, $state, $stateParams) {
    $scope.$state = $state;
    $scope.entity = $stateParams.entity;
    $scope.resources = $rootScope.resources;
}

export default ['$rootScope', '$scope', '$state', '$stateParams',
    MenuController
];
