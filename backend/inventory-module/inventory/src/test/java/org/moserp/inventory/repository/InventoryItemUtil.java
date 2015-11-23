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
