package org.moserp.common.structure.factories;

import org.junit.Before;
import org.junit.Test;
import org.moserp.common.structure.domain.EntityProperty;
import org.moserp.common.structure.domain.EntityPropertyType;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class EnumPropertyFactoryTest extends BasicPropertyFactoryTest {

    @Before
    public void setup() {
        propertyFactory = new EnumPropertyFactory();
    }

    @Test
    public void testEnumProperty() {
        EntityProperty enumProperty = getEntityProperty("enumProperty");
        assertEquals("description", null, enumProperty.getDescription());
        assertEquals("format", null, enumProperty.getFormat());
        assertEquals("type", EntityPropertyType.ENUM, enumProperty.getType());
        assertEquals("uri", null, enumProperty.getUri());
        assertNotNull("items", enumProperty.getItems());
        assertEquals("item size", 2, enumProperty.getItems().size());
    }

    @Override
    protected List<String> getPositiveProperties() {
        return Arrays.asList("enumProperty");
    }

}
