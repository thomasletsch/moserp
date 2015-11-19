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

    // TODO: Use service registry for resource -> module lookup
    public String getModuleForResource(String resource) {
        switch (resource) {
            case "users" : return "MOS_ERP_ENVIRONMENT_MODULE";
            case "unitOfMeasurements" : return "MOS_ERP_ENVIRONMENT_MODULE";
            case "valueLists" : return "MOS_ERP_ENVIRONMENT_MODULE";
            case "facilities" : return "MOS_ERP_FACILITY_MODULE";
            case "products" : return "MOS_ERP_PRODUCT_MODULE";
            case "productCatalog" : return "MOS_ERP_PRODUCT_MODULE";
            case "incomingDeliveries" : return "MOS_ERP_INVENTORY_MODULE";
            case "outgoingDeliveries" : return "MOS_ERP_INVENTORY_MODULE";
            case "inventoryTransfers" : return "MOS_ERP_INVENTORY_MODULE";
            case "inventoryItems" : return "MOS_ERP_INVENTORY_MODULE";
            default: return "";
        }
    }

}
