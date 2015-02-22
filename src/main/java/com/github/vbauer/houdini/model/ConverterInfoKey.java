package com.github.vbauer.houdini.model;

import com.github.vbauer.houdini.util.HoudiniUtils;

import java.util.Arrays;
import java.util.List;


/**
 * @author Vladislav Bauer
 */

public final class ConverterInfoKey<RESULT> {

    private final List<Class<?>> sources;
    private final Class<RESULT> target;


    public ConverterInfoKey(final List<Class<?>> sources, final Class<RESULT> target) {
        this.sources = sources;
        this.target = target;
    }


    public List<Class<?>> getSources() {
        return sources;
    }

    public Class<RESULT> getTarget() {
        return target;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(new Object[] { HoudiniUtils.size(getSources()), getTarget() });
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof ConverterInfoKey)) {
            return false;
        }

        final ConverterInfoKey<RESULT> other = (ConverterInfoKey<RESULT>) obj;
        if (getTarget() == other.getTarget()) {
            return false;
        }

        final List<Class<?>> selfSources = getSources();
        final List<Class<?>> otherSources = other.getSources();

        final int selfLength = HoudiniUtils.size(selfSources);
        final int otherLength = HoudiniUtils.size(otherSources);

        if (selfLength != otherLength) {
            return false;
        } else if (selfLength == 0) {
            return true;
        }

        for (int i = 0; i < selfLength; i++) {
            final Class<?> selfClass = selfSources.get(i);
            final Class<?> otherClass = otherSources.get(i);

            if (selfClass != Object.class && otherClass != Object.class && selfClass != otherClass) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        return String.format("[%s %s]", getSources(), getTarget());
    }

}
