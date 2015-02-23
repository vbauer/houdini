package com.github.vbauer.houdini.service;

import com.github.vbauer.houdini.model.ObjectConverterInfoValue;

import java.lang.reflect.Method;

/**
 * @author Vladislav Bauer
 */

public interface ObjectConverterRegistry {

    void registerConverter(Object bean, Method method);

    <RESULT> ObjectConverterInfoValue<RESULT> findConverter(Class<RESULT> resultClass, Object... sources);

}
