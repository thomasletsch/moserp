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

package org.moserp.environment.rest;

import org.junit.Before;
import org.junit.Test;
import org.moserp.common.rest.BaseWebIntegrationTest;
import org.moserp.environment.domain.ValueList;
import org.moserp.environment.domain.ValueListItem;
import org.moserp.environment.repository.ValueListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpMethod;

import static org.junit.Assert.assertEquals;

public class ValueListControllerTest extends BaseWebIntegrationTest {

    @Autowired
    private ValueListRepository valueListRepository;
    private String key = "testList";

    @Before
    public void setUpDb() {
        valueListRepository.deleteAll();
    }

    @Test
    public void testFindByKey() {
        ValueList valueList = new ValueList();
        valueList.setKey(key);
        valueList.addValue(new ValueListItem("TestValue"));
        valueListRepository.save(valueList);
        Resources<ValueListItem> valueListItems = restTemplate
                .exchange(testEnvironment.createRestUri("valueLists/" + key + "/values"), HttpMethod.GET, null, new ParameterizedTypeReference<Resources<ValueListItem>>() {
                }).getBody();
        assertEquals("size", 1, valueListItems.getContent().size());
        assertEquals("value", valueList.getValues().get(0), valueListItems.getContent().iterator().next());
    }

}
