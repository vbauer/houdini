package com.github.vbauer.houdini.util;

import com.github.vbauer.houdini.core.BasicTest;
import org.junit.Assert;
import org.junit.Test;

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

}
