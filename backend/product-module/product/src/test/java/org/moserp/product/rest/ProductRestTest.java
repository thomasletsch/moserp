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
import org.moserp.product.repository.ProductUtil;
import org.moserp.product.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ProductRestTest extends BaseWebIntegrationTest {

    @Autowired
    private ProductUtil productUtil;

    @Before
    public void cleanRepository() {
        productUtil.setup();
    }

    @Test
    public void createProduct() {
        ResponseEntity<Object> response = restTemplate.postForEntity(testEnvironment.createRestUri("/products"),
                new Product("My Product"), null);
        assertEquals("Status", HttpStatus.CREATED, response.getStatusCode());
        String productUri = response.getHeaders().getLocation().toString();
        assertNotNull("productUri", productUri);
        assertNotNull(productUtil.getByUri(productUri));
    }

    @Test
    public void retrieveProduct() {
        Response post = given().contentType(ContentType.JSON).body(new Product("My Product")).when().post("/products");
        String productUri = post.header(HttpHeaders.LOCATION);
        Product product = restTemplate.getForEntity(productUri, Product.class).getBody();
        assertNotNull("productInstance", product);
    }

}
