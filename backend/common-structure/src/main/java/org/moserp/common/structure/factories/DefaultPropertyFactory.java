package org.moserp.common.structure.factories;

import org.moserp.common.structure.PropertyFactoryContext;
import org.moserp.common.structure.domain.EntityProperty;
import org.moserp.common.structure.domain.EntityPropertyType;
import org.springframework.stereotype.Component;

@Component
public class DefaultPropertyFactory extends BasicPropertyFactory {

    @Override
    public boolean appliesTo(PropertyFactoryContext context) {
        return false;  // prevent from being selected automatically as factory
    }

    @Override
    public EntityProperty create(PropertyFactoryContext context) {
        EntityProperty property = createPropertyWithBasicValues(context);
        property.setType(EntityPropertyType.STRING);
        return property;
    }

}
