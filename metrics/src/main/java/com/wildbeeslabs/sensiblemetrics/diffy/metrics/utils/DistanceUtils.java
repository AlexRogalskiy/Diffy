package com.wildbeeslabs.sensiblemetrics.diffy.metrics.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class DistanceUtils {

    /**
     * Calculate the Damerau-Levenshtein Distance between two strings.  The basic difference
     * between this algorithm and the general Levenshtein algorithm is that damerau-Levenshtein
     * counts a swap of two characters next to each other as 1 instead of 2.  This breaks the
     * 'triangular equality', which makes it unusable for Metric trees.  See Wikipedia pages on
     * both Levenshtein and Damerau-Levenshtein and then make your decision as to which algorithm
     * is appropriate for your situation.
     *
     * @param source Source input string
     * @param target Target input string
     * @return The number of substitutions it would take
     * to make the source string identical to the target
     * string
     */
    public static int damerauLevenshteinDistance(CharSequence source, CharSequence target) {
        if (source == null || "".equals(source)) {
            return target == null || "".equals(target) ? 0 : target.length();
        } else if (target == null || "".equals(target)) {
            return source.length();
        }

        int srcLen = source.length();
        int targetLen = target.length();
        int[][] distanceMatrix = new int[srcLen + 1][targetLen + 1];

        // We need indexers from 0 to the length of the source string.
        // This sequential set of numbers will be the row "headers"
        // in the matrix.
        for (int srcIndex = 0; srcIndex <= srcLen; srcIndex++) {
            distanceMatrix[srcIndex][0] = srcIndex;
        }

        // We need indexers from 0 to the length of the target string.
        // This sequential set of numbers will be the
        // column "headers" in the matrix.
        for (int targetIndex = 0; targetIndex <= targetLen; targetIndex++) {
            // Set the value of the first cell in the column
            // equivalent to the current value of the iterator
            distanceMatrix[0][targetIndex] = targetIndex;
        }

        for (int srcIndex = 1; srcIndex <= srcLen; srcIndex++) {
            for (int targetIndex = 1; targetIndex <= targetLen; targetIndex++) {
                // If the current characters in both strings are equal
                int cost = source.charAt(srcIndex - 1) == target.charAt(targetIndex - 1) ? 0 : 1;

                // Find the current distance by determining the shortest path to a
                // match (hence the 'minimum' calculation on distances).
                distanceMatrix[srcIndex][targetIndex] = (int) MathUtilities.minimum(
                    // Character match between current character in
                    // source string and next character in target
                    distanceMatrix[srcIndex - 1][targetIndex] + 1,
                    // Character match between next character in
                    // source string and current character in target
                    distanceMatrix[srcIndex][targetIndex - 1] + 1,
                    // No match, at current, add cumulative penalty
                    distanceMatrix[srcIndex - 1][targetIndex - 1] + cost);

                // We don't want to do the next series of calculations on
                // the first pass because we would get an index out of bounds
                // exception.
                if (srcIndex == 1 || targetIndex == 1) {
                    continue;
                }

                // transposition check (if the current and previous
                // character are switched around (e.g.: t[se]t and t[es]t)...
                if (source.charAt(srcIndex - 1) == target.charAt(targetIndex - 2) && source.charAt(srcIndex - 2) == target.charAt(targetIndex - 1)) {
                    // What's the minimum cost between the current distance
                    // and a transposition.
                    distanceMatrix[srcIndex][targetIndex] = (int) MathUtilities.minimum(
                        // Current cost
                        distanceMatrix[srcIndex][targetIndex],
                        // Transposition
                        distanceMatrix[srcIndex - 2][targetIndex - 2] + cost);
                }
            }
        }

        return distanceMatrix[srcLen][targetLen];
    }
}
