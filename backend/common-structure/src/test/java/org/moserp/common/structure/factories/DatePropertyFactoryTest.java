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
