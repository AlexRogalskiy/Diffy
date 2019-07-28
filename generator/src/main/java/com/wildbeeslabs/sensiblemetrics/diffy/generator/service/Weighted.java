package com.wildbeeslabs.sensiblemetrics.diffy.generator.service;

public final class Weighted<T> {
    public final T item;
    public final int weight;

    public Weighted(final T item, final int weight) {
        if (weight <= 0)
            throw new IllegalArgumentException("non-positive weight: " + weight);

        this.item = item;
        this.weight = weight;
    }
}
