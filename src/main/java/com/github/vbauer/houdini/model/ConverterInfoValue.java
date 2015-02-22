package com.github.vbauer.houdini.model;

import java.lang.reflect.Method;

/**
 * @author Vladislav Bauer
 */

public final class ConverterInfoValue<T> {

    private final Method method;
    private final T object;


    public ConverterInfoValue(final Method method, final T object) {
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
