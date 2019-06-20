package com.wildbeeslabs.sensiblemetrics.diffy.metrics.score;

import com.wildbeeslabs.sensiblemetrics.diffy.metrics.iface.SimilarityScore;
import com.wildbeeslabs.sensiblemetrics.diffy.utility.ValidationUtils;
import lombok.Getter;

import java.util.Locale;
import java.util.Objects;

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
