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

public class ValuePropertyFactoryTest extends BasicPropertyFactoryTest {

    @Before
    public void setup() throws URISyntaxException {
        ModuleRegistry moduleRegistry = mock(ModuleRegistry.class);
        when(moduleRegistry.getBaseUriForResource(anyString())).thenReturn(new RestUri("http://localhost:8080/valueLists"));
        propertyFactory = new ValuePropertyFactory(moduleRegistry);
    }

    @Test
    public void testValueProperty() {
        EntityProperty valueProperty = getEntityProperty("valueProperty");
        assertEquals("description", null, valueProperty.getDescription());
        assertEquals("format", null, valueProperty.getFormat());
        assertEquals("type", EntityPropertyType.VALUE, valueProperty.getType());
        assertEquals("uri", "http://localhost:8080/valueLists/listKey/values", valueProperty.getUri());
        assertEquals("items", null, valueProperty.getItems());
    }
    @Override
    protected List<String> getPositiveProperties() {
        return Arrays.asList("valueProperty");
    }

}
