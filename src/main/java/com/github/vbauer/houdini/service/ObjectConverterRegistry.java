package com.github.vbauer.houdini.service;

import com.github.vbauer.houdini.model.ObjectConverterInfoValue;

/**
 * @author Vladislav Bauer
 */

public interface ObjectConverterRegistry {

    void registerConverters(Object bean);

    <RESULT> ObjectConverterInfoValue<RESULT> findConverter(
        Class<RESULT> resultClass, Object... sources
    );

}
