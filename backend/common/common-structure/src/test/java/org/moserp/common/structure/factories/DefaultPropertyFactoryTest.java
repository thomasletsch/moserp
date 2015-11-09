package org.moserp.common.structure.factories;

import org.junit.Before;
import org.junit.Test;
import org.moserp.common.structure.domain.BusinessEntity;
import org.moserp.common.structure.domain.EntityProperty;
import org.moserp.common.structure.domain.EntityPropertyType;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DefaultPropertyFactoryTest extends BasicPropertyFactoryTest {

    private BusinessEntity businessEntity;

    @Before
    public void setup() {
        propertyFactory = new DefaultPropertyFactory();
    }

    @Test
    public void testDependentEntityProperty() {
        EntityProperty dependentEntityProperty = getEntityProperty("ownSerializerProperty");
        assertEquals("description", null, dependentEntityProperty.getDescription());
        assertEquals("format", null, dependentEntityProperty.getFormat());
        assertEquals("type", EntityPropertyType.STRING, dependentEntityProperty.getType());
        assertEquals("uri", null, dependentEntityProperty.getUri());
        assertEquals("items", null, dependentEntityProperty.getItems());
        assertEquals("dependentEntity", null, dependentEntityProperty.getDependentEntity());
    }

    @Override
    protected List<String> getPositiveProperties() {
        return Arrays.asList();
    }

}
