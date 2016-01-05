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
//        property.setRequired(context.getDefinition().isRequired());
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
