package com.github.vbauer.houdini;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

/**
 * @author Vladislav Bauer
 */

public interface ConverterService {

    <RESULT> void registerConverter(
            Class<RESULT> resultClass, List<Class<?>> sourceClasses,
            Method method, Object object
    );

    <RESULT> RESULT convert(Class<RESULT> resultClass, Object... sources);

    <RESULT, SOURCE> Set<RESULT> convert(Class<RESULT> resultClass, Set<SOURCE> sources);

    <RESULT, SOURCE> List<RESULT> convert(Class<RESULT> resultClass, List<SOURCE> sources);

    <RESULT, SOURCE> Object convertToOneOrList(Class<RESULT> resultClass, List<SOURCE> sources);

    <RESULT, SOURCE> Object convertToOneOrSet(Class<RESULT> resultClass, Set<SOURCE> sources);

}
