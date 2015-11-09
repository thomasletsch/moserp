package org.moserp.inventory.service;

import org.moserp.inventory.domain.InventoryItem;
import org.moserp.inventory.domain.InventoryTransfer;
import org.moserp.inventory.repository.InventoryItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler
public class InventoryTransferEventHandler {

    @Autowired
    private InventoryItemRepository inventoryRepository;

    @HandleBeforeCreate
    public void processInventoryTransfer(InventoryTransfer inventoryTransfer) {
        InventoryItem sourceInventory = inventoryRepository.findByProductAndFacility(inventoryTransfer.getProductInstance(),
                inventoryTransfer.getFromFacility());
        if (sourceInventory == null) {
            sourceInventory = new InventoryItem(inventoryTransfer.getProductInstance(), inventoryTransfer.getFromFacility());
        }
        InventoryItem targetInventory = inventoryRepository.findByProductAndFacility(inventoryTransfer.getProductInstance(),
                inventoryTransfer.getToFacility());
        if (targetInventory == null) {
            targetInventory = new InventoryItem(inventoryTransfer.getProductInstance(), inventoryTransfer.getToFacility());
        }
        sourceInventory.bookOutgoing(inventoryTransfer);
        targetInventory.bookIncoming(inventoryTransfer);
        inventoryRepository.save(sourceInventory);
        inventoryRepository.save(targetInventory);
    }

}
