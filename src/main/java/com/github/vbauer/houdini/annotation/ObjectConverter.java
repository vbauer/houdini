package com.github.vbauer.houdini.annotation;

import java.lang.annotation.*;

/**
 * @author Vladislav Bauer
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface ObjectConverter {
}
