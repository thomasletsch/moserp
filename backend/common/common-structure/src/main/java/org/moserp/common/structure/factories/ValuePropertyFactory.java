package org.moserp.common.structure.factories;

import org.moserp.common.annotations.ValueListKey;
import org.moserp.common.modules.ModuleRegistry;
import org.moserp.common.structure.PropertyFactoryContext;
import org.moserp.common.structure.domain.EntityProperty;
import org.moserp.common.structure.domain.EntityPropertyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.BaseUri;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class ValuePropertyFactory extends BasicPropertyFactory {

    private ModuleRegistry moduleRegistry;

    @Autowired
    public ValuePropertyFactory(ModuleRegistry moduleRegistry) {
        this.moduleRegistry = moduleRegistry;
    }

    @Override
    public boolean appliesTo(PropertyFactoryContext context) {
        return isAnnotationPresent(context, ValueListKey.class);
    }

    @Override
    public EntityProperty create(PropertyFactoryContext context) {
        EntityProperty property = createPropertyWithBasicValues(context);
        property.setType(EntityPropertyType.VALUE);
        property.setUri(calculateValueListUri(context));
        return property;
    }

    private String calculateValueListUri(PropertyFactoryContext context) {
        ValueListKey valueListValue = getAnnotation(context, ValueListKey.class);
        String valueListKey = valueListValue.value();
        final BaseUri baseUri = new BaseUri(moduleRegistry.getBaseUriForResource("valueLists").getUri());
        UriComponentsBuilder builder = baseUri.getUriComponentsBuilder();
        return builder.pathSegment(valueListKey).pathSegment("values").build().toUriString();
    }
}
