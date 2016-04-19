package com.github.vbauer.houdini.model;

import com.github.vbauer.houdini.core.BasicTest;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.collection.IsArrayContainingInAnyOrder.arrayContainingInAnyOrder;
import static org.junit.Assert.assertThat;

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

        final ObjectConverterInfoKey<Integer> key1 = new ObjectConverterInfoKey<>(returnType1, sourceTypes1);

        assertThat(key1.getSources(), arrayContainingInAnyOrder(sourceTypes1));
        assertThat(key1.getTarget(), equalTo(returnType1));

        assertThat(key1.hashCode(), not(equalTo(0)));
        assertThat(key1.toString(), notNullValue());

        assertThat(new ObjectConverterInfoKey<>(returnType1, sourceTypes1), equalTo(key1));
        assertThat(new ObjectConverterInfoKey<>(returnType1), not(equalTo(key1)));
        assertThat(new ObjectConverterInfoKey<>(returnType1, sourceTypes2), not(equalTo(key1)));

        assertThat(
            new ObjectConverterInfoKey<>(returnType2, sourceTypes1),
            not(equalTo((ObjectConverterInfoKey) key1))
        );

        assertThat(
            new ObjectConverterInfoKey<>(returnType1),
            equalTo(new ObjectConverterInfoKey<>(returnType1))
        );

        assertThat(key1, not(equalTo(new Object())));
    }

    @Test
    public void testObjectConverterInfoValue() {
        final Integer object = 1;
        final ObjectConverterInfoValue<Integer> value =
            new ObjectConverterInfoValue<>(null, object);

        assertThat(value.getObject(), equalTo(object));
        assertThat(value.getMethod(), nullValue());
        assertThat(value.toString(), notNullValue());
    }

}
