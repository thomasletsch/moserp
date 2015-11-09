package org.moserp.common.structure.factories;

import org.moserp.common.structure.PropertyFactoryContext;
import org.moserp.common.structure.domain.EntityProperty;
import org.moserp.common.structure.domain.EntityPropertyType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EnumPropertyFactory extends BasicPropertyFactory {

    @Override
    public boolean appliesTo(PropertyFactoryContext context) {
        return Enum.class.isAssignableFrom(context.getDefinition().getAccessor().getRawType());
    }

    @Override
    public EntityProperty create(PropertyFactoryContext context) {
        EntityProperty property = createPropertyWithBasicValues(context);
        property.setType(EntityPropertyType.ENUM);
        List<String> items = new ArrayList<>();
        for (Object value : context.getDefinition().getAccessor().getRawType().getEnumConstants()) {
            items.add(value.toString());
        }
        property.setItems(items);
        return property;
    }

}
