package com.github.vbauer.houdini.ext.spring;

import com.github.vbauer.houdini.service.ObjectConverterService;
import com.github.vbauer.houdini.service.impl.ObjectConverterServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Vladislav Bauer
 */

@Configuration
@ComponentScan(basePackages = "com.github.vbauer.houdini")
public class SpringTestContext {

    @Bean
    public ObjectConverterService objectConverterService() {
        return new ObjectConverterServiceImpl();
    }

    @Bean
    public ObjectConverterBeanPostProcessor objectConverterBeanPostProcessor() {
        return new ObjectConverterBeanPostProcessor(objectConverterService());
    }

}
