package com.wildbeeslabs.sensiblemetrics.diffy.metrics.distance;

import com.wildbeeslabs.sensiblemetrics.diffy.metrics.iface.SimilarityDistance;
import com.wildbeeslabs.sensiblemetrics.diffy.metrics.score.LongestCommonSubsequenceScore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Objects;

/**
 * Longest common {@link SimilarityDistance} implementation
 */
@Data
@EqualsAndHashCode
@ToString
public class LongestCommonSubsequenceDistance implements SimilarityDistance<CharSequence, Integer> {

    /**
     * Object for calculating the longest common subsequence that we can then normalize in apply.
     */
    private final LongestCommonSubsequenceScore longestCommonSubsequence = new LongestCommonSubsequenceScore();

    /**
     * Calculates an edit distance between two <code>CharSequence</code>'s <code>left</code> and
     * <code>right</code> as: <code>left.length() + right.length() - 2 * LCS(left, right)</code>, where
     * <code>LCS</code> is given in {@link LongestCommonSubsequence#apply(CharSequence, CharSequence)}.
     *
     * @param left  first character sequence
     * @param right second character sequence
     * @return distance
     * @throws IllegalArgumentException if either String input {@code null}
     */
    @Override
    public Integer apply(final CharSequence left, final CharSequence right) {
        Objects.requireNonNull(left, "Left sequence should not be null");
        Objects.requireNonNull(right, "Right sequence should not be null");

        return left.length() + right.length() - 2 * this.longestCommonSubsequence.apply(left, right);
    }
}
