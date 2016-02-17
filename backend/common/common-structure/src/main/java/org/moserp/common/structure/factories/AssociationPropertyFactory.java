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

import org.moserp.common.modules.ModuleRegistry;
import org.moserp.common.structure.PropertyFactoryContext;
import org.moserp.common.structure.domain.EntityProperty;
import org.moserp.common.structure.domain.EntityPropertyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.mapping.ResourceMappings;
import org.springframework.data.rest.core.mapping.ResourceMetadata;
import org.springframework.data.rest.webmvc.BaseUri;
import org.springframework.data.rest.webmvc.mapping.AssociationLinks;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class AssociationPropertyFactory extends BasicPropertyFactory {

    private final RepositoryRestConfiguration configuration;
    private final ResourceMappings mappings;
    private ModuleRegistry moduleRegistry;

    @Autowired
    public AssociationPropertyFactory(RepositoryRestConfiguration configuration, ResourceMappings mappings, ModuleRegistry moduleRegistry) {
        this.configuration = configuration;
        this.mappings = mappings;
        this.moduleRegistry = moduleRegistry;
    }

    @Override
    public boolean appliesTo(PropertyFactoryContext context) {
        final AssociationLinks associationLinks = new AssociationLinks(mappings);
        return associationLinks.isLinkableAssociation(context.getPersistentProperty());
    }

    @Override
    public EntityProperty create(PropertyFactoryContext context) {
        EntityProperty property = createPropertyWithBasicValues(context);
        property.setType(EntityPropertyType.ASSOCIATION);
        property.setUri(calculateUri(context));
        return property;
    }

    private String calculateUri(PropertyFactoryContext context) {
        ResourceMetadata mapping = mappings.getMetadataFor(context.getPersistentProperty().getType());
        final BaseUri baseUri = new BaseUri("http://" + moduleRegistry.getModuleForResource(mapping.getRel()));
        UriComponentsBuilder builder = baseUri.getUriComponentsBuilder();
        return builder.path(mapping.getPath().toString()).build().toUriString();
    }
}
