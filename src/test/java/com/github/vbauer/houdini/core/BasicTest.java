package com.github.vbauer.houdini.core;

import com.github.vbauer.houdini.service.ObjectConverterService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Vladislav Bauer
 */

@SuppressWarnings("SpringJavaAutowiringInspection")
@ContextConfiguration(classes = TestContext.class)
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class BasicTest {

    @Autowired
    protected ObjectConverterService converterService;

}
