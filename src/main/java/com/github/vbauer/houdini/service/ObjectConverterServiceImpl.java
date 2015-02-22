package com.github.vbauer.houdini.service;

import com.github.vbauer.houdini.exception.DuplicatedObjectConverterException;
import com.github.vbauer.houdini.exception.MissedObjectConverterException;
import com.github.vbauer.houdini.model.ObjectConverterInfoKey;
import com.github.vbauer.houdini.model.ObjectConverterInfoValue;
import com.github.vbauer.houdini.util.HoudiniUtils;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Vladislav Bauer 
 */

public class ObjectConverterServiceImpl implements ObjectConverterService {

    private final ConcurrentMap<ObjectConverterInfoKey<?>, ObjectConverterInfoValue<?>> registeredConverters =
        new ConcurrentHashMap<ObjectConverterInfoKey<?>, ObjectConverterInfoValue<?>>();


    @Override
    @SuppressWarnings("unchecked")
    public void registerConverterMethod(final Object bean, final Method method) {
        final Class<?> returnType = method.getReturnType();
        final Class<?>[] parameterTypes = method.getParameterTypes();

        final ObjectConverterInfoValue<?> result = registeredConverters.putIfAbsent(
            new ObjectConverterInfoKey(returnType, parameterTypes),
            new ObjectConverterInfoValue<Object>(method, bean)
        );

        if (result != null) {
            throw new DuplicatedObjectConverterException(returnType, parameterTypes);
        }
    }

    @Override
    public <RESULT> RESULT convert(final Class<RESULT> resultClass, final Object... sources) {
        final ObjectConverterInfoValue<RESULT> converterInfo = findConverterInfo(resultClass, sources);
        return processObject(converterInfo, sources);
    }

    @Override
    public <RESULT, SOURCE> Set<RESULT> convert(final Class<RESULT> resultClass, final Set<SOURCE> sources) {
        final Set<RESULT> result = new HashSet<RESULT>();
        processObjects(sources, resultClass, result);
        return result;
    }

    @Override
    public <RESULT, SOURCE> List<RESULT> convert(final Class<RESULT> resultClass, final List<SOURCE> sources) {
        final List<RESULT> result = new ArrayList<RESULT>();
        processObjects(sources, resultClass, result);
        return result;
    }

    @Override
    public <RESULT, SOURCE> Object convertToOneOrList(final Class<RESULT> resultClass, final List<SOURCE> sources) {
        final List<RESULT> result = new ArrayList<RESULT>();
        processObjects(sources, resultClass, result);
        return HoudiniUtils.oneOrMany(result);
    }

    @Override
    public <RESULT, SOURCE> Object convertToOneOrSet(final Class<RESULT> resultClass, final Set<SOURCE> sources) {
        final Set<RESULT> result = new HashSet<RESULT>();
        processObjects(sources, resultClass, result);
        return HoudiniUtils.oneOrMany(result);
    }


    /*
     * Internal API.
     */

    @SuppressWarnings("unchecked")
    private <RESULT> ObjectConverterInfoValue<RESULT> findConverterInfo(
        final Class<RESULT> resultClass, final Object... sources
    ) {
        final Class<?>[] sourceClasses = HoudiniUtils.getClassesWithoutProxies(sources);
        final ObjectConverterInfoKey<RESULT> key = new ObjectConverterInfoKey<RESULT>(resultClass, sourceClasses);
        final ObjectConverterInfoValue<RESULT> value = (ObjectConverterInfoValue<RESULT>) registeredConverters.get(key);
        
        if (value == null) {
            throw new MissedObjectConverterException(resultClass, sourceClasses);
        }
        
        return value;
    }

    private <RESULT> void processObjects(
        final Collection<?> sources, final Class<RESULT> resultClass, final Collection<RESULT> result
    ) {
        if (!CollectionUtils.isEmpty(sources)) {
            for (final Object source : sources) {
                final ObjectConverterInfoValue<RESULT> converterInfo = findConverterInfo(resultClass, source);
                result.add(processObject(converterInfo, source));
            }
        }
    }

    @SuppressWarnings("unchecked")
    private <RESULT> RESULT processObject(
        final ObjectConverterInfoValue<RESULT> converterInfo, final Object... sources
    ) {
        final Method method = converterInfo.getMethod();
        final Object object = converterInfo.getObject();
        try {
            return (RESULT) method.invoke(object, sources);
        } catch (final Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
