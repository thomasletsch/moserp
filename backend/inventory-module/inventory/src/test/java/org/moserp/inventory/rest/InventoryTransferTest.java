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

package org.moserp.inventory.rest;

import org.junit.Before;
import org.junit.Test;
import org.moserp.common.domain.Quantity;
import org.moserp.common.domain.RestUri;
import org.moserp.inventory.domain.InventoryTransfer;
import org.moserp.product.domain.ProductInstance;
import org.moserp.inventory.repository.InventoryItemUtil;
import org.moserp.inventory.repository.InventoryTransferUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URI;

import static org.junit.Assert.assertNotNull;

public class InventoryTransferTest extends BaseWebInventoryTest {

    @Autowired
    private InventoryItemUtil inventoryItemUtil;

    @Autowired
    private InventoryTransferUtil inventoryTransferUtil;

    private RestUri product;
    private RestUri fromFacility;

    @Before
    public void setup() {
        product = moduleRegistry.getBaseUriForResource("products").slash("1");
        fromFacility = moduleRegistry.getBaseUriForResource("facilities").slash("1");
        inventoryItemUtil.setupOneInventoryItem(product, fromFacility);
        inventoryTransferUtil.setup();
    }

    @Test
    public void testTransfer() throws Exception {
        ProductInstance productInstance = new ProductInstance(product);
        RestUri toFacility = moduleRegistry.getBaseUriForResource("facilities").slash("2");
        InventoryTransfer inventoryTransfer = new InventoryTransfer(productInstance, fromFacility, toFacility, new Quantity(55));
        URI inventoryTransferUri = restTemplate.postForLocation(testEnvironment.createRestUri("inventoryTransfers"), inventoryTransfer);
        InventoryTransfer inventoryTransferResource = restTemplate.getForObject(inventoryTransferUri, InventoryTransfer.class);
        assertNotNull(inventoryTransferResource);
        assertNotNull(inventoryTransferResource.getFromFacility());
        assertNotNull(inventoryTransferResource.getToFacility());
        assertNotNull(inventoryTransferResource.getProductInstance());
        inventoryItemUtil.checkInventory(productInstance.getProduct(), fromFacility, 45);
        inventoryItemUtil.checkInventory(productInstance.getProduct(), toFacility, 55);
    }

}
