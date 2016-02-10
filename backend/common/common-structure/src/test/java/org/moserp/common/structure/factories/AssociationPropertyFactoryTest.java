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
import org.moserp.common.structure.domain.OtherRepositoryClass;
import org.springframework.data.rest.core.Path;
import org.springframework.data.rest.core.mapping.ResourceMappings;
import org.springframework.data.rest.core.mapping.ResourceMetadata;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class AssociationPropertyFactoryTest extends BasicPropertyFactoryTest {

    @Before
    public void setup() {
        when(persistentProperty.isAssociation()).thenReturn(true);
        when(persistentProperty.getActualType()).thenReturn((Class) OtherRepositoryClass.class);
        when(persistentProperty.getType()).thenReturn((Class) OtherRepositoryClass.class);

        ResourceMappings mappings = mock(ResourceMappings.class);
        ResourceMetadata metaData = mock(ResourceMetadata.class);
        when(metaData.getPath()).thenReturn(new Path("otherRepositoryClasses"));
        when(metaData.isExported()).thenReturn(true);
        when(mappings.getMetadataFor(eq(OtherRepositoryClass.class))).thenReturn(metaData);

        propertyFactory = new AssociationPropertyFactory(createConfiguration(), mappings);
    }

    @Test
    public void testAssociationProperty() {
        EntityProperty valueProperty = getEntityProperty("otherRepositoryClass");
        assertEquals("description", null, valueProperty.getDescription());
        assertEquals("format", null, valueProperty.getFormat());
        assertEquals("type", EntityPropertyType.ASSOCIATION, valueProperty.getType());
        assertEquals("uri", "http://localhost:8080/otherRepositoryClasses", valueProperty.getUri());
        assertEquals("items", null, valueProperty.getItems());
    }

    @Test
    public void testCreatedBy() {
        EntityProperty valueProperty = getEntityProperty("createdBy");
        assertEquals("description", null, valueProperty.getDescription());
        assertEquals("format", null, valueProperty.getFormat());
        assertEquals("type", EntityPropertyType.ASSOCIATION, valueProperty.getType());
        assertEquals("uri", "http://localhost:8080/otherRepositoryClasses", valueProperty.getUri());
        assertEquals("items", null, valueProperty.getItems());
        assertEquals("isReadOnly", true, valueProperty.isReadOnly());
    }

    @Override
    protected List<String> getPositiveProperties() {
        return Arrays.asList("otherRepositoryClass");
    }
}
