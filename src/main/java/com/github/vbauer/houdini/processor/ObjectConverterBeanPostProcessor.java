package com.github.vbauer.houdini.processor;

import com.github.vbauer.houdini.annotation.ObjectConverter;
import com.github.vbauer.houdini.service.ObjectConverterService;
import com.github.vbauer.houdini.util.HoudiniUtils;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;

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
        final Class<?> beanClass = HoudiniUtils.getClassWithoutProxies(bean);

        ReflectionUtils.doWithMethods(
            beanClass,
            new ReflectionUtils.MethodCallback() {
                @Override
                public void doWith(final Method method) throws IllegalAccessException {
                    converterService.registerConverterMethod(bean, method);
                }
            },
            new ReflectionUtils.MethodFilter() {
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