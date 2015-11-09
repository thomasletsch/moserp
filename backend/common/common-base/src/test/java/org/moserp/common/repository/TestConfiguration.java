package org.moserp.common.repository;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan(basePackageClasses = { SequenceService.class })
@EnableMongoRepositories(basePackageClasses = { TestEntityRepository.class })
public class TestConfiguration {

}
