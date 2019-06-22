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
package com.wildbeeslabs.sensiblemetrics.diffy.matcher.service;

import com.wildbeeslabs.sensiblemetrics.diffy.common.annotation.Factory;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.description.iface.MatchDescription;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.interfaces.Matcher;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Objects;

/**
 * Throwable cause {@link AbstractMatcher} implementation
 *
 * @param <T> the type of the throwable being matched
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ThrowableCauseMatcher<T extends Throwable> extends AbstractMatcher<T> {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = -7894738824768468436L;

    private final Matcher<T> matcher;

    public ThrowableCauseMatcher(final Matcher<T> matcher) {
        Objects.requireNonNull(matcher, "Matcher should not be null");
        this.matcher = matcher;
    }

    @Override
    public boolean matches(final T item) {
        return this.matcher.matches((T) item.getCause());
    }

    @Override
    public void describe(final T item, final MatchDescription description) {
        super.describe(item, description);
        this.matcher.describeBy(item, description);
    }

    @Override
    public void describeTo(final MatchDescription description) {
        super.describeTo(description);
        description.appendDescriptionOf(this.matcher);
    }

    /**
     * Returns a matcher that verifies that the outer exception has a cause for which the supplied matcher
     * evaluates to true.
     *
     * @param matcher to apply to the cause of the outer exception
     * @param <T>     type of the outer exception
     */
    @Factory
    public static <T extends Throwable> Matcher<T> of(final Matcher<T> matcher) {
        return new ThrowableCauseMatcher<T>(matcher);
    }
}
