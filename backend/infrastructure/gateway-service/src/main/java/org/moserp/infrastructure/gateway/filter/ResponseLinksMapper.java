/*******************************************************************************
 * Copyright 2016 Thomas Letsch (contact@thomas-letsch.de)
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

package org.moserp.infrastructure.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.zuul.filters.discovery.ServiceRouteMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
public class ResponseLinksMapper {

    private static final Pattern standardUrlPattern = Pattern.compile("http[s]?://[a-zA-Z0-9]+:[0-9]+.*");
    private static final Pattern urlWithServiceNamePattern = Pattern.compile("http[s]?://([a-zA-Z0-9\\-]+)(.*?)");
    public static final List<String> NODE_NAMES = Arrays.asList("uri", "href");

    private ServiceRouteMapper serviceRouteMapper;
    private DiscoveryClient discoveryClient;

    private List<String> services;

    @Autowired
    public ResponseLinksMapper(ServiceRouteMapper serviceRouteMapper, DiscoveryClient discoveryClient) {
        this.serviceRouteMapper = serviceRouteMapper;
        this.discoveryClient = discoveryClient;
    }

    @PostConstruct
    public void fillServices() {
        services = discoveryClient.getServices();
    }

    public void fixFor(Map<String, Object> node) {
        for (Map.Entry<String, Object> entry : node.entrySet()) {
            if (NODE_NAMES.contains(entry.getKey().toLowerCase())) {
                entry.setValue(fixLink(entry.getValue()));
            } else {
                if (entry.getValue() instanceof Collection) {
                    fixFor((Collection) entry.getValue());
                } else if (entry.getValue() instanceof Map) {
                    fixFor((Map<String, Object>) entry.getValue());
                }
            }
        }
    }

    void fixFor(Collection<Map> value) {
        for (Object entry : value) {
            if (entry instanceof Collection) {
                fixFor((Collection) entry);
            } else if (entry instanceof Map) {
                fixFor((Map<String, Object>) entry);
            }
        }
    }

    Object fixLink(Object value) {
        if (!(value instanceof String)) {
            return value;
        }
        String href = (String) value;
        Matcher urlWithServiceNameMatcher = urlWithServiceNamePattern.matcher(href);
        Matcher standardUrlMatcher = standardUrlPattern.matcher(href);
        if (!standardUrlMatcher.matches() && urlWithServiceNameMatcher.matches()) {
            String possibleServiceName = urlWithServiceNameMatcher.group(1);
            log.debug("Possible service name: " + possibleServiceName);
            if (services.contains(possibleServiceName)) {
                log.debug("Service found");
                String gatewayPath = serviceRouteMapper.apply(possibleServiceName);
                String originalRestPath = urlWithServiceNameMatcher.group(2);
                ServletUriComponentsBuilder servletUriComponentsBuilder = ServletUriComponentsBuilder.fromCurrentRequest();
                UriComponents uriComponents = servletUriComponentsBuilder.replacePath(gatewayPath).path(originalRestPath).build();
                log.debug("Mapping " + value + " to " +  uriComponents);
                return uriComponents.toString();
            }
        }
        return href;
    }

}
