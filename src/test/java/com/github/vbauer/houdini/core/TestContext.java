package com.github.vbauer.houdini.core;

import com.github.vbauer.houdini.processor.ObjectConverterBeanPostProcessor;
import com.github.vbauer.houdini.service.ObjectConverterService;
import com.github.vbauer.houdini.service.ObjectConverterServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Vladislav Bauer
 */

@Configuration
@ComponentScan(basePackages = "com.github.vbauer.houdini")
public class TestContext {

    @Bean
    public ObjectConverterService objectConverterService() {
        return new ObjectConverterServiceImpl();
    }

    @Bean
    public ObjectConverterBeanPostProcessor objectConverterBeanPostProcessor() {
        return new ObjectConverterBeanPostProcessor(objectConverterService());
    }

}
