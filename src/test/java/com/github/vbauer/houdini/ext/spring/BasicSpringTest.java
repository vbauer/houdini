package com.github.vbauer.houdini.ext.spring;

import com.github.vbauer.houdini.converter.UserConverter;
import com.github.vbauer.houdini.core.BasicIoCTest;
import com.github.vbauer.houdini.service.ObjectConverterService;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

/**
 * @author Vladislav Bauer
 */

public abstract class BasicSpringTest extends BasicIoCTest {

    @Autowired
    private ApplicationContext context;


    @Before
    public void onInit() {
        this.converterService = context.getBean(ObjectConverterService.class);
        this.userConverter = context.getBean(UserConverter.class);
    }

}
