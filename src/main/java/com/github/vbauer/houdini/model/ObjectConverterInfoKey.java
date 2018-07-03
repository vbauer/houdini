package com.github.vbauer.houdini.model;

import java.util.Arrays;
import java.util.Objects;

/**
 * Class represents key information about input and output parameters for object converter.
 *
 * @param <R> class type
 * @author Vladislav Bauer
 */

public final class ObjectConverterInfoKey<R> {

    private final Class<?>[] sources;
    private final Class<R> target;


    public ObjectConverterInfoKey(final Class<R> target, final Class<?>... sources) {
        this.target = target;
        this.sources = sources;
    }


    /**
     * Get array with input parameter classes.
     *
     * @return source classes
     */
    @SuppressWarnings("all")
    public Class<?>[] getSources() {
        return sources;
    }

    /**
     * Get target / output class.
     *
     * @return target class
     */
    @SuppressWarnings("all")
    public Class<R> getTarget() {
        return target;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Arrays.hashCode(new Object[]{sources.length, target});
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

        final ObjectConverterInfoKey<R> other = (ObjectConverterInfoKey<R>) obj;
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

    private boolean hasSameTarget(final ObjectConverterInfoKey<R> other) {
        return Objects.equals(getTarget(), other.getTarget());
    }

    private boolean hasSameSources(final ObjectConverterInfoKey<R> other) {
        final Class<?>[] selfSources = getSources();
        final Class<?>[] otherSources = other.getSources();

        final int selfLength = selfSources.length;
        final int otherLength = otherSources.length;

        if (selfLength != otherLength) {
            return false;
        } else if (selfLength == 0) {
            return true;
        }

        for (int i = 0; i < selfLength; i++) {
            final Class<?> selfClass = selfSources[i];
            final Class<?> otherClass = otherSources[i];

            if (!Objects.equals(selfClass, otherClass)
                && !Objects.equals(selfClass, Object.class)
                && !Objects.equals(otherClass, Object.class)) {
                return false;
            }
        }

        return true;
    }

}
