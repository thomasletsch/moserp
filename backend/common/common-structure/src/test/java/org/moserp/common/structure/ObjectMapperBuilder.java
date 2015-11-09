package org.moserp.common.structure;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.moserp.common.domain.Price;
import org.moserp.common.domain.Quantity;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.zalando.jackson.datatype.money.MoneyModule;

public class ObjectMapperBuilder {

    public ObjectMapper build() {
        ObjectMapper mapper = new ObjectMapper();
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
        module.addSerializer(Quantity.class, new BigDecimalWrapperSerializer());
        module.addSerializer(Price.class, new BigDecimalWrapperSerializer());
        mapper.registerModule(module);
    }

}
