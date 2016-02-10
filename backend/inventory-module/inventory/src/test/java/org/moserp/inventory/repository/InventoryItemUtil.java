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

package org.moserp.inventory.repository;

import org.moserp.common.domain.Quantity;
import org.moserp.common.domain.RestUri;
import org.moserp.inventory.domain.InventoryItem;
import org.moserp.inventory.domain.InventoryTransfer;
import org.moserp.product.domain.ProductInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.junit.Assert.assertEquals;

@Component
public class InventoryItemUtil {

    @Autowired
    private InventoryItemRepository inventoryItemRepository;

    public void setup() {
        inventoryItemRepository.deleteAll();
    }

    public void setupOneInventoryItem(RestUri product, RestUri facility) {
        inventoryItemRepository.deleteAll();
        InventoryItem item = new InventoryItem(new ProductInstance(product), facility);
        item.bookIncoming(new InventoryTransfer(new ProductInstance(product), facility, facility, new Quantity(100)));
        inventoryItemRepository.save(item);
    }

    public void checkInventory(RestUri product, RestUri facility, int quantity) {
        List<InventoryItem> items = inventoryItemRepository.findByProductAndFacility(product, facility);
        assertEquals("items size", 1, items.size());
        InventoryItem inventoryItem = items.get(0);
        assertEquals("quantityOnHand", new Quantity(quantity), inventoryItem.getQuantityOnHand());
    }

}
