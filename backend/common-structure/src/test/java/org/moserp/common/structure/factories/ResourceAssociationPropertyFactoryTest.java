package org.moserp.common.structure.factories;

import org.junit.Before;
import org.junit.Test;
import org.moserp.common.domain.RestUri;
import org.moserp.common.modules.ModuleRegistry;
import org.moserp.common.structure.domain.EntityProperty;
import org.moserp.common.structure.domain.EntityPropertyType;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ResourceAssociationPropertyFactoryTest extends BasicPropertyFactoryTest {

    @Before
    public void setup() throws URISyntaxException {
        ModuleRegistry moduleRegistry = mock(ModuleRegistry.class);
        when(moduleRegistry.getBaseUriForResource(eq("unitOfMeasurements"))).thenReturn(new RestUri("http://localhost:8080/unitOfMeasurement"));
        propertyFactory = new ResourceAssociationPropertyFactory(moduleRegistry);
    }

    @Test
    public void testResourceAssociationProperty() {
        EntityProperty valueProperty = getEntityProperty("resourceAssociationProperty");
        assertEquals("description", null, valueProperty.getDescription());
        assertEquals("format", null, valueProperty.getFormat());
        assertEquals("type", EntityPropertyType.ASSOCIATION, valueProperty.getType());
        assertEquals("uri", "http://localhost:8080/unitOfMeasurement", valueProperty.getUri());
        assertEquals("items", null, valueProperty.getItems());
    }
    @Override
    protected List<String> getPositiveProperties() {
        return Arrays.asList("resourceAssociationProperty");
    }

}
