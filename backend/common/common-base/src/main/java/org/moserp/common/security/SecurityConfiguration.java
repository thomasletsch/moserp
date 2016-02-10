/*******************************************************************************
 * Copyright 2013 Thomas Letsch (contact@thomas-letsch.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package org.moserp.common.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@Profile({"!test"})
@EnableResourceServer
public class SecurityConfiguration extends ResourceServerConfigurerAdapter {

    /**
     * Provide security so that endpoints are only served if the request is
     * already authenticated.
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.requestMatchers()
                .antMatchers("/**")
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated();
//                .antMatchers(HttpMethod.GET, "/**").access("#oauth2.hasScope('read')")
//                .antMatchers(HttpMethod.OPTIONS, "/**").access("#oauth2.hasScope('read')")
//                .antMatchers(HttpMethod.POST, "/**").access("#oauth2.hasScope('write')")
//                .antMatchers(HttpMethod.PUT, "/**").access("#oauth2.hasScope('write')")
//                .antMatchers(HttpMethod.PATCH, "/**").access("#oauth2.hasScope('write')")
//                .antMatchers(HttpMethod.DELETE, "/**").access("#oauth2.hasScope('write')");
    }

    /**
     * Id of the resource that you are letting the client have access to.
     * Supposing you have another api ("say api2"), then you can customize the
     * access within resource server to define what api is for what resource id.
     * <br>
     * <br>
     * <p>
     * So suppose you have 2 APIs, then you can define 2 resource servers.
     * <ol>
     * <li>Client 1 has been configured for access to resourceid1, so he can
     * only access "api1" if the resource server configures the resourceid to
     * "api1".</li>
     * <li>Client 1 can't access resource server 2 since it has configured the
     * resource id to "api2"
     * </li>
     * </ol>
     */
//    @Override
//    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//        resources.resourceId("apis");
//    }
}
