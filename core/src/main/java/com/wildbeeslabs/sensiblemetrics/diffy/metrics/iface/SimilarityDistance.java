package com.wildbeeslabs.sensiblemetrics.diffy.metrics.iface;

/**
 * Edit distance interface declaration
 *
 * @param <T> type of similarity item
 * @param <R> type of similarity score unit
 */
public interface SimilarityDistance<T, R> extends SimilarityScore<T, R> {

    /**
     * Compares two CharSequences.
     *
     * @param left  the first CharSequence
     * @param right the second CharSequence
     * @return the similarity score between two CharSequences
     */
    @Override
    R apply(final T left, final T right);
}
