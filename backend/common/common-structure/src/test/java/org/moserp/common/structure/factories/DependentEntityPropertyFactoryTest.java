package org.moserp.common.structure.factories;

import org.junit.Before;
import org.junit.Test;
import org.moserp.common.structure.ApplicationStructureBuilder;
import org.moserp.common.structure.domain.BusinessEntity;
import org.moserp.common.structure.domain.DependentEntityClass;
import org.moserp.common.structure.domain.EntityProperty;
import org.moserp.common.structure.domain.EntityPropertyType;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class DependentEntityPropertyFactoryTest extends BasicPropertyFactoryTest {

    private BusinessEntity businessEntity;

    @Before
    public void setup() {
        ApplicationStructureBuilder builder = mock(ApplicationStructureBuilder.class);
        businessEntity = new BusinessEntity();
        when(builder.buildFor(eq(DependentEntityClass.class))).thenReturn(businessEntity);
        propertyFactory = new DependentEntityPropertyFactory(builder);
    }

    @Test
    public void testDependentEntityProperty() {
        EntityProperty dependentEntityProperty = getEntityProperty("dependentEntityProperty");
        assertEquals("description", null, dependentEntityProperty.getDescription());
        assertEquals("format", null, dependentEntityProperty.getFormat());
        assertEquals("type", EntityPropertyType.DEPENDENT_ENTITY, dependentEntityProperty.getType());
        assertEquals("uri", null, dependentEntityProperty.getUri());
        assertEquals("items", null, dependentEntityProperty.getItems());
        assertEquals("dependentEntity", businessEntity, dependentEntityProperty.getDependentEntity());
    }

    @Override
    protected List<String> getPositiveProperties() {
        return Arrays.asList("dependentEntityProperty");
    }

}
