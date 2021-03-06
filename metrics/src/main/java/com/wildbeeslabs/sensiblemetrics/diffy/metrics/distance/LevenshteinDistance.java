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

import java.util.Arrays;

/**
 * Levenshtein {@link SimilarityDistance} implementation
 */
@Data
@EqualsAndHashCode
@ToString
public class LevenshteinDistance implements SimilarityDistance<CharSequence, Integer> {
    /**
     * Default {@link LevenshteinDistance} instance
     */
    private static final LevenshteinDistance DEFAULT_INSTANCE = new LevenshteinDistance();

    /**
     * Threshold.
     */
    private final Integer threshold;

    /**
     * <p>
     * This returns the default instance that uses a version
     * of the algorithm that does not use a threshold parameter.
     * </p>
     *
     * @see LevenshteinDistance#getDefaultInstance()
     */
    public LevenshteinDistance() {
        this(null);
    }

    /**
     * <p>
     * If the threshold is not null, distance calculations will be limited to a maximum length.
     * If the threshold is null, the unlimited version of the algorithm will be used.
     * </p>
     *
     * @param threshold If this is null then distances calculations will not be limited.
     *                  This may not be negative.
     */
    public LevenshteinDistance(final Integer threshold) {
        if (threshold != null && threshold < 0) {
            throw new IllegalArgumentException("Threshold must not be negative");
        }
        this.threshold = threshold;
    }

    /**
     * <p>Find the Levenshtein distance between two Strings.</p>
     *
     * <p>A higher score indicates a greater distance.</p>
     *
     * <p>The previous implementation of the Levenshtein distance algorithm
     * was from <a href="http://www.merriampark.com/ld.htm">http://www.merriampark.com/ld.htm</a></p>
     *
     * <p>Chas Emerick has written an implementation in Java, which avoids an OutOfMemoryError
     * which can occur when my Java implementation is used with very large strings.<br>
     * This implementation of the Levenshtein distance algorithm
     * is from <a href="http://www.merriampark.com/ldjava.htm">http://www.merriampark.com/ldjava.htm</a></p>
     *
     * <pre>
     * distance.apply(null, *)             = IllegalArgumentException
     * distance.apply(*, null)             = IllegalArgumentException
     * distance.apply("","")               = 0
     * distance.apply("","a")              = 1
     * distance.apply("aaapppp", "")       = 7
     * distance.apply("frog", "fog")       = 1
     * distance.apply("fly", "ant")        = 3
     * distance.apply("elephant", "hippo") = 7
     * distance.apply("hippo", "elephant") = 7
     * distance.apply("hippo", "zzzzzzzz") = 8
     * distance.apply("hello", "hallo")    = 1
     * </pre>
     *
     * @param left  the first string, must not be null
     * @param right the second string, must not be null
     * @return result distance, or -1
     * @throws IllegalArgumentException if either String input {@code null}
     */
    @Override
    public Integer apply(final CharSequence left, final CharSequence right) {
        if (threshold != null) {
            return limitedCompare(left, right, threshold);
        }
        return unlimitedCompare(left, right);
    }

