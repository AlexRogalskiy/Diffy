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

import java.util.Arrays;

/**
 * Jaro-Winkler {@link SimilarityScore} implementation
 */
public class JaroWinklerScore implements SimilarityScore<CharSequence, Double> {

    /**
     * Represents a failed index search.
     */
    public static final int INDEX_NOT_FOUND = -1;

    /**
     * Find the Jaro Winkler Distance which indicates the similarity score
     * between two CharSequences.
     *
     * <pre>
     * distance.apply(null, null)          = IllegalArgumentException
     * distance.apply("","")               = 0.0
     * distance.apply("","a")              = 0.0
     * distance.apply("aaapppp", "")       = 0.0
     * distance.apply("frog", "fog")       = 0.93
     * distance.apply("fly", "ant")        = 0.0
     * distance.apply("elephant", "hippo") = 0.44
     * distance.apply("hippo", "elephant") = 0.44
     * distance.apply("hippo", "zzzzzzzz") = 0.0
     * distance.apply("hello", "hallo")    = 0.88
     * distance.apply("ABC Corporation", "ABC Corp") = 0.93
     * distance.apply("D N H Enterprises Inc", "D &amp; H Enterprises, Inc.") = 0.95
     * distance.apply("My Gym Children's Fitness Center", "My Gym. Childrens Fitness") = 0.92
     * distance.apply("PENNSYLVANIA", "PENNCISYLVNIA")    = 0.88
     * </pre>
     *
     * @param left  the first CharSequence, must not be null
     * @param right the second CharSequence, must not be null
     * @return result distance
     * @throws IllegalArgumentException if either CharSequence input is {@code null}
     */
    @Override
    public Double apply(final CharSequence left, final CharSequence right) {
        ValidationUtils.notNull(left, "Left sequence should not be null");
        ValidationUtils.notNull(right, "Right sequence should not be null");

        final double defaultScalingFactor = 0.1;
        final int[] mtp = matches(left, right);
        final double m = mtp[0];
        if (m == 0) {
            return 0D;
        }
        final double j = ((m / left.length() + m / right.length() + (m - (double) mtp[1] / 2) / m)) / 3;
        final double jw = j < 0.7D ? j : j + defaultScalingFactor * mtp[2] * (1D - j);
        return jw;
    }

    /**
     * This method returns the Jaro-Winkler string matches, half transpositions, prefix array.
     *
     * @param first  the first string to be matched
     * @param second the second string to be matched
     * @return mtp array containing: matches, half transpositions, and prefix
     */
    protected static int[] matches(final CharSequence first, final CharSequence second) {
        CharSequence max, min;
        if (first.length() > second.length()) {
            max = first;
            min = second;
        } else {
            max = second;
            min = first;
        }
        final int range = Math.max(max.length() / 2 - 1, 0);
        final int[] matchIndexes = new int[min.length()];
        Arrays.fill(matchIndexes, -1);
        final boolean[] matchFlags = new boolean[max.length()];
        int matches = 0;
        for (int mi = 0; mi < min.length(); mi++) {
            final char c1 = min.charAt(mi);
            for (int xi = Math.max(mi - range, 0), xn = Math.min(mi + range + 1, max.length()); xi < xn; xi++) {
                if (!matchFlags[xi] && c1 == max.charAt(xi)) {
                    matchIndexes[mi] = xi;
                    matchFlags[xi] = true;
                    matches++;
                    break;
                }
            }
        }
        final char[] ms1 = new char[matches];
        final char[] ms2 = new char[matches];
        for (int i = 0, si = 0; i < min.length(); i++) {
            if (matchIndexes[i] != -1) {
                ms1[si] = min.charAt(i);
                si++;
            }
        }
        for (int i = 0, si = 0; i < max.length(); i++) {
            if (matchFlags[i]) {
                ms2[si] = max.charAt(i);
                si++;
            }
        }
        int halfTranspositions = 0;
        for (int mi = 0; mi < ms1.length; mi++) {
            if (ms1[mi] != ms2[mi]) {
                halfTranspositions++;
            }
        }
        int prefix = 0;
        for (int mi = 0; mi < Math.min(4, min.length()); mi++) {
            if (first.charAt(mi) == second.charAt(mi)) {
                prefix++;
            } else {
                break;
            }
        }
        return new int[]{matches, halfTranspositions, prefix};
    }
}
