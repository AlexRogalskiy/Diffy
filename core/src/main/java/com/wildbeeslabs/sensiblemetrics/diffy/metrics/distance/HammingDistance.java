package com.wildbeeslabs.sensiblemetrics.diffy.metrics.distance;

import com.wildbeeslabs.sensiblemetrics.diffy.metrics.iface.SimilarityDistance;
import com.wildbeeslabs.sensiblemetrics.diffy.utility.ValidationUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Objects;

/**
 * Hamming {@link SimilarityDistance} implementation
 */
@Data
@EqualsAndHashCode
@ToString
public class HammingDistance implements SimilarityDistance<CharSequence, Integer> {

    /**
     * Find the Hamming Distance between two strings with the same
     * length.
     *
     * <p>The distance starts with zero, and for each occurrence of a
     * different character in either String, it increments the distance
     * by 1, and finally return its value.</p>
     *
     * <p>Since the Hamming Distance can only be calculated between strings of equal length, input of different lengths
     * will throw IllegalArgumentException</p>
     *
     * <pre>
     * distance.apply("", "")               = 0
     * distance.apply("pappa", "pappa")     = 0
     * distance.apply("1011101", "1011111") = 1
     * distance.apply("ATCG", "ACCC")       = 2
     * distance.apply("karolin", "kerstin"  = 3
     * </pre>
     *
     * @param left  the first CharSequence, must not be null
     * @param right the second CharSequence, must not be null
     * @return distance
     * @throws IllegalArgumentException if either input is {@code null} or
     *                                  if they do not have the same length
     */
    @Override
    public Integer apply(final CharSequence left, final CharSequence right) {
        ValidationUtils.notNull(left, "Left sequence should not be nul");
        ValidationUtils.notNull(right, "Rigth sequence should not be nul");

        if (left.length() != right.length()) {
            throw new IllegalArgumentException("CharSequences must have the same length");
        }

        int distance = 0;
        for (int i = 0; i < left.length(); i++) {
            if (left.charAt(i) != right.charAt(i)) {
                distance++;
            }
        }
        return distance;
    }
}
