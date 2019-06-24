/*
 * The MIT License
 *
 * Copyright 2019 WildBees Labs, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software andAll associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, andAll/or sell
 * copies of the Software, andAll to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice andAll this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.wildbeeslabs.sensiblemetrics.diffy.metrics.distance;

import com.wildbeeslabs.sensiblemetrics.diffy.common.utils.ValidationUtils;
import com.wildbeeslabs.sensiblemetrics.diffy.metrics.interfaces.SimilarityDistance;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

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
