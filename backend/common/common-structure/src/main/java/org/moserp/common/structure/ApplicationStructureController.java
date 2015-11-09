package org.moserp.common.structure;

import org.moserp.common.structure.domain.BusinessEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.support.Repositories;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.mapping.ResourceMapping;
import org.springframework.data.rest.core.mapping.ResourceMappings;
import org.springframework.data.rest.core.mapping.ResourceMetadata;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.data.rest.webmvc.BaseUri;
import org.springframework.data.rest.webmvc.RootResourceInformation;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@BasePathAwareController
public class ApplicationStructureController {

    public static final String BASE_PATH = "/structure";
    private static final String REPOSITORY = "/{repository}";

    private ApplicationStructureBuilder applicationStructureBuilder;
    private final RepositoryRestConfiguration configuration;
    private final Repositories repositories;
    private ResourceMappings mappings;

    private GroupResolver groupResolver = new GroupResolver();

    @Autowired
    public ApplicationStructureController(ApplicationStructureBuilder applicationStructureBuilder, RepositoryRestConfiguration configuration, Repositories repositories, ResourceMappings mappings) {
        Assert.notNull(applicationStructureBuilder, "ApplicationStructureBuilder must not be null!");
        Assert.notNull(configuration, "RepositoryRestConfiguration must not be null!");
        Assert.notNull(repositories, "Repositories must not be null!");
        Assert.notNull(mappings, "ResourceMappings must not be null!");

        this.applicationStructureBuilder = applicationStructureBuilder;
        this.configuration = configuration;
        this.repositories = repositories;
        this.mappings = mappings;
    }

    @RequestMapping(value = BASE_PATH, method = RequestMethod.GET)
    public HttpEntity<Map<String, ResourceSupport>> listRepositories() {

        Map<String, ResourceSupport> groups = new HashMap<>();
        for (Class<?> domainType : repositories) {
            ResourceMetadata metadata = mappings.getMetadataFor(domainType);
            if (!metadata.isExported()) {
                continue;
            }
            String group = groupResolver.resolveGroup(domainType);
            ResourceSupport resource = groups.get(group);
            if(resource == null) {
                resource = new ResourceSupport();
                groups.put(group, resource);
            }
            resource.add(new Link(getPath(metadata), metadata.getRel()));
        }
        return new ResponseEntity<>(groups, HttpStatus.OK);
    }

    @RequestMapping(value = BASE_PATH + REPOSITORY, method = GET)
    public HttpEntity<BusinessEntity> schema(RootResourceInformation resourceInformation) {
        BusinessEntity schema = applicationStructureBuilder.buildFor(resourceInformation.getDomainType());
        return new ResponseEntity<>(schema, HttpStatus.OK);
    }

    private String getRootPath() {
        BaseUri baseUri = new BaseUri(configuration.getBaseUri());
        return baseUri.getUriComponentsBuilder().path(BASE_PATH).build().toString();
    }

    private String getPath(ResourceMapping mapping) {
        String path = getRootPath();
        return mapping == null ? path : path + mapping.getPath();
    }

}
