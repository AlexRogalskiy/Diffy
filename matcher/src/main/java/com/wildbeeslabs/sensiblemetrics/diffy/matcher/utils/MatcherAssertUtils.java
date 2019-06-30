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
package com.wildbeeslabs.sensiblemetrics.diffy.matcher.utils;

import com.wildbeeslabs.sensiblemetrics.diffy.matcher.description.iface.MatchDescription;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.description.impl.StringMatchDescription;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.interfaces.Matcher;
import lombok.experimental.UtilityClass;

import static org.apache.commons.lang3.StringUtils.EMPTY;

/**
 * RegexMatcher assertion utilities implementation
 */
@UtilityClass
public class MatcherAssertUtils {

    /**
     * Asserts by input parameters
     *
     * @param <T>     type of input element to be matched by operation
     * @param actual  - initial input actual {@code T} value
     * @param matcher - initial input {@link Matcher}
     */
    public static <T> void assertThat(final T actual, final Matcher<? super T> matcher) {
        assertThat(EMPTY, actual, matcher);
    }

    /**
     * Asserts by input parameters
     *
     * @param <T>     type of input element to be matched by operation
     * @param reason  - initial input assertion reason {@link String}
     * @param actual  - initial input actual {@code T} value
     * @param matcher - initial input {@link Matcher}
     */
    public static <T> void assertThat(final String reason, final T actual, final Matcher<? super T> matcher) {
        if (!matcher.matches(actual)) {
            final MatchDescription description = new StringMatchDescription();
            description.appendText(reason)
                .appendText("\nExpected: ")
                .append(matcher.getDescription())
                .appendText("\n     but: ");
            matcher.describeBy(actual, description);
            throw new AssertionError(description.toString());
        }
    }

    /**
     * Asserts by input parameters
     *
     * @param reason    - initial input assertion reason {@link String}
     * @param assertion - initial input binary assertion flag
     */
    public static void assertThat(final String reason, final boolean assertion) {
        if (!assertion) {
            throw new AssertionError(reason);
        }
    }
}
