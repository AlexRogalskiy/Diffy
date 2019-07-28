package com.wildbeeslabs.sensiblemetrics.diffy.generator.service;

import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.GenerationStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SamplingDomainGenerator extends Generator<Object> {
    private final List<?> items;

    public SamplingDomainGenerator(Set<?> items) {
        super(Object.class);

        this.items = new ArrayList<>(items);
    }

    @Override
    public Object generate(SourceOfRandomness random, GenerationStatus status) {
        return random.choose(items);
    }
}
