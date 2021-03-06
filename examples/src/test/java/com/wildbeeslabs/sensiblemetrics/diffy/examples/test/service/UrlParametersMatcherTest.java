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
package com.wildbeeslabs.sensiblemetrics.diffy.examples.test.service;

import com.wildbeeslabs.sensiblemetrics.diffy.examples.matcher.UrlParametersMatcher;
import com.wildbeeslabs.sensiblemetrics.diffy.examples.test.AbstractDiffTest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Url parameters matcher unit test
 *
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UrlParametersMatcherTest extends AbstractDiffTest {

    /**
     * Default url string
     */
    private String urlString;

    @Before
    public void setUp() {
        this.urlString = this.getAlphaNumericStringMock().val();
    }

    @Test
    @DisplayName("Test match url strings by default url parameters matcher")
    public void test_validUrlParameters_Matcher() {
        // given
        final String urlString = "arg1=val1&arg2=val2&arg3=val3";
        final String testString = urlString;
        final UrlParametersMatcher urlParametersMatcher = UrlParametersMatcher.of(urlString);

        // then
        assertTrue(urlParametersMatcher.matchesSafe(testString));
    }

    @Test
    @DisplayName("Test mismatch url strings length by default url parameters matcher")
    public void test_mismatchUrlParametersLength_Matcher() {
        // given
        final String urlString = "arg1=val1&arg2=val2&arg3=val3";
        final String testString = "arg1=val1&arg2=val2&arg3=val3&arg4=val4";
        final UrlParametersMatcher urlParametersMatcher = UrlParametersMatcher.of(urlString);

        // then
        assertFalse(urlParametersMatcher.matchesSafe(testString));
    }

    @Test
    @DisplayName("Test mismatch url string names by default url parameter matcher")
    public void test_mismatchUrlParametersNames_Matcher() {
        // given
        final String urlString = "arg1=val1&arg22=val2&arg3=val3";
        final String testString = "arg1=val1&arg2=val2&arg3=val3";
        final UrlParametersMatcher urlParametersMatcher = UrlParametersMatcher.of(urlString);

        // then
        assertFalse(urlParametersMatcher.matchesSafe(testString));
    }

    @Test
    @DisplayName("Test mismatch url string values by default url parameter matcher")
    public void test_mismatchUrlParametersValues_Matcher() {
        // given
        final String urlString = "arg1=val1&arg2=val22&arg3=val3";
        final String testString = "arg1=val1&arg2=val2&arg3=val3";
        final UrlParametersMatcher urlParametersMatcher = UrlParametersMatcher.of(urlString);

        // then
        assertFalse(urlParametersMatcher.matchesSafe(testString));
    }

    @Test
    @DisplayName("Test mismatch nullable url string values by default url parameter matcher")
    public void test_mismatchNullableUrlParametersValues_by_Matcher() {
        // given
        final String urlString = null;
        final String testString = "arg1=val1&arg2=val2&arg3=val3";
        final UrlParametersMatcher urlParametersMatcher = UrlParametersMatcher.of(urlString);
        ;

        // then
        assertFalse(urlParametersMatcher.matchesSafe(testString));
    }

    @Test(expected = NullPointerException.class)
    @DisplayName("Test mismatch url string values by nullable url parameter matcher")
    public void test_mismatchUrlParametersValues_by_nullableMatcher() {
        // given
        final String urlString = "arg1=val1&arg2=val22&arg3=val3";
        final String testString = "arg1=val1&arg2=val2&arg3=val3";
        final UrlParametersMatcher urlParametersMatcher = null;

        // then
        assertFalse(urlParametersMatcher.matchesSafe(testString));
    }
}
