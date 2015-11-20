package org.moserp.common.modules;

import lombok.extern.slf4j.Slf4j;
import org.moserp.common.domain.RestUri;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.env.Environment;
import org.springframework.data.rest.webmvc.BaseUri;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@Service
public class ModuleRegistry {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private Environment environment;

   public RestUri getBaseUriForResource(String resource) {
        String module = getModuleForResource(resource);
        RestUri baseUriForModule = getBaseUriForModule(module);
        log.debug("getBaseUriForResource " + resource + ", module: " + module + ", baseUriForModule: " + baseUriForModule);
       if(baseUriForModule == null) {
           return null;
       }
        return baseUriForModule.slash(resource);
    }

    public RestUri getSearchUriForResource(String resource) {
        return getBaseUriForResource(resource).slash("search");
    }

    public RestUri getBaseUriForModule(String module) {
        String property = environment.getProperty(module + ".baseUri");
        if(property != null) {
            return new RestUri(property);
        }
        List<ServiceInstance> serviceInstances = discoveryClient.getInstances(module);
        if(serviceInstances.size() > 0) {
            URI uri = serviceInstances.get(0).getUri();
            return RestUri.from(uri).withoutPath();
        }
        return null;
    }

    public boolean isModuleRegistered(String module) {
        String property = environment.getProperty(module + ".baseUri");
        if(property != null) {
            return true;
        }
        return discoveryClient.getServices().contains(module);
    }

    // TODO: Use service registry for resource -> module lookup
    public String getModuleForResource(String resource) {
        switch (resource) {
            case "users" : return "environment.moserp.org";
            case "unitOfMeasurements" : return "environment.moserp.org";
            case "valueLists" : return "environment.moserp.org";
            case "facilities" : return "facility.moserp.org";
            case "products" : return "product.moserp.org";
            case "productCatalog" : return "product.moserp.org";
            case "incomingDeliveries" : return "inventory.moserp.org";
            case "outgoingDeliveries" : return "inventory.moserp.org";
            case "inventoryTransfers" : return "inventory.moserp.org";
            case "inventoryItems" : return "inventory.moserp.org";
            default: return "";
        }
    }

}
