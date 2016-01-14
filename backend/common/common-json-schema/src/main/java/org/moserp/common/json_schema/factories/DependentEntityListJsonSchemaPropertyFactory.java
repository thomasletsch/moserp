package org.moserp.common.json_schema.factories;

import org.moserp.common.domain.DependentEntity;
import org.moserp.common.json_schema.JsonSchemaBuilder;
import org.moserp.common.json_schema.PropertyFactoryContext;
import org.moserp.common.json_schema.domain.EntityProperty;
import org.moserp.common.json_schema.domain.EntityPropertyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DependentEntityListJsonSchemaPropertyFactory extends BasicPropertyFactory {

    private JsonSchemaBuilder jsonSchemaBuilder;

    @Autowired
    public DependentEntityListJsonSchemaPropertyFactory(JsonSchemaBuilder jsonSchemaBuilder) {
        this.jsonSchemaBuilder = jsonSchemaBuilder;
    }

    @Override
    public boolean appliesTo(PropertyFactoryContext context) {
        if(context.getPersistentProperty() == null || context.getPersistentProperty().getComponentType() == null) {
            return false;
        }
        boolean isCollection = context.getPersistentProperty().isCollectionLike();
        boolean isDependentEntity = DependentEntity.class.isAssignableFrom(context.getPersistentProperty().getComponentType());
        return isCollection && isDependentEntity;
    }

    @Override
    public EntityProperty create(PropertyFactoryContext context) {
        EntityProperty property = createPropertyWithBasicValues(context);
        property.setType(EntityPropertyType.ARRAY);
        property.setItems(jsonSchemaBuilder.buildFor(context.getPersistentProperty().getComponentType()));
        return property;
    }

}
