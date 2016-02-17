/*******************************************************************************
 * Copyright 2013 Thomas Letsch (contact@thomas-letsch.de)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package org.moserp.common.modules;

import lombok.extern.slf4j.Slf4j;
import org.moserp.common.domain.RestUri;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ModuleRegistry {

    public static final String MODULE_POST_FIX = "-module";

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private Environment environment;

    @Autowired
    private RestTemplate restTemplate;

    private Map<String, String> resourceToModuleMap = new HashMap<>();

    public RestUri getBaseUriForResource(String resource) {
        String module = getModuleForResource(resource);
        RestUri baseUriForModule = getBaseUriForModule(module);
        log.debug("getBaseUriForResource " + resource + ", module: " + module + ", baseUriForModule: " + baseUriForModule);
        if (baseUriForModule == null) {
            return null;
        }
        return baseUriForModule.slash(resource);
    }

    public RestUri getSearchUriForResource(String resource) {
        return getBaseUriForResource(resource).slash("search");
    }

    public RestUri getBaseUriForModule(String module) {
        String property = environment.getProperty(module + ".baseUri");
        if (property != null) {
            return new RestUri(property);
        }
        URI uri = getInstanceUri(module);
        if (uri != null) {
            return RestUri.from(uri).withoutPath();
        }
        return null;
    }

    public boolean isModuleRegistered(String module) {
        String property = environment.getProperty(module + ".baseUri");
        if (property != null) {
            return true;
        }
        return discoveryClient.getServices().contains(module);
    }

    public void loadResources() {
        for (String serviceId : discoveryClient.getServices()) {
            log.debug("Analyzing service " + serviceId);
            if(!serviceId.endsWith(MODULE_POST_FIX)) {
                continue;
            }
            URI uri = getInstanceUri(serviceId);
            if (uri == null) {
                continue;
            }
            ParameterizedTypeReference<Map<String, ResourceSupport>> typeReference = new ParameterizedTypeReference<Map<String, ResourceSupport>>() {
            };
            String url = uri.toString() + "/structure";
            ResponseEntity<Map<String, ResourceSupport>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, typeReference);
            Map<String, ResourceSupport> groupMap = responseEntity.getBody();
            for (String group : groupMap.keySet()) {
                ResourceSupport support = groupMap.get(group);
                for (Link link : support.getLinks()) {
                    resourceToModuleMap.put(link.getRel(), serviceId);
                }
            }
        }
    }

    public String getModuleForResource(String resource) {
        String property = environment.getProperty(resource + ".module");
        if (property != null) {
            return property;
        }
        String module = resourceToModuleMap.get(resource);
        if (module == null) {
            loadResources();
            module = resourceToModuleMap.get(resource);
        }
        if(module == null) {
            log.warn("Could not find module for " + resource);
        }
        return module;
    }

    private URI getInstanceUri(String serviceId) {
        // TODO: Use Ribbon Load Balancer and fail over here
        List<ServiceInstance> serviceInstances = discoveryClient.getInstances(serviceId);
        if (serviceInstances.size() > 0) {
            return serviceInstances.get(0).getUri();
        } else {
            log.warn("Service Instance not found for service " + serviceId + "!");
            return null;
        }
    }

}
