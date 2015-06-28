package com.github.vbauer.houdini.ext.spring;

import com.github.vbauer.houdini.converter.UserConverter;
import com.github.vbauer.houdini.core.BasicIoCTest;
import com.github.vbauer.houdini.service.ObjectConverterService;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Vladislav Bauer
 */

@SuppressWarnings("SpringJavaAutowiringInspection")
@ContextConfiguration(classes = SpringTestContext.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class SpringConverterServiceTest extends BasicIoCTest {

    @Autowired
    private ApplicationContext context;


    @Before
    public void onInit() {
        this.converterService = context.getBean(ObjectConverterService.class);
        this.userConverter = context.getBean(UserConverter.class);
    }


}
