package com.github.vbauer.houdini.ext.spring;

import com.github.vbauer.houdini.service.ObjectConverterRegistry;
import com.github.vbauer.houdini.service.ObjectConverterService;
import com.github.vbauer.houdini.service.impl.ObjectConverterRegistryImpl;
import com.github.vbauer.houdini.service.impl.ObjectConverterServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Vladislav Bauer
 */

@Configuration
public class ObjectConverterConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ObjectConverterRegistry objectConverterRegistry() {
        return new ObjectConverterRegistryImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public ObjectConverterService objectConverterService(
        final ObjectConverterRegistry objectConverterRegistry
    ) {
        return new ObjectConverterServiceImpl(objectConverterRegistry);
    }

    @Bean
    @ConditionalOnMissingBean
    public ObjectConverterBeanPostProcessor objectConverterBeanPostProcessor(
        final ObjectConverterService objectConverterService
    ) {
        return new ObjectConverterBeanPostProcessor(objectConverterService);
    }

}
