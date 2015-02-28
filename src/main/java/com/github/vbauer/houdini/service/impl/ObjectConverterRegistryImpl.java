package com.github.vbauer.houdini.service.impl;

import com.github.vbauer.houdini.exception.DuplicatedObjectConverterException;
import com.github.vbauer.houdini.exception.MissedObjectConverterException;
import com.github.vbauer.houdini.model.ObjectConverterInfoKey;
import com.github.vbauer.houdini.model.ObjectConverterInfoValue;
import com.github.vbauer.houdini.service.ObjectConverterRegistry;
import com.github.vbauer.houdini.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Vladislav Bauer
 */

public class ObjectConverterRegistryImpl implements ObjectConverterRegistry {

    private final ConcurrentMap<ObjectConverterInfoKey<?>, ObjectConverterInfoValue<?>> converters =
        new ConcurrentHashMap<ObjectConverterInfoKey<?>, ObjectConverterInfoValue<?>>();



    @Override
    @SuppressWarnings("unchecked")
    public void registerConverter(final Object bean, final Method method) {
        final Class<?> returnType = method.getReturnType();
        final Class<?>[] parameterTypes = method.getParameterTypes();

        @SuppressWarnings("rawtypes")
        final ObjectConverterInfoValue<?> result = converters.putIfAbsent(
            new ObjectConverterInfoKey(returnType, parameterTypes),
            new ObjectConverterInfoValue<Object>(method, bean)
        );

        if (result != null) {
            throw new DuplicatedObjectConverterException(returnType, parameterTypes);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public  <RESULT> ObjectConverterInfoValue<RESULT> findConverter(
        final Class<RESULT> resultClass, final Object... sources
    ) {
        final Class<?>[] sourceClasses = ReflectionUtils.getClassesWithoutProxies(sources);
        final ObjectConverterInfoKey<RESULT> key = new ObjectConverterInfoKey<RESULT>(resultClass, sourceClasses);
        final ObjectConverterInfoValue<RESULT> value = (ObjectConverterInfoValue<RESULT>) converters.get(key);

        if (value == null) {
            throw new MissedObjectConverterException(resultClass, sourceClasses);
        }

        return value;
    }

}
