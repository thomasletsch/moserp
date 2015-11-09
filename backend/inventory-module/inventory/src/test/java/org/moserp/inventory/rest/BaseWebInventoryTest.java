package org.moserp.inventory.rest;

import org.moserp.common.domain.RestUri;
import org.moserp.common.rest.BaseWebIntegrationTest;
import org.moserp.inventory.domain.InventoryItem;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpMethod;
import org.springframework.web.util.UriTemplate;

import java.util.Map;

import static org.junit.Assert.*;

public abstract class BaseWebInventoryTest extends BaseWebIntegrationTest {

    protected void checkInventory(RestUri product, RestUri facility, int quantity) {
        String restUri = facility + "/inventoryItems?productId=" + getProductIdFromUri(product.getUri());
        ParameterizedTypeReference<Resources<InventoryItem>> responseType = new ParameterizedTypeReference<Resources<InventoryItem>>() {
        };
        Resources<InventoryItem> inventories = restTemplate.exchange(restUri, HttpMethod.GET, null, responseType).getBody();
        InventoryItem inventory = inventories.getContent().iterator().next();
        assertNotNull("environment", inventory);
//        assertNotNull("Inventory facility", environment.getFacility());
//        assertNotNull("Inventory productInstance", environment.getProductInstance());
        assertEquals("Inventory quantity", quantity, inventory.getQuantityOnHand().intValue());
    }

    protected void checkInventory(String productUri, String facilityUri, int quantity) {
        String url = facilityUri + "/inventoryItems?productId=" + getProductIdFromUri(productUri);
        ParameterizedTypeReference<Resources<InventoryItem>> responseType = new ParameterizedTypeReference<Resources<InventoryItem>>() {
        };
        Resources<InventoryItem> inventories = restTemplate.exchange(url, HttpMethod.GET, null, responseType).getBody();
        InventoryItem inventory = inventories.getContent().iterator().next();
        assertNotNull("environment", inventory);
        assertEquals("Inventory quantity", quantity, inventory.getQuantityOnHand().intValue());
    }

    public String getProductIdFromUri(String productURI) {
        UriTemplate uriTemplate = new UriTemplate("/products/{productId}");
        Map<String, String> variables = uriTemplate.match(productURI);
        return variables.get("productId");
    }
}
