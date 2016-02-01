package org.moserp.common.modules;

import lombok.extern.slf4j.Slf4j;
import org.moserp.common.domain.RestUri;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;

@Slf4j
@Service
public class ModuleRegistry {

    public static final String MODULE_POST_FIX = "-module";
    
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
            case "users" : return "environment" + MODULE_POST_FIX;
            case "unitOfMeasurements" : return "environment" + MODULE_POST_FIX;
            case "valueLists" : return "environment" + MODULE_POST_FIX;
            case "facilities" : return "facility" + MODULE_POST_FIX;
            case "products" : return "product" + MODULE_POST_FIX;
            case "productCatalog" : return "product" + MODULE_POST_FIX;
            case "incomingDeliveries" : return "inventory" + MODULE_POST_FIX;
            case "outgoingDeliveries" : return "inventory" + MODULE_POST_FIX;
            case "inventoryTransfers" : return "inventory" + MODULE_POST_FIX;
            case "inventoryItems" : return "inventory" + MODULE_POST_FIX;
            default: return "";
        }
    }

}
