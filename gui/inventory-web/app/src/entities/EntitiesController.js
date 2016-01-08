/**
 * Main App Controller for the Angular Material Starter App
 * @param $scope
 * @param $mdSidenav
 * @param avatarsService
 * @constructor
 */
function EntitiesController( entitiesService, $mdSidenav, $mdBottomSheet, $log ) {

    $log = $log.getInstance( "SessionController" );
    $log.debug( "instanceOf() ");

    var self = this;

    self.selected     = null;
    self.entities        = [ ];
    self.selectEntity   = selectEntity;
    self.toggleList   = toggleEntitiesList;
    self.share        = share;

    // Load all registered entities

    entitiesService
        .loadAll()
        .then( function( entities ) {
            self.entities    = [].concat(entities);
            self.selected = entities[0];
        });

    // *********************************
    // Internal methods
    // *********************************

    /**
     * Hide or Show the 'left' sideNav area
     */
    function toggleEntitiesList() {
        $log.debug( "toggleEntitiesList() ");
        $mdSidenav('left').toggle();
    }

    /**
     * Select the current avatars
     * @param menuId
     */
    function selectEntity ( entity ) {
        $log.debug( "selectEntity( {name} ) ", entity);

        self.selected = angular.isNumber(entity) ? $scope.entities[entity] : entity;
        self.toggleList();
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

    /**
     * Show the bottom sheet
     */
    function share($event) {
        $log.debug( "contactEntity()");

        var entity = self.selected;

        $mdBottomSheet.show({
            parent: angular.element(document.getElementById('content')),
            templateUrl: '/src/entities/view/contactSheet.html',
            controller: [ '$mdBottomSheet', '$log', EntitySheetController],
            controllerAs: "vm",
            bindToController : true,
            targetEvent: $event
        }).then(function(clickedItem) {
            $log.debug( clickedItem.name + ' clicked!');
        });

        /**
         * Bottom Sheet controller for the Avatar Actions
         */
        function EntitySheetController( $mdBottomSheet, $log ) {

            $log = $log.getInstance( "EntitySheetController" );
            $log.debug( "instanceOf() ");

            this.entity = entity;
            this.items = [
                { name: 'Phone'       , icon: 'phone'       , icon_url: 'assets/svg/phone.svg'},
                { name: 'Twitter'     , icon: 'twitter'     , icon_url: 'assets/svg/twitter.svg'},
                { name: 'Google+'     , icon: 'google_plus' , icon_url: 'assets/svg/google_plus.svg'},
                { name: 'Hangout'     , icon: 'hangouts'    , icon_url: 'assets/svg/hangouts.svg'}
            ];
            this.performAction = function(action) {
                $log.debug( "makeContactWith( {name} )", action);
                $mdBottomSheet.hide(action);
            };

        }
    }
}

export default [
    'entitiesService', '$mdSidenav', '$mdBottomSheet', '$log',
    EntitiesController
];
