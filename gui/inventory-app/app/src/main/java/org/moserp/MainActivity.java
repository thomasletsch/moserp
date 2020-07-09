package org.moserp;

import android.app.SearchManager;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import de.greenrobot.event.EventBus;
import org.androidannotations.annotations.*;
import org.moserp.common.AnyOrientationCaptureActivity;
import org.moserp.common.ToolbarActivity;
import org.moserp.common.cache.CacheContainer;
import org.moserp.common.databinding.OnItemClickListener;
import org.moserp.common.databinding.RecycleViewResourceAdapter;
import org.moserp.common.events.AppStartedEvent;
import org.moserp.common.events.RestServiceRegistryInitializedEvent;
import org.moserp.common.preferences.PreferencesActivity_;
import org.moserp.common.rest.RestServiceRegistry;
import org.moserp.environment.Facility;
import org.moserp.environment.FacilityRestService;
import org.moserp.inventory.IncomingDeliveryActivity;
import org.moserp.inventory.InventoryTransferActivity;
import org.moserp.inventory.OutgoingDeliveryActivity;
import org.moserp.inventory.R;
import org.moserp.inventory.databinding.IncludeFacilityViewBinding;
import org.moserp.product.Product;
import org.moserp.product.ProductListActivity;
import org.moserp.product.ProductRestService;

import java.util.List;

@EActivity
@OptionsMenu(R.menu.main_action_bar)
public class MainActivity extends ToolbarActivity {

    @ViewById
    DrawerLayout drawerLayout;

    @SystemService
    SearchManager searchManager;

    @Bean
    RestServiceRegistry restServiceRegistry;

    @Bean
    CacheContainer cacheContainer;

    private Facility selectedFacility = null;
    private IncludeFacilityViewBinding includeFacilityViewBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.activity_main);
        View contentView = findViewById(R.id.main_content);
        includeFacilityViewBinding = DataBindingUtil.findBinding(contentView);
        initRecyclerView(includeFacilityViewBinding.facilitiesList);
        initToolbar();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_black_18dp);
        }
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupNavigationView(navigationView);
        }
        setupActionButtons();
    }

    private void setupActionButtons() {
        ImageButton incomingDeliveryButton = (ImageButton) findViewById(R.id.incomingDeliveryButton);
        incomingDeliveryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(IncomingDeliveryActivity.buildIntent(getApplicationContext(), getSelectedFacilityUri(), null));
            }
        });
        ImageButton inventoryTransferButton = (ImageButton) findViewById(R.id.inventoryTransferButton);
        inventoryTransferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(InventoryTransferActivity.buildIntent(getApplicationContext(), getSelectedFacilityUri(), null));
            }
        });
        ImageButton outgoingDeliveryButton = (ImageButton) findViewById(R.id.outgoingDeliveryButton);
        outgoingDeliveryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(OutgoingDeliveryActivity.buildIntent(getApplicationContext(), getSelectedFacilityUri(), null));
            }
        });
    }

    private String getSelectedFacilityUri() {
        if (selectedFacility == null) {
            return null;
        }
        return selectedFacility.getSelf().getHref();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        EventBus.getDefault().register(restServiceRegistry);
        EventBus.getDefault().post(new AppStartedEvent());
//        if (cacheContainer.get(Facility.class) != null) {
//            updateFacilityList();
//        }
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        EventBus.getDefault().unregister(restServiceRegistry);
        super.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Log.d("MainActivity", "Cancelled scan");
                Toast.makeText(this.getApplicationContext(), R.string.toast_scan_cancelled, Toast.LENGTH_LONG).show();
            } else {
                Log.d("MainActivity", "Scanned: " + result.getContents());
                startActivity(ProductListActivity.buildIntent(getApplicationContext(), result.getContents()));
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void setupNavigationView(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        int id = menuItem.getItemId();
                        switch (id) {
                            case R.id.action_settings:
                                startActivity(new Intent(getApplicationContext(), PreferencesActivity_.class));
                                return true;
                            //missing default case
                            default:
                                // add default case
                                 break;

                        }
                        return false;
                    }
                });
    }

    @OptionsItem(R.id.action_search)
    void menuActionSearch(MenuItem searchItem) {
        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        }
    }

    @OptionsItem(R.id.action_scan)
    void scanSelected() {
        new IntentIntegrator(this).setOrientationLocked(false).setCaptureActivity(AnyOrientationCaptureActivity.class).initiateScan();
    }

    @OptionsItem(android.R.id.home)
    void homeSelected() {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void onEventAsync(RestServiceRegistryInitializedEvent event) {
        queryFacilities();
        queryProducts();
    }

    void queryFacilities() {
        FacilityRestService facilityRestService = restServiceRegistry.getFacilityRestService();
        List<Facility> facilities = facilityRestService.getFacilities().getList();
        Log.d("MainActivity", "Facilities: " + facilities);
        if (facilities == null) {
            return;
        }
        cacheContainer.cache(Facility.class, facilities);
        updateFacilityList();
    }

    void queryProducts() {
        ProductRestService productRestService = restServiceRegistry.getProductRestService();
        List<Product> products = productRestService.get().getList();
        cacheContainer.cache(Product.class, products);
    }

    @UiThread
    void updateFacilityList() {
        List<Facility> facilities = cacheContainer.get(Facility.class).getAll();
        if (includeFacilityViewBinding.facilitiesList.getAdapter() == null) {
            RecycleViewResourceAdapter<Facility> adapter = new RecycleViewResourceAdapter<>(facilities, R.layout.facility_list);
            adapter.setOnItemClickListener(new OnItemClickListener<Facility>() {
                @Override
                public void onItemClick(int position, Facility facility) {
                    selectedFacility = facility;
                }
            });
            includeFacilityViewBinding.facilitiesList.setAdapter(adapter);
        }
        RecycleViewResourceAdapter<Facility> adapter = (RecycleViewResourceAdapter) includeFacilityViewBinding.facilitiesList.getAdapter();
        adapter.setResources(facilities);
    }
}
