package org.moserp.inventory.service;

import org.moserp.common.domain.RestUri;
import org.moserp.inventory.domain.DeliveryItem;
import org.moserp.inventory.domain.InventoryItem;
import org.moserp.inventory.domain.OutgoingDelivery;
import org.moserp.inventory.repository.InventoryItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler
public class OutgoingDeliveryEventHandler {

    @Autowired
    private InventoryItemRepository inventoryItemRepository;

    @HandleBeforeCreate
    public void processOutgoingDelivery(OutgoingDelivery OutgoingDelivery) {
        OutgoingDelivery.getItems().forEach(item -> bookItem(OutgoingDelivery.getFromFacility(), item));
    }

    private void bookItem(RestUri facility, DeliveryItem item) {
        InventoryItem targetInventory = inventoryItemRepository.findByProductAndFacility(item.getProductInstance(), facility);
        if (targetInventory == null) {
            targetInventory = new InventoryItem(item.getProductInstance(), facility);
        }
        targetInventory.bookOutgoing(item);
        inventoryItemRepository.save(targetInventory);
    }

}
