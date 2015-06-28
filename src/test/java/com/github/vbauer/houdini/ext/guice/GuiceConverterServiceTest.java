package com.github.vbauer.houdini.ext.guice;

import com.github.vbauer.houdini.converter.UserConverter;
import com.github.vbauer.houdini.core.BasicIoCTest;
import com.github.vbauer.houdini.service.ObjectConverterRegistry;
import com.github.vbauer.houdini.service.ObjectConverterService;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Vladislav Bauer
 */

public class GuiceConverterServiceTest extends BasicIoCTest {

    private Injector injector;


    @Before
    public void onInit() {
        this.injector = Guice.createInjector(new ObjectConverterModule());
        this.converterService = injector.getInstance(ObjectConverterService.class);
        this.userConverter = injector.getInstance(UserConverter.class);
    }


    @Test
    public void testDefinedServices() {
        Assert.assertNotNull(injector.getInstance(ObjectConverterService.class));
        Assert.assertNotNull(injector.getInstance(ObjectConverterRegistry.class));
    }

}
