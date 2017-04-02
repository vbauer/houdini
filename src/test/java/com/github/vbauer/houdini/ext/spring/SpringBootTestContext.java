package com.github.vbauer.houdini.ext.spring;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Vladislav Bauer
 */

@Configuration
@ComponentScan(basePackages = "com.github.vbauer.houdini")
@ImportAutoConfiguration(ObjectConverterConfiguration.class)
class SpringBootTestContext {
}
