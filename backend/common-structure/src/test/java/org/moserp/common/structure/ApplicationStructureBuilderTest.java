package org.moserp.common.structure;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.moserp.common.domain.RestUri;
import org.moserp.common.modules.ModuleRegistry;
import org.moserp.common.structure.domain.BusinessEntity;
import org.moserp.common.structure.domain.EntityProperty;
import org.moserp.common.structure.domain.SimpleClass;
import org.moserp.common.structure.factories.*;
import org.springframework.context.ApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.data.mapping.PersistentEntity;
import org.springframework.data.mapping.PersistentProperty;
import org.springframework.data.mapping.context.PersistentEntities;
import org.springframework.data.rest.core.Path;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.mapping.ResourceMappings;
import org.springframework.data.rest.core.mapping.ResourceMetadata;
import org.springframework.data.rest.webmvc.support.BaseUriLinkBuilder;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationStructureBuilderTest {

    @Mock
    private ApplicationContext applicationContext;

    @Mock
    private PersistentEntities persistentEntities;

    @Mock
    private RepositoryEntityLinks entityLinks;

    @Spy
    private ObjectMapper objectMapper = new ObjectMapperBuilder().build();

    @InjectMocks
    private ApplicationStructureBuilder builder;

    private BusinessEntity entity;

    private ResourceMappings mappings;
    private ResourceMetadata metaData;
    private RepositoryRestConfiguration configuration;
    private ModuleRegistry moduleRegistry;

    @Before
    public void setup() throws Exception {
        setupMocks();
        setupFactories();
        buildEntity();
    }

    public void setupMocks() throws URISyntaxException {
        configuration = mock(RepositoryRestConfiguration.class);
        when(configuration.getBaseUri()).thenReturn(new URI("http://localhost:8080/"));
        mappings = mock(ResourceMappings.class);
        metaData = mock(ResourceMetadata.class);
        when(metaData.getPath()).thenReturn(new Path("valueLists"));
        PersistentEntity persistentEntity = mock(PersistentEntity.class);
        when(persistentEntities.getPersistentEntity(any())).thenReturn(persistentEntity);
        PersistentProperty persistentProperty = mock(PersistentProperty.class);
        when(persistentEntity.getPersistentProperty(any(String.class))).thenReturn(persistentProperty);
        when(entityLinks.linkFor(any())).thenReturn(BaseUriLinkBuilder.create(new URI("http://localhost:8080/")));
        moduleRegistry = mock(ModuleRegistry.class);
        when(moduleRegistry.getBaseUriForResource(anyString())).thenReturn(new RestUri("http://localhost:8080/valueLists"));
    }

    public void setupFactories() {
        Map<String, PropertyFactory> factories = new HashMap<>();
        factories.put("valuePropertyFactory", new ValuePropertyFactory(moduleRegistry));
        factories.put("simplePropertyFactory", new SimplePropertyFactory());
        factories.put("enumPropertyFactory", new EnumPropertyFactory());
        factories.put("datePropertyFactory", new DatePropertyFactory());
        factories.put("dependentEntityPropertyFactory", new DependentEntityPropertyFactory(builder));
        factories.put("associationPropertyFactory", new AssociationPropertyFactory(configuration, mappings));
        when(applicationContext.getBeansOfType(eq(PropertyFactory.class))).thenReturn(factories);
    }

    public void buildEntity() {
        entity = builder.buildFor(SimpleClass.class);
    }

    @Test
    public void testBusinessEntity() throws Exception {
        assertNotNull("entity", entity);
        assertEquals("name", "SimpleClass", entity.getName());
        assertEquals("group", "org.moserp.common.structure", entity.getGroup());
        assertNotNull("properties", entity.getProperties());
    }

    @Test
    public void testReadonlyProperty() {
        assertEquals("createdDate isReadOnly", true, getEntityProperty("createdDate").isReadOnly());
        assertEquals("lastModifiedDate isReadOnly", true, getEntityProperty("lastModifiedDate").isReadOnly());
        assertEquals("id isReadOnly", true, getEntityProperty("id").isReadOnly());
        assertEquals("intProperty isReadOnly", true, getEntityProperty("id").isReadOnly());
        assertEquals("stringProperty isReadOnly", true, getEntityProperty("id").isReadOnly());
    }

    @Test
    public void testOrderedProperty() {
        assertEquals("createdDate order", Ordered.LOWEST_PRECEDENCE - 2, getEntityProperty("createdDate").getOrder());
        assertEquals("lastModifiedDate order", Ordered.LOWEST_PRECEDENCE, getEntityProperty("lastModifiedDate").getOrder());
    }

    private EntityProperty getEntityProperty(String name) {
        List<EntityProperty> properties = entity.getProperties();
        for (EntityProperty property : properties) {
            if (property.getName().equals(name)) {
                return property;
            }
        }
        fail("Property " + name + " not found");
        throw new AssertionError();  // Will fail before. Only to prevent warning about NPE
    }


}
