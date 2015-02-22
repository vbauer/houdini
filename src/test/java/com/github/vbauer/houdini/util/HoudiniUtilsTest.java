package com.github.vbauer.houdini.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.util.*;

/**
 * @author Vladislav Bauer 
 */

@RunWith(BlockJUnit4ClassRunner.class)
public class HoudiniUtilsTest {
    
    @Test
    public void testSize() {
        Assert.assertEquals(0, HoudiniUtils.size(null));
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
    
}
