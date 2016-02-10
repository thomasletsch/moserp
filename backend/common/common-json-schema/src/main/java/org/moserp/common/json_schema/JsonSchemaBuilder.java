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
        populateProperties(domainType, entity);
        return entity;
    }

    private void populateProperties(Class<?> domainType, BusinessEntity entity) {
        Map<String, EntityProperty> properties = new HashMap<>();
        final PersistentEntity<?, ?> persistentEntity = persistentEntities.getPersistentEntity(domainType);
        JacksonMetadata jacksonMetadata = new JacksonMetadata(objectMapper, domainType);
        for (BeanPropertyDefinition definition : jacksonMetadata) {
            PersistentProperty<?> persistentProperty = persistentEntity.getPersistentProperty(definition.getInternalName());
            PropertyFactoryContext context = new PropertyFactoryContext(definition, jacksonMetadata, persistentProperty);
            PropertyFactory factory = getFactoryFor(context);
            if (factory != null) {
                EntityProperty property = factory.create(context);
                properties.put(definition.getInternalName(), property);
                if(property.isRequired()) {
                    entity.getRequired().add(definition.getInternalName());
                }
            }
        }
        entity.setProperties(properties);
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
        return null;
    }

}
