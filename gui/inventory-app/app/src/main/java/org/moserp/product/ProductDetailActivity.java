package org.moserp.product;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.moserp.common.ToolbarActivity;
import org.moserp.common.cache.CacheContainer;
import org.moserp.common.databinding.OnItemClickListener;
import org.moserp.common.rest.RestServiceRegistry;
import org.moserp.common.rest.RestUri;
import org.moserp.inventory.*;
import org.moserp.inventory.databinding.ActivityProductDetailBinding;

import java.util.List;

@EActivity
public class ProductDetailActivity extends ToolbarActivity {
    private static final String EXTRA_PRODUCT_URI = "EXTRA_PRODUCT_URI";
    private ActivityProductDetailBinding binding;

    @Bean
    RestServiceRegistry restServiceRegistry;

    @Bean
    CacheContainer cacheContainer;

    private InventoryItem selectedInventoryItem = null;

    public static Intent buildIntent(final Context context, String productUri) {
        Intent intent = new Intent(context, ProductDetailActivity_.class);
        intent.putExtra(EXTRA_PRODUCT_URI, productUri);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail);
        initRecyclerView(binding.inventoryItemsList);
        initToolbar();
        setupActionButtons();
    }

    private void setupActionButtons() {
        ImageButton incomingDeliveryButton = (ImageButton) findViewById(R.id.incomingDeliveryButton);
        incomingDeliveryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(IncomingDeliveryActivity.buildIntent(getApplicationContext(), getSelectedFacilityUri(), getSelectedProductUri()));
            }
        });
        ImageButton inventoryTransferButton = (ImageButton) findViewById(R.id.inventoryTransferButton);
        inventoryTransferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(InventoryTransferActivity.buildIntent(getApplicationContext(), getSelectedFacilityUri(), getSelectedProductUri()));
            }
        });
        ImageButton outgoingDeliveryButton = (ImageButton) findViewById(R.id.outgoingDeliveryButton);
        outgoingDeliveryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(OutgoingDeliveryActivity.buildIntent(getApplicationContext(), getSelectedFacilityUri(), getSelectedProductUri()));
            }
        });
    }

    private String getSelectedFacilityUri() {
        if (selectedInventoryItem == null) {
            return null;
        }
        return selectedInventoryItem.getFacility();
    }

    private String getSelectedProductUri() {
        if (selectedInventoryItem == null) {
            return null;
        }
        return selectedInventoryItem.getProductInstance().getProduct();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDataFromProductUri();
    }

    void loadDataFromProductUri() {
        final String productUri = getIntent().getStringExtra(EXTRA_PRODUCT_URI);
        Log.d("ProductDetailActivity", "onCreate Intent: " + getIntent().getAction() + " productUri: " + productUri);
        if (productUri == null) {
            return;
        }
        RestUri productRestUri = new RestUri(productUri);
        queryProduct(productRestUri);
        queryTotalQuantityOnHand(productRestUri);
        queryInventoryItems(productRestUri);
    }

    @Background
    void queryInventoryItems(RestUri productUri) {
        InventoryItemRestService inventoryItemRestService = restServiceRegistry.getInventoryRestService();
        List<InventoryItem> inventoryItems = inventoryItemRestService.findInventoryItemsByProduct(productUri.getId()).getList();
        updateProductDetails(inventoryItems);
    }

    @Background
    void queryTotalQuantityOnHand(RestUri productUri) {
        ProductRestService productRestService = restServiceRegistry.getProductRestService();
        String totalQuantityOnHand = productRestService.getQuantityOnHand(productUri.getId());
        updateQuantityOnHand(totalQuantityOnHand);
    }

    @Background
    void queryProduct(RestUri productUri) {
        ProductRestService productRestService = restServiceRegistry.getProductRestService();
        Product product = productRestService.getById(productUri.getId());
        binding.setProduct(product);
        updateToolbar(product);
    }

    @UiThread
    void updateProductDetails(List<InventoryItem> inventoryItems) {
        if (binding.inventoryItemsList.getAdapter() == null) {
            InventoryItemResourceAdapter adapter = new InventoryItemResourceAdapter(inventoryItems, R.layout.inventory_item_per_product_list, cacheContainer);
            binding.inventoryItemsList.setAdapter(adapter);
        }
        InventoryItemResourceAdapter adapter = (InventoryItemResourceAdapter) binding.inventoryItemsList.getAdapter();
        adapter.setOnItemClickListener(new OnItemClickListener<InventoryItem>() {
            @Override
            public void onItemClick(int position, InventoryItem inventoryItem) {
                selectedInventoryItem = inventoryItem;
            }
        });
        adapter.setResources(inventoryItems);
    }

    @UiThread
    void updateQuantityOnHand(String totalQuantityOnHand) {
        binding.setTotalQuantityOnHand(totalQuantityOnHand);
    }

    @UiThread
    void updateToolbar(Product product) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        toolbar.setTitle(product.getName());
    }

}
