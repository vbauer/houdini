package com.github.vbauer.houdini.service;

import java.util.List;
import java.util.Set;

/**
 * The main service to make conversions.
 *
 * @author Vladislav Bauer
 */

public interface ObjectConverterService {

    /**
     * Get the corresponding {@link ObjectConverterRegistry}.
     *
     * @return object converter registry
     */
    ObjectConverterRegistry getConverterRegistry();

    /**
     * Convert data to the result class.
     *
     * @param resultClass result class
     * @param sources array with input parameters for the corresponding converter
     * @param <RESULT> type of the result class
     * @return converted object
     */
    <RESULT> RESULT convert(Class<RESULT> resultClass, Object... sources);

    /**
     * Convert a set of objects to the set of objects with the different type.
     *
     * @param resultClass result class
     * @param sources set with input objects
     * @param <RESULT> type of the result class
     * @return set of converted objects
     */
    <RESULT, SOURCE> Set<RESULT> convert(Class<RESULT> resultClass, Set<SOURCE> sources);

    /**
     * Convert a list of objects to the list of objects with the different type.
     *
     * @param resultClass result class
     * @param sources list with input objects
     * @param <RESULT> type of the result class
     * @return list of converted objects
     */
    <RESULT, SOURCE> List<RESULT> convert(Class<RESULT> resultClass, List<SOURCE> sources);

    /**
     * Convert a set of objects to the set of objects with the different type.
     * If the result set contains a single element it returns only this element instead of set.
     *
     * @param resultClass result class
     * @param sources set with input objects
     * @param <RESULT> type of the result class
     * @return set of objects or single element
     */
    <RESULT, SOURCE> Object convertToOneOrSet(Class<RESULT> resultClass, Set<SOURCE> sources);

    /**
     * Convert a list of objects to the list of objects with the different type.
     * If the result list contains a single element it returns only this element instead of list.
     *
     * @param resultClass result class
     * @param sources list with input objects
     * @param <RESULT> type of the result class
     * @return list of converted objects or single element
     */
    <RESULT, SOURCE> Object convertToOneOrList(Class<RESULT> resultClass, List<SOURCE> sources);

}
