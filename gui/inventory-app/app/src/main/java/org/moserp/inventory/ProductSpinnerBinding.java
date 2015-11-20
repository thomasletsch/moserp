package org.moserp.inventory;

import android.widget.Spinner;

import org.moserp.common.ToolbarActivity;
import org.moserp.common.cache.ResourceCache;
import org.moserp.common.databinding.SpinnerBinding;
import org.moserp.common.databinding.SpinnerValueBinding;
import org.moserp.product.Product;

public class ProductSpinnerBinding extends SpinnerBinding<Product> {

    public ProductSpinnerBinding(Spinner spinner, SpinnerValueBinding<Product> valueBinding, ResourceCache<Product> resourceCache) {
        super(spinner, R.layout.product_item, valueBinding, resourceCache);
    }

    public void bind(ToolbarActivity activity, String productUri) {
        bindSpinner(resourceCache.getAll());
        retrieveSelectedElementFromUri(activity, productUri, Product.class);
    }

}
