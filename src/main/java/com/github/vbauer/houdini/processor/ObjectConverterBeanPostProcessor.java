package com.github.vbauer.houdini.processor;

import com.github.vbauer.houdini.annotation.ObjectConverter;
import com.github.vbauer.houdini.service.ObjectConverterRegistry;
import com.github.vbauer.houdini.service.ObjectConverterService;
import com.github.vbauer.houdini.util.ReflectionUtils;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Method;

/**
 * @author Vladislav Bauer
 */

public class ObjectConverterBeanPostProcessor implements BeanPostProcessor {

    private final ObjectConverterService converterService;


    public ObjectConverterBeanPostProcessor(final ObjectConverterService converterService) {
        this.converterService = converterService;
    }


    @Override
    public Object postProcessBeforeInitialization(final Object bean, final String beanName) {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(final Object bean, final String beanName) {
        final Class<?> beanClass = ReflectionUtils.getClassWithoutProxies(bean);

        org.springframework.util.ReflectionUtils.doWithMethods(
                beanClass,
                new org.springframework.util.ReflectionUtils.MethodCallback() {
                    @Override
                    public void doWith(final Method method) throws IllegalAccessException {
                        final ObjectConverterRegistry registry = converterService.getConverterRegistry();
                        registry.registerConverter(bean, method);
                    }
                },
                new org.springframework.util.ReflectionUtils.MethodFilter() {
                    @Override
                    public boolean matches(final Method method) {
                        final boolean isDeclaredMethod = method.getDeclaringClass() == beanClass;
                        final boolean isProxyMethod = method.isBridge() || method.isSynthetic();
                        final boolean hasAnnotation = method.getAnnotation(ObjectConverter.class) != null
                                || beanClass.getAnnotation(ObjectConverter.class) != null;

                        return !isProxyMethod && isDeclaredMethod && hasAnnotation;
                    }
                }
        );

        return bean;
    }

}
