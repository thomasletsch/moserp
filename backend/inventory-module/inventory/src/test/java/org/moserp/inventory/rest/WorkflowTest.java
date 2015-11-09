package org.moserp.inventory.rest;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.moserp.common.domain.Quantity;
import org.moserp.common.domain.RestUri;
import org.moserp.facility.domain.Facility;
import org.moserp.inventory.domain.IncomingDelivery;
import org.moserp.inventory.domain.InventoryTransfer;
import org.moserp.inventory.domain.ProductInstance;
import org.moserp.inventory.repository.DeliveryUtil;
import org.moserp.inventory.repository.IncomingDeliveryRepository;
import org.moserp.inventory.repository.InventoryItemUtil;
import org.moserp.inventory.repository.InventoryTransferRepository;
import org.moserp.product.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

@Ignore
public class WorkflowTest extends BaseWebInventoryTest {

    @Autowired
    private InventoryItemUtil inventoryUtil;

    @Autowired
    private DeliveryUtil deliveryUtil;

    @Before
    public void setUp() {
        inventoryUtil.setup();
        deliveryUtil.setup();
    }

    @Test
    public void workflowTest() {
        RestUri productUri = createTestProduct();
        RestUri facilityUri = createTestFacility(new Facility());
        RestUri targetFacilityUri = createTestFacility(new Facility());
        incomingDelivery(productUri, facilityUri);
        checkInventory(productUri, facilityUri, 100);
        moveToTargetFacility(productUri, facilityUri, targetFacilityUri);
    }

    private void moveToTargetFacility(RestUri productUri, RestUri facilityUri, RestUri targetFacilityUri) {
        InventoryTransfer transfer = new InventoryTransfer(new ProductInstance(productUri), facilityUri, targetFacilityUri, new Quantity(55));
        restTemplate.postForLocation(testEnvironment.createRestUri(InventoryTransferRepository.URL), transfer);
        checkInventory(productUri, facilityUri, 45);
        checkInventory(productUri, targetFacilityUri, 55);
    }

     private void incomingDelivery(RestUri productUri, RestUri facilityUri) {
        IncomingDelivery incomingDelivery = deliveryUtil.createIncomingDelivery(productUri, facilityUri);
        ResponseEntity<Object> response = restTemplate.postForEntity(testEnvironment.createRestUri(IncomingDeliveryRepository.URL), incomingDelivery, null);
    }

    private RestUri createTestProduct() {
        ResponseEntity<Object> response = restTemplate.postForEntity(testEnvironment.createRestUri("products"), createProduct(), null);
        return new RestUri(response.getHeaders().getLocation().toString());
    }

    private Product createProduct() {
        return new Product();
    }

    private RestUri createTestFacility(Facility facility) {
        ResponseEntity<Object> response = restTemplate.postForEntity(testEnvironment.createRestUri("facilities"), facility, null);
        return new RestUri(response.getHeaders().getLocation().toString());
    }

}
