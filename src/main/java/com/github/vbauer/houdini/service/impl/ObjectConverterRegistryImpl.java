package com.github.vbauer.houdini.service.impl;

import com.github.vbauer.houdini.annotation.ObjectConverter;
import com.github.vbauer.houdini.exception.DuplicatedObjectConverterException;
import com.github.vbauer.houdini.exception.MissedObjectConverterException;
import com.github.vbauer.houdini.model.ObjectConverterInfoKey;
import com.github.vbauer.houdini.model.ObjectConverterInfoValue;
import com.github.vbauer.houdini.service.ObjectConverterRegistry;
import com.github.vbauer.houdini.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * {@link ObjectConverterRegistry}.
 *
 * @author Vladislav Bauer
 */

public class ObjectConverterRegistryImpl implements ObjectConverterRegistry {

    private final ConcurrentMap<ObjectConverterInfoKey<?>, ObjectConverterInfoValue<?>> converters =
        new ConcurrentHashMap<>();


    /**
     * {@inheritDoc}
     */
    @Override
    public void registerConverters(final Object bean) {
        final Class<?> beanClass = ReflectionUtils.getClassWithoutProxies(bean);

        if (beanClass != null) {
            final Method[] methods = beanClass.getDeclaredMethods();
            for (final Method method : methods) {
                if (isConverterMethod(beanClass, method)) {
                    registerConverter(bean, method);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public  <R> ObjectConverterInfoValue<R> findConverter(
        final Class<R> resultClass, final Object... sources
    ) {
        final Class<?>[] sourceClasses = ReflectionUtils.getClassesWithoutProxies(sources);
        final ObjectConverterInfoKey<R> key = new ObjectConverterInfoKey<>(resultClass, sourceClasses);
        final ObjectConverterInfoValue<R> value = (ObjectConverterInfoValue<R>) converters.get(key);

        if (value == null) {
            throw new MissedObjectConverterException(resultClass, sourceClasses);
        }

        return value;
    }


    /*
     Internal API.
     */

    private boolean isConverterMethod(final Class<?> beanClass, final Method method) {
        final boolean isDeclaredMethod = method.getDeclaringClass() == beanClass;
        final boolean isProxyMethod = method.isBridge() || method.isSynthetic();
        final boolean hasAnnotation = method.getAnnotation(ObjectConverter.class) != null
                || beanClass.getAnnotation(ObjectConverter.class) != null;
        final boolean isPublic = Modifier.isPublic(method.getModifiers());

        return !isProxyMethod && isDeclaredMethod && hasAnnotation && isPublic;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void registerConverter(final Object bean, final Method method) {
        final Class<?> returnType = method.getReturnType();
        final Class<?>[] parameterTypes = method.getParameterTypes();

        final ObjectConverterInfoKey key = new ObjectConverterInfoKey(returnType, parameterTypes);
        final ObjectConverterInfoValue<Object> value = new ObjectConverterInfoValue<>(method, bean);
        final ObjectConverterInfoValue<?> result = converters.putIfAbsent(key, value);

        if (result != null) {
            throw new DuplicatedObjectConverterException(returnType, parameterTypes);
        }
    }

}
