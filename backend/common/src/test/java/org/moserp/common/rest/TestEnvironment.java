package org.moserp.common.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class TestEnvironment {

    @Autowired(required = false)
    ConfigurableApplicationContext context;

    public int getPort() {
        return ((AnnotationConfigEmbeddedWebApplicationContext)context).getEmbeddedServletContainer().getPort();
    }

    public String getUsername() {
        return "user";
    }

    public String getPassword() {
        return "password";
    }

    public String createRestUri(String resource) {
        return "http://localhost:" + getPort() + "/" + resource;
    }

}
