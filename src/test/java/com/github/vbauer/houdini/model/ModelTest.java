package com.github.vbauer.houdini.model;

import com.github.vbauer.houdini.core.BasicTest;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Vladislav Bauer
 */

public class ModelTest extends BasicTest {

    @Test
    public void testObjectConverterInfoKey() {
        final Class<Integer> returnType1 = Integer.class;
        final Class<String> returnType2 = String.class;

        final Class<?>[] sourceTypes1 = new Class<?>[] { String.class };
        final Class<?>[] sourceTypes2 = new Class<?>[] { Integer.class };

        final ObjectConverterInfoKey<Integer> key1 = new ObjectConverterInfoKey<Integer>(returnType1, sourceTypes1);

        Assert.assertArrayEquals(sourceTypes1, key1.getSources());
        Assert.assertEquals(returnType1, key1.getTarget());

        Assert.assertTrue(key1.hashCode() != 0);
        Assert.assertNotNull(key1.toString());

        Assert.assertEquals(key1, new ObjectConverterInfoKey<Integer>(returnType1, sourceTypes1));
        Assert.assertNotEquals(key1, new ObjectConverterInfoKey<Integer>(returnType1));
        Assert.assertNotEquals(key1, new ObjectConverterInfoKey<String>(returnType2, sourceTypes1));
        Assert.assertNotEquals(key1, new ObjectConverterInfoKey<Integer>(returnType1, sourceTypes2));

        Assert.assertEquals(
            new ObjectConverterInfoKey<Integer>(returnType1),
            new ObjectConverterInfoKey<Integer>(returnType1)
        );

        Assert.assertNotEquals(key1, new Object());

    }

    @Test
    public void testObjectConverterInfoValue() {
        final ObjectConverterInfoValue<Integer> value = new ObjectConverterInfoValue<Integer>(null, 1);
        Assert.assertNotNull(value.toString());
    }

}
