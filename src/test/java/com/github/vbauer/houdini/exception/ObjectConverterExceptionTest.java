package com.github.vbauer.houdini.exception;

import com.github.vbauer.houdini.core.BasicTest;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Vladislav Bauer
 */

public class ObjectConverterExceptionTest extends BasicTest {

    @Test(expected = DuplicatedObjectConverterException.class)
    public void testDuplicatedObjectConverterException() {
        final Class<Object> returnType = Object.class;
        final Class<?>[] parametersType = new Class<?>[] { Object.class };
        final DuplicatedObjectConverterException ex =
            new DuplicatedObjectConverterException(returnType, parametersType);

        Assert.assertEquals(returnType, ex.getReturnType());
        Assert.assertArrayEquals(parametersType, ex.getParameterTypes());
        Assert.assertNotNull(ex.getMessage());

        throw ex;
    }

    @Test(expected = MissedObjectConverterException.class)
    public void testMissedObjectConverterException() {
        final Class<Object> returnType = Object.class;
        final Class<?>[] parametersType = new Class<?>[] { Object.class };
        final MissedObjectConverterException ex =
            new MissedObjectConverterException(returnType, parametersType);

        Assert.assertEquals(returnType, ex.getReturnType());
        Assert.assertArrayEquals(parametersType, ex.getParameterTypes());
        Assert.assertNotNull(ex.getMessage());

        throw ex;
    }

}
