package com.github.vbauer.houdini.service.impl;

import com.google.common.annotations.VisibleForTesting;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;

/**
 * Util-class which provides additional operation to work with reflection mechanism.
 *
 * @author Vladislav Bauer 
 */

final class ReflectionUtils {
    
    private ReflectionUtils() {
        throw new UnsupportedOperationException();
    }


    /**
     * Get class without possible proxies. It works only with Hibernate library.
     * Otherwise it returns the {@link Object#getClass()}.
     *
     * @param object object
     * @param <T> type of the result object
     * @return type of the object without some proxy wrappers
     */
    @SuppressWarnings("unchecked")
    @VisibleForTesting
    static <T> Class<T> getClassWithoutProxies(final T object) {
        try {
            // XXX: Use HibernateProxyHelper to un-proxy object and get the original class.
            return (Class<T>) Class.forName("org.hibernate.proxy.HibernateProxyHelper")
                .getDeclaredMethod("getClassWithoutInitializingProxy", Object.class)
                .invoke(null, object);
        } catch (final Exception ex) {
            try {
                return (Class<T>) object.getClass();
            } catch (final Exception e) {
                return null;
            }
        }
    }

    /**
     * Get array of classes which represents un-proxy classes of the given objects.
     *
     * @param sources array with objects
     * @return un-proxy classes
     */
    @VisibleForTesting
    static Class<?>[] getClassesWithoutProxies(final Object[] sources) {
        final int size = sources == null ? 0 : sources.length;
        final Class<?>[] sourceClasses = new Class<?>[size];

        for (int i = 0; i < size; i++) {
            final Object source = sources[i];
            sourceClasses[i] = ReflectionUtils.getClassWithoutProxies(source);
        }

        return sourceClasses;
    }

    /**
     * Convert exception to the {@link RuntimeException}.
     *
     * @param ex exception
     */
    @VisibleForTesting
    static void handleReflectionException(final Exception ex) {
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

    @VisibleForTesting
    static void handleInvocationTargetException(final InvocationTargetException ex) {
        final Throwable targetException = ex.getTargetException();
        rethrowRuntimeException(targetException);
    }

    @VisibleForTesting
    static void rethrowRuntimeException(final Throwable ex) {
        if (ex instanceof RuntimeException) {
            throw (RuntimeException) ex;
        } else if (ex instanceof Error) {
            throw (Error) ex;
        } else {
            throw new UndeclaredThrowableException(ex);
        }
    }

}
