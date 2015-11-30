package com.github.vbauer.houdini.exception;

import java.lang.reflect.Modifier;

import org.junit.Assert;
import org.junit.Test;

import com.github.vbauer.houdini.core.BasicTest;
import com.github.vbauer.houdini.exception.base.MethodObjectConverterException;
import com.github.vbauer.houdini.exception.base.ObjectConverterException;

/**
 * @author Vladislav Bauer
 */

public class ObjectConverterExceptionTest extends BasicTest {

    @Test
    public void testObjectConverterException() {
        final Class<ObjectConverterException> clazz = ObjectConverterException.class;
        Assert.assertTrue(RuntimeException.class.isAssignableFrom(clazz));
        Assert.assertTrue(Modifier.isAbstract(clazz.getModifiers()));

        // Create empty exception instance.
        final ObjectConverterException ex = new ObjectConverterException() {
            private static final long serialVersionUID = 1L;
        };
        Assert.assertNotNull(ex.getMessage());
    }

    @Test
    public void testMethodObjectConverterException() {
        final Class<MethodObjectConverterException> clazz = MethodObjectConverterException.class;
        Assert.assertTrue(ObjectConverterException.class.isAssignableFrom(clazz));
        Assert.assertTrue(Modifier.isAbstract(clazz.getModifiers()));

        // Create empty exception instance.
        final MethodObjectConverterException ex = new MethodObjectConverterException(null) {
            private static final long serialVersionUID = 1L;
        };
        Assert.assertNotNull(ex.getMessage());
    }

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
