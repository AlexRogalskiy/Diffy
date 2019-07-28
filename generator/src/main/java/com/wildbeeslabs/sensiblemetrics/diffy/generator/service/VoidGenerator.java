package com.wildbeeslabs.sensiblemetrics.diffy.generator.service;

import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.GenerationStatus;

import static java.util.Arrays.asList;

/**
 * Produces values for property parameters of type {@code void} or
 * {@link Void}.
 */
public class VoidGenerator extends Generator<Void> {
    @SuppressWarnings("unchecked")
    public VoidGenerator() {
        super(asList(Void.class, void.class));
    }

    @Override
    public Void generate(final SourceOfRandomness random, GenerationStatus status) {
        return null;
    }

    @Override
    public boolean canRegisterAsType(Class<?> type) {
        return !Object.class.equals(type);
    }
}
