package com.github.vbauer.houdini.ext.spring;

import com.github.vbauer.houdini.service.ObjectConverterRegistry;
import com.github.vbauer.houdini.service.ObjectConverterService;
import com.github.vbauer.houdini.service.impl.ObjectConverterRegistryImpl;
import com.github.vbauer.houdini.service.impl.ObjectConverterServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Auto-configuration component for Spring Boot.
 *
 * @author Vladislav Bauer
 */

@Configuration
public class ObjectConverterConfiguration {

    /**
     * Create {@link ObjectConverterRegistry} and register it in application context.
     *
     * @return object converter registry
     */
    @Bean
    @ConditionalOnMissingBean
    public ObjectConverterRegistry objectConverterRegistry() {
        return new ObjectConverterRegistryImpl();
    }

    /**
     * Create {@link ObjectConverterService} and register it in application context.
     *
     * @param objectConverterRegistry object converter registry
     * @return object converter service
     */
    @Bean
    @ConditionalOnMissingBean
    public ObjectConverterService objectConverterService(
        final ObjectConverterRegistry objectConverterRegistry
    ) {
        return new ObjectConverterServiceImpl(objectConverterRegistry);
    }

    /**
     * Create {@link ObjectConverterBeanPostProcessor} and register it in application context.
     *
     * @param objectConverterService object converter service
     * @return bean post processor to collect object converters
     */
    @Bean
    @ConditionalOnMissingBean
    public ObjectConverterBeanPostProcessor objectConverterBeanPostProcessor(
        final ObjectConverterService objectConverterService
    ) {
        return new ObjectConverterBeanPostProcessor(objectConverterService);
    }

}
