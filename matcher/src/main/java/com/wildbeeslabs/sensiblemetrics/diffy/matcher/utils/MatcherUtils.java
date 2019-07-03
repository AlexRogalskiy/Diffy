package com.wildbeeslabs.sensiblemetrics.diffy.matcher.utils;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

@UtilityClass
public class MatcherUtils {

    /**
     * Compares pairs of strings using {@link #matches(String, String)} method and returns
     * Compare two strings and return match information:
     * <ul>
     * <li>{@code EXACT} when all string pairs have exact match</li>
     * <li>{@code PARTIAL} when no less than one string pair have partial match, while other can have exact match</li>
     * <li>{@code NO_MATCH} when at least one string pair have no match, while other pairs can have partial or exact match</li>
     * </ul>
     *
     * @param ss string pairs. Every odd parameter forms first element of pair, every even parameter - the second one.
     *           All extra parameters are ignored, for example {@code allMatches("s1", "s2", "s3")} call will process only
     *           one pair (s1, s2) and 's3' will be ignored.
     * @return {@link MatchMode} result
     */
    public static MatchMode allMatches(final String... ss) {
        MatchMode matchMode = MatchMode.EXACT;
        for (int i = 0; i < ss.length / 2; i++) {
            matchMode = worseMatch(matchMode, matches(ss[i * 2], ss[i * 2 + 1]));
        }
        return matchMode;
    }

    /**
     * Compares two strings and returns match information:
     * <ul>
     * <li>{@code EXACT} when both strings are either null or blank, or both strings are equal (case is ignored)</li>
     * <li>{@code PARTIAL} when exactly one of two strings is empty and another one contains any data</li>
     * <li>{@code NO_MATCH} when both strings are non-empty and their contents is different</li>
     * </ul>
     *
     * @param s1 first string
     * @param s2 second string
     * @return {@link MatchMode} result
     */
    public static MatchMode matches(final String s1, final String s2) {
        if ((StringUtils.isEmpty(s1) && StringUtils.isEmpty(s2)) || (StringUtils.isNotEmpty(s1) && StringUtils.isNotEmpty(s2) && s1.equalsIgnoreCase(s2))) {
            return MatchMode.EXACT;
        } else if (StringUtils.isEmpty(s1) || StringUtils.isEmpty(s2)) {
            return MatchMode.PARTIAL;
        }
        return MatchMode.NO_MATCH;
    }

    public static MatchMode worseMatch(final MatchMode m1, final MatchMode m2) {
        if (m1.ordinal() > m2.ordinal()) {
            return m1;
        }
        return m2;
    }

    /**
     * Match mode enumeration
     */
    public enum MatchMode {
        EXACT,
        PARTIAL,
        NO_MATCH
    }
}
