package org.moserp;

import org.moserp.common.converter.*;
import org.moserp.common.security.SpringSecurityAuditorAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableMongoAuditing
@EnableMongoRepositories
public class MongoDbConfiguration {

    @Bean
    public AuditorAware<String> myAuditorProvider() {
        return new SpringSecurityAuditorAware();
    }

    @Bean
    @Primary
    public CustomConversions customConversions() {
        List<Converter<?, ?>> converterList = new ArrayList<Converter<?, ?>>();
        converterList.add(new BigDecimalWrapperToStringConverter());
        converterList.add(new StringToQuantityConverter());
        converterList.add(new StringToPriceConverter());
        converterList.add(new RestUriToStringConverter());
        converterList.add(new StringToRestUriConverter());
        return new CustomConversions(converterList);
    }

}
