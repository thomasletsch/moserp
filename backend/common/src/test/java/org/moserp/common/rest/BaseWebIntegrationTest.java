package org.moserp.common.rest;

import com.jayway.restassured.RestAssured;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.moserp.TestWebApplication;
import org.moserp.common.modules.ModuleRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

@WebIntegrationTest(randomPort = true)
@SpringApplicationConfiguration(classes = { TestWebApplication.class })
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class BaseWebIntegrationTest {

    @Autowired
    protected RestTemplate restTemplate;

    @Autowired
    protected TestEnvironment testEnvironment;

    @Autowired
    protected ModuleRegistry moduleRegistry;

    @Before
    public void setUpRestAssured() {
        RestAssured.port = testEnvironment.getPort();
        RestAssured.authentication = RestAssured.basic(testEnvironment.getUsername(), testEnvironment.getPassword());
        RestAssured.config = RestAssured.config();
        RestAssured.config.getEncoderConfig().defaultContentCharset("UTF-8");
    }

}
