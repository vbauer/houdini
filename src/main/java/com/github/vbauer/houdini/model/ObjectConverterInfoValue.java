package com.github.vbauer.houdini.model;

import java.lang.reflect.Method;

/**
 * Class represents internal information about object converter.
 *
 * @param <T> class type
 * @author Vladislav Bauer
 */

public final class ObjectConverterInfoValue<T> {

    private final Method method;
    private final T object;


    public ObjectConverterInfoValue(final Method method, final T object) {
        this.method = method;
        this.object = object;
    }


    /**
     * Get method that is used to convert data.
     *
     * @return method for conversion
     */
    public Method getMethod() {
        return method;
    }

    /**
     * Get object that contains method for conversion.
     * @return root object
     */
    public T getObject() {
        return object;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format("[%s %s]", getMethod(), getObject());
    }

}
