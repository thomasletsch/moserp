package org.moserp.common.modules;

import lombok.extern.slf4j.Slf4j;
import org.moserp.common.domain.RestUri;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.rest.webmvc.BaseUri;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
public class ModuleRegistry {

    @Autowired
    private Environment environment;

    public RestUri getBaseUriForResource(String resource) {
        String module = getModuleForResource(resource);
        RestUri baseUriForModule = getBaseUriForModule(module);
        log.debug("getBaseUriForResource " + resource + ", module: " + module + ", baseUriForModule: " + baseUriForModule);
        final BaseUri baseUri = new BaseUri(baseUriForModule.getUri());
        UriComponentsBuilder builder = baseUri.getUriComponentsBuilder();
        return new RestUri(builder.pathSegment(resource).build().toString());
    }

    public RestUri getSearchUriForResource(String resource) {
        String module = getModuleForResource(resource);
        RestUri baseUriForModule = getBaseUriForModule(module);
        final BaseUri baseUri = new BaseUri(baseUriForModule.getUri());
        UriComponentsBuilder builder = baseUri.getUriComponentsBuilder();
        return new RestUri(builder.pathSegment(resource).pathSegment("search").build().toString());
    }

    public RestUri getBaseUriForModule(String module) {
        String property = environment.getProperty("module." + module + ".baseUri");
        if(property == null) {
            log.debug("module." + module + ".baseUri" + " not found in properties. Assuming own module.");
            String port = environment.getProperty("server.port");
            return new RestUri("http://localhost:" + port);
        }
        return new RestUri(property);
    }

    public String getModuleForResource(String resource) {
        switch (resource) {
            case "users" : return "environment";
            case "unitOfMeasurements" : return "environment";
            case "valueLists" : return "environment";
            case "facilities" : return "facility";
            case "products" : return "product";
            case "productCatalog" : return "product";
            case "incomingDeliveries" : return "inventory";
            case "outgoingDeliveries" : return "inventory";
            case "inventoryTransfers" : return "inventory";
            case "inventoryItems" : return "inventory";
            default: return "";
        }
    }

}
