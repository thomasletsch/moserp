package org.moserp.inventory.rest;

import org.junit.Before;
import org.junit.Test;
import org.moserp.common.domain.Quantity;
import org.moserp.common.domain.RestUri;
import org.moserp.inventory.domain.DeliveryItem;
import org.moserp.inventory.domain.OutgoingDelivery;
import org.moserp.product.domain.ProductInstance;
import org.moserp.inventory.repository.DeliveryUtil;
import org.moserp.inventory.repository.InventoryItemUtil;
import org.moserp.inventory.repository.OutgoingDeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class OutgoingDeliveryTest extends BaseWebInventoryTest {

    @Autowired
    private InventoryItemUtil inventoryUtil;

    @Autowired
    private DeliveryUtil deliveryUtil;

    private RestUri facility;

    private RestUri product;

    @Before
    public void setup() {
        facility = moduleRegistry.getBaseUriForResource("facilities").slash("1");
        product = moduleRegistry.getBaseUriForResource("products").slash("1");
        inventoryUtil.setupOneInventoryItem(product, facility);
        deliveryUtil.setup();
    }

    @Test
    public void testOutgoingDelivery() {
        DeliveryItem item = new DeliveryItem(new ProductInstance(product), new Quantity(55));
        OutgoingDelivery outgoingDelivery = new OutgoingDelivery(facility, item);

        restTemplate.postForLocation(testEnvironment.createRestUri(OutgoingDeliveryRepository.URL), outgoingDelivery);
        inventoryUtil.checkInventory(product, facility, 45);
    }

}
