package com.github.vbauer.houdini.util;

import com.google.common.annotations.VisibleForTesting;

import java.util.Collection;

/**
 * Util-class to work with collections.
 *
 * @author Vladislav Bauer
 */

public final class CollectionUtils {

    private CollectionUtils() {
        throw new UnsupportedOperationException();
    }


    /**
     * Check if collection is an empty. Method is null-safe.
     *
     * @param collection collection with data
     * @return true if empty, false - otherwise.
     */
    public static boolean isEmpty(final Collection<?> collection) {
        return size(collection) == 0;
    }

    /**
     * Get a size of the given array. Method is null-safe.
     *
     * @param array array with data
     * @param <T> type of the array
     * @return size of the given array
     */
    public static <T> int size(final T[] array) {
        return array == null ? 0 : array.length;
    }

    /**
     * Method uses the following logic:
     *
     * <ul>
     *     <li>If collection is empty, it returns null.</li>
     *     <li>If collection contains only one element, it returns this element.</li>
     *     <li>Otherwise it returns the given collection</li>
     * </ul>
     *
     * @param collection collection with data or null.
     * @return collection or single reference
     */
    public static Object oneOrMany(final Collection<?> collection) {
        switch (size(collection)) {
            case 0:
                return null;
            case 1:
                return collection.iterator().next();
            default:
                return collection;
        }
    }


    /**
     * Get a size of the given collection. Method is null-safe.
     *
     * @param collection collection with data
     * @return size of the given collection
     */
    @VisibleForTesting
    static int size(final Collection<?> collection) {
        return collection == null ? 0 : collection.size();
    }

}
