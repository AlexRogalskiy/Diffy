package com.wildbeeslabs.sensiblemetrics.diffy.metrics.common;

import com.wildbeeslabs.sensiblemetrics.diffy.metrics.iface.SimilarityScore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Objects;

@Data
@EqualsAndHashCode
@ToString
public class SimilarityScoreEntry<T, R> {

    /**
     * Similarity score.
     */
    private final SimilarityScore<T, R> similarityScore;
    /**
     * Left parameter used in distance function.
     */
    private final T left;

    /**
     * <p>This accepts the similarity score implementation and the "left" string.</p>
     *
     * @param similarityScore This may not be null.
     * @param left            This may be null here,
     *                        but the SimilarityScore#compare(CharSequence left, CharSequence right)
     *                        implementation may not accept nulls.
     */
    public SimilarityScoreEntry(final SimilarityScore<T, R> similarityScore, final T left) {
        Objects.requireNonNull(similarityScore, "Similarity score should not be null");
        this.similarityScore = similarityScore;
        this.left = left;
    }

    /**
     * <p>
     * This compares "left" field against the "right" parameter
     * using the "similarity score" implementation.
     * </p>
     *
     * @param right the second CharSequence
     * @return the similarity score between two CharSequences
     */
    public R apply(final T right) {
        return this.similarityScore.apply(left, right);
    }
}
