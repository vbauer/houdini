package com.github.vbauer.houdini.model;

import com.github.vbauer.houdini.util.CollectionUtils;

import java.util.Arrays;


/**
 * @param <RESULT> class type
 * @author Vladislav Bauer
 */

public final class ObjectConverterInfoKey<RESULT> {

    private final Class<?>[] sources;
    private final Class<RESULT> target;


    public ObjectConverterInfoKey(final Class<RESULT> target, final Class<?>... sources) {
        this.target = target;
        this.sources = sources;
    }


    public Class<?>[] getSources() {
        return sources;
    }

    public Class<RESULT> getTarget() {
        return target;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Arrays.hashCode(new Object[]{CollectionUtils.size(getSources()), getTarget()});
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof ObjectConverterInfoKey)) {
            return false;
        }

        final ObjectConverterInfoKey<RESULT> other = (ObjectConverterInfoKey<RESULT>) obj;
        return hasSameTarget(other) && hasSameSources(other);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format("[%s %s]", Arrays.toString(getSources()), getTarget());
    }


    /*
     * Internal API.
     */

    private boolean hasSameTarget(final ObjectConverterInfoKey<RESULT> other) {
        return getTarget() == other.getTarget();
    }

    private boolean hasSameSources(final ObjectConverterInfoKey<RESULT> other) {
        final Class<?>[] selfSources = getSources();
        final Class<?>[] otherSources = other.getSources();

        final int selfLength = CollectionUtils.size(selfSources);
        final int otherLength = CollectionUtils.size(otherSources);

        if (selfLength != otherLength) {
            return false;
        } else if (selfLength == 0) {
            return true;
        }

        for (int i = 0; i < selfLength; i++) {
            final Class<?> selfClass = selfSources[i];
            final Class<?> otherClass = otherSources[i];

            if (selfClass != Object.class && otherClass != Object.class && selfClass != otherClass) {
                return false;
            }
        }

        return true;
    }

}
