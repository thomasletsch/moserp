package org.moserp.common.json_schema.factories;

import org.junit.Before;
import org.junit.Test;
import org.moserp.common.json_schema.domain.BusinessEntity;
import org.moserp.common.json_schema.domain.EntityProperty;
import org.moserp.common.json_schema.domain.EntityPropertyType;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DefaultJsonSchemaPropertyFactoryTest extends BasicPropertyFactoryTest {

    private BusinessEntity businessEntity;

    @Before
    public void setup() {
        propertyFactory = new DefaultJsonSchemaPropertyFactory();
    }

    @Test
    public void testDependentEntityProperty() {
        EntityProperty dependentEntityProperty = getEntityProperty("ownSerializerProperty");
        assertEquals("format", null, dependentEntityProperty.getFormat());
        assertEquals("type", EntityPropertyType.STRING, dependentEntityProperty.getType());
        assertEquals("items", null, dependentEntityProperty.getItems());
    }

    @Override
    protected List<String> getPositiveProperties() {
        return Arrays.asList();
    }

}
