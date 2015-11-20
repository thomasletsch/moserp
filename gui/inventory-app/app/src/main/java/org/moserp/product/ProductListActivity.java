package org.moserp.product;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.UiThread;
import org.moserp.common.ToolbarActivity;
import org.moserp.common.databinding.OnItemClickListener;
import org.moserp.common.databinding.RecycleViewResourceAdapter;
import org.moserp.common.rest.RestServiceRegistry;
import org.moserp.inventory.R;
import org.moserp.inventory.databinding.ActivityProductListBinding;

import java.util.List;

@EActivity
@OptionsMenu(R.menu.menu_product_list)
public class ProductListActivity extends ToolbarActivity {

    private ActivityProductListBinding productListBinding;

    @Bean
    RestServiceRegistry restServiceRegistry;

    public static Intent buildIntent(final Context context, String productQuery) {
        Intent intent = new Intent(context, ProductListActivity_.class);
        intent.setAction(Intent.ACTION_SEARCH);
        intent.putExtra(SearchManager.QUERY, productQuery);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        Intent intent = getIntent();
        Log.d("ProductListActivity", "onCreate Intent: " + intent.getAction());
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            searchForProductNameOrEan(query);
        }
        productListBinding = DataBindingUtil.setContentView(this, R.layout.activity_product_list);
        initRecyclerView(productListBinding.productsList);
        initToolbar();
    }

    @Background
    void searchForProductNameOrEan(String query) {
        ProductRestService productRestService = restServiceRegistry.getProductRestService();
        List<Product> products = productRestService.findByNameOrEan(query).getList();
        Log.v("ProductListActivity", products.toString());
        if(products.size() == 0) {
            returnToMainActivity();
        } else if(products.size() == 1) {
            startActivity(ProductDetailActivity.buildIntent(getApplicationContext(), products.get(0).getSelf().getHref()));
        } else {
            updateProductList(products);
        }
    }

    @UiThread
    void returnToMainActivity() {
        onBackPressed();
        Toast.makeText(getApplicationContext(), R.string.toast_no_product_found, Toast.LENGTH_SHORT).show();
    }

    @UiThread
     void updateProductList(List<Product> products) {
        RecycleViewResourceAdapter<Product> adapter = new RecycleViewResourceAdapter<>(products, R.layout.product_list);
        adapter.setOnItemClickListener(new OnItemClickListener<Product>() {
            @Override
            public void onItemClick(int id, Product product) {
                startActivity(ProductDetailActivity.buildIntent(getApplicationContext(), product.getSelf().getHref()));
            }
        });
        productListBinding.productsList.setAdapter(adapter);
    }

}
