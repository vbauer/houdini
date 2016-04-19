package com.github.vbauer.houdini.ext.spring;

import com.github.vbauer.houdini.service.ObjectConverterService;
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
    public ObjectConverterService objectConverterService() {
        return new ObjectConverterServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public ObjectConverterBeanPostProcessor objectConverterBeanPostProcessor(
        final ObjectConverterService objectConverterService
    ) {
        return new ObjectConverterBeanPostProcessor(objectConverterService);
    }

}
