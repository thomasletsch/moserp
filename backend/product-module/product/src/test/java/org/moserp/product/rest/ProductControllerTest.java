package org.moserp.product.rest;

import com.jayway.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.moserp.common.rest.BaseWebIntegrationTest;
import org.moserp.product.repository.ProductUtil;
import org.moserp.product.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;

public class ProductControllerTest extends BaseWebIntegrationTest {

    @Autowired
    private ProductUtil productUtil;

    private Product product;

    @Before
    public void setupRepository() {
        product = productUtil.setupOneProduct();
    }

    @Test
    public void testProductEan() {
        Response imageResponse = given().when().get("/products/" + product.getId() + "/ean");
        assertNotNull("response", imageResponse);
        imageResponse.then().statusCode(HttpStatus.OK.value()).and().contentType(MediaType.IMAGE_PNG_VALUE);
    }

}
