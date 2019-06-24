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

import com.google.common.base.Strings;
import com.wildbeeslabs.sensiblemetrics.diffy.common.utils.RegexUtils;
import com.wildbeeslabs.sensiblemetrics.diffy.common.utils.StringUtils;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.interfaces.Matcher;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * String validator type {@link Enum}
 */
@Getter
@RequiredArgsConstructor
public enum StringMatcherType {
    /**
     * com.wildbeeslabs.sensiblemetrics.diffy.common.utils.StringUtils
     */
    IS_CONTAIN_ISO_CHAR(StringUtils::containsIsoControlCharacter),
    IS_NOT_CONTAIN_ISO_CHAR(StringUtils::doesNotContainIsoControlCharacter),
    IS_PALINDROME(StringUtils::isPalindrome),
    IS_PERMUTATION_PALINDROME(StringUtils::isPermutationOfPalindrome),
    IS_UNIQUE(StringUtils::isUnique),
    IS_VALID_PACKAGE(StringUtils::isValidPackage),
    /**
     * com.wildbeeslabs.sensiblemetrics.diffy.common.utils.RegexUtils
     */
    IS_REGEX(RegexUtils::isRegex),
    /**
     * com.google.common.base.Strings
     */
    IS_NULL_OR_EMPTY(Strings::isNullOrEmpty),
    /**
     * org.apache.commons.lang3.math.NumberUtils
     */
    IS_CREATABLE(NumberUtils::isCreatable),
    IS_DIGITS(NumberUtils::isDigits),
    IS_PARSABLE(NumberUtils::isParsable),
    IS_NUMBER(org.apache.commons.lang.math.NumberUtils::isNumber),
    /**
     * org.apache.commons.lang3.StringUtils
     */
    IS_LOWER_CASE(org.apache.commons.lang3.StringUtils::isAllLowerCase),
    IS_UPPER_CASE(org.apache.commons.lang3.StringUtils::isAllUpperCase),
    IS_ALPHA(org.apache.commons.lang3.StringUtils::isAlpha),
    IS_ALPHA_NUMERIC(org.apache.commons.lang3.StringUtils::isAlphanumericSpace),
    IS_ALPHA_NUMERIC_SPACE(org.apache.commons.lang3.StringUtils::isAlphanumericSpace),
    IS_EMPTY(org.apache.commons.lang3.StringUtils::isEmpty),
    IS_ALPHA_SPACE(org.apache.commons.lang3.StringUtils::isAlphaSpace),
    IS_BLANK(org.apache.commons.lang3.StringUtils::isBlank),
    IS_NOT_BLANK(org.apache.commons.lang3.StringUtils::isNotBlank),
    IS_ASCII_PRINTABLE(org.apache.commons.lang3.StringUtils::isAsciiPrintable),
    IS_MIXED_CASE(org.apache.commons.lang3.StringUtils::isMixedCase),
    IS_NUMERIC(org.apache.commons.lang3.StringUtils::isNumeric),
    IS_NUMERIC_SPACE(org.apache.commons.lang3.StringUtils::isNumericSpace),
    IS_WHITESPACE(org.apache.commons.lang3.StringUtils::isWhitespace),
    /**
     * javax.lang.model.SourceVersion
     */
    IS_IDENTIFIER(javax.lang.model.SourceVersion::isIdentifier),
    IS_KEYWORD(javax.lang.model.SourceVersion::isKeyword),
    IS_NAME(javax.lang.model.SourceVersion::isName);

    /**
     * String {@link Matcher} operator
     */
    private final Matcher<String> matcher;
}
