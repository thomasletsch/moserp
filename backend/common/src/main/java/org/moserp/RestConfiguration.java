package org.moserp;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.isrsal.logging.LoggingFilter;
import cz.jirutka.spring.exhandler.support.HttpMessageConverterUtils;
import org.moserp.common.domain.IdentifiableEntity;
import org.moserp.common.modules.ModuleRegistry;
import org.moserp.common.rest.ObjectMapperCustomizer;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.*;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.hateoas.config.EnableEntityLinks;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.zalando.jackson.datatype.money.MoneyModule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Configuration
@EnableEntityLinks
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
@ComponentScan(basePackageClasses = {ObjectMapperCustomizer.class, ModuleRegistry.class})
public class RestConfiguration extends RepositoryRestConfigurerAdapter {

    @Bean
    public FilterRegistrationBean loggingFilterRegistration() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(loggingFilter());
        registrationBean.setUrlPatterns(Collections.singletonList("/*"));
        registrationBean.setName("LoggingFilter");
        return registrationBean;
    }

    @Bean
    public LoggingFilter loggingFilter() {
        return new LoggingFilter();
    }

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(true);
        provider.addIncludeFilter(new AssignableTypeFilter(IdentifiableEntity.class));
        Set<BeanDefinition> components = provider.findCandidateComponents(this.getClass().getPackage().getName());
        List<Class<?>> classes = new ArrayList<>();

        components.forEach(component -> {
            try {
                classes.add(Class.forName(component.getBeanClassName()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        config.exposeIdsFor(classes.toArray(new Class[classes.size()]));
    }

    @Override
    public void configureExceptionHandlerExceptionResolver(ExceptionHandlerExceptionResolver exceptionResolver) {
        exceptionResolver.setMessageConverters(HttpMessageConverterUtils.getDefaultHttpMessageConverters());
    }

    @Bean
    public Module moneyModule() {
        return new MoneyModule();
    }

    @Bean
    public Module jsr310Module() {
        return new JavaTimeModule();
    }
}
