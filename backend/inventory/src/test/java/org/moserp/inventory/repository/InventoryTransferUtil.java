package org.moserp.inventory.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InventoryTransferUtil {

    @Autowired
    private InventoryTransferRepository inventoryTransferRepository;

    public void setup() {
        inventoryTransferRepository.deleteAll();
    }

}
