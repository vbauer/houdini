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
     * @param <R> type of the result class
     * @return converted object
     */
    <R> R convert(Class<R> resultClass, Object... sources);

    /**
     * Convert a set of objects to the set of objects with the different type.
     *
     * @param resultClass result class
     * @param sources set with input objects
     * @param <R> type of the result class
     * @param <S> type of the input parameters
     * @return set of converted objects
     */
    <R, S> Set<R> convert(Class<R> resultClass, Set<S> sources);

    /**
     * Convert a list of objects to the list of objects with the different type.
     *
     * @param resultClass result class
     * @param sources list with input objects
     * @param <R> type of the result class
     * @param <S> type of the input parameters
     * @return list of converted objects
     */
    <R, S> List<R> convert(Class<R> resultClass, List<S> sources);

    /**
     * Convert a set of objects to the set of objects with the different type.
     * If the result set contains a single element it returns only this element instead of set.
     *
     * @param resultClass result class
     * @param sources set with input objects
     * @param <R> type of the result class
     * @param <S> type of the input parameters
     * @return set of objects or single element
     */
    <R, S> Object convertToOneOrSet(Class<R> resultClass, Set<S> sources);

    /**
     * Convert a list of objects to the list of objects with the different type.
     * If the result list contains a single element it returns only this element instead of list.
     *
     * @param resultClass result class
     * @param sources list with input objects
     * @param <R> type of the result class
     * @param <S> type of the input parameters
     * @return list of converted objects or single element
     */
    <R, S> Object convertToOneOrList(Class<R> resultClass, List<S> sources);

}
