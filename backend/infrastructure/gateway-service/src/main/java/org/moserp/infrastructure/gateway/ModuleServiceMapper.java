package org.moserp.infrastructure.gateway;

import org.springframework.cloud.netflix.zuul.filters.discovery.ServiceRouteMapper;

public class ModuleServiceMapper implements ServiceRouteMapper {

    @Override
    public String apply(String serviceId) {
        if(serviceId.endsWith("module")) {
            return serviceId.replace("-module", "");
        }
        return serviceId;
    }
}
