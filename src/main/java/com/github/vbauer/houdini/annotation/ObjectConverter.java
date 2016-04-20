package com.github.vbauer.houdini.annotation;

import java.lang.annotation.*;

/**
 * This annotation allows to mark all methods in class or specific methods as object converters.
 *
 * @author Vladislav Bauer
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface ObjectConverter {
}
