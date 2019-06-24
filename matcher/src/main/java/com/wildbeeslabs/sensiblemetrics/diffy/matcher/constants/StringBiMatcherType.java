/*
 * The MIT License
 *
 * Copyright 2019 WildBees Labs, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
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
package com.wildbeeslabs.sensiblemetrics.diffy.matcher.constants;

import com.wildbeeslabs.sensiblemetrics.diffy.matcher.interfaces.BiMatcher;
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
