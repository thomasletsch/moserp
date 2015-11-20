package org.moserp.inventory;

import org.moserp.common.domain.IdentifiableEntity;
import org.moserp.common.domain.Quantity;

public class InventoryItem extends IdentifiableEntity {

    private Quantity quantityOnHand = Quantity.ZERO;
    private ProductInstance productInstance;

    public String getFacility() {
        return getLinkHref("facility");
    }

    public ProductInstance getProductInstance() {
        return productInstance;
    }

    public void setProductInstance(ProductInstance productInstance) {
        this.productInstance = productInstance;
    }

    public Quantity getQuantityOnHand() {
        return quantityOnHand;
    }

    public void setQuantityOnHand(Quantity quantityOnHand) {
        this.quantityOnHand = quantityOnHand;
    }
}
