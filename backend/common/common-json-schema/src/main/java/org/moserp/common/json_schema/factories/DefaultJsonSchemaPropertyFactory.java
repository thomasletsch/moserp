package org.moserp.common.json_schema.factories;

import org.moserp.common.json_schema.domain.EntityProperty;
import org.moserp.common.json_schema.PropertyFactoryContext;
import org.moserp.common.json_schema.domain.EntityPropertyType;
import org.springframework.stereotype.Component;

@Component
public class DefaultJsonSchemaPropertyFactory extends BasicPropertyFactory {

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
