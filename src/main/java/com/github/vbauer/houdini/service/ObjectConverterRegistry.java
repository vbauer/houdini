package com.github.vbauer.houdini.service;

import com.github.vbauer.houdini.model.ObjectConverterInfoValue;

/**
 * Registry to store information about available converters.
 *
 * @author Vladislav Bauer
 */

public interface ObjectConverterRegistry {

    /**
     * Register a new converter in the store.
     *
     * @param bean object with converter methods
     */
    void registerConverters(Object bean);

    /**
     * Find converter in the registry.
     *
     * @param resultClass class of result/output parameter
     * @param sources array with classes for input parameters
     * @param <R> type of the result class
     * @return information about available converter or MissedObjectConverterException otherwise
     */
    <R> ObjectConverterInfoValue<R> findConverter(Class<R> resultClass, Object... sources);

}
