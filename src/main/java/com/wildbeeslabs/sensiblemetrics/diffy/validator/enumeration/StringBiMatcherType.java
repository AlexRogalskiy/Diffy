package com.wildbeeslabs.sensiblemetrics.diffy.validator.enumeration;

import com.wildbeeslabs.sensiblemetrics.diffy.matcher.iface.BiMatcher;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.text.WordUtils;

/**
 * String binary matcher type {@link Enum}
 */
@Getter
@RequiredArgsConstructor
public enum StringBiMatcherType {
    /**
     * org.apache.commons.lang3.StringUtils
     */
    STARTS_WITH(org.apache.commons.lang3.StringUtils::startsWith),
    START_WITH_ICASE(org.apache.commons.lang3.StringUtils::startsWithIgnoreCase),
    ENDS_WITH(org.apache.commons.lang3.StringUtils::endsWith),
    ENDS_WITH_ICASE(org.apache.commons.lang3.StringUtils::endsWithIgnoreCase),
    CONTAINS(org.apache.commons.lang3.StringUtils::contains),
    EQUALS_ICASE(org.apache.commons.lang3.StringUtils::equalsIgnoreCase),
    EQUALS(org.apache.commons.lang3.StringUtils::equals),
    CONTAINS_ONLY(org.apache.commons.lang3.StringUtils::containsOnly),
    IS_NONE_BLANK(org.apache.commons.lang3.StringUtils::isNoneBlank),
    CONTAINS_ANY(org.apache.commons.lang3.StringUtils::containsAny),
    CONTAINS_NONE(org.apache.commons.lang3.StringUtils::containsNone),
    CONTAINS_ICASE(org.apache.commons.lang3.StringUtils::containsIgnoreCase),
    IS_NONE_EMPTY(org.apache.commons.lang3.StringUtils::isNoneEmpty),
    IS_ALL_BLANK(org.apache.commons.lang3.StringUtils::isAllBlank),
    IS_ALL_EMPTY(org.apache.commons.lang3.StringUtils::isAllEmpty),
    IS_ANY_BLANK(org.apache.commons.lang3.StringUtils::isAnyBlank),
    IS_ANY_EMPTY(org.apache.commons.lang3.StringUtils::isAnyEmpty),
    /**
     * org.apache.commons.text.WordUtils
     */
    IS_ALL_WORDS(WordUtils::containsAllWords);

    /**
     * String {@link BiMatcher} operator
     */
    private final BiMatcher<String> matcher;
}
