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

import org.junit.Before;
import org.junit.Test;
import org.moserp.infrastructure.gateway.config.ModuleServiceMapper;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.zuul.filters.discovery.ServiceRouteMapper;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ResponseLinksMapperTest {

    @Before
    public void setUp(){
        MockHttpServletRequest servletRequest = new MockHttpServletRequest();
        servletRequest.setRequestURI("/api/products");
        servletRequest.setServerPort(8080);
        RequestContextHolder.setRequestAttributes(new ServletWebRequest(servletRequest));
    }

    @Test
    public void testRegExp() {
        Pattern serviceNamePattern = Pattern.compile("http[s]?://([a-zA-Z0-9\\-]+).*?");
        Matcher matcher = serviceNamePattern.matcher("http://service-name/service");
        assertTrue("Matches", matcher.matches());
        assertEquals("Group count", 1, matcher.groupCount());
        assertEquals("Group", "service-name", matcher.group(1));
    }

    @Test
    public void testFixLink() throws Exception {
        ServiceRouteMapper mapper = new ModuleServiceMapper();
        DiscoveryClient discoveryClient = mock(DiscoveryClient.class);
        ResponseLinksMapper responseLinksMapper = new ResponseLinksMapper(mapper, discoveryClient);
        when(discoveryClient.getServices()).thenReturn(Collections.singletonList("service-module"));
        responseLinksMapper.fillServices();
        assertEquals("http://localhost:8080/api/service/entity", responseLinksMapper.fixLink("http://service-module/entity"));
    }
}
