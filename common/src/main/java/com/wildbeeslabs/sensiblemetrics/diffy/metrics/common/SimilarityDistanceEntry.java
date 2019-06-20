package com.wildbeeslabs.sensiblemetrics.diffy.metrics.common;

import com.wildbeeslabs.sensiblemetrics.diffy.metrics.iface.SimilarityDistance;
import com.wildbeeslabs.sensiblemetrics.diffy.utility.ValidationUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Objects;

@Data
@EqualsAndHashCode
@ToString
public class SimilarityDistanceEntry<T, R> {

    /**
     * Edit distance.
     */
    private final SimilarityDistance<T, R> similarityDistance;
    /**
     * Left parameter used in distance function.
     */
    private final T left;

    /**
     * <p>This accepts the edit distance implementation and the "left" string.</p>
     *
     * @param editDistance This may not be null.
     * @param left         This may be null here,
     *                     but the EditDistance#compare(CharSequence left, CharSequence right)
     *                     implementation may not accept nulls.
     */
    public SimilarityDistanceEntry(final SimilarityDistance<T, R> similarityDistance, final T left) {
        ValidationUtils.notNull(similarityDistance, "Similariy distance should not be null");
        this.similarityDistance = similarityDistance;
        this.left = left;
    }

    /**
     * <p>
     * This compares "left" field against the "right" parameter
     * using the "edit distance" implementation.
     * </p>
     *
     * @param right the second CharSequence
     * @return the similarity score between two CharSequences
     */
    public R apply(final T right) {
        return this.similarityDistance.apply(left, right);
    }
}
