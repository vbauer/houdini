package com.github.vbauer.houdini.util;

import com.github.vbauer.houdini.core.BasicTest;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * @author Vladislav Bauer
 */

public class CollectionUtilsTest extends BasicTest {

    @Test
    public void testIsEmptyCollection() {
        Assert.assertTrue(CollectionUtils.isEmpty(null));
        Assert.assertTrue(CollectionUtils.isEmpty(Collections.EMPTY_LIST));
        Assert.assertFalse(CollectionUtils.isEmpty(Collections.singleton(1)));
    }

    @Test
    public void testSizeArray() {
        Assert.assertEquals(0, CollectionUtils.size((Integer[]) null));
        Assert.assertEquals(0, CollectionUtils.size(new Integer[]{}));
        Assert.assertEquals(1, CollectionUtils.size(new Integer[]{1}));
    }

    @Test
    public void testSizeCollection() {
        Assert.assertEquals(0, CollectionUtils.size((Collection<?>) null));
        Assert.assertEquals(0, CollectionUtils.size(Collections.emptyList()));
        Assert.assertEquals(0, CollectionUtils.size(Collections.emptySet()));
        Assert.assertEquals(1, CollectionUtils.size(Collections.singleton(1)));
        Assert.assertEquals(1, CollectionUtils.size(Collections.singletonList(1)));
    }

    @Test
    public void testOneOrMany() {
        Assert.assertNull(CollectionUtils.oneOrMany(null));
        Assert.assertNull(CollectionUtils.oneOrMany(Collections.emptyList()));
        Assert.assertNull(CollectionUtils.oneOrMany(Collections.emptySet()));
        Assert.assertEquals(1, CollectionUtils.oneOrMany(Collections.singleton(1)));
        Assert.assertEquals(1, CollectionUtils.oneOrMany(Collections.singletonList(1)));

        final List<Integer> list = Arrays.asList(1, 2);
        Assert.assertEquals(2, ((Collection<?>) CollectionUtils.oneOrMany(list)).size());
        Assert.assertEquals(2, ((Collection<?>) CollectionUtils.oneOrMany(new HashSet<Integer>(list))).size());
    }

    @Test
    public void testConstructorContract() throws Exception {
        checkUtilConstructorContract(CollectionUtils.class);
    }

}
