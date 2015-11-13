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
        List<ServiceInstance> serviceInstances = discoveryClient.getInstances(module);
        if(serviceInstances.size() > 0) {
            URI uri = serviceInstances.get(0).getUri();
            return RestUri.from(uri).withoutPath();
        }
        String property = environment.getProperty("module." + module + ".baseUri");
        if(property == null) {
            log.debug("module." + module + ".baseUri" + " not found in properties. Assuming own module.");
            String port = environment.getProperty("server.port");
            return new RestUri("http://localhost:" + port);
        }
        return new RestUri(property);
    }

    // TODO: Use service registry for resource -> module lookup
    public String getModuleForResource(String resource) {
        switch (resource) {
            case "users" : return "mos_erp_environment_module";
            case "unitOfMeasurements" : return "mos_erp_environment_module";
            case "valueLists" : return "mos_erp_environment_module";
            case "facilities" : return "mos_erp_facility_module";
            case "products" : return "mos_erp_product_module";
            case "productCatalog" : return "mos_erp_product_module";
            case "incomingDeliveries" : return "mos_erp_inventory_module";
            case "outgoingDeliveries" : return "mos_erp_inventory_module";
            case "inventoryTransfers" : return "mos_erp_inventory_module";
            case "inventoryItems" : return "mos_erp_inventory_module";
            default: return "";
        }
    }

}
