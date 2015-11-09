package org.moserp.inventory.rest;

import org.junit.Before;
import org.junit.Test;
import org.moserp.common.domain.Quantity;
import org.moserp.common.domain.RestUri;
import org.moserp.inventory.domain.DeliveryItem;
import org.moserp.inventory.domain.IncomingDelivery;
import org.moserp.inventory.domain.ProductInstance;
import org.moserp.inventory.repository.DeliveryUtil;
import org.moserp.inventory.repository.IncomingDeliveryRepository;
import org.moserp.inventory.repository.InventoryItemUtil;
import org.springframework.beans.factory.annotation.Autowired;

public class IncomingDeliveryTest extends BaseWebInventoryTest {

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
        inventoryUtil.setup();
        deliveryUtil.setup();
    }

    @Test
    public void testIncomingDelivery() {
        DeliveryItem item = new DeliveryItem(new ProductInstance(product), new Quantity(100));
        IncomingDelivery incomingDelivery = new IncomingDelivery(facility, item);

        restTemplate.postForLocation(testEnvironment.createRestUri(IncomingDeliveryRepository.URL), incomingDelivery);
        inventoryUtil.checkInventory(product, facility, 100);
    }


}
