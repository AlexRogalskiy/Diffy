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
package com.wildbeeslabs.sensiblemetrics.diffy.factory;

import com.wildbeeslabs.sensiblemetrics.diffy.annotation.Factory;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.iface.DiffMatcher;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.iface.Matcher;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.impl.DefaultDiffMatcher;
import lombok.experimental.UtilityClass;

import java.util.Optional;

import static java.util.Arrays.asList;

/**
 * Default difference matcher factory implementation
 *
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@UtilityClass
public class DefaultDiffMatcherFactory {

    /**
     * Creates difference matcher instance {@link DiffMatcher}
     *
     * @param <T> type of input element to create matcher for
     * @param <E> type of difference matcher instance
     * @return difference matcher {@link DiffMatcher}
     */
    @Factory
    public static <T, E extends DiffMatcher<T>> E create() {
        return (E) new DefaultDiffMatcher<>();
    }

    /**
     * Creates difference matcher instance {@link DiffMatcher} by input matcher {@link Iterable}
     *
     * @param <T>     type of input element to create matcher for
     * @param <E>     type of difference matcher instance
     * @param matcher - initial input argument matcher {@link Matcher}
     * @return difference matcher {@link DiffMatcher}
     */
    @Factory
    public static <T, E extends DiffMatcher<T>> E create(final Matcher<? super T> matcher) {
        final DefaultDiffMatcher result = new DefaultDiffMatcher<>();
        result.include(matcher);
        return (E) result;
    }

    /**
     * Creates difference matcher instance {@link DiffMatcher} by input collection of matchers
     *
     * @param <T>      type of input element to create matcher for
     * @param <E>      type of difference matcher instance
     * @param matchers - initial input collection of matchers
     * @return difference matcher {@link DiffMatcher}
     */
    @Factory
    public static <T, E extends DiffMatcher<T>> E create(final Matcher<? super T>... matchers) {
        final DefaultDiffMatcher result = new DefaultDiffMatcher<>();
        result.include(asList(Optional.ofNullable(matchers).orElse(new Matcher[0])));
        return (E) result;
    }

    /**
     * Creates difference matcher instance {@link DiffMatcher} by input iterable collection of matchers {@link Iterable}
     *
     * @param <T>      type of input element to create matcher for
     * @param <E>      type of difference matcher instance
     * @param matchers - initial input iterable collection of matchers {@link Iterable}
     * @return difference matcher {@link DiffMatcher}
     */
    @Factory
    public static <T, E extends DiffMatcher<T>> E create(final Iterable<Matcher<? super T>> matchers) {
        final DefaultDiffMatcher result = new DefaultDiffMatcher<>();
        result.include(matchers);
        return (E) result;
    }
}
