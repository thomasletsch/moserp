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

package org.moserp.common.rest;

import com.jayway.restassured.RestAssured;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.moserp.TestWebApplication;
import org.moserp.common.modules.ModuleRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

@WebIntegrationTest(randomPort = true)
@ActiveProfiles("test")
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
