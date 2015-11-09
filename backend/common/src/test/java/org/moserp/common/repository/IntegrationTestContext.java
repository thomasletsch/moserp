package org.moserp.common.repository;

import org.moserp.MongoDbConfiguration;
import org.moserp.common.rest.TestEnvironment;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({MongoDbConfiguration.class})
public class IntegrationTestContext {

    @Bean
    public TestEnvironment testEnvironment() {
        return new TestEnvironment();
    }

}
