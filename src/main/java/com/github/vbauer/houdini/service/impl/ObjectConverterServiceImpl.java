package com.github.vbauer.houdini.service.impl;

import com.github.vbauer.houdini.model.ObjectConverterInfoValue;
import com.github.vbauer.houdini.service.ObjectConverterRegistry;
import com.github.vbauer.houdini.service.ObjectConverterService;
import com.github.vbauer.houdini.util.CollectionUtils;
import com.github.vbauer.houdini.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.*;

/**
 * @author Vladislav Bauer 
 */

public class ObjectConverterServiceImpl implements ObjectConverterService {

    private final ObjectConverterRegistry converterRegistry;


    public ObjectConverterServiceImpl() {
        this(new ObjectConverterRegistryImpl());
    }

    public ObjectConverterServiceImpl(final ObjectConverterRegistry converterRegistry) {
        this.converterRegistry = converterRegistry;
    }


    @Override
    public ObjectConverterRegistry getConverterRegistry() {
        return converterRegistry;
    }

    @Override
    public <RESULT> RESULT convert(final Class<RESULT> resultClass, final Object... sources) {
        final ObjectConverterRegistry registry = getConverterRegistry();
        final ObjectConverterInfoValue<RESULT> converterInfo = registry.findConverter(resultClass, sources);
        return processObject(converterInfo, sources);
    }

    @Override
    public <RESULT, SOURCE> Set<RESULT> convert(final Class<RESULT> resultClass, final Set<SOURCE> sources) {
        return processObjects(sources, resultClass, new HashSet<RESULT>());
    }

    @Override
    public <RESULT, SOURCE> List<RESULT> convert(final Class<RESULT> resultClass, final List<SOURCE> sources) {
        return processObjects(sources, resultClass, new ArrayList<RESULT>());
    }

    @Override
    public <RESULT, SOURCE> Object convertToOneOrList(final Class<RESULT> resultClass, final List<SOURCE> sources) {
        return CollectionUtils.oneOrMany(processObjects(sources, resultClass, new ArrayList<RESULT>()));
    }

    @Override
    public <RESULT, SOURCE> Object convertToOneOrSet(final Class<RESULT> resultClass, final Set<SOURCE> sources) {
        return CollectionUtils.oneOrMany(processObjects(sources, resultClass, new HashSet<RESULT>()));
    }


    /*
     * Internal API.
     */

    private <RESULT, COLLECTION extends Collection<RESULT>> COLLECTION processObjects(
        final Collection<?> sources, final Class<RESULT> resultClass, final COLLECTION result
    ) {
        if (!CollectionUtils.isEmpty(sources)) {
            for (final Object source : sources) {
                final ObjectConverterRegistry registry = getConverterRegistry();
                final ObjectConverterInfoValue<RESULT> converterInfo = registry.findConverter(resultClass, source);

                result.add(processObject(converterInfo, source));
            }
        }
        return result;
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
            ReflectionUtils.handleReflectionException(ex);
            return null;
        }
    }

}
