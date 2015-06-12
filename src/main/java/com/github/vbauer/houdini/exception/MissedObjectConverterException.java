package com.github.vbauer.houdini.exception;

import com.github.vbauer.houdini.exception.base.MethodObjectConverterException;

import java.util.Arrays;

/**
 * @author Vladislav Bauer
 */

@SuppressWarnings("serial")
public class MissedObjectConverterException extends MethodObjectConverterException {

    public MissedObjectConverterException(
        final Class<?> returnType, final Class<?>... parameterTypes
    ) {
        super(returnType, parameterTypes);
    }


    @Override
    public String getMessage() {
        return String.format(
            "Converter was not found. Parameter types: %s, return type: %s",
            Arrays.toString(getParameterTypes()), getReturnType()
        );
    }

}
