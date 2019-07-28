package com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces;

import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.SourceOfRandomness;

import java.util.List;

/**
 * Represents a strategy for producing objects "smaller than" a given object.
 *
 * @param <T> type of shrunken objects produced
 */
@FunctionalInterface
public interface Shrink<T> {
    /**
     * Gives some objects that are "smaller" than a given object.
     *
     * @param random source of randomness to use in shrinking, if desired
     * @param larger the larger object
     * @return objects that are "smaller" than the larger object
     */
    List<T> shrink(SourceOfRandomness random, Object larger);
}
