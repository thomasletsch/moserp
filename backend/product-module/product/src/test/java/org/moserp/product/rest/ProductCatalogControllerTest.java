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

package org.moserp.product.rest;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.moserp.common.rest.BaseWebIntegrationTest;
import org.moserp.environment.domain.UnitOfMeasurement;
import org.moserp.product.domain.Product;
import org.moserp.product.domain.ProductAttributeValue;
import org.moserp.product.repository.ProductUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.*;

public class ProductCatalogControllerTest extends BaseWebIntegrationTest {

    @Autowired
    private ProductUtil productUtil;

    private Product product;

    @Before
    public void setupRepository() {
        product = productUtil.setupOneProduct();
    }

    @Test
    public void testUpdateProduct() {
        List<Product> products = new ArrayList<>();
        Product changeProduct = productUtil.createProduct();
        changeProduct.setName("New Name");
        products.add(changeProduct);
        Response response = given().contentType(ContentType.JSON).when().body(products).put("/productCatalogs/" + this.product.getCatalog().getId() + "/products");
        assertNotNull("response", response);
        assertEquals("response code", 200, response.statusCode());
        Product updated = productUtil.getById(product.getId());
        assertEquals("name", "New Name", updated.getName());
    }

    @Test
    public void testUpdateProductNewProduct() {
        List<Product> products = new ArrayList<>();
        Product alternativeProduct = productUtil.createAlternativeProduct();
        products.add(alternativeProduct);
        Response response = given().contentType(ContentType.JSON).when().body(products).put("/productCatalogs/" + this.product.getCatalog().getId() + "/products");
        assertNotNull("response", response);
        assertEquals("response code", 200, response.statusCode());
        Product updated = productUtil.getByExternalId(alternativeProduct.getExternalId());
        assertEquals("name", "Another Product", updated.getName());
    }

    @Test
    public void testNewProductWithAttributes() {
        List<Product> products = new ArrayList<>();
        Product alternativeProduct = productUtil.createAlternativeProduct();
        productUtil.addAttributes(alternativeProduct);
        products.add(alternativeProduct);
        restTemplate.put(testEnvironment.createRestUri("/productCatalogs/" + this.product.getCatalog().getId() + "/products"), products);
        Product updated = productUtil.getByExternalId(alternativeProduct.getExternalId());
        assertEquals("name", "Another Product", updated.getName());
        assertEquals("attributes size", 1, updated.getAttributes().size());
        ProductAttributeValue productAttribute = (ProductAttributeValue) updated.getAttributes().get(0);
        assertNotNull("attribute", productAttribute);
        assertNotNull("unit", productAttribute.getUnit());
    }

}
