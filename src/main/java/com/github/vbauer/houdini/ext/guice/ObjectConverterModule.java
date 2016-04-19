package com.github.vbauer.houdini.ext.guice;

import com.github.vbauer.houdini.service.ObjectConverterRegistry;
import com.github.vbauer.houdini.service.ObjectConverterService;
import com.github.vbauer.houdini.service.impl.ObjectConverterRegistryImpl;
import com.github.vbauer.houdini.service.impl.ObjectConverterServiceImpl;
import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.matcher.Matcher;
import com.google.inject.matcher.Matchers;
import com.google.inject.spi.InjectionListener;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;

/**
 * @author Vladislav Bauer
 */

public class ObjectConverterModule extends AbstractModule {

    private final Matcher<Object> typeMatcher;


    public ObjectConverterModule() {
        this(Matchers.any());
    }

    public ObjectConverterModule(final Matcher<Object> typeMatcher) {
        this.typeMatcher = typeMatcher;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    protected void configure() {
        final ObjectConverterRegistry registry = new ObjectConverterRegistryImpl();
        final ObjectConverterServiceImpl converter = new ObjectConverterServiceImpl(registry);

        bind(ObjectConverterRegistry.class).toInstance(registry);
        bind(ObjectConverterService.class).toInstance(converter);
        bindListener(typeMatcher, createTypeListener(registry));
    }


    private TypeListener createTypeListener(final ObjectConverterRegistry registry) {
        return new TypeListener() {
            @Override
            public <I> void hear(final TypeLiteral<I> typeLiteral, final TypeEncounter<I> typeEncounter) {
                typeEncounter.register(new InjectionListener<I>() {
                    @Override
                    public void afterInjection(final I injectee) {
                        registry.registerConverters(injectee);
                    }
                });
            }
        };
    }

}
