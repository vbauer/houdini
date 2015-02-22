package com.github.vbauer.houdini.model;

import java.lang.reflect.Method;

/**
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


    public Method getMethod() {
        return method;
    }

    public T getObject() {
        return object;
    }

    @Override
    public String toString() {
        return String.format("[%s %s]", getMethod(), getObject());
    }

}
