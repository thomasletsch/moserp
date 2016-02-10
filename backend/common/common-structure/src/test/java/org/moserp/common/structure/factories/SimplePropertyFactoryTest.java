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

package org.moserp.common.structure.factories;

import org.junit.Before;
import org.junit.Test;
import org.moserp.common.structure.domain.EntityProperty;
import org.moserp.common.structure.domain.EntityPropertyType;
import org.springframework.core.Ordered;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SimplePropertyFactoryTest extends BasicPropertyFactoryTest {

    @Before
    public void setup() {
        propertyFactory = new SimplePropertyFactory();
    }

    @Test
    public void testIntProperty() {
        EntityProperty intProperty = getEntityProperty("intProperty");
        assertEquals("description", null, intProperty.getDescription());
        assertEquals("format", null, intProperty.getFormat());
        assertEquals("type", EntityPropertyType.INTEGER, intProperty.getType());
        assertEquals("uri", null, intProperty.getUri());
        assertEquals("items", null, intProperty.getItems());
        assertEquals("order", 0, intProperty.getOrder());
        assertEquals("readOnly", false, intProperty.isReadOnly());
    }

    @Test
    public void testBooleanProperty() {
        EntityProperty booleanProperty = getEntityProperty("booleanProperty");
        assertEquals("description", null, booleanProperty.getDescription());
        assertEquals("format", null, booleanProperty.getFormat());
        assertEquals("type", EntityPropertyType.BOOLEAN, booleanProperty.getType());
        assertEquals("uri", null, booleanProperty.getUri());
        assertEquals("items", null, booleanProperty.getItems());
        assertEquals("order", 0, booleanProperty.getOrder());
        assertEquals("readOnly", false, booleanProperty.isReadOnly());
    }

    @Test
    public void testIdProperty() {
        EntityProperty intProperty = getEntityProperty("id");
        assertEquals("description", null, intProperty.getDescription());
        assertEquals("format", null, intProperty.getFormat());
        assertEquals("type", EntityPropertyType.STRING, intProperty.getType());
        assertEquals("uri", null, intProperty.getUri());
        assertEquals("items", null, intProperty.getItems());
        assertEquals("order", Ordered.HIGHEST_PRECEDENCE, intProperty.getOrder());
        assertEquals("readOnly", true, intProperty.isReadOnly());
    }

    @Test
    public void testStringProperty() {
        EntityProperty stringProperty = getEntityProperty("stringProperty");
        assertEquals("description", null, stringProperty.getDescription());
        assertEquals("format", null, stringProperty.getFormat());
        assertEquals("type", EntityPropertyType.STRING, stringProperty.getType());
        assertEquals("uri", null, stringProperty.getUri());
        assertEquals("items", null, stringProperty.getItems());
        assertEquals("order", 0, stringProperty.getOrder());
        assertEquals("readOnly", false, stringProperty.isReadOnly());
    }

    @Test
    public void testStringMethod() {
        EntityProperty stringMethod = getEntityProperty("stringMethod");
        assertEquals("description", null, stringMethod.getDescription());
        assertEquals("format", null, stringMethod.getFormat());
        assertEquals("type", EntityPropertyType.STRING, stringMethod.getType());
        assertEquals("uri", null, stringMethod.getUri());
        assertEquals("items", null, stringMethod.getItems());
        assertEquals("order", 0, stringMethod.getOrder());
        assertEquals("readOnly", true, stringMethod.isReadOnly());
        assertEquals("required", false, stringMethod.isRequired());
    }

    @Override
    protected List<String> getPositiveProperties() {
        return Arrays.asList("id", "version", "intProperty", "stringProperty", "stringMethod", "displayName", "booleanProperty", "createdBy", "lastModifiedBy");
    }
}
