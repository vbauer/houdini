package com.github.vbauer.houdini.util;

import java.util.Collection;

/**
 * @author Vladislav Bauer
 */

public final class CollectionUtils {

    private CollectionUtils() {
        throw new UnsupportedOperationException();
    }


    public static int size(final Collection<?> collection) {
        return collection == null ? 0 : collection.size();
    }

    public static <T> int size(final T[] array) {
        return array == null ? 0 : array.length;
    }

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

}
