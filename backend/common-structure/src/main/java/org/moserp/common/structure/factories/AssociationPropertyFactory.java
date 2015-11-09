package org.moserp.common.structure.factories;

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

    @Autowired
    public AssociationPropertyFactory(RepositoryRestConfiguration configuration, ResourceMappings mappings) {
        this.configuration = configuration;
        this.mappings = mappings;
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
        final BaseUri baseUri = new BaseUri(configuration.getBaseUri());
        UriComponentsBuilder builder = baseUri.getUriComponentsBuilder();
        ResourceMetadata mapping = mappings.getMetadataFor(context.getPersistentProperty().getType());
        return builder.path(mapping.getPath().toString()).build().toUriString();
    }
}
