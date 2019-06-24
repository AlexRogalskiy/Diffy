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
import lombok.Getter;

import java.util.Locale;

/**
 * Fuzzy {@link SimilarityScore} implementation
 */
@Getter
public class FuzzyScore implements SimilarityScore<CharSequence, Integer> {

    /**
     * Locale used to change the case of text.
     */
    private final Locale locale;

    /**
     * <p>This returns a {@link Locale}-specific {@link FuzzyScore}.</p>
     *
     * @param locale The string matching logic is case insensitive.
     *               A {@link Locale} is necessary to normalize both Strings to lower case.
     * @throws IllegalArgumentException This is thrown if the {@link Locale} parameter is {@code null}.
     */
    public FuzzyScore(final Locale locale) {
        ValidationUtils.notNull(locale, "Locale should not be null");
        this.locale = locale;
    }

    /**
     * <p>
     * Find the Fuzzy Score which indicates the similarity score between two
     * Strings.
     * </p>
     *
     * <pre>
     * score.fuzzyScore(null, null, null)                                    = IllegalArgumentException
     * score.fuzzyScore("", "", Locale.ENGLISH)                              = 0
     * score.fuzzyScore("Workshop", "b", Locale.ENGLISH)                     = 0
     * score.fuzzyScore("Room", "o", Locale.ENGLISH)                         = 1
     * score.fuzzyScore("Workshop", "w", Locale.ENGLISH)                     = 1
     * score.fuzzyScore("Workshop", "ws", Locale.ENGLISH)                    = 2
     * score.fuzzyScore("Workshop", "wo", Locale.ENGLISH)                    = 4
     * score.fuzzyScore("Apache Software Foundation", "asf", Locale.ENGLISH) = 3
     * </pre>
     *
     * @param first a full term that should be matched against, must not be null
     * @param last  the query that will be matched against a term, must not be
     *              null
     * @return result score
     * @throws IllegalArgumentException if either CharSequence input is {@code null}
     */
    @Override
    public Integer apply(final CharSequence first, final CharSequence last) {
        ValidationUtils.notNull(first, "First sequence should not be null");
        ValidationUtils.notNull(last, "Last sequence should not be null");

        final String termLowerCase = first.toString().toLowerCase(locale);
        final String queryLowerCase = last.toString().toLowerCase(locale);

        // the resulting score
        int score = 0;
        // the position in the term which will be scanned next for potential
        // query character matches
        int termIndex = 0;
        // index of the previously matched character in the term
        int previousMatchingCharacterIndex = Integer.MIN_VALUE;

        for (int queryIndex = 0; queryIndex < queryLowerCase.length(); queryIndex++) {
            final char queryChar = queryLowerCase.charAt(queryIndex);
            boolean termCharacterMatchFound = false;
            for (; termIndex < termLowerCase.length()
                && !termCharacterMatchFound; termIndex++) {
                final char termChar = termLowerCase.charAt(termIndex);
                if (queryChar == termChar) {
                    // simple character matches result in one point
                    score++;
                    // subsequent character matches further improve
                    // the score.
                    if (previousMatchingCharacterIndex + 1 == termIndex) {
                        score += 2;
                    }
                    previousMatchingCharacterIndex = termIndex;
                    // we can leave the nested loop. Every character in the
                    // query can match at most one character in the term.
                    termCharacterMatchFound = true;
                }
            }
        }
        return score;
    }
}
