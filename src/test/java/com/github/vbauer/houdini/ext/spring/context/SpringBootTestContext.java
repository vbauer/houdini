package com.github.vbauer.houdini.ext.spring.context;

import com.github.vbauer.houdini.ext.spring.ObjectConverterConfiguration;
import org.springframework.boot.autoconfigure.test.ImportAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Vladislav Bauer
 */

@Configuration
@ComponentScan
@ImportAutoConfiguration(ObjectConverterConfiguration.class)
public class SpringBootTestContext {
}
