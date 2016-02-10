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

package org.moserp.product.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.moserp.common.repository.IntegrationTestContext;
import org.moserp.product.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@IntegrationTest
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {IntegrationTestContext.class, ProductIntegrationTestContext.class})
public class ProductRepositoryTest {

    @Autowired
    private ProductUtil productUtil;

    @Autowired
    private ProductRepository productRepository;

    @Before
    public void setupRepository() {
        productUtil.setupOneProduct();
    }

    @Test
    public void testFindByNameOrEan_Name() {
        List<Product> byName = productRepository.findByNameOrEan("My");
        assertEquals("size", 1, byName.size());
    }

    @Test
    public void testFindByNameOrEan_NameWrongCase() {
        List<Product> byName = productRepository.findByNameOrEan("my");
        assertEquals("size", 1, byName.size());
    }

    @Test
    public void testFindByNameOrEan_Ean() {
        List<Product> byName = productRepository.findByNameOrEan("456");
        assertEquals("size", 1, byName.size());
    }

}
