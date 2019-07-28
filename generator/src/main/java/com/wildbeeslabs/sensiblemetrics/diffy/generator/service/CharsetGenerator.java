package com.wildbeeslabs.sensiblemetrics.diffy.generator.service;

import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.GenerationStatus;

import java.nio.charset.Charset;

import static java.nio.charset.Charset.availableCharsets;

/**
 * Produces values of type {@link java.nio.charset.Charset}.
 */
public class CharsetGenerator extends Generator<Charset> {
    public CharsetGenerator() {
        super(Charset.class);
    }

    @Override
    public Charset generate(SourceOfRandomness random, GenerationStatus status) {
        return Charset.forName(random.choose(availableCharsets().keySet()));
    }
}
