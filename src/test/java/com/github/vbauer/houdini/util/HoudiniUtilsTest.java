package com.github.vbauer.houdini.util;

import com.github.vbauer.houdini.core.BasicTest;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * @author Vladislav Bauer 
 */

public class HoudiniUtilsTest extends BasicTest {

    @Test
    public void testSizeArray() {
        Assert.assertEquals(0, HoudiniUtils.size((Integer []) null));
        Assert.assertEquals(0, HoudiniUtils.size(new Integer [] {}));
        Assert.assertEquals(1, HoudiniUtils.size(new Integer[] { 1 }));
    }

    @Test
    public void testSizeCollection() {
        Assert.assertEquals(0, HoudiniUtils.size((Collection) null));
        Assert.assertEquals(0, HoudiniUtils.size(Collections.emptyList()));
        Assert.assertEquals(0, HoudiniUtils.size(Collections.emptySet()));
        Assert.assertEquals(1, HoudiniUtils.size(Collections.singleton(1)));
        Assert.assertEquals(1, HoudiniUtils.size(Collections.singletonList(1)));
    }

    @Test
    public void testOneOrMany() {
        Assert.assertNull(HoudiniUtils.oneOrMany(null));
        Assert.assertNull(HoudiniUtils.oneOrMany(Collections.emptyList()));
        Assert.assertNull(HoudiniUtils.oneOrMany(Collections.emptySet()));
        Assert.assertEquals(1, HoudiniUtils.oneOrMany(Collections.singleton(1)));
        Assert.assertEquals(1, HoudiniUtils.oneOrMany(Collections.singletonList(1)));
        
        final List<Integer> list = Arrays.asList(1, 2);
        Assert.assertEquals(2, ((Collection) HoudiniUtils.oneOrMany(list)).size());
        Assert.assertEquals(2, ((Collection) HoudiniUtils.oneOrMany(new HashSet<Integer>(list))).size());
    }
    
    @Test
    public void testGetClassWithoutProxies() {
        Assert.assertNull(HoudiniUtils.getClassWithoutProxies(null));
        Assert.assertEquals(Object.class, HoudiniUtils.getClassWithoutProxies(new Object()));
        Assert.assertEquals(String.class, HoudiniUtils.<String>getClassWithoutProxies("test"));
    }

    @Test
    public void testGetClassesWithoutProxies() {
        Assert.assertEquals(0, HoudiniUtils.getClassesWithoutProxies(null).length);
        Assert.assertEquals(0, HoudiniUtils.getClassesWithoutProxies(new Object[] {}).length);
    }

    @Test
    public void testConstructorContract() throws Exception {
        checkUtilConstructorContract(HoudiniUtils.class);
    }


    private void checkUtilConstructorContract(final Class<HoudiniUtils> utilClass) throws Exception {
        try {
            Assert.fail(utilClass.newInstance().toString());
        } catch (final IllegalAccessException ex) {
            final Constructor<HoudiniUtils> constructor = utilClass.getDeclaredConstructor();
            constructor.setAccessible(true);

            try {
                Assert.fail(constructor.newInstance().toString());
            } catch (final InvocationTargetException e) {
                final Throwable targetException = e.getTargetException();
                Assert.assertEquals(UnsupportedOperationException.class, targetException.getClass());
            }
        }
    }

}
