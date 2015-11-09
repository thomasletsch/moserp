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
