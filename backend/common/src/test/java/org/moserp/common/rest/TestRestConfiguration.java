package org.moserp.common.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.mvc.TypeConstrainedMappingJackson2HttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.stream.Stream;

@Configuration
@ComponentScan(basePackageClasses = {ObjectMapperCustomizer.class})
public class TestRestConfiguration {

    private String userName = "user";
    private String password = "password";

    @Bean
    public RestTemplate restTemplate(ObjectMapper objectMapper, TypeConstrainedMappingJackson2HttpMessageConverter halJacksonHttpMessageConverter) {
        RestTemplate restTemplate = new TestRestTemplate(userName, password);
        Stream<HttpMessageConverter<?>> jacksonConverters = restTemplate.getMessageConverters().stream().filter(httpMessageConverter -> {
            return MappingJackson2HttpMessageConverter.class.isAssignableFrom(httpMessageConverter.getClass());
        });
        jacksonConverters.forEach(httpMessageConverter -> ((MappingJackson2HttpMessageConverter) httpMessageConverter).setObjectMapper(objectMapper));
        restTemplate.getMessageConverters().add(halJacksonHttpMessageConverter);
        return restTemplate;
    }

}
