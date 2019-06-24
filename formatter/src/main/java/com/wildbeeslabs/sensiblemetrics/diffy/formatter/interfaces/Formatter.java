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
package com.wildbeeslabs.sensiblemetrics.diffy.formatter.interfaces;

import com.wildbeeslabs.sensiblemetrics.diffy.common.utils.ValidationUtils;

import java.util.function.Function;

/**
 * Formatter interface declaration {@link Function}
 *
 * @param <T> type of input element to be matched by operation
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@FunctionalInterface
@SuppressWarnings("unchecked")
public interface Formatter<T> {

    /**
     * Returns formattd representation of {@code T} value
     *
     * @param value - initial input argument value to be formatted {@code T}
     * @return formatted representation {@link String}
     */
    CharSequence format(final T value);

    default Formatter<T> andThen(final Function<CharSequence, CharSequence> after) {
        ValidationUtils.notNull(after, "After function should not be null");
        return (final T t) -> after.apply(format(t));
    }

    default <V> Formatter<V> compose(final Function<? super V, ? extends T> before) {
        ValidationUtils.notNull(before, "Before function should not be null");
        return (final V v) -> format(before.apply(v));
    }
}
