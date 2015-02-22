package com.github.vbauer.houdini.annotation;

import java.lang.annotation.*;

/**
 * @author Vladislav Bauer
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface ObjectConverter {
}
