package org.moserp.common.modules;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = { ModuleRegistry.class })
public class ModuleTestConfiguration {

}
