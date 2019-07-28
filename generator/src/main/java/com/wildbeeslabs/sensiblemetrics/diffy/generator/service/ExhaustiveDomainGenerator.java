package com.wildbeeslabs.sensiblemetrics.diffy.generator.service;

import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.GenerationStatus;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

public class ExhaustiveDomainGenerator extends Generator<Object> {
    private final Iterator<?> items;

    public ExhaustiveDomainGenerator(Collection<?> items) {
        super(Object.class);

        this.items = new HashSet<>(items).iterator();
    }

    @Override
    public Object generate(SourceOfRandomness random, GenerationStatus status) {
        return items.next();
    }

    boolean hasNext() {
        return items.hasNext();
    }
}
