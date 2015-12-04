package org.moserp.common.rest;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.moserp.common.converter.BigDecimalWrapperToStringConverter;
import org.moserp.common.converter.RestUriToStringConverter;
import org.moserp.common.domain.Price;
import org.moserp.common.domain.Quantity;
import org.moserp.common.domain.RestUri;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.stereotype.Component;
import org.zalando.jackson.datatype.money.MoneyModule;

@Component
public class ObjectMapperCustomizer implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        if (!(bean instanceof ObjectMapper)) {
            return bean;
        }

        ObjectMapper mapper = (ObjectMapper) bean;
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
        mapper.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        registerQuantitySerializer(mapper);
        mapper.registerModules(new MoneyModule(), new JavaTimeModule(), new Jackson2HalModule());

        return mapper;
    }

    private void registerQuantitySerializer(ObjectMapper mapper) {
        SimpleModule module = new SimpleModule();
        module.addSerializer(Quantity.class, new WithConverterSerializer<>(new BigDecimalWrapperToStringConverter()));
        module.addSerializer(Price.class, new WithConverterSerializer<>(new BigDecimalWrapperToStringConverter()));
        module.addSerializer(RestUri.class, new WithConverterSerializer<>(new RestUriToStringConverter()));
        mapper.registerModule(module);
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
