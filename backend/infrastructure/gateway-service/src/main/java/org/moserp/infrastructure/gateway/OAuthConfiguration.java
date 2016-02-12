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

package org.moserp.infrastructure.gateway;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * The Class OAuthConfiguration that sets up the OAuth2 single sign on
 * configuration and the web security associated with it.
 */
@Configuration
@EnableOAuth2Sso
public class OAuthConfiguration extends WebSecurityConfigurerAdapter {

	private static final String CSRF_COOKIE_NAME = "XSRF-TOKEN";
	private static final String CSRF_ANGULAR_HEADER_NAME = "X-XSRF-TOKEN";
	
	/**
	 * Define the security that applies to the proxy
	 */
    public void configure(HttpSecurity http) throws Exception {
		http.logout().and()
                .antMatcher("/**").authorizeRequests()
                .antMatchers("/index.html", "/home.html", "/web/**", "/uaa/oauth/**").permitAll()
                .anyRequest().authenticated().and()
                .csrf().csrfTokenRepository(getCSRFTokenRepository()).ignoringAntMatchers("/uaa/oauth/token").and()
                .addFilterAfter(createCSRFHeaderFilter(), CsrfFilter.class);
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // you USUALLY want this
        config.addAllowedOrigin("http://localhost:8899");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

	/**
	 * Spring security offers in-built protection for cross site request forgery
	 * (CSRF) by needing a custom token in the header for any requests that are
	 * NOT safe i.e. modify the resources from the server e.g. POST, PUT & PATCH
	 * etc.<br>
	 * <br>
	 * 
	 * This protection is achieved using cookies that send a custom value (would
	 * remain same for the session) in the first request and then the front-end
	 * would send back the value as a custom header.<br>
	 * <br>
	 * 
	 * In this method we create a filter that is applied to the web security as
	 * follows:
	 * <ol>
	 * <li>Spring security provides the CSRF token value as a request attribute;
	 * so we extract it from there.</li>
	 * <li>If we have the token, Angular wants the cookie name to be
	 * "XSRF-TOKEN". So we add the cookie if it's not there and set the path for
	 * the cookie to be "/" which is root. In more complicated cases, this might
	 * have to be the context root of the api gateway.</li>
	 * <li>We forward the request to the next filter in the chain</li>
	 * </ol>
	 * 
	 * The request-to-cookie filter that we add needs to be after the
	 * <code>csrf()</code> filter so that the request attribute for CsrfToken
	 * has been already added before we start to process it.
	 * 
	 * @return
	 */
	private Filter createCSRFHeaderFilter() {
		return new OncePerRequestFilter() {
			@Override
			protected void doFilterInternal(HttpServletRequest request,
					HttpServletResponse response, FilterChain filterChain)
					throws ServletException, IOException {
				CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class
						.getName());
				if (csrf != null) {
					Cookie cookie = WebUtils.getCookie(request, CSRF_COOKIE_NAME);
					String token = csrf.getToken();
					if (cookie == null || token != null
							&& !token.equals(cookie.getValue())) {
						cookie = new Cookie(CSRF_COOKIE_NAME, token);
						cookie.setPath("/");
						response.addCookie(cookie);
					}
				}
				filterChain.doFilter(request, response);
			}
		};
	}

	/**
	 * Angular sends the CSRF token in a custom header named "X-XSRF-TOKEN"
	 * rather than the default "X-CSRF-TOKEN" that Spring security expects.
	 * Hence we are now telling Spring security to expect the token in the
	 * "X-XSRF-TOKEN" header.<br><br>
	 * 
	 * This customization is added to the <code>csrf()</code> filter.
	 * 
	 * @return
	 */
	private CsrfTokenRepository getCSRFTokenRepository() {
		HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
		repository.setHeaderName(CSRF_ANGULAR_HEADER_NAME);
		return repository;
	}
}
