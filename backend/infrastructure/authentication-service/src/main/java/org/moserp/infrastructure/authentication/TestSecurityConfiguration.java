package org.moserp.infrastructure.authentication;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({"local", "test"})
public class TestSecurityConfiguration {

    @Bean
    public ContextStartedListener contextStartedListener() {
        return new ContextStartedListener();
    }
}
