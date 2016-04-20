package com.github.vbauer.houdini.core;

import com.pushtorefresh.private_constructor_checker.PrivateConstructorChecker;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

/**
 * @author Vladislav Bauer
 */

@RunWith(BlockJUnit4ClassRunner.class)
public abstract class BasicTest {

    protected <T> Class<T> checkUtilConstructorContract(final Class<T> utilClass) throws Exception {
        PrivateConstructorChecker
            .forClass(utilClass)
            .expectedTypeOfException(UnsupportedOperationException.class)
            .check();

        return utilClass;
    }

}
