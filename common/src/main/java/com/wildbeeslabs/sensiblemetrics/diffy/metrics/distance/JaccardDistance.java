package com.wildbeeslabs.sensiblemetrics.diffy.metrics.distance;

import com.wildbeeslabs.sensiblemetrics.diffy.metrics.iface.SimilarityDistance;
import com.wildbeeslabs.sensiblemetrics.diffy.metrics.score.JaccardScore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Jaccard {@link SimilarityDistance} implementation
 */
@Data
@EqualsAndHashCode
@ToString
public class JaccardDistance implements SimilarityDistance<CharSequence, Double> {

    /**
     * We normalize the jaccard smilarity for the purpose of computing the distance.
     */
    private final JaccardScore jaccardSimilarity = new JaccardScore();

    /**
     * Calculates Jaccard distance of two set character sequence passed as
     * input. Calculates Jaccard similarity and returns the complement of it.
     *
     * @param left  first character sequence
     * @param right second character sequence
     * @return index
     * @throws IllegalArgumentException if either String input {@code null}
     */
    @Override
    public Double apply(final CharSequence left, final CharSequence right) {
        if (left == null || right == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }
        return Math.round((1 - jaccardSimilarity.apply(left, right)) * 100d) / 100d;
    }
}
