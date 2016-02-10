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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.moserp.common.domain.RestUri;
import org.moserp.common.repository.IntegrationTestContext;
import org.moserp.inventory.domain.InventoryItem;
import org.moserp.product.domain.ProductInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@IntegrationTest
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {IntegrationTestContext.class, InventoryIntegrationTestContext.class})
public class InventoryItemRepositoryTest {

    @Autowired
    private InventoryItemRepository inventoryItemRepository;

    @Autowired
    private InventoryItemUtil inventoryItemUtil;

    private RestUri facility;
    private RestUri product;

    @Before
    public void setUp() {
        facility = new RestUri("http://localhost:8080/facilities/1");
        product = new RestUri("http://localhost:8080/products/1");
        inventoryItemUtil.setupOneInventoryItem(product, facility);
    }

    @Test
    public void findByProductIdAndFacilityId() {
        List<InventoryItem> items = inventoryItemRepository.findByProductAndFacility(product, facility);
        assertEquals("Size", 1, items.size());
    }

    @Test
    public void findByProductIdAndFacilityIdNoItem() throws Exception {
        List<InventoryItem> items = inventoryItemRepository.findByProductAndFacility(product, facility.append("_invalid"));
        assertEquals("Size", 0, items.size());
    }

    @Test
    public void findByProductId() {
        List<InventoryItem> items = inventoryItemRepository.findByProductInstanceProduct(product);
        assertEquals("Size", 1, items.size());
    }

    @Test
    public void findByProductIdNoItem() {
        List<InventoryItem> items = inventoryItemRepository.findByProductInstanceProduct(product.append("_invalid"));
        assertEquals("Size", 0, items.size());
    }

    @Test
    public void findByProductAndFacility() {
        InventoryItem item = inventoryItemRepository.findByProductAndFacility(new ProductInstance(product), facility);
        assertNotNull("item", item);
    }

    @Test
    public void findByProductAndFacilityNoItem() {
        InventoryItem item = inventoryItemRepository.findByProductAndFacility(new ProductInstance(product), facility.append("_invalid"));
        assertNull("item", item);
    }

}
