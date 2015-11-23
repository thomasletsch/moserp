package org.moserp.inventory.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.moserp.common.annotations.ResourceAssociation;
import org.moserp.common.domain.EntityWithAuditInfo;
import org.moserp.common.domain.Quantity;
import org.moserp.common.domain.RestUri;
import org.moserp.product.domain.ProductInstance;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.ValidationException;

@Data
@Document
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class InventoryItem extends EntityWithAuditInfo {

    private ProductInstance productInstance;

    @ResourceAssociation("facilities")
    private RestUri facility;

    private Quantity quantityOnHand = Quantity.ZERO;

    public InventoryItem(ProductInstance productInstance, RestUri facility) {
        this.productInstance = productInstance;
        this.facility = facility;
    }

    public void bookOutgoing(InventoryTransfer inventoryTransfer) {
        checkProduct(inventoryTransfer.getProductInstance());
        checkForEnoughItems(inventoryTransfer.getQuantity());
        book(inventoryTransfer.getQuantity().negate());
    }

    public void bookOutgoing(DeliveryItem item) {
        checkProduct(item.getProductInstance());
        checkForEnoughItems(item.getQuantity());
        book(item.getQuantity().negate());
    }

    public void bookIncoming(InventoryTransfer inventoryTransfer) {
        checkProduct(inventoryTransfer.getProductInstance());
        book(inventoryTransfer.getQuantity());
    }

    public void bookIncoming(DeliveryItem item) {
        checkProduct(item.getProductInstance());
        book(item.getQuantity());
    }

    private void book(Quantity quantity) {
        quantityOnHand = quantityOnHand.add(quantity);
    }

    private void checkProduct(ProductInstance productToCheck) {
        if (!productInstance.equals(productToCheck)) {
            throw new ValidationException(String.format("Wrong Inventory! Transferring %s, environment contains %s", productToCheck, productInstance));
        }
    }

    private void checkForEnoughItems(Quantity quantity) {
        if (quantityOnHand.lowerThan(quantity)) {
            throw new ValidationException(String.format("Can not transfer %s units of %s, only %s units available.",
                    quantity, productInstance.getDisplayName(), quantityOnHand));
        }
    }


}
