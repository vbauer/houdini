package com.github.vbauer.houdini.util;

import com.github.vbauer.houdini.core.BasicTest;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;

/**
 * @author Vladislav Bauer 
 */

public class ReflectionUtilsTest extends BasicTest {

    @Test
    public void testGetClassWithoutProxies() {
        Assert.assertNull(ReflectionUtils.getClassWithoutProxies(null));
        Assert.assertEquals(Object.class, ReflectionUtils.getClassWithoutProxies(new Object()));
        Assert.assertEquals(String.class, ReflectionUtils.<String>getClassWithoutProxies("test"));
    }

    @Test
    public void testGetClassesWithoutProxies() {
        Assert.assertEquals(0, ReflectionUtils.getClassesWithoutProxies(null).length);
        Assert.assertEquals(0, ReflectionUtils.getClassesWithoutProxies(new Object[]{}).length);
    }

    @Test
    public void testConstructorContract() throws Exception {
        checkUtilConstructorContract(ReflectionUtils.class);
    }

    @Test(expected = IllegalStateException.class)
    public void testHandleReflectionException1() {
        ReflectionUtils.handleReflectionException(new NoSuchMethodException());
    }

    @Test(expected = IllegalStateException.class)
    public void testHandleReflectionException2() {
        ReflectionUtils.handleReflectionException(new IllegalAccessException());
    }

    @Test(expected = RuntimeException.class)
    public void testHandleReflectionException3() {
        ReflectionUtils.handleReflectionException(new RuntimeException());
    }

    @Test(expected = UndeclaredThrowableException.class)
    public void testHandleReflectionException4() {
        ReflectionUtils.handleReflectionException(new Exception());
    }

    @Test(expected = Error.class)
    public void testHandleReflectionException5() {
        ReflectionUtils.handleReflectionException(new InvocationTargetException(new Error()));
    }

    @Test(expected = RuntimeException.class)
    public void testRethrowRuntimeException1() {
        ReflectionUtils.rethrowRuntimeException(new RuntimeException());
    }

    @Test(expected = Error.class)
    public void testRethrowRuntimeException2() {
        ReflectionUtils.rethrowRuntimeException(new Error());
    }

    @Test(expected = UndeclaredThrowableException.class)
    public void testRethrowRuntimeException3() {
        ReflectionUtils.rethrowRuntimeException(new Exception());
    }

    @Test(expected = Error.class)
    public void testHandleInvocationTargetException() {
        ReflectionUtils.handleInvocationTargetException(
            new InvocationTargetException(new Error())
        );
    }

}
