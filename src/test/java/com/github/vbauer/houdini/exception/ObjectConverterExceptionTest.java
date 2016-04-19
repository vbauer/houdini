package com.github.vbauer.houdini.exception;

import com.github.vbauer.houdini.core.BasicTest;
import com.github.vbauer.houdini.exception.base.MethodObjectConverterException;
import com.github.vbauer.houdini.exception.base.ObjectConverterException;
import org.junit.Test;

import java.lang.reflect.Modifier;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.collection.IsArrayContainingInAnyOrder.arrayContainingInAnyOrder;
import static org.junit.Assert.assertThat;

/**
 * @author Vladislav Bauer
 */

public class ObjectConverterExceptionTest extends BasicTest {

    @Test
    public void testObjectConverterException() {
        final Class<ObjectConverterException> clazz = ObjectConverterException.class;
        assertThat(RuntimeException.class.isAssignableFrom(clazz), equalTo(true));
        assertThat(Modifier.isAbstract(clazz.getModifiers()), equalTo(true));

        // Create empty exception instance.
        final ObjectConverterException ex = new ObjectConverterException() {
            private static final long serialVersionUID = 1L;
        };
        assertThat(ex.getMessage(), notNullValue());
    }

    @Test
    public void testMethodObjectConverterException() {
        final Class<MethodObjectConverterException> clazz = MethodObjectConverterException.class;
        assertThat(ObjectConverterException.class.isAssignableFrom(clazz), equalTo(true));
        assertThat(Modifier.isAbstract(clazz.getModifiers()), equalTo(true));

        // Create empty exception instance.
        final MethodObjectConverterException ex = new MethodObjectConverterException(null) {
            private static final long serialVersionUID = 1L;
        };
        assertThat(ex.getMessage(), notNullValue());
    }

    @Test(expected = DuplicatedObjectConverterException.class)
    public void testDuplicatedObjectConverterException() {
        final Class<?> returnType = Object.class;
        final Class<?>[] parametersType = new Class<?>[] { Object.class };
        final DuplicatedObjectConverterException ex =
            new DuplicatedObjectConverterException(returnType, parametersType);

        assertThat(ex.getReturnType(), equalTo((Class) returnType));
        assertThat(ex.getParameterTypes(), arrayContainingInAnyOrder(parametersType));
        assertThat(ex.getMessage(), notNullValue());

        throw ex;
    }

    @Test(expected = MissedObjectConverterException.class)
    public void testMissedObjectConverterException() {
        final Class<Object> returnType = Object.class;
        final Class<?>[] parametersType = new Class<?>[] { Object.class };
        final MissedObjectConverterException ex =
            new MissedObjectConverterException(returnType, parametersType);

        assertThat(ex.getReturnType(), equalTo((Class) returnType));
        assertThat(ex.getParameterTypes(), arrayContainingInAnyOrder(parametersType));
        assertThat(ex.getMessage(), notNullValue());

        throw ex;
    }

}