    /**
     * Gets the default instance.
     *
     * @return the default instance
     */
    public static LevenshteinDistance getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    /**
     * Find the Levenshtein distance between two CharSequences if it's less than or
     * equal to a given threshold.
     *
     * <p>
     * This implementation follows from Algorithms on Strings, Trees and
     * Sequences by Dan Gusfield and Chas Emerick's implementation of the
     * Levenshtein distance algorithm from <a
     * href="http://www.merriampark.com/ld.htm"
     * >http://www.merriampark.com/ld.htm</a>
     * </p>
     *
     * <pre>
     * limitedCompare(null, *, *)             = IllegalArgumentException
     * limitedCompare(*, null, *)             = IllegalArgumentException
     * limitedCompare(*, *, -1)               = IllegalArgumentException
     * limitedCompare("","", 0)               = 0
     * limitedCompare("aaapppp", "", 8)       = 7
     * limitedCompare("aaapppp", "", 7)       = 7
     * limitedCompare("aaapppp", "", 6))      = -1
     * limitedCompare("elephant", "hippo", 7) = 7
     * limitedCompare("elephant", "hippo", 6) = -1
     * limitedCompare("hippo", "elephant", 7) = 7
     * limitedCompare("hippo", "elephant", 6) = -1
     * </pre>
     *
     * @param left      the first CharSequence, must not be null
     * @param right     the second CharSequence, must not be null
     * @param threshold the target threshold, must not be negative
     * @return result distance, or -1
     */
    private static int limitedCompare(final CharSequence left, final CharSequence right, final int threshold) {
        ValidationUtils.notNull(left, "Left sequence should not be null");
        ValidationUtils.notNull(right, "Right sequence should not be null");

        CharSequence leftSequence = left;
        CharSequence rightSequence = right;
        if (threshold < 0) {
            throw new IllegalArgumentException("Threshold must not be negative");
        }

        /*
         * This implementation only computes the distance if it's less than or
         * equal to the threshold value, returning -1 if it's greater. The
         * advantage is performance: unbounded distance is O(nm), but a bound of
         * k allows us to reduce it to O(km) time by only computing a diagonal
         * stripe of width 2k + 1 of the cost table. It is also possible to use
         * this to compute the unbounded Levenshtein distance by starting the
         * threshold at 1 and doubling each time until the distance is found;
         * this is O(dm), where d is the distance.
         *
         * One subtlety comes from needing to ignore entries on the border of
         * our stripe eg. p[] = |#|#|#|* d[] = *|#|#|#| We must ignore the entry
         * to the left of the leftmost member We must ignore the entry above the
         * rightmost member
         *
         * Another subtlety comes from our stripe running off the matrix if the
         * strings aren't of the same size. Since string s is always swapped to
         * be the shorter of the two, the stripe will always run off to the
         * upper right instead of the lower left of the matrix.
         *
         * As a concrete example, suppose s is of length 5, t is of length 7,
         * and our threshold is 1. ParamType this case we're going to walk a stripe of
         * length 3. The matrix would look like so:
         *
         * <pre>
         *    1 2 3 4 5
         * 1 |#|#| | | |
         * 2 |#|#|#| | |
         * 3 | |#|#|#| |
         * 4 | | |#|#|#|
         * 5 | | | |#|#|
         * 6 | | | | |#|
         * 7 | | | | | |
         * </pre>
         *
         * Note how the stripe leads off the table as there is no possible way
         * to turn a string of length 5 into one of length 7 in edit distance of
         * 1.
         *
         * Additionally, this implementation decreases memory usage by using two
         * single-dimensional arrays and swapping them back and forth instead of
         * allocating an entire n by m matrix. This requires a few minor
         * changes, such as immediately returning when it's detected that the
         * stripe has run off the matrix and initially filling the arrays with
         * large values so that entries we don't compute are ignored.
         *
         * See Algorithms on Strings, Trees and Sequences by Dan Gusfield for
         * some discussion.
         */

        int n = leftSequence.length(); // length of left
        int m = right.length(); // length of right

        // if one string is empty, the edit distance is necessarily the length
        // of the other
        if (n == 0) {
            return m <= threshold ? m : -1;
        } else if (m == 0) {
            return n <= threshold ? n : -1;
        }

        if (n > m) {
            // swap the two strings to consume less memory
            final CharSequence tmp = leftSequence;
            leftSequence = rightSequence;
            rightSequence = tmp;
            n = m;
            m = rightSequence.length();
        }

        // the edit distance cannot be less than the length difference
        if (m - n > threshold) {
            return -1;
        }

        int[] p = new int[n + 1]; // 'previous' cost array, horizontally
        int[] d = new int[n + 1]; // cost array, horizontally
        int[] tempD; // placeholder to assist in swapping p and d

        // fill in starting table values
        final int boundary = Math.min(n, threshold) + 1;
        for (int i = 0; i < boundary; i++) {
            p[i] = i;
        }
        // these fills ensure that the value above the rightmost entry of our
        // stripe will be ignored in following loop iterations
        Arrays.fill(p, boundary, p.length, Integer.MAX_VALUE);
        Arrays.fill(d, Integer.MAX_VALUE);

        // iterates through t
        for (int j = 1; j <= m; j++) {
            final char rightJ = rightSequence.charAt(j - 1); // jth character of right
            d[0] = j;

            // compute stripe indices, constrain to array size
            final int min = Math.max(1, j - threshold);
            final int max = j > Integer.MAX_VALUE - threshold ? n : Math.min(n, j + threshold);

            // ignore entry left of leftmost
            if (min > 1) {
                d[min - 1] = Integer.MAX_VALUE;
            }

            // iterates through [min, max] in s
            for (int i = min; i <= max; i++) {
                if (leftSequence.charAt(i - 1) == rightJ) {
                    // diagonally left and up
                    d[i] = p[i - 1];
                } else {
                    // 1 + minimum of cell to the left, to the top, diagonally
                    // left and up
                    d[i] = 1 + Math.min(Math.min(d[i - 1], p[i]), p[i - 1]);
                }
            }

            // copy current distance counts to 'previous row' distance counts
            tempD = p;
            p = d;
            d = tempD;
        }

        // if p[n] is greater than the threshold, there's no guarantee on it
        // being the correct
        // distance
        if (p[n] <= threshold) {
            return p[n];
        }
        return -1;
    }

