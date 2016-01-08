/**
 * Entities DataService
 * Uses embedded, hard-coded data model; acts asynchronously to simulate
 * remote data service call(s).
 *
 * @returns {{loadAll: Function}}
 * @constructor
 */
function EntitiesDataservice($q, $log, StructureService) {
    $log = $log.getInstance( "EntitiesDataservice" );
    $log.debug( "instanceOf() ");

    // Promise-based API
    return {
        loadAll : function() {
            $log.debug("loadAll()");

            // Simulate async nature of real remote calls
            return $q.when(StructureService.listResources());
        }
    };
}

export default [ '$q', '$log','StructureService', EntitiesDataservice ];
