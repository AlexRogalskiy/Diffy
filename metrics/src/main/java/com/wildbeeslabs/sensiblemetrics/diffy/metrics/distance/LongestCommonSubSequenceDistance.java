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
import com.wildbeeslabs.sensiblemetrics.diffy.metrics.score.LongestCommonSubsequenceScore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Longest common sub-sequence {@link SimilarityDistance} implementation
 */
@Data
@EqualsAndHashCode
@ToString
public class LongestCommonSubSequenceDistance implements SimilarityDistance<CharSequence, Integer> {

    /**
     * Object for calculating the longest common subsequence that we can then normalize in apply.
     */
    private final LongestCommonSubsequenceScore longestCommonSubSequence = new LongestCommonSubsequenceScore();

    /**
     * Calculates an edit distance between two <code>CharSequence</code>'s <code>left</code> and
     * <code>right</code> as: <code>left.length() + right.length() - 2 * LCS(left, right)</code>, where
     * <code>LCS</code> is given in {@link LongestCommonSubSequenceDistance#apply(CharSequence, CharSequence)}.
     *
     * @param left  first character sequence
     * @param right second character sequence
     * @return distance
     * @throws IllegalArgumentException if either String input {@code null}
     */
    @Override
    public Integer apply(final CharSequence left, final CharSequence right) {
        ValidationUtils.notNull(left, "Left sequence should not be null");
        ValidationUtils.notNull(right, "Right sequence should not be null");
        return left.length() + right.length() - 2 * this.longestCommonSubSequence.apply(left, right);
    }
}
