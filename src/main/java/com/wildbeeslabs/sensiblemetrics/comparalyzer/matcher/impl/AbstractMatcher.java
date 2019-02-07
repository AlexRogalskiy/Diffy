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
package com.wildbeeslabs.sensiblemetrics.comparalyzer.matcher.impl;

import com.wildbeeslabs.sensiblemetrics.comparalyzer.matcher.Matcher;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * Abstract matcher implementation
 *
 * @param <T>
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@Slf4j
@Data
@EqualsAndHashCode
@ToString
public abstract class AbstractMatcher<T> implements Matcher<T> {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 4127438327874076332L;

    /**
     * Default collection of matchers {@link List}
     */
    private final List<Matcher<? super T>> matchers = new ArrayList<>();
    /**
     * Default collection of failed matchers {@link List}
     */
    private final List<Matcher<? super T>> failedMatchers = new ArrayList<>();

    /**
     * Default abstract matcher constructor
     */
    public AbstractMatcher() {
        this(null);
    }

    /**
     * Default abstract matcher constructor with input iterable collection of matchers {@link Iterable}
     *
     * @param matchers - initial input iterable collection of matchers {@link Iterable}
     */
    public AbstractMatcher(final Iterable<Matcher<? super T>> matchers) {
        withMatchers(matchers);
    }

    /**
     * Returns binary flag by initial argument comparison
     *
     * @param value - initial input argument value to be matched
     * @return true - if initial value matches input argument, false - otherwise
     */
    @Override
    public boolean matches(final T value) {
        getFailedMatchers().clear();
        getMatchers().forEach(matcher -> {
            if (!matcher.matches(value)) {
                getFailedMatchers().add(matcher);
            }
        });
        return getFailedMatchers().isEmpty();
    }

    /**
     * Removes input iterable collection of matchers {@link Matcher} from current matchers collection {@link List}
     *
     * @param matchers - initial input collection of matchers {@link Iterable}
     */
    public AbstractMatcher<T> withoutMatchers(final Iterable<Matcher<? super T>> matchers) {
        Optional.ofNullable(matchers)
                .orElseGet(Collections::emptyList)
                .forEach(property -> withoutMatcher(property));
        return this;
    }

    /**
     * Removes input matcher {@link Matcher} from current matchers collection {@link List}
     *
     * @param matcher - initial input matcher argument {@link Matcher}
     */
    public AbstractMatcher<T> withoutMatcher(final Matcher<? super T> matcher) {
        if (Objects.nonNull(matcher)) {
            getMatchers().remove(matcher);
        }
        return this;
    }

    /**
     * Adds input iterable collection of matchers {@link Iterable} to current matchers collection {@link List}
     *
     * @param matchers - initial input collection of matchers {@link Iterable}
     */
    public AbstractMatcher<T> withMatchers(final Iterable<Matcher<? super T>> matchers) {
        getMatchers().clear();
        Optional.ofNullable(matchers)
                .orElseGet(Collections::emptyList)
                .forEach(property -> withMatcher(property));
        return this;
    }

    /**
     * Adds input matcher {@link Matcher} to current matchers collection {@link List}
     *
     * @param matcher - initial input matcher argument {@link Matcher}
     */
    public AbstractMatcher<T> withMatcher(final Matcher<? super T> matcher) {
        if (Objects.nonNull(matcher)) {
            getMatchers().add(matcher);
        }
        return this;
    }
}
