/*
 * The MIT License
 *
 * Copyright 2019 WildBees Labs, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software andAll associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, andAll/or sell
 * copies of the Software, andAll to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice andAll this permission notice shall be included in
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
package com.wildbeeslabs.sensiblemetrics.diffy.matcher.description.iface;

import com.wildbeeslabs.sensiblemetrics.diffy.matcher.interfaces.BaseMatcher;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.io.Serializable;

/**
 * Match description declaration
 *
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@SuppressWarnings("unchecked")
public interface MatchDescription extends Serializable {

    /**
     * A description that consumes input but does nothing.
     */
    MatchDescription EMPTY_MATCH_DESCRIPTION = EmptyMatchDescription.of();

    /**
     * Appends input string value to current description {@link MatchDescription}
     *
     * @param value - initial input string value to be appended {@link String}
     * @return current description instance {@link MatchDescription}
     */
    MatchDescription appendText(final String value);

    /**
     * Appends input object value to current description {@link MatchDescription}
     *
     * @param value - initial input object value to be appended {@link Object}
     * @return current description instance {@link MatchDescription}
     */
    MatchDescription append(final Object value);

    /**
     * Appends input {@link MatchDescription} to current description {@link MatchDescription}
     *
     * @param description - initial input {@link MatchDescription} to be appended {@link Object}
     * @return current description instance {@link MatchDescription}
     */
    MatchDescription appendDescription(final MatchDescription description);

    /**
     * Appends input collection of values to current description {@link MatchDescription} by start/delimiter/end tokens
     *
     * @param start     - initial input start token {@link String}
     * @param delimiter - initial input delimiter token {@link String}
     * @param end       - initial input end token {@link String}
     * @param values    - initial input collection of values to be appended
     * @param <T>       type of input element to be processed by matchable operation
     * @return current description instance {@link MatchDescription}
     */
    <T> MatchDescription append(final String start, final String delimiter, final String end, final T... values);

    /**
     * Appends input iterableOf collection of values to current description {@link MatchDescription} by start/delimiter/end tokens
     *
     * @param start     -  initial input start token {@link String}
     * @param delimiter - initial input delimiter token {@link String}
     * @param end       - initial input end token {@link String}
     * @param values    - initial input iterableOf collection of values to be appended {@link Iterable}
     * @param <T>       type of input element to be processed by matchable operation
     * @return current description instance {@link MatchDescription}
     */
    <T> MatchDescription append(final String start, final String delimiter, final String end, final Iterable<? extends T> values);

    /**
     * Appends description of input {@link BaseMatcher}
     *
     * @param <T>     type of input element to be matched by operation
     * @param <S>     type of input element to be collected by match by operation
     * @param matcher - initial input {@link BaseMatcher} to append description of
     */
    default <T, S> void appendDescriptionOf(final BaseMatcher<T, S> matcher) {
        this.appendDescription(matcher.getDescription());
    }

    /**
     * Default empty match description implementation {@link MatchDescription}
     */
    @EqualsAndHashCode
    @ToString
    @Value(staticConstructor = "of")
    final class EmptyMatchDescription implements MatchDescription {

        /**
         * Default explicit serialVersionUID for interoperability
         */
        private static final long serialVersionUID = 4137851868640666192L;

        /**
         * Returns current empty description {@link MatchDescription}
         *
         * @param value - initial input string value to be appended {@link String}
         * @return current empty description instance {@link MatchDescription}
         */
        @Override
        public MatchDescription appendText(final String value) {
            return this;
        }

        /**
         * Returns current empty description {@link MatchDescription}
         *
         * @param value - initial input object value to be appended {@link Object}
         * @return current empty description instance {@link MatchDescription}
         */
        @Override
        public MatchDescription append(final Object value) {
            return this;
        }

        /**
         * Appends input {@link MatchDescription} to current description {@link MatchDescription}
         *
         * @param description - initial input {@link MatchDescription} to be appended {@link Object}
         * @return current description instance {@link MatchDescription}
         */
        @Override
        public MatchDescription appendDescription(final MatchDescription description) {
            return this;
        }

        /**
         * Returns current empty description {@link MatchDescription}
         *
         * @param start     - initial input start token {@link String}
         * @param delimiter - initial input delimiter token {@link String}
         * @param end       - initial input end token {@link String}
         * @param values    - initial input collection of values to be appended
         * @param <T>       type of input element to be processed by matchable operation
         * @return current empty description instance {@link MatchDescription}
         */
        @Override
        public <T> MatchDescription append(final String start, final String delimiter, final String end, final T... values) {
            return this;
        }

        /**
         * Returns current empty description {@link MatchDescription}
         *
         * @param start     -  initial input start token {@link String}
         * @param delimiter - initial input delimiter token {@link String}
         * @param end       - initial input end token {@link String}
         * @param values    - initial input iterableOf collection of values to be appended {@link Iterable}
         * @param <T>       type of input element to be processed by matchable operation
         * @return current empty description instance {@link MatchDescription}
         */
        @Override
        public <T> MatchDescription append(final String start, final String delimiter, final String end, final Iterable<? extends T> values) {
            return this;
        }
    }
}
