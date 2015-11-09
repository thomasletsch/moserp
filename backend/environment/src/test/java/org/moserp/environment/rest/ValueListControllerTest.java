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
