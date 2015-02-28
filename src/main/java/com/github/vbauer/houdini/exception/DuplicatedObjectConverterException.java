package com.github.vbauer.houdini.exception;

import com.github.vbauer.houdini.exception.base.ObjectConverterException;

import java.util.Arrays;

/**
 * @author Vladislav Bauer
 */

@SuppressWarnings("serial")
public class DuplicatedObjectConverterException extends ObjectConverterException {

    private final Class<?>[] parameterTypes;
    private final Class<?> returnType;


    public DuplicatedObjectConverterException(final Class<?> returnType, final Class<?>... parameterTypes) {
        this.returnType = returnType;
        this.parameterTypes = parameterTypes;
    }


    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public Class<?> getReturnType() {
        return returnType;
    }


    @Override
    public String getMessage() {
        return String.format(
            "Duplicated converter detected. Parameter types: %s, return type: %s",
            Arrays.toString(getParameterTypes()), getReturnType()
        );
    }

}
