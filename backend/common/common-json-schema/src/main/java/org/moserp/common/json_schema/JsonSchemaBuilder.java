package org.moserp.common.json_schema;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import lombok.extern.slf4j.Slf4j;
import org.moserp.common.json_schema.domain.BusinessEntity;
import org.moserp.common.json_schema.domain.EntityProperty;
import org.moserp.common.json_schema.factories.DefaultJsonSchemaPropertyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mapping.PersistentEntity;
import org.springframework.data.mapping.PersistentProperty;
import org.springframework.data.mapping.context.PersistentEntities;
import org.springframework.data.rest.webmvc.json.JacksonMetadata;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class JsonSchemaBuilder {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private PersistentEntities persistentEntities;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DefaultJsonSchemaPropertyFactory defaultJsonSchemaPropertyFactory;

    public BusinessEntity buildFor(Class<?> domainType) {
        BusinessEntity entity = new BusinessEntity();
        entity.setTitle(domainType.getSimpleName());
        entity.setType("object");
        entity.setProperties(buildProperties(domainType));
        return entity;
    }

    private Map<String, EntityProperty> buildProperties(Class<?> domainType) {
        Map<String, EntityProperty> properties = new HashMap<>();
        final PersistentEntity<?, ?> persistentEntity = persistentEntities.getPersistentEntity(domainType);
        JacksonMetadata jacksonMetadata = new JacksonMetadata(objectMapper, domainType);
        for (BeanPropertyDefinition definition : jacksonMetadata) {
            PersistentProperty<?> persistentProperty = persistentEntity.getPersistentProperty(definition.getInternalName());
            PropertyFactoryContext context = new PropertyFactoryContext(definition, jacksonMetadata, persistentProperty);
            PropertyFactory factory = getFactoryFor(context);
            if (factory != null) {
                properties.put(definition.getInternalName(), factory.create(context));
            }
        }
        return properties;
    }

    private PropertyFactory getFactoryFor(PropertyFactoryContext context) {
        log.debug("getFactoryFor: " + context.getDefinition().getName());
        Collection<PropertyFactory> factories = applicationContext.getBeansOfType(PropertyFactory.class).values();
        for (PropertyFactory factory : factories) {
            if (factory.appliesTo(context)) {
                return factory;
            }
        }
        log.debug("No factory found for " + context.getDefinition().getName() + ". Using default.");
        return defaultJsonSchemaPropertyFactory;
    }

}
