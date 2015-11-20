package org.moserp.inventory.rest;

import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static junit.framework.TestCase.assertNotNull;

public class ProductControllerUnitTest {

    @Test
    public void testRibbonUrl() throws URISyntaxException {
        URI uri = new URI("http://inventory.moserp.org/inventoryItems/1/quantityOnHand");
        assertNotNull("hostName", uri.getHost());
    }


}
