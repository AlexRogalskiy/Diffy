package com.wildbeeslabs.sensiblemetrics.diffy.generator.service.common;

import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.GenerationStatus;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.Generator;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.SourceOfRandomness;

import java.util.Locale;

import static java.util.Locale.getAvailableLocales;

/**
 * Produces values of type {@link Locale}.
 */
public class LocaleGenerator extends Generator<Locale> {
    public LocaleGenerator() {
        super(Locale.class);
    }

    @Override
    public Locale generate(SourceOfRandomness random, GenerationStatus status) {
        return random.choose(getAvailableLocales());
    }
}
