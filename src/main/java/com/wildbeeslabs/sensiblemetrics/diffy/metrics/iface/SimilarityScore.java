package com.wildbeeslabs.sensiblemetrics.diffy.metrics.iface;

public interface SimilarityScore<T, R> {

    /**
     * Compares two CharSequences.
     *
     * @param left  the first CharSequence
     * @param right the second CharSequence
     * @return the similarity score between two CharSequences
     */
    R apply(final T left, final T right);
}
