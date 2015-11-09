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
