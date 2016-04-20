package com.github.vbauer.houdini.util;

import com.github.vbauer.houdini.core.BasicTest;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * @author Vladislav Bauer
 */

public class CollectionUtilsTest extends BasicTest {

    @Test
    public void testIsEmptyCollection() {
        assertThat(CollectionUtils.isEmpty(null), equalTo(true));
        assertThat(CollectionUtils.isEmpty(Collections.EMPTY_LIST), equalTo(true));
        assertThat(CollectionUtils.isEmpty(Collections.singleton(1)), equalTo(false));
    }

    @Test
    public void testSizeArray() {
        assertThat(CollectionUtils.size((Integer[]) null), equalTo(0));
        assertThat(CollectionUtils.size(new Integer[]{}), equalTo(0));
        assertThat(CollectionUtils.size(new Integer[]{1}), equalTo(1));
    }

    @Test
    public void testSizeCollection() {
        assertThat(CollectionUtils.size((Collection<?>) null), equalTo(0));
        assertThat(CollectionUtils.size(Collections.emptyList()), equalTo(0));
        assertThat(CollectionUtils.size(Collections.emptySet()), equalTo(0));
        assertThat(CollectionUtils.size(Collections.singleton(1)), equalTo(1));
        assertThat(CollectionUtils.size(Collections.singletonList(1)), equalTo(1));
    }

    @Test
    public void testOneOrMany() {
        assertThat(CollectionUtils.oneOrMany(null), nullValue());
        assertThat(CollectionUtils.oneOrMany(Collections.emptyList()), nullValue());
        assertThat(CollectionUtils.oneOrMany(Collections.emptySet()), nullValue());
        assertThat((Integer) CollectionUtils.oneOrMany(Collections.singleton(1)), equalTo(1));
        assertThat((Integer) CollectionUtils.oneOrMany(Collections.singletonList(1)), equalTo(1));

        final List<Integer> list = Arrays.asList(1, 2);
        assertThat(((Collection<?>) CollectionUtils.oneOrMany(list)), hasSize(2));
        assertThat(((Collection<?>) CollectionUtils.oneOrMany(new HashSet<>(list))), hasSize(2));
    }

    @Test
    public void testConstructorContract() throws Exception {
        assertThat(checkUtilConstructorContract(CollectionUtils.class), notNullValue());
    }

}
