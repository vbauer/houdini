package com.github.vbauer.houdini.util;

import java.lang.reflect.Method;
import java.util.Collection;

/**
 * @author Vladislav Bauer 
 */

public final class HoudiniUtils {
    
    private HoudiniUtils() {
        throw new UnsupportedOperationException();
    }

    
    public static int size(final Collection collection) {
        return collection == null ? 0 : collection.size();
    }

    public static <T> int size(final T[] array) {
        return array == null ? 0 : array.length;
    }

    public static Object oneOrMany(@SuppressWarnings("rawtypes") final Collection collection) {
        switch (size(collection)) {
            case 0:
                return null;
            case 1:
                return collection.iterator().next();
            default:
                return collection;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> Class<T> getClassWithoutProxies(final Object object) {
        if (object == null) {
            return null;
        }
        
        try {
            // XXX: Use HibernateProxyHelper to un-proxy object and get the original class.
            final Class<?> clazz = Class.forName("org.hibernate.proxy.HibernateProxyHelper");
            final Method method = clazz.getDeclaredMethod("getClassWithoutInitializingProxy", Object.class);

            return (Class<T>) method.invoke(null, object);
        } catch (final Exception ex) {
            return (Class<T>) object.getClass();
        }
    }

    public static Class<?>[] getClassesWithoutProxies(final Object[] sources) {
        final int size = sources == null ? 0 : sources.length;
        final Class<?>[] sourceClasses = new Class<?>[size];

        for (int i = 0; i < size; i++) {
            final Object source = sources[i];
            sourceClasses[i] = HoudiniUtils.getClassWithoutProxies(source);
        }

        return sourceClasses;
    }

}
