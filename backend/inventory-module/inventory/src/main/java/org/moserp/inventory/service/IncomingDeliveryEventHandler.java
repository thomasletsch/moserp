package org.moserp.inventory.service;

import org.moserp.common.domain.RestUri;
import org.moserp.inventory.domain.DeliveryItem;
import org.moserp.inventory.domain.IncomingDelivery;
import org.moserp.inventory.domain.InventoryItem;
import org.moserp.inventory.repository.InventoryItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RepositoryEventHandler
public class IncomingDeliveryEventHandler {

    @Autowired
    private InventoryItemRepository inventoryItemRepository;

    @HandleBeforeCreate
    public void processIncomingDelivery(IncomingDelivery incomingDelivery) {
        incomingDelivery.getItems().forEach(item -> bookItem(incomingDelivery.getToFacility(), item));
    }

    private void bookItem(RestUri facility, DeliveryItem item) {
        // TODO: Check for product attributes as well
        List<InventoryItem> items = inventoryItemRepository.findByProductAndFacility(item.getProductInstance().getProduct(), facility);
        InventoryItem targetInventory;
        if (items.isEmpty()) {
            targetInventory = new InventoryItem(item.getProductInstance(), facility);
        } else {
            targetInventory = items.get(0);
        }
        targetInventory.bookIncoming(item);
        inventoryItemRepository.save(targetInventory);
    }

}
