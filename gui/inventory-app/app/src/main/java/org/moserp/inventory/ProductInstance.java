package org.moserp.inventory;

import org.moserp.common.domain.DependentEntity;
import org.moserp.product.Product;

public class ProductInstance extends DependentEntity {

    public String getProduct() {
        return getLinkHref("product");
    }

    public void setProduct(Product product) {
        addLink("product", product.getSelf());
    }
}
