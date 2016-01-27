package org.moserp.common.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.mvc.TypeConstrainedMappingJackson2HttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
@ComponentScan(basePackageClasses = {ObjectMapperCustomizer.class})
public class TestRestConfiguration {

    private String userName = "user";
    private String password = "password";

    @Bean
    public RestTemplate restTemplate(ObjectMapper objectMapper, TypeConstrainedMappingJackson2HttpMessageConverter halJacksonHttpMessageConverter) {
        RestTemplate restTemplate = new TestRestTemplate(userName, password);
        filterXmlConverters(restTemplate);
        correctJacksonConverter(objectMapper, halJacksonHttpMessageConverter, restTemplate);
        return restTemplate;
    }

    private void filterXmlConverters(RestTemplate restTemplate) {
        Stream<HttpMessageConverter<?>> messageConverterStream = restTemplate.getMessageConverters().stream();
        Predicate<HttpMessageConverter<?>> noXmlConverters = httpMessageConverter -> !(httpMessageConverter instanceof Jaxb2RootElementHttpMessageConverter) && !(httpMessageConverter instanceof MappingJackson2XmlHttpMessageConverter);
        List<HttpMessageConverter<?>> filtered = messageConverterStream.filter(noXmlConverters).collect(Collectors.toList());
        restTemplate.setMessageConverters(filtered);
    }

    private void correctJacksonConverter(ObjectMapper objectMapper,
                                         TypeConstrainedMappingJackson2HttpMessageConverter halJacksonHttpMessageConverter,
                                         RestTemplate restTemplate) {
        Stream<HttpMessageConverter<?>> jacksonConverters = restTemplate.getMessageConverters().stream().filter(httpMessageConverter -> {
            return MappingJackson2HttpMessageConverter.class.isAssignableFrom(httpMessageConverter.getClass());
        });
        jacksonConverters.forEach(httpMessageConverter -> ((MappingJackson2HttpMessageConverter) httpMessageConverter).setObjectMapper(objectMapper));
        restTemplate.getMessageConverters().add(halJacksonHttpMessageConverter);
    }

}
