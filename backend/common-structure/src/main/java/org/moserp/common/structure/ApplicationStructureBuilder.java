package org.moserp.common.structure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import lombok.extern.slf4j.Slf4j;
import org.moserp.common.structure.domain.BusinessEntity;
import org.moserp.common.structure.domain.EntityProperty;
import org.moserp.common.structure.factories.DefaultPropertyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mapping.PersistentEntity;
import org.springframework.data.mapping.PersistentProperty;
import org.springframework.data.mapping.context.PersistentEntities;
import org.springframework.data.rest.webmvc.json.JacksonMetadata;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@Service
public class ApplicationStructureBuilder {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private PersistentEntities persistentEntities;

    @Autowired
    private RepositoryEntityLinks entityLinks;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DefaultPropertyFactory defaultPropertyFactory;

    private GroupResolver groupResolver = new GroupResolver();

    public BusinessEntity buildFor(Class<?> domainType) {
        BusinessEntity entity = new BusinessEntity();
        entity.setName(domainType.getSimpleName());
        entity.setUri(entityLinks.linkFor(domainType).toUri().toString());
        entity.setGroup(groupResolver.resolveGroup(domainType));
        entity.setProperties(buildProperties(domainType));
        return entity;
    }

    private List<EntityProperty> buildProperties(Class<?> domainType) {
        List<EntityProperty> properties = new ArrayList<>();
        final PersistentEntity<?, ?> persistentEntity = persistentEntities.getPersistentEntity(domainType);
        JacksonMetadata jacksonMetadata = new JacksonMetadata(objectMapper, domainType);
        for (BeanPropertyDefinition definition : jacksonMetadata) {
            PersistentProperty<?> persistentProperty = persistentEntity.getPersistentProperty(definition.getInternalName());
            PropertyFactoryContext context = new PropertyFactoryContext(definition, jacksonMetadata, persistentProperty);
            PropertyFactory factory = getFactoryFor(context);
            if (factory != null) {
                properties.add(factory.create(context));
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
        return defaultPropertyFactory;
    }

}
