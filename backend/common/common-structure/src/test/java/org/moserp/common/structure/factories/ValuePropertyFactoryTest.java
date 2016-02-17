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
import org.moserp.common.modules.ModuleRegistry;
import org.moserp.common.structure.domain.EntityProperty;
import org.moserp.common.structure.domain.EntityPropertyType;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ValuePropertyFactoryTest extends BasicPropertyFactoryTest {

    @Before
    public void setup() throws URISyntaxException {
        ModuleRegistry moduleRegistry = mock(ModuleRegistry.class);
        when(moduleRegistry.getModuleForResource(anyString())).thenReturn("environment-module");
        propertyFactory = new ValuePropertyFactory(moduleRegistry);
    }

    @Test
    public void testValueProperty() {
        EntityProperty valueProperty = getEntityProperty("valueProperty");
        assertEquals("description", null, valueProperty.getDescription());
        assertEquals("format", null, valueProperty.getFormat());
        assertEquals("type", EntityPropertyType.VALUE, valueProperty.getType());
        assertEquals("uri", "http://environment-module/valueLists/listKey/values", valueProperty.getUri());
        assertEquals("items", null, valueProperty.getItems());
    }
    @Override
    protected List<String> getPositiveProperties() {
        return Arrays.asList("valueProperty");
    }

}
