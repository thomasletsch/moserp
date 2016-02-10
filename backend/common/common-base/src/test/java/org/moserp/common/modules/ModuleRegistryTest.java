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

package org.moserp.common.modules;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@IntegrationTest
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { ModuleTestConfiguration.class})
public class ModuleRegistryTest {

    public static final String ENVIRONMENT_MOSERP_ORG = "environment.moserp.org";
    @Autowired
    private ModuleRegistry moduleRegistry;

    @Test
    public void testGetBaseUriForResource() throws Exception {
        String usersUri = moduleRegistry.getBaseUriForResource("users").getUri();
        assertEquals("uri", "http://localhost:8080/users", usersUri);
    }

    @Test
    public void testGetBaseUriForModule() throws Exception {
        String uri = moduleRegistry.getBaseUriForModule(ENVIRONMENT_MOSERP_ORG).getUri();
        assertEquals("uri", "http://localhost:8080/", uri);
    }

    @Test
    public void testGetModuleForResource() throws Exception {
        String module = moduleRegistry.getModuleForResource("users");
        assertEquals("module", ENVIRONMENT_MOSERP_ORG, module);
    }

    @Test
    public void testGetSearchUriForResource() throws Exception {
        String uri = moduleRegistry.getSearchUriForResource("users").getUri();
        assertEquals("uri", "http://localhost:8080/users/search", uri);
    }
}
