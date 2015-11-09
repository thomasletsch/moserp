package org.moserp.common.structure.factories;

import org.moserp.common.annotations.ResourceAssociation;
import org.moserp.common.domain.RestUri;
import org.moserp.common.modules.ModuleRegistry;
import org.moserp.common.structure.PropertyFactoryContext;
import org.moserp.common.structure.domain.EntityProperty;
import org.moserp.common.structure.domain.EntityPropertyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ResourceAssociationPropertyFactory extends BasicPropertyFactory {

    private ModuleRegistry moduleRegistry;

    @Autowired
    public ResourceAssociationPropertyFactory(ModuleRegistry moduleRegistry) {
        this.moduleRegistry = moduleRegistry;
    }

    @Override
    public boolean appliesTo(PropertyFactoryContext context) {
        return isAnnotationPresent(context, ResourceAssociation.class);
    }

    @Override
    public EntityProperty create(PropertyFactoryContext context) {
        EntityProperty property = createPropertyWithBasicValues(context);
        property.setType(EntityPropertyType.ASSOCIATION);
        property.setUri(calculateValueListUri(context));
        return property;
    }

    private String calculateValueListUri(PropertyFactoryContext context) {
        ResourceAssociation resourceAssociation = getAnnotation(context, ResourceAssociation.class);
        String resourceName = resourceAssociation.value();
        RestUri resourceUri = moduleRegistry.getBaseUriForResource(resourceName);
        return resourceUri.getUri();
    }
}
