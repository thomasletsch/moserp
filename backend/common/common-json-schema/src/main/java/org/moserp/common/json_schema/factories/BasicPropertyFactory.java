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

import org.moserp.common.json_schema.PropertyFactory;
import org.moserp.common.json_schema.PropertyFactoryContext;
import org.moserp.common.json_schema.domain.EntityProperty;
import org.springframework.data.annotation.*;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;

public abstract class BasicPropertyFactory implements PropertyFactory {

    private static final List<Class<? extends Annotation>> READONLY_ANNOTATIONS = Arrays.<Class<? extends Annotation>>asList(
            CreatedDate.class, LastModifiedDate.class, CreatedBy.class, LastModifiedBy.class, Id.class, Version.class);

    protected boolean isAnnotationPresent(PropertyFactoryContext context, Class<? extends Annotation> annotationClass) {
        return getAnnotation(context, annotationClass) != null;
    }

    protected <T extends Annotation> T getAnnotation(PropertyFactoryContext context, Class<T> annotationClass) {
        if (context.getDefinition().getField() != null && context.getDefinition().getField().getAnnotated().isAnnotationPresent(annotationClass)) {
            return context.getDefinition().getField().getAnnotated().getAnnotation(annotationClass);
        }
        return context.getDefinition().getAccessor().getAnnotated().getAnnotation(annotationClass);
    }

    protected EntityProperty createPropertyWithBasicValues(PropertyFactoryContext context) {
        EntityProperty property = new EntityProperty();
        property.setTitle(context.getDefinition().getName());
        property.setRequired(context.getDefinition().isRequired());
        property.setReadOnly(isReadonly(context));
        return property;
    }

    private boolean isReadonly(PropertyFactoryContext context) {
        return (context.getDefinition().hasGetter() && !context.getDefinition().hasSetter()) || hasReadOnlyAnnotation(context);
    }

    private boolean hasReadOnlyAnnotation(PropertyFactoryContext context) {
        for (Class<? extends Annotation> annotation : READONLY_ANNOTATIONS) {
            if (isAnnotationPresent(context, annotation)) {
                return true;
            }
        }
        return false;
    }
}
