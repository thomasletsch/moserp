package org.moserp.inventory.rest;

import org.junit.Before;
import org.junit.Test;
import org.moserp.common.domain.Quantity;
import org.moserp.common.domain.RestUri;
import org.moserp.inventory.domain.InventoryItem;
import org.moserp.inventory.domain.InventoryTransfer;
import org.moserp.product.domain.ProductInstance;
import org.moserp.inventory.repository.InventoryItemUtil;
import org.moserp.inventory.repository.InventoryTransferUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpMethod;

import static org.junit.Assert.assertEquals;

public class InventoryTest extends BaseWebInventoryTest {

    @Autowired
    private InventoryItemUtil inventoryItemUtil;

    @Autowired
    private InventoryTransferUtil inventoryTransferUtil;

    private RestUri facility;
    private RestUri mobileFacility;
    private RestUri product;

    @Before
    public void setup() {
        facility = moduleRegistry.getBaseUriForResource("facilities").slash("1");
        mobileFacility = moduleRegistry.getBaseUriForResource("facilities").slash("2");
        product = moduleRegistry.getBaseUriForResource("products").slash("1");
        inventoryItemUtil.setupOneInventoryItem(product, facility);
        inventoryTransferUtil.setup();
    }

    @Test
    public void testTotalQuantityPerProduct() {
        InventoryTransfer inventoryTransfer = new InventoryTransfer(new ProductInstance(product), facility, mobileFacility, new Quantity(55));
        restTemplate.postForLocation(testEnvironment.createRestUri("inventoryTransfers"), inventoryTransfer);
        Quantity quantity = restTemplate.getForObject(testEnvironment.createRestUri("products") + "/1/quantityOnHand", Quantity.class);
        assertEquals("quantity", quantity, new Quantity(100));
    }

    @Test
    public void testInventoryItemsPerProduct() {
        InventoryTransfer inventoryTransfer = new InventoryTransfer(new ProductInstance(product), facility, mobileFacility, new Quantity(55));
        restTemplate.postForLocation(testEnvironment.createRestUri("inventoryTransfers"), inventoryTransfer);
        Resources<InventoryItem> inventories = restTemplate
                .exchange(testEnvironment.createRestUri("inventoryItems/search/findByProductIdOrFacilityId?productId=1"), HttpMethod.GET, null, new ParameterizedTypeReference<Resources<InventoryItem>>() {
                }).getBody();
        assertEquals("size", 2, inventories.getContent().size());
    }

    @Test
    public void testInventoryItemsPerFacility() {
        InventoryTransfer inventoryTransfer = new InventoryTransfer(new ProductInstance(product), facility, mobileFacility, new Quantity(55));
        restTemplate.postForLocation(testEnvironment.createRestUri("inventoryTransfers"), inventoryTransfer);
        Resources<InventoryItem> inventories = restTemplate
                .exchange(testEnvironment.createRestUri("inventoryItems/search/findByProductIdOrFacilityId?facilityId=2"), HttpMethod.GET, null, new ParameterizedTypeReference<Resources<InventoryItem>>() {
                }).getBody();
        assertEquals("size", 1, inventories.getContent().size());
    }
}
