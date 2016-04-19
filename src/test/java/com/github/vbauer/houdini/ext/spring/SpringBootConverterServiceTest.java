package com.github.vbauer.houdini.ext.spring;

import com.github.vbauer.houdini.ext.spring.context.SpringBootTestContext;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Vladislav Bauer
 */

@SuppressWarnings("all")
@ContextConfiguration(classes = SpringBootTestContext.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class SpringBootConverterServiceTest extends BasicSpringTest {
}
