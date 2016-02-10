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

package org.moserp.common.structure.factories;

import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import org.junit.Before;
import org.junit.Test;
import org.moserp.common.structure.ObjectMapperBuilder;
import org.moserp.common.structure.PropertyFactory;
import org.moserp.common.structure.PropertyFactoryContext;
import org.moserp.common.structure.domain.EntityProperty;
import org.moserp.common.structure.domain.SimpleClass;
import org.springframework.data.mapping.PersistentEntity;
import org.springframework.data.mapping.PersistentProperty;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.json.JacksonMetadata;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public abstract class BasicPropertyFactoryTest {
    protected PropertyFactory propertyFactory;
    protected PersistentProperty<?> persistentProperty;
    private JacksonMetadata jacksonMetadata;
    private PersistentEntity persistentEntity;

    @Before
    public void setupBasicMocks() {
        jacksonMetadata = new JacksonMetadata(new ObjectMapperBuilder().build(), SimpleClass.class);
        persistentProperty = mock(PersistentProperty.class);

        persistentEntity = mock(PersistentEntity.class);
        when(persistentEntity.getType()).thenReturn(SimpleClass.class);
        when(persistentProperty.getOwner()).thenReturn(persistentEntity);
    }

    protected EntityProperty getEntityProperty(String name) {
        when(persistentProperty.getName()).thenReturn(name);
        BeanPropertyDefinition definition = jacksonMetadata.getDefinitionFor(persistentProperty);
        PropertyFactoryContext context = new PropertyFactoryContext(definition, jacksonMetadata, persistentProperty);
        return propertyFactory.create(context);
    }

    @Test
    public void appliesOnlyToOwnProperties() {
        List<String> positiveProperties = getPositiveProperties();
        for (String positiveProperty : positiveProperties) {
            when(persistentEntity.getPersistentProperty(eq(positiveProperty))).thenReturn(persistentProperty);
        }
        for (BeanPropertyDefinition definition : jacksonMetadata) {
            String name = definition.getInternalName();
            PersistentProperty<?> persistentProperty = persistentEntity.getPersistentProperty(name);
            PropertyFactoryContext context = new PropertyFactoryContext(definition, jacksonMetadata, persistentProperty);
            if (positiveProperties.contains(name)) {
                assertTrue("Should apply to " + name, propertyFactory.appliesTo(context));
            } else {
                assertFalse("Should NOT apply to " + name, propertyFactory.appliesTo(context));
            }
        }
    }

    protected abstract List<String> getPositiveProperties();

    protected RepositoryRestConfiguration createConfiguration() {
        RepositoryRestConfiguration configuration = mock(RepositoryRestConfiguration.class);
        try {
            when(configuration.getBaseUri()).thenReturn(new URI("http://localhost:8080/"));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return configuration;
    }
}
