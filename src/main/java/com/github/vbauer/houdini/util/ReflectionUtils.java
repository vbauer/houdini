package com.github.vbauer.houdini.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;

/**
 * @author Vladislav Bauer 
 */

public final class ReflectionUtils {
    
    private ReflectionUtils() {
        throw new UnsupportedOperationException();
    }


    @SuppressWarnings("unchecked")
    public static <T> Class<T> getClassWithoutProxies(final Object object) {
        try {
            // XXX: Use HibernateProxyHelper to un-proxy object and get the original class.
            final Class<?> clazz = Class.forName("org.hibernate.proxy.HibernateProxyHelper");
            final Method method = clazz.getDeclaredMethod(
                "getClassWithoutInitializingProxy", Object.class
            );

            return (Class<T>) method.invoke(null, object);
        } catch (final Exception ex) {
            try {
                return (Class<T>) object.getClass();
            } catch (final Exception e) {
                return null;
            }
        }
    }

    public static Class<?>[] getClassesWithoutProxies(final Object[] sources) {
        final int size = sources == null ? 0 : sources.length;
        final Class<?>[] sourceClasses = new Class<?>[size];

        for (int i = 0; i < size; i++) {
            final Object source = sources[i];
            sourceClasses[i] = ReflectionUtils.getClassWithoutProxies(source);
        }

        return sourceClasses;
    }

    public static void handleReflectionException(final Exception ex) {
        if (ex instanceof NoSuchMethodException) {
            throw new IllegalStateException("Method not found: " + ex.getMessage());
        } else if (ex instanceof IllegalAccessException) {
            throw new IllegalStateException("Could not access method: " + ex.getMessage());
        } else {
            if (ex instanceof InvocationTargetException) {
                handleInvocationTargetException((InvocationTargetException) ex);
            }

            if (ex instanceof RuntimeException) {
                throw (RuntimeException) ex;
            } else {
                throw new UndeclaredThrowableException(ex);
            }
        }
    }

    public static void handleInvocationTargetException(final InvocationTargetException ex) {
        final Throwable targetException = ex.getTargetException();
        rethrowRuntimeException(targetException);
    }

    public static void rethrowRuntimeException(final Throwable ex) {
        if (ex instanceof RuntimeException) {
            throw (RuntimeException) ex;
        } else if (ex instanceof Error) {
            throw (Error) ex;
        } else {
            throw new UndeclaredThrowableException(ex);
        }
    }

}
