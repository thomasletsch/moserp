/*******************************************************************************
 * Copyright 2013 Thomas Letsch (contact@thomas-letsch.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package org.moserp.common.json_schema.factories;

import org.moserp.common.annotations.ResourceAssociation;
import org.moserp.common.annotations.ValueListKey;
import org.moserp.common.json_schema.PropertyFactoryContext;
import org.moserp.common.json_schema.domain.EntityProperty;
import org.moserp.common.json_schema.domain.EntityPropertyType;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class SimpleJsonSchemaPropertyFactory extends BasicPropertyFactory {

    private static List<Class<?>> INTEGER_TYPES = Arrays.<Class<?>>asList(Long.class, long.class, Integer.class,
            int.class, Short.class, short.class);

    @Override
    public boolean appliesTo(PropertyFactoryContext context) {
        return !isAnnotationPresent(context, ValueListKey.class) && !isAnnotationPresent(context, ResourceAssociation.class)
                && calculatePropertyType(context) != null;
    }

    @Override
    public EntityProperty create(PropertyFactoryContext context) {
        EntityProperty property = createPropertyWithBasicValues(context);
        property.setType(calculatePropertyType(context));
        return property;
    }

    private String calculatePropertyType(PropertyFactoryContext context) {
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
