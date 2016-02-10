/*******************************************************************************
 * Copyright 2013 Thomas Letsch (contact@thomas-letsch.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

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
