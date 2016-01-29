package org.moserp.infrastructure.gateway;

import org.springframework.cloud.netflix.zuul.filters.discovery.ServiceRouteMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZuulConfiguration {
    @Bean
    public ServiceRouteMapper serviceRouteMapper() {
        return new MosErpServiceMapper();
    }
}
