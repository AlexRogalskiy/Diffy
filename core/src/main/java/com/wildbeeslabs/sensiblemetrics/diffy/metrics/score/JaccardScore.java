package com.wildbeeslabs.sensiblemetrics.diffy.metrics.score;

import com.wildbeeslabs.sensiblemetrics.diffy.metrics.iface.SimilarityScore;
import com.wildbeeslabs.sensiblemetrics.diffy.utility.ValidationUtils;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Jaccard {@link SimilarityScore} implementation
 */
public class JaccardScore implements SimilarityScore<CharSequence, Double> {

    /**
     * Calculates Jaccard Similarity of two set character sequence passed as
     * input.
     *
     * @param left  first character sequence
     * @param right second character sequence
     * @return index
     * @throws IllegalArgumentException if either String input {@code null}
     */
    @Override
    public Double apply(final CharSequence left, final CharSequence right) {
        ValidationUtils.notNull(left, "Left sequence should not be null");
        ValidationUtils.notNull(right, "Right sequence should not be null");

        return Math.round(calculateJaccardSimilarity(left, right) * 100d) / 100d;
    }

    /**
     * Calculates Jaccard Similarity of two character sequences passed as
     * input. Does the calculation by identifying the union (characters in at
     * least one of the two sets) of the two sets and intersection (characters
     * which are present in set one which are present in set two)
     *
     * @param left  first character sequence
     * @param right second character sequence
     * @return index
     */
    private Double calculateJaccardSimilarity(final CharSequence left, final CharSequence right) {
        final int leftLength = left.length();
        final int rightLength = right.length();
        if (leftLength == 0 || rightLength == 0) {
            return 0d;
        }
        final Set<Character> leftSet = new HashSet<>();
        for (int i = 0; i < leftLength; i++) {
            leftSet.add(left.charAt(i));
        }
        final Set<Character> rightSet = new HashSet<>();
        for (int i = 0; i < rightLength; i++) {
            rightSet.add(right.charAt(i));
        }
        final Set<Character> unionSet = new HashSet<>(leftSet);
        unionSet.addAll(rightSet);
        final int intersectionSize = leftSet.size() + rightSet.size() - unionSet.size();
        return 1.0d * intersectionSize / unionSet.size();
    }
}
