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

package org.moserp.common.json_schema.factories;

import org.junit.Before;
import org.junit.Test;
import org.moserp.common.json_schema.domain.EntityProperty;
import org.moserp.common.json_schema.domain.EntityPropertyType;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SimpleJsonSchemaPropertyFactoryTest extends BasicPropertyFactoryTest {

    @Before
    public void setup() {
        propertyFactory = new SimpleJsonSchemaPropertyFactory();
    }

    @Test
    public void testIntProperty() {
        EntityProperty intProperty = getEntityProperty("intProperty");
        assertEquals("format", null, intProperty.getFormat());
        assertEquals("type", EntityPropertyType.INTEGER, intProperty.getType());
        assertEquals("items", null, intProperty.getItems());
    }

    @Test
    public void testBooleanProperty() {
        EntityProperty booleanProperty = getEntityProperty("booleanProperty");
        assertEquals("format", null, booleanProperty.getFormat());
        assertEquals("type", EntityPropertyType.BOOLEAN, booleanProperty.getType());
        assertEquals("items", null, booleanProperty.getItems());
    }

    @Test
    public void testIdProperty() {
        EntityProperty intProperty = getEntityProperty("id");
        assertEquals("format", null, intProperty.getFormat());
        assertEquals("type", EntityPropertyType.STRING, intProperty.getType());
        assertEquals("items", null, intProperty.getItems());
    }

    @Test
    public void testStringProperty() {
        EntityProperty stringProperty = getEntityProperty("stringProperty");
        assertEquals("format", null, stringProperty.getFormat());
        assertEquals("type", EntityPropertyType.STRING, stringProperty.getType());
        assertEquals("items", null, stringProperty.getItems());
        assertEquals("readOnly", false, stringProperty.getReadOnly());
    }

    @Test
    public void testStringMethod() {
        EntityProperty stringMethod = getEntityProperty("stringMethod");
        assertEquals("format", null, stringMethod.getFormat());
        assertEquals("type", EntityPropertyType.STRING, stringMethod.getType());
        assertEquals("items", null, stringMethod.getItems());
        assertEquals("readOnly", true, stringMethod.getReadOnly());
    }

    @Override
    protected List<String> getPositiveProperties() {
        return Arrays.asList("id", "version", "intProperty", "stringProperty", "stringMethod", "displayName", "booleanProperty", "createdBy", "lastModifiedBy");
    }
}
