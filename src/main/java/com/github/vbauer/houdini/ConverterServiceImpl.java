package com.github.vbauer.houdini;

import com.github.vbauer.houdini.model.ConverterInfoKey;
import com.github.vbauer.houdini.model.ConverterInfoValue;
import com.github.vbauer.houdini.util.HoudiniUtils;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Vladislav Bauer 
 */

public class ConverterServiceImpl implements ConverterService {

    private final ConcurrentMap<ConverterInfoKey<?>, ConverterInfoValue<?>> registeredConverters =
        new ConcurrentHashMap<ConverterInfoKey<?>, ConverterInfoValue<?>>();


    @Override
    public <RESULT> void registerConverter(
        final Class<RESULT> resultClass, final List<Class<?>> sourceClasses,
        final Method method, final Object object
    ) {
        final ConverterInfoValue<?> result = registeredConverters.putIfAbsent(
            new ConverterInfoKey<RESULT>(sourceClasses, resultClass),
            new ConverterInfoValue<Object>(method, object)
        );

        if (result != null) {
            throw new IllegalStateException(String.format(
                "Can't register two converters with the target class %s", resultClass.getSimpleName()
            ));
        }
    }

    @Override
    public <RESULT> RESULT convert(final Class<RESULT> resultClass, final Object... sources) {
        final ConverterInfoValue<RESULT> converterInfo = findConverterInfo(resultClass, sources);
        return processObject(converterInfo, sources);
    }

    @Override
    public <RESULT, SOURCE> Set<RESULT> convert(
        final Class<RESULT> resultClass, final Set<SOURCE> sources
    ) {
        final Set<RESULT> result = new HashSet<RESULT>();
        processObjects(sources, resultClass, result);
        return result;
    }

    @Override
    public <RESULT, SOURCE> List<RESULT> convert(
        final Class<RESULT> resultClass, final List<SOURCE> sources
    ) {
        final List<RESULT> result = new ArrayList<RESULT>();
        processObjects(sources, resultClass, result);
        return result;
    }

    @Override
    public <RESULT, SOURCE> Object convertToOneOrList(
        final Class<RESULT> resultClass, final List<SOURCE> sources
    ) {
        final List<RESULT> result = new ArrayList<RESULT>();
        processObjects(sources, resultClass, result);
        return HoudiniUtils.oneOrMany(result);
    }

    @Override
    public <RESULT, SOURCE> Object convertToOneOrSet(
        final Class<RESULT> resultClass, final Set<SOURCE> sources
    ) {
        final Set<RESULT> result = new HashSet<RESULT>();
        processObjects(sources, resultClass, result);
        return HoudiniUtils.oneOrMany(result);
    }


    @SuppressWarnings("unchecked")
    private <RESULT> ConverterInfoValue<RESULT> findConverterInfo(
        final Class<RESULT> resultClass, final Object... sources
    ) {
        final List<Class<?>> sourceClasses = new ArrayList<Class<?>>();
        for (final Object source : sources) {
            sourceClasses.add(HoudiniUtils.getClassWithoutProxies(source));
        }

        final ConverterInfoKey<RESULT> key = new ConverterInfoKey<RESULT>(sourceClasses, resultClass);
        final ConverterInfoValue<RESULT> value = (ConverterInfoValue<RESULT>) registeredConverters.get(key);
        
        if (value == null) {
            throw new IllegalArgumentException(String.format("Can't find needed object converter (key: %s)", key));
        }
        
        return value;
    }

    private <RESULT> void processObjects(
        final Collection<?> sources, final Class<RESULT> resultClass,
        final Collection<RESULT> result
    ) {
        if (!CollectionUtils.isEmpty(sources)) {
            for (final Object source : sources) {
                final ConverterInfoValue<RESULT> converterInfo = findConverterInfo(resultClass, source);
                result.add(processObject(converterInfo, source));
            }
        }
    }

    @SuppressWarnings("unchecked")
    private <RESULT> RESULT processObject(
        final ConverterInfoValue<RESULT> converterInfo, final Object... sources
    ) {
        final Method method = converterInfo.getMethod();
        final Object object = converterInfo.getObject();
        try {
            return (RESULT) method.invoke(object, sources);
        } catch (final IllegalAccessException ex) {
            throw new RuntimeException(ex);
        } catch (final InvocationTargetException ex) {
            throw new RuntimeException(ex);
        }
    }
    
}
