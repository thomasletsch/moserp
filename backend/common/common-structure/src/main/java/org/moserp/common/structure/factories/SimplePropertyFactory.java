package org.moserp.common.structure.factories;

import org.moserp.common.annotations.ResourceAssociation;
import org.moserp.common.annotations.ValueListKey;
import org.moserp.common.structure.PropertyFactoryContext;
import org.moserp.common.structure.domain.EntityProperty;
import org.moserp.common.structure.domain.EntityPropertyType;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class SimplePropertyFactory extends BasicPropertyFactory {

    private static List<Class<?>> INTEGER_TYPES = Arrays.<Class<?>> asList(Long.class, long.class, Integer.class,
            int.class, Short.class, short.class);

    @Override
    public boolean appliesTo(PropertyFactoryContext context) {
        return !isAnnotationPresent(context, ValueListKey.class) &&!isAnnotationPresent(context, ResourceAssociation.class) && calculatePropertyType(context) != null;
    }

    @Override
    public EntityProperty create(PropertyFactoryContext context) {
        EntityProperty property = createPropertyWithBasicValues(context);
        property.setType(calculatePropertyType(context));
        return property;
    }

    private EntityPropertyType calculatePropertyType(PropertyFactoryContext context) {
        Class<?> rawType = context.getDefinition().getAccessor().getRawType();
        if (INTEGER_TYPES.contains(rawType)) {
            return EntityPropertyType.INTEGER;
        }
        if (String.class.equals(rawType)) {
            return EntityPropertyType.STRING;
        }
        if (Boolean.class.equals(rawType)) {
            return EntityPropertyType.BOOLEAN;
        }
        return null;
    }
}
