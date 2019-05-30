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
package com.wildbeeslabs.sensiblemetrics.diffy.matcher.iface;

import com.wildbeeslabs.sensiblemetrics.diffy.exception.InvalidParameterException;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.enums.BiMatcherModeType;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Objects;

import static com.wildbeeslabs.sensiblemetrics.diffy.utils.StringUtils.formatMessage;

/**
 * Binary matcher interface declaration {@link BaseMatcher}
 *
 * @param <T> type of input element to be matched by operation
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@FunctionalInterface
public interface BiMatcher<T> extends BaseMatcher<T> {

    /**
     * Compares the two provided objects whether they are equal.
     *
     * @param value1 - initial input first value {@code T}
     * @param value2 - initial input last value {@code T}
     * @return true - if objects {@code T} are equal, false - otherwise
     */
    boolean matches(final T value1, final T value2);

    /**
     * Returns {@link BiMatcherModeType}
     *
     * @return {@link BiMatcherModeType}
     */
    @Override
    default BiMatcherModeType getMode() {
        return BiMatcherModeType.STRICT;
    }

    /**
     * Returns negated {@link BiMatcher} operator
     *
     * @return negated {@link BiMatcher} operator
     */
    default BiMatcher<T> negate() {
        return (final T t1, final T t2) -> !matches(t1, t2);
    }

    /**
     * Returns composed {@link BiMatcher} operator that represents a short-circuiting logical "AND" of current predicate and another
     *
     * @param other - initial input {@link BiMatcher} operator to perform operation by
     * @return composed {@link BiMatcher} operator
     * @throws NullPointerException if {@code after} is null
     *                              <p>
     *                              Input 1	Input 2	Output
     *                              0		0		0
     *                              0		1		0
     *                              1	 	0		0
     *                              1		1		1
     *                              </p>
     */
    default BiMatcher<T> and(final BiMatcher<? super T> other) {
        Objects.requireNonNull(other, "BiMatcher should not be null!");
        return (final T t1, final T t2) -> matches(t1, t2) && other.matches(t1, t2);
    }

    /**
     * Returns composed {@link BiMatcher} operator that represents a short-circuiting logical "NOT" of this predicate and another
     *
     * @param other - initial input {@link BiMatcher} operator to perform operation by
     * @return composed {@link BiMatcher} operator
     * @throws NullPointerException if matchers is {@code null}
     *                              <p>
     *                              Input 1	Output
     *                              0		1
     *                              1 		0
     *                              </p>
     */
    default BiMatcher<T> not(final BiMatcher<? super T> other) {
        Objects.requireNonNull(other, "BiMatcher should not be null!");
        return (BiMatcher<T>) other.negate();
    }

    /**
     * Returns composed {@link BiMatcher} operator that represents a short-circuiting logical "OR" of this predicate and another
     *
     * @param other - initial input {@link BiMatcher} operator to perform operation by
     * @return composed {@link BiMatcher} operator
     * @throws NullPointerException if matchers is {@code null}
     *                              <p>
     *                              Input 1	Input 2	Output
     *                              0		0		0
     *                              0 		1		1
     *                              1		0 		1
     *                              1		1		1
     *                              </p>
     */
    default BiMatcher<T> or(final BiMatcher<? super T> other) {
        Objects.requireNonNull(other, "BiMatcher should not be null!");
        return (final T t1, final T t2) -> matches(t1, t2) || other.matches(t1, t2);
    }

    /**
     * Returns composed {@link BiMatcher} operator that represents a short-circuiting logical "XOR" of this predicate and another
     *
     * @param other - initial input {@link BiMatcher} operator to perform operation by
     * @return composed {@link BiMatcher} operator
     * @throws NullPointerException if matchers is {@code null}
     *                              <p>
     *                              Input 1	Input 2	Output
     *                              0		0		0
     *                              0		1		1
     *                              1		0 		1
     *                              1		1		0
     *                              </p>
     */
    default BiMatcher<T> xor(final BiMatcher<? super T> other) {
        Objects.requireNonNull(other, "BiMatcher should not be null!");
        return (final T t1, final T t2) -> matches(t1, t2) ^ other.matches(t1, t2);
    }

    /**
     * Returns composed {@link BiMatcher} operator that represents a short-circuiting logical "NAND" of this predicate and another
     *
     * @param other - initial input {@link BiMatcher} operator to perform operation by
     * @return composed {@link BiMatcher} operator
     * @throws NullPointerException if matchers is {@code null}
     *                              <p>
     *                              Input 1	Input 2	Output
     *                              0 		0 		1
     *                              0 		1		1
     *                              1		0 		1
     *                              1		1	 	0
     *                              </p>
     */
    default BiMatcher<T> nand(final BiMatcher<? super T> other) {
        Objects.requireNonNull(other, "BiMatcher should not be null!");
        return (final T t1, final T t2) -> not(and(other)).matches(t1, t2);
    }

    /**
     * Returns composed {@link BiMatcher} operator that represents a short-circuiting logical "NOR" of this predicate and another
     *
     * @param other - initial input {@link BiMatcher} operator to perform operation by
     * @return composed {@link BiMatcher} operator
     * @throws NullPointerException if matchers is {@code null}
     *                              <p>
     *                              Input 1	Input 2	Output
     *                              0		0 	 	1
     *                              0 		1		0
     *                              1		0		0
     *                              1		1		0
     *                              </p>
     */
    default BiMatcher<T> nor(final BiMatcher<? super T> other) {
        Objects.requireNonNull(other, "BiMatcher should not be null!");
        return (final T t1, final T t2) -> not(or(other)).matches(t1, t2);
    }

    /**
     * Returns composed {@link BiMatcher} operator that represents a short-circuiting logical "XNOR" of this predicate and another
     *
     * @param other - initial input {@link BiMatcher} operator to perform operation by
     * @return composed {@link BiMatcher} operator
     * @throws NullPointerException if matchers is {@code null}
     *                              <p>
     *                              Input 1	Input 2	Output
     *                              0		0 	 	1
     *                              0 		1	 	0
     *                              1		0		0
     *                              1		1		1
     *                              </p>
     */
    default BiMatcher<T> xnor(final BiMatcher<? super T> other) {
        Objects.requireNonNull(other, "BiMatcher should not be null!");
        return (final T t1, final T t2) -> not(xor(other)).matches(t1, t2);
    }

    /**
     * Returns composed {@link BiMatcher} operator that represents a short-circuiting logical "AND" of {@link BiMatcher}s collection
     *
     * @param matchers - initial input {@link BiMatcher} operators to perform operation by
     * @return composed {@link BiMatcher} operator
     * @throws NullPointerException if matchers is {@code null}
     *                              <p>
     *                              Input 1	Input 2	Output
     *                              0		0		0
     *                              0		1		0
     *                              1	 	0		0
     *                              1		1		1
     *                              </p>
     */
    @SuppressWarnings("varargs")
    static <T> BiMatcher<T> andAll(final BiMatcher<T>... matchers) {
        Objects.requireNonNull(matchers, "BiMatchers should not be null!");
        return Arrays.stream(matchers).filter(m -> m.getMode().isEnable()).reduce(BiMatcher::and).orElseThrow(() -> InvalidParameterException.throwInvalidParameter(formatMessage("Unable to combine matchers = {%s} via logical AND", StringUtils.join(matchers, "|"))));
    }

    /**
     * Returns composed {@link BiMatcher} operator that represents a short-circuiting logical "OR" of {@link BiMatcher}s collection
     *
     * @param matchers - initial input {@link BiMatcher} operators to perform operation by
     * @return composed {@link BiMatcher} operator
     * @throws NullPointerException if matchers is {@code null}
     *                              <p>
     *                              Input 1	Input 2	Output
     *                              0		0		0
     *                              0 		1		1
     *                              1		0 		1
     *                              1		1		1
     *                              </p>
     */
    @SuppressWarnings("varargs")
    static <T> BiMatcher<T> orAll(final BiMatcher<T>... matchers) {
        Objects.requireNonNull(matchers, "BiMatchers should not be null!");
        return Arrays.stream(matchers).filter(m -> m.getMode().isEnable()).reduce(BiMatcher::or).orElseThrow(() -> InvalidParameterException.throwInvalidParameter(formatMessage("Unable to combine matchers = {%s} via logical OR", StringUtils.join(matchers, "|"))));
    }

    /**
     * Returns composed {@link BiMatcher} operator that represents a short-circuiting logical "XOR" of {@link BiMatcher}s collection
     *
     * @param matchers - initial input {@link BiMatcher} operators to perform operation by
     * @return composed {@link BiMatcher} operator
     * @throws NullPointerException if matchers is {@code null}
     *                              <p>
     *                              Input 1	Input 2	Output
     *                              0		0		0
     *                              0		1		1
     *                              1		0 		1
     *                              1		1		0
     *                              </p>
     */
    @SuppressWarnings("varargs")
    static <T> BiMatcher<T> xorAll(final BiMatcher<T>... matchers) {
        Objects.requireNonNull(matchers, "BiMatchers should not be null!");
        return Arrays.stream(matchers).filter(m -> m.getMode().isEnable()).reduce(BiMatcher::xor).orElseThrow(() -> InvalidParameterException.throwInvalidParameter(formatMessage("Unable to combine matchers = {%s} via logical XOR", StringUtils.join(matchers, "|"))));
    }

    /**
     * Returns composed {@link BiMatcher} operator that represents a short-circuiting logical "NAND" of {@link BiMatcher}s collection
     *
     * @param matchers - initial input {@link BiMatcher} operators to perform operation by
     * @return composed {@link BiMatcher} operator
     * @throws NullPointerException if matchers is {@code null}
     *                              <p>
     *                              Input 1	Input 2	Output
     *                              0 		0 		1
     *                              0 		1		1
     *                              1		0 		1
     *                              1		1	 	0
     *                              </p>
     */
    @SuppressWarnings("varargs")
    static <T> BiMatcher<T> nandAll(final BiMatcher<T>... matchers) {
        Objects.requireNonNull(matchers, "BiMatchers should not be null!");
        return Arrays.stream(matchers).filter(m -> m.getMode().isEnable()).reduce(BiMatcher::nand).orElseThrow(() -> InvalidParameterException.throwInvalidParameter(formatMessage("Unable to combine matchers = {%s} via logical NAND", StringUtils.join(matchers, "|"))));
    }

    /**
     * Returns composed {@link BiMatcher} operator that represents a short-circuiting logical "NOR" of {@link BiMatcher}s collection
     *
     * @param matchers - initial input {@link BiMatcher} operators to perform operation by
     * @return composed {@link BiMatcher} operator
     * @throws NullPointerException if matchers is {@code null}
     *                              <p>
     *                              Input 1	Input 2	Output
     *                              0		0 	 	1
     *                              0 		1		0
     *                              1		0		0
     *                              1		1		0
     *                              </p>
     */
    @SuppressWarnings("varargs")
    static <T> BiMatcher<T> norAll(final BiMatcher<T>... matchers) {
        Objects.requireNonNull(matchers, "BiMatchers should not be null!");
        return Arrays.stream(matchers).filter(m -> m.getMode().isEnable()).reduce(BiMatcher::nor).orElseThrow(() -> InvalidParameterException.throwInvalidParameter(formatMessage("Unable to combine matchers = {%s} via logical NOR", StringUtils.join(matchers, "|"))));
    }

    /**
     * Returns composed {@link BiMatcher} operator that represents a short-circuiting logical "XNOR" of {@link BiMatcher} collection
     *
     * @param matchers - initial input {@link BiMatcher} operators to perform operation by
     * @return composed {@link BiMatcher} operator
     * @throws NullPointerException if matchers is {@code null}
     *                              <p>
     *                              Input 1	Input 2	Output
     *                              0		0 	 	1
     *                              0 		1	 	0
     *                              1		0		0
     *                              1		1		1
     *                              </p>
     */
    @SuppressWarnings("varargs")
    static <T> BiMatcher<T> xnorAll(final BiMatcher<T>... matchers) {
        Objects.requireNonNull(matchers, "BiMatchers should not be null!");
        return Arrays.stream(matchers).filter(m -> m.getMode().isEnable()).reduce(BiMatcher::xnor).orElseThrow(() -> InvalidParameterException.throwInvalidParameter(formatMessage("Unable to combine matchers = {%s} via logical XNOR", StringUtils.join(matchers, "|"))));
    }
}
