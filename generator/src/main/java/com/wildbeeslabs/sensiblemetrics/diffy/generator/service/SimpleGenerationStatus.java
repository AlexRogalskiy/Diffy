package com.wildbeeslabs.sensiblemetrics.diffy.generator.service;

import com.wildbeeslabs.sensiblemetrics.diffy.generator.utils.GeometricDistribution;

public class SimpleGenerationStatus extends AbstractGenerationStatus {
    private final int attempts;

    public SimpleGenerationStatus(
        GeometricDistribution distro,
        SourceOfRandomness random,
        int attempts) {

        super(distro, random);
        this.attempts = attempts;
    }

    @Override
    public int attempts() {
        return attempts;
    }
}
