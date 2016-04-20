package com.github.vbauer.houdini.service.impl;

import com.github.vbauer.houdini.model.ObjectConverterInfoValue;
import com.github.vbauer.houdini.service.ObjectConverterRegistry;
import com.github.vbauer.houdini.service.ObjectConverterService;
import com.github.vbauer.houdini.util.CollectionUtils;
import com.github.vbauer.houdini.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.*;

/**
 * {@link ObjectConverterService}.
 *
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


    /**
     * {@inheritDoc}
     */
    @Override
    public ObjectConverterRegistry getConverterRegistry() {
        return converterRegistry;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <R> R convert(final Class<R> resultClass, final Object... sources) {
        final ObjectConverterRegistry registry = getConverterRegistry();
        final ObjectConverterInfoValue<R> converterInfo = registry.findConverter(resultClass, sources);
        return processObject(converterInfo, sources);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <R, S> Set<R> convert(final Class<R> resultClass, final Set<S> sources) {
        return processObjects(sources, resultClass, new HashSet<R>());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <R, S> List<R> convert(final Class<R> resultClass, final List<S> sources) {
        return processObjects(sources, resultClass, new ArrayList<R>());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <R, S> Object convertToOneOrList(final Class<R> resultClass, final List<S> sources) {
        return CollectionUtils.oneOrMany(processObjects(sources, resultClass, new ArrayList<R>()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <R, S> Object convertToOneOrSet(final Class<R> resultClass, final Set<S> sources) {
        return CollectionUtils.oneOrMany(processObjects(sources, resultClass, new HashSet<R>()));
    }


    /*
     * Internal API.
     */

    private <R, C extends Collection<R>> C processObjects(
        final Collection<?> sources, final Class<R> resultClass, final C result
    ) {
        if (!CollectionUtils.isEmpty(sources)) {
            for (final Object source : sources) {
                final ObjectConverterRegistry registry = getConverterRegistry();
                final ObjectConverterInfoValue<R> converterInfo = registry.findConverter(resultClass, source);

                result.add(processObject(converterInfo, source));
            }
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    private <R> R processObject(
        final ObjectConverterInfoValue<R> converterInfo, final Object... sources
    ) {
        final Method method = converterInfo.getMethod();
        final Object object = converterInfo.getObject();

        try {
            return (R) method.invoke(object, sources);
        } catch (final Exception ex) {
            ReflectionUtils.handleReflectionException(ex);
            return null;
        }
    }

}
