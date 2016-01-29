package org.moserp.infrastructure.gateway;

import org.springframework.cloud.netflix.zuul.filters.discovery.ServiceRouteMapper;

public class MosErpServiceMapper implements ServiceRouteMapper {

    @Override
    public String apply(String serviceId) {
        if(serviceId.endsWith(".moserp.org")) {
            String newId = serviceId.replace(".moserp.org", "");
            return newId;
        }
        return serviceId;
    }
}
