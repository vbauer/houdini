package com.github.vbauer.houdini.ext.spring;

import com.github.vbauer.houdini.service.ObjectConverterRegistry;
import com.github.vbauer.houdini.service.ObjectConverterService;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @author Vladislav Bauer
 */

public class ObjectConverterBeanPostProcessor implements BeanPostProcessor {

    private final ObjectConverterService converterService;


    public ObjectConverterBeanPostProcessor(final ObjectConverterService converterService) {
        this.converterService = converterService;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Object postProcessBeforeInitialization(final Object bean, final String beanName) {
        return bean;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object postProcessAfterInitialization(final Object bean, final String beanName) {
        final ObjectConverterRegistry registry = converterService.getConverterRegistry();
        registry.registerConverters(bean);
        return bean;
    }

}
