package org.moserp.inventory;

import android.app.SearchManager;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.rest.RestService;
import org.moserp.common.ToolbarActivity;
import org.moserp.common.databinding.OnItemClickListener;
import org.moserp.common.databinding.RecycleViewResourceAdapter;
import org.moserp.common.rest.RestServiceRegistry;
import org.moserp.inventory.databinding.ActivityInventoryBinding;

import java.util.List;

@EActivity
public class InventoryActivity extends ToolbarActivity {

    private ActivityInventoryBinding binding;

    @RestService
    InventoryItemRestService inventoryItemRestService;
    @Bean
    RestServiceRegistry restServiceRegistry;

    private InventoryItem selectedInventoryItem = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_inventory);
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

    @AfterInject
    void adjustRestService() {
        restServiceRegistry.adjustRestService(inventoryItemRestService, "MOS_ERP_INVENTORY_MODULE");
    }

    @Override
    protected void onStart() {
        super.onStart();
        queryInventoryItems();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_action_bar, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Background
    void queryInventoryItems() {
        List<InventoryItem> inventoryItems = inventoryItemRestService.get().getList();
        RecycleViewResourceAdapter<InventoryItem> adapter = new RecycleViewResourceAdapter<>(inventoryItems, R.layout.inventory_item_per_product_list);
        adapter.setOnItemClickListener(new OnItemClickListener<InventoryItem>() {
            @Override
            public void onItemClick(int position, InventoryItem inventoryItem) {
                selectedInventoryItem = inventoryItem;
            }
        });
        binding.inventoryItemsList.setAdapter(adapter);
    }

}
