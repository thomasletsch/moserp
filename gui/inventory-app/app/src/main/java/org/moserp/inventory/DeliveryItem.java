package org.moserp.inventory;

import org.moserp.common.domain.DependentEntity;
import org.moserp.common.domain.Quantity;

public class DeliveryItem extends DependentEntity {

    private Quantity quantity;
    private ProductInstance productInstance = new ProductInstance();

    public DeliveryItem() {
    }

    public ProductInstance getProductInstance() {
        return productInstance;
    }

    public void setProductInstance(ProductInstance productInstance) {
        this.productInstance = productInstance;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public void setQuantity(Quantity quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "DeliveryItem [product=" + getProductInstance().getProduct() + ", quantity=" + quantity + "]";
    }

}
