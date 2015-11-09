package org.moserp;

import org.moserp.common.rest.TestRestConfiguration;
import org.moserp.common.security.TestSecurityConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({ TestSecurityConfiguration.class, MongoDbConfiguration.class, RestConfiguration.class, TestRestConfiguration.class })
public class TestWebApplication {


}
