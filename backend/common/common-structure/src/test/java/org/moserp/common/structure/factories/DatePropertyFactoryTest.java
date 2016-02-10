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

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DatePropertyFactoryTest extends BasicPropertyFactoryTest {

    @Before
    public void setup() {
        propertyFactory = new DatePropertyFactory();
    }

    @Test
    public void testLocalDateProperty() {
        EntityProperty localDateProperty = getEntityProperty("localDateProperty");
        assertEquals("description", null, localDateProperty.getDescription());
        assertEquals("format", "yyyy-MM-dd", localDateProperty.getFormat());
        assertEquals("type", EntityPropertyType.DATE, localDateProperty.getType());
        assertEquals("uri", null, localDateProperty.getUri());
        assertEquals("items", null, localDateProperty.getItems());
    }

    @Test
    public void testTimeInstantProperty() {
        EntityProperty timeInstantProperty = getEntityProperty("instantProperty");
        assertEquals("description", null, timeInstantProperty.getDescription());
        assertEquals("format", "yyyy-MM-ddTHH:mm:ssZ", timeInstantProperty.getFormat());
        assertEquals("type", EntityPropertyType.DATE, timeInstantProperty.getType());
        assertEquals("uri", null, timeInstantProperty.getUri());
        assertEquals("items", null, timeInstantProperty.getItems());
    }

    @Override
    protected List<String> getPositiveProperties() {
        return Arrays.asList("localDateProperty", "instantProperty", "createdDate", "lastModifiedDate");
    }

}
