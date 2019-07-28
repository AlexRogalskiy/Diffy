package com.wildbeeslabs.sensiblemetrics.diffy.generator.service;

import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.GenerationStatus;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.utils.GeometricDistribution;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

abstract class AbstractGenerationStatus implements GenerationStatus {
    private final GeometricDistribution distro;
    private final SourceOfRandomness random;
    private final Map<Key<?>, Object> contextValues = new HashMap<>();

    AbstractGenerationStatus(
        GeometricDistribution distro,
        SourceOfRandomness random) {

        this.distro = distro;
        this.random = random;
    }

    @Override
    public <T> GenerationStatus setValue(Key<T> key, T value) {
        contextValues.put(key, value);
        return this;
    }

    @Override
    public <T> Optional<T> valueOf(Key<T> key) {
        return Optional.ofNullable(key.cast(contextValues.get(key)));
    }

    @Override
    public int size() {
        return distro.sampleWithMean(attempts() + 1, random);
    }

    protected final SourceOfRandomness random() {
        return random;
    }
}
