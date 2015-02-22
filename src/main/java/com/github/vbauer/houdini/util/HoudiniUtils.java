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
            final Class<?> clazz = Class.forName("org.hibernate.proxy.HibernateProxyHelper");
            final Method method = clazz.getDeclaredMethod("getClassWithoutInitializingProxy");
            return (Class<T>) method.invoke(null, object);
        } catch (final Exception ex) {
            return (Class<T>) object.getClass();
        }
    }

}
