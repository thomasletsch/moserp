package org.moserp.inventory;

import org.moserp.common.domain.IdentifiableEntity;
import org.moserp.common.domain.Quantity;
import org.moserp.environment.Facility;

public class InventoryTransfer extends IdentifiableEntity {
    private Quantity quantity;
    private ProductInstance productInstance = new ProductInstance();
    private String status;

    public InventoryTransfer() {
    }

    public ProductInstance getProductInstance() {
        return productInstance;
    }

    public void setProductInstance(ProductInstance productInstance) {
        this.productInstance = productInstance;
    }

    public String getToFacility() {
        return getLinkHref("toFacility");
    }

    public void setToFacility(Facility facility) {
        addLink("toFacility", facility.getSelf());
    }

    public String getFromFacility() {
        return getLinkHref("fromFacility");
    }

    public void setFromFacility(Facility facility) {
        addLink("fromFacility", facility.getSelf());
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public void setQuantity(Quantity quantity) {
        this.quantity = quantity;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "InventoryTransfer [fromFacility=" + getFromFacility() + ", toFacility=" + getToFacility() + ", quantity=" + quantity + ", status="
                + status + ", " + super.toString() + "]";
    }

}
