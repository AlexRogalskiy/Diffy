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
package com.wildbeeslabs.sensiblemetrics.diffy.metrics.score;

import com.wildbeeslabs.sensiblemetrics.diffy.common.utils.ValidationUtils;
import com.wildbeeslabs.sensiblemetrics.diffy.metrics.interfaces.SimilarityScore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Longest common {@link SimilarityScore} implementation
 */
@Data
@EqualsAndHashCode
@ToString
public class LongestCommonSubsequenceScore implements SimilarityScore<CharSequence, Integer> {

    /**
     * Calculates longest common subsequence similarity score of two <code>CharSequence</code>'s passed as
     * input.
     *
     * @param left  first character sequence
     * @param right second character sequence
     * @return longestCommonSubsequenceLength
     * @throws IllegalArgumentException if either String input {@code null}
     */
    @Override
    public Integer apply(final CharSequence left, final CharSequence right) {
        ValidationUtils.notNull(left, "Left sequence should not be null");
        ValidationUtils.notNull(right, "Right sequence should not be null");

        return longestCommonSubsequence(left, right).length();
    }

    /**
     * Computes the longest common subsequence between the two <code>CharSequence</code>'s passed as input.
     *
     * <p>
     * Note, a substring and subsequence are not necessarily the same thing. Indeed, <code>abcxyzqrs</code> and
     * <code>xyzghfm</code> have both the same common substring and subsequence, namely <code>xyz</code>. However,
     * <code>axbyczqrs</code> and <code>abcxyzqtv</code> have the longest common subsequence <code>xyzq</code> because a
     * subsequence need not have adjacent characters.
     * </p>
     *
     * <p>
     * For reference, we give the definition of a subsequence for the reader: a <i>subsequence</i> is a sequence that
     * can be derived from another sequence by deleting some elements without changing the order of the remaining
     * elements.
     * </p>
     *
     * @param left  first character sequence
     * @param right second character sequence
     * @return the longest common subsequence found
     * @throws IllegalArgumentException if either String input {@code null}
     * @deprecated Deprecated as of 1.2 due to a typo in the method name.
     * Use {@link #longestCommonSubsequence(CharSequence, CharSequence)} instead.
     * This method will be removed in 2.0.
     */
    @Deprecated
    public CharSequence logestCommonSubsequence(final CharSequence left, final CharSequence right) {
        return longestCommonSubsequence(left, right);
    }

    /**
     * Computes the longest common subsequence between the two <code>CharSequence</code>'s passed as
     * input.
     *
     * <p>
     * Note, a substring and subsequence are not necessarily the same thing. Indeed, <code>abcxyzqrs</code> and
     * <code>xyzghfm</code> have both the same common substring and subsequence, namely <code>xyz</code>. However,
     * <code>axbyczqrs</code> and <code>abcxyzqtv</code> have the longest common subsequence <code>xyzq</code> because a
     * subsequence need not have adjacent characters.
     * </p>
     *
     * <p>
     * For reference, we give the definition of a subsequence for the reader: a <i>subsequence</i> is a sequence that
     * can be derived from another sequence by deleting some elements without changing the order of the remaining
     * elements.
     * </p>
     *
     * @param left  first character sequence
     * @param right second character sequence
     * @return the longest common subsequence found
     * @throws IllegalArgumentException if either String input {@code null}
     * @since 1.2
     */
    public CharSequence longestCommonSubsequence(final CharSequence left, final CharSequence right) {
        // Quick return
        if (left == null || right == null) {
            throw new IllegalArgumentException("Inputs must not be null");
        }
        final StringBuilder longestCommonSubstringArray = new StringBuilder(Math.max(left.length(), right.length()));
        final int[][] lcsLengthArray = longestCommonSubstringLengthArray(left, right);
        int i = left.length() - 1;
        int j = right.length() - 1;
        int k = lcsLengthArray[left.length()][right.length()] - 1;
        while (k >= 0) {
            if (left.charAt(i) == right.charAt(j)) {
                longestCommonSubstringArray.append(left.charAt(i));
                i = i - 1;
                j = j - 1;
                k = k - 1;
            } else if (lcsLengthArray[i + 1][j] < lcsLengthArray[i][j + 1]) {
                i = i - 1;
            } else {
                j = j - 1;
            }
        }
        return longestCommonSubstringArray.reverse().toString();
    }

    /**
     * Computes the lcsLengthArray for the sake of doing the actual lcs calculation. This is the
     * dynamic programming portion of the algorithm, and is the reason for the runtime complexity being
     * O(m*n), where m=left.length() and n=right.length().
     *
     * @param left  first character sequence
     * @param right second character sequence
     * @return lcsLengthArray
     */
    public int[][] longestCommonSubstringLengthArray(final CharSequence left, final CharSequence right) {
        final int[][] lcsLengthArray = new int[left.length() + 1][right.length() + 1];
        for (int i = 0; i < left.length(); i++) {
            for (int j = 0; j < right.length(); j++) {
                if (i == 0) {
                    lcsLengthArray[i][j] = 0;
                }
                if (j == 0) {
                    lcsLengthArray[i][j] = 0;
                }
                if (left.charAt(i) == right.charAt(j)) {
                    lcsLengthArray[i + 1][j + 1] = lcsLengthArray[i][j] + 1;
                } else {
                    lcsLengthArray[i + 1][j + 1] = Math.max(lcsLengthArray[i + 1][j], lcsLengthArray[i][j + 1]);
                }
            }
        }
        return lcsLengthArray;
    }
}