    /**
     * <p>Find the Levenshtein distance between two Strings.</p>
     *
     * <p>A higher score indicates a greater distance.</p>
     *
     * <p>The previous implementation of the Levenshtein distance algorithm
     * was from <a href="https://web.archive.org/web/20120526085419/http://www.merriampark.com/ldjava.htm">
     * https://web.archive.org/web/20120526085419/http://www.merriampark.com/ldjava.htm</a></p>
     *
     * <p>This implementation only need one single-dimensional arrays of length s.length() + 1</p>
     *
     * <pre>
     * unlimitedCompare(null, *)             = IllegalArgumentException
     * unlimitedCompare(*, null)             = IllegalArgumentException
     * unlimitedCompare("","")               = 0
     * unlimitedCompare("","a")              = 1
     * unlimitedCompare("aaapppp", "")       = 7
     * unlimitedCompare("frog", "fog")       = 1
     * unlimitedCompare("fly", "ant")        = 3
     * unlimitedCompare("elephant", "hippo") = 7
     * unlimitedCompare("hippo", "elephant") = 7
     * unlimitedCompare("hippo", "zzzzzzzz") = 8
     * unlimitedCompare("hello", "hallo")    = 1
     * </pre>
     *
     * @param left  the first CharSequence, must not be null
     * @param right the second CharSequence, must not be null
     * @return result distance, or -1
     * @throws IllegalArgumentException if either CharSequence input is {@code null}
     */
    private static int unlimitedCompare(final CharSequence left, final CharSequence right) {
        ValidationUtils.notNull(left, "Left sequence should not be null");
        ValidationUtils.notNull(right, "Right sequence should not be null");

        CharSequence leftSequence = left;
        CharSequence rightSequence = right;

        int n = leftSequence.length();
        int m = rightSequence.length();

        if (n == 0) {
            return m;
        } else if (m == 0) {
            return n;
        }

        if (n > m) {
            // swap the input strings to consume less memory
            final CharSequence tmp = leftSequence;
            leftSequence = rightSequence;
            rightSequence = tmp;
            n = m;
            m = rightSequence.length();
        }

        final int[] p = new int[n + 1];

        // indexes into strings left and right
        int i; // iterates through left
        int j; // iterates through right
        int upperLeft;
        int upper;

        char rightJ; // jth character of right
        int cost; // cost

        for (i = 0; i <= n; i++) {
            p[i] = i;
        }

        for (j = 1; j <= m; j++) {
            upperLeft = p[0];
            rightJ = rightSequence.charAt(j - 1);
            p[0] = j;

            for (i = 1; i <= n; i++) {
                upper = p[i];
                cost = leftSequence.charAt(i - 1) == rightJ ? 0 : 1;
                // minimum of cell to the left+1, to the top+1, diagonally left and up +cost
                p[i] = Math.min(Math.min(p[i - 1] + 1, p[i] + 1), upperLeft + cost);
                upperLeft = upper;
            }
        }
        return p[n];
    }

    /**
     * Utility method to return the minimum of three integers
     *
     * @param i0 The first integer
     * @param i1 The second integer
     * @param i2 The third integer
     * @return The minimum of the three parameters
     */
    private static int min3(int i0, int i1, int i2) {
        return Math.min(i0, Math.min(i1, i2));
    }

    /**
     * Compute the Levenshtein distance between Strings stringA and stringB,
     * respecting supplementary characters (i.e., surrogate pairs).
     * The algorithm is the two-row technique from:
     * <p>
     * https://en.wikipedia.org/wiki/Levenshtein_distance
     * <p>
     * which is derived from Hjelmqvist, Sten (26 Mar 2012), Fast, memory
     * efficient Levenshtein algorithm:
     * <p>
     * http://www.codeproject.com/Articles/13525/Fast-memory-efficient-Levenshtein-algorithm
     *
     * @param stringA the first string, must be non-null
     * @param stringB the second string, must be non-null
     * @return the Levenshtein distance between the two strings
     * @throws NullPointerException if either string is null
     */
    public static int lev(final String stringA, final String stringB) {
        ValidationUtils.notNull(stringA);
        ValidationUtils.notNull(stringB);

        // handle degenerate cases
        if (stringA.equals(stringB))
            return 0;
        if (stringA.length() == 0)
            return stringB.length();
        if (stringB.length() == 0)
            return stringA.length();

        // convert strings to code points, represented as int[]
        int[] s = stringA.codePoints().toArray();
        int[] t = stringB.codePoints().toArray();

        // create work vectors
        int[] v0 = new int[t.length + 1];
        int[] v1 = new int[t.length + 1];
        Arrays.setAll(v0, i -> i);

        for (int i = 0; i < s.length; i++) {
            // calculate v1 (current row distances) from the previous row v0
            // first element of v1 is A[i+1][0]
            // edit distance is delete (i+1) chars from s to match empty t
            v1[0] = i + 1;
            // use formula to fill in the rest of the row
            for (int j = 0; j < t.length; j++) {
                int cost = (s[i] == t[j]) ? 0 : 1;
                v1[j + 1] = min3(v1[j] + 1, v0[j + 1] + 1, v0[j] + cost);
            }
            // copy v1 (current row) to v0 (previous row) for next iteration
            Arrays.setAll(v0, j -> v1[j]);
        }
        return v1[t.length];
    }
}
