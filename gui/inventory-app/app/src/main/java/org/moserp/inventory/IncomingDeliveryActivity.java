package org.moserp.inventory;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.rest.RestService;
import org.moserp.common.ToolbarActivity;
import org.moserp.common.cache.CacheContainer;
import org.moserp.common.rest.RestServiceRegistry;
import org.moserp.environment.Facility;
import org.moserp.inventory.databinding.ActivityIncomingDeliveryBinding;
import org.moserp.product.Product;
import org.springframework.http.ResponseEntity;

@EActivity
public class IncomingDeliveryActivity extends ToolbarActivity {
    private static final String EXTRA_PRODUCT_URI = "EXTRA_PRODUCT_URI";
    private static final String EXTRA_FACILITY_URI = "EXTRA_FACILITY_URI";

    private ActivityIncomingDeliveryBinding binding;

    @RestService
    IncomingDeliveryRestService incomingDeliveryRestService;

    @Bean
    RestServiceRegistry restServiceRegistry;

    @Bean
    CacheContainer cacheContainer;

    public static Intent buildIntent(final Context context, String facilityUri, String productUri) {
        Intent intent = new Intent(context, IncomingDeliveryActivity_.class);
        intent.putExtra(EXTRA_FACILITY_URI, facilityUri);
        intent.putExtra(EXTRA_PRODUCT_URI, productUri);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBindingAndData();
        initToolbar();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @AfterInject
    void adjustRestService() {
        restServiceRegistry.adjustRestService(incomingDeliveryRestService, "MOS_ERP_INVENTORY_MODULE");
    }

    private void initBindingAndData() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_incoming_delivery);
        binding.setIncomingDeliveryViewModel(new IncomingDeliveryViewModel());
        binding.setSaveClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveClicked();
            }
        });
        binding.setCancelClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        FacilitySpinnerBinding facilitySpinnerBinding = new FacilitySpinnerBinding(binding.facilitiesSpinner, binding.getIncomingDeliveryViewModel().getToFacilityBinding(), cacheContainer.get(Facility.class));
        facilitySpinnerBinding.bind(this, getIntent().getStringExtra(EXTRA_FACILITY_URI));
        ProductSpinnerBinding productSpinnerBinding = new ProductSpinnerBinding(binding.productsSpinner, binding.getIncomingDeliveryViewModel().getProductBinding(), cacheContainer.get(Product.class));
        productSpinnerBinding.bind(this, getIntent().getStringExtra(EXTRA_PRODUCT_URI));
    }

    @Background
    void saveClicked() {
        Log.v("IncomingDelivery", "Save called. " + binding.getIncomingDeliveryViewModel());
        ResponseEntity<IncomingDelivery> re = incomingDeliveryRestService.post(binding.getIncomingDeliveryViewModel().toIncomingDelivery());
        if (re != null) {
            Log.v("IncomingDelivery", "IncomingDelivery saved. URI: " + re.getHeaders().getLocation());
            returnToParentActivity();
        }
    }

    @UiThread
    void returnToParentActivity() {
        onBackPressed();
    }

}
