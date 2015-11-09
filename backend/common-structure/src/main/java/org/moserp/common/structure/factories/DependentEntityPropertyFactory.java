package org.moserp.common.structure.factories;

import org.moserp.common.domain.DependentEntity;
import org.moserp.common.structure.ApplicationStructureBuilder;
import org.moserp.common.structure.PropertyFactoryContext;
import org.moserp.common.structure.domain.EntityProperty;
import org.moserp.common.structure.domain.EntityPropertyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DependentEntityPropertyFactory extends BasicPropertyFactory {

    private ApplicationStructureBuilder applicationStructureBuilder;

    @Autowired
    public DependentEntityPropertyFactory(ApplicationStructureBuilder applicationStructureBuilder) {
        this.applicationStructureBuilder = applicationStructureBuilder;
    }

    @Override
    public boolean appliesTo(PropertyFactoryContext context) {
        return DependentEntity.class.isAssignableFrom(context.getDefinition().getAccessor().getRawType());
    }

    @Override
    public EntityProperty create(PropertyFactoryContext context) {
        EntityProperty property = createPropertyWithBasicValues(context);
        property.setType(EntityPropertyType.DEPENDENT_ENTITY);
        property.setDependentEntity(applicationStructureBuilder.buildFor(context.getDefinition().getAccessor().getRawType()));
        return property;
    }

}
