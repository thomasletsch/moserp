package org.moserp.common.rest;

import android.content.Context;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;
import de.greenrobot.event.EventBus;
import org.androidannotations.annotations.*;
import org.androidannotations.annotations.rest.RestService;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.api.rest.RestClientErrorHandling;
import org.androidannotations.api.rest.RestClientRootUrl;
import org.androidannotations.api.rest.RestClientSupport;
import org.moserp.common.events.AppStartedEvent;
import org.moserp.common.events.RegistryLoadedEvent;
import org.moserp.common.events.RestServiceRegistryInitializedEvent;
import org.moserp.common.preferences.BackendPreferences_;
import org.moserp.environment.FacilityResources;
import org.moserp.environment.FacilityRestService;
import org.moserp.inventory.InventoryItemResources;
import org.moserp.inventory.InventoryItemRestService;
import org.moserp.inventory.R;
import org.moserp.product.ProductResources;
import org.moserp.product.ProductRestService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@EBean(scope = EBean.Scope.Singleton)
public class RestServiceRegistry {

    public static final int LOOP_TIMEOUT = 10000;

    @RootContext
    Context context;

    @Bean
    RestErrorHandlerImpl restErrorHandler;

    @Bean
    HalRestTemplate restTemplate;

    @Pref
    BackendPreferences_ backendPreferences;

    @Bean
    BasicAuthorizationInterceptor basicAuthorizationInterceptor;

    @RestService
    FacilityRestService facilityRestService;

    @RestService
    ProductRestService productRestService;

    @RestService
    InventoryItemRestService inventoryRestService;

    @RestService
    EurekaRestService eurekaRestService;

    @RestService
    StructureRestService structureRestService;

    private Map<String, String> resourceUris = new HashMap<String, String>();

    boolean initialized = false;

    public FacilityRestService getFacilityRestService() {
        waitForInitialized();
        return facilityRestService;
    }

    public ProductRestService getProductRestService() {
        waitForInitialized();
        return productRestService;
    }

    public InventoryItemRestService getInventoryRestService() {
        return inventoryRestService;
    }

    public void onEventAsync(AppStartedEvent event) {
        Log.d("RestServiceRegistry", "onEvent() called");
        loadRegistry();
    }

    public void onEventAsync(RegistryLoadedEvent event) {
        adjustRestService(facilityRestService, FacilityResources.RELATIVE);
        adjustRestService(productRestService, ProductResources.RELATIVE);
        adjustRestService(inventoryRestService, InventoryItemResources.RELATIVE);
    }

    public void adjustRestService(Object baseRestService, String resourceName) {
        ((RestClientErrorHandling) baseRestService).setRestErrorHandler(restErrorHandler);
        ((RestClientRootUrl) baseRestService).setRootUrl(queryRegistryForResource(resourceName));
        ((RestClientSupport) baseRestService).setRestTemplate(restTemplate);
        EventBus.getDefault().post(new RestServiceRegistryInitializedEvent());
    }

    public void loadRegistry() {
        Log.d("RestServiceRegistry", "Loading registry");
        String eurekaUrl = backendPreferences.backendUrl().get();
        if (!eurekaUrl.endsWith("/")) {
            eurekaUrl = eurekaUrl + "/";
        }
        eurekaUrl += "eureka";
        try {
            eurekaRestService.setRootUrl(eurekaUrl);
            Map apps = eurekaRestService.getApps();
            Map applications = (Map) apps.get("applications");
            List<Map> application = (List) applications.get("application");
            for (Map map : application) {
                String module = (String) map.get("name");
                Log.d("RestServiceRegistry", "Reading application " + module);
                Map instance = (Map) map.get("instance");
                String structurePage = (String) instance.get("statusPageUrl");
                structureRestService.setRootUrl(structurePage);
                Map structure = structureRestService.getStructure();
                Set<String> structureGroups = structure.keySet();
                for (String structureGroup : structureGroups) {
                    Log.d("RestServiceRegistry", "Reading structure group " + structureGroup);
                    Map structureGroupMap = (Map) structure.get(structureGroup);
                    List<Map> links = (List) structureGroupMap.get("links");
                    for (Map link : links) {
                        String resources = (String) link.get("rel");
                        Map resourceStructure = structureRestService.getResourceStructure(resources);
                        String uri = (String) resourceStructure.get("uri");
                        Log.d("RestServiceRegistry", "Inserting resource uri: " + resources + " - " + uri);
                        resourceUris.put(resources, uri);
                    }
                }
            }
            EventBus.getDefault().post(new RegistryLoadedEvent());
            initialized = true;
        } catch (Exception e) {
            Log.e("RestServiceRegistry", "Could not init Registry!", e);
            showError(e);
        }
    }

    @UiThread
    void showError(Exception e) {
        Toast.makeText(context, R.string.toast_backend_error, Toast.LENGTH_SHORT).show();
        Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }

    private String queryRegistryForResource(String resourceName) {
        String uri = resourceUris.get(resourceName);
        Log.d("RestServiceRegistry", "Query for Resource " + resourceName + ": uri=" + uri);
        return uri;
    }

    private void waitForInitialized() {
        int sleepTime = 200;
        int timeElapsed = 0;
        while (!initialized && !(timeElapsed > LOOP_TIMEOUT)) {
            Log.d("RestServiceRegistry", "waiting...");
            SystemClock.sleep(sleepTime);
            timeElapsed += sleepTime;
        }
    }

}
