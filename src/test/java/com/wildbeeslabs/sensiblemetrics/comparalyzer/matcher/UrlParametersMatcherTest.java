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
package com.wildbeeslabs.sensiblemetrics.comparalyzer.matcher;

import com.wildbeeslabs.sensiblemetrics.comparalyzer.AbstractDiffTest;
import com.wildbeeslabs.sensiblemetrics.comparalyzer.examples.matcher.UrlParametersMatcher;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Url parameters matcher unit test
 *
 * @author Alexander Rogalskiy
 * @version %I%, %G%
 * @since 1.0
 */
@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UrlParametersMatcherTest extends AbstractDiffTest {

    /**
     * Default url parameters string
     */
    private String urlString;

    @Before
    public void setUp() {
        this.urlString = getAlphaNumericStringMock().val();
    }

    @Test
    public void assertMatches() {
        final String urlString = "arg1=val1&arg2=val2&arg3=val3";
        final String testString = urlString;

        final UrlParametersMatcher urlParametersMatcher = UrlParametersMatcher.getInstance(urlString);
        assertTrue(urlParametersMatcher.matchesSafe(testString));
    }

    @Test
    public void assertLengthMismatch() {
        final String urlString = "arg1=val1&arg2=val2&arg3=val3";
        final String testString = "arg1=val1&arg2=val2&arg3=val3&arg4=val4";

        final UrlParametersMatcher urlParametersMatcher = UrlParametersMatcher.getInstance(urlString);
        assertFalse(urlParametersMatcher.matchesSafe(testString));
    }

    @Test
    public void assertNamesMismatch() {
        final String urlString = "arg1=val1&arg22=val2&arg3=val3";
        final String testString = "arg1=val1&arg2=val2&arg3=val3";

        final UrlParametersMatcher urlParametersMatcher = UrlParametersMatcher.getInstance(urlString);
        assertFalse(urlParametersMatcher.matchesSafe(testString));
    }

    @Test
    public void assertValuesMismatch() {
        final String urlString = "arg1=val1&arg2=val22&arg3=val3";
        final String testString = "arg1=val1&arg2=val2&arg3=val3";

        final UrlParametersMatcher urlParametersMatcher = UrlParametersMatcher.getInstance(urlString);
        assertFalse(urlParametersMatcher.matchesSafe(testString));
    }
}