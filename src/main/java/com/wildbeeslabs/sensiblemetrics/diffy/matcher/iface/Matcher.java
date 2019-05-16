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
package com.wildbeeslabs.sensiblemetrics.diffy.matcher.iface;

import com.wildbeeslabs.sensiblemetrics.diffy.entry.description.iface.MatchDescription;
import com.wildbeeslabs.sensiblemetrics.diffy.exception.InvalidParameterException;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

import static com.wildbeeslabs.sensiblemetrics.diffy.entry.description.iface.MatchDescription.DEFAULT_EMPTY_MATCH_DESCRIPTION;
import static com.wildbeeslabs.sensiblemetrics.diffy.utils.StringUtils.formatMessage;

/**
 * Matcher interface declaration by input object instance
 *
 * @param <T> type of input element to be matched by operation
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@FunctionalInterface
public interface Matcher<T> extends Serializable {

    /**
     * Returns binary flag by initial argument match comparison
     *
     * @param value - initial input argument value to be matched {@code T}
     * @return true - if initial value matches input argument, false - otherwise
     */
    boolean matches(final T value);

    /**
     * Returns default matcher description {@link MatchDescription}
     *
     * @return matcher description {@link MatchDescription}
     */
    default MatchDescription getDescription() {
        return DEFAULT_EMPTY_MATCH_DESCRIPTION;
    }

    /**
     * Returns negated {@link Matcher} operator
     *
     * @return negated {@link Matcher} operator
     */
    default Matcher<T> negate() {
        return (final T t) -> !matches(t);
    }

    /**
     * Returns composed {@link Matcher} operator that represents a short-circuiting logical "AND" of current predicate and another
     *
     * @param other - initial input {@link Matcher} operator to perform operation by
     * @return composed {@link Matcher} operator
     * @throws NullPointerException if {@code after} is null
     */
    default Matcher<T> and(final Matcher<? super T> other) {
        Objects.requireNonNull(other, "Matcher should not be null!");
        return (final T t) -> matches(t) && other.matches(t);
    }

    /**
     * Returns composed {@link Matcher} operator that represents a short-circuiting logical "NOT" of this predicate and another
     *
     * @param other - initial input {@link Matcher} operator to perform operation by
     * @return composed {@link Matcher} operator
     * @throws NullPointerException if matchers is {@code null}
     */
    default Matcher<T> not(final Matcher<? super T> other) {
        Objects.requireNonNull(other, "Matcher should not be null!");
        return (Matcher<T>) other.negate();
    }

    /**
     * Returns composed {@link Matcher} operator that represents a short-circuiting logical "OR" of this predicate and another
     *
     * @param other - initial input {@link Matcher} operator to perform operation by
     * @return composed {@link Matcher} operator
     * @throws NullPointerException if matchers is {@code null}
     */
    default Matcher<T> or(final Matcher<? super T> other) {
        Objects.requireNonNull(other, "Matcher should not be null!");
        return (final T t) -> matches(t) || other.matches(t);
    }

    /**
     * Returns composed {@link Matcher} operator that represents a short-circuiting logical "XOR" of this predicate and another
     *
     * @param other - initial input {@link Matcher} operator to perform operation by
     * @return composed {@link Matcher} operator
     * @throws NullPointerException if matchers is {@code null}
     */
    default Matcher<T> xor(final Matcher<? super T> other) {
        Objects.requireNonNull(other, "Matcher should not be null!");
        return (final T t) -> matches(t) ^ other.matches(t);
    }

    /**
     * Returns composed {@link Matcher} operator that represents a short-circuiting logical "NAND" of this predicate and another
     *
     * @param other - initial input {@link Matcher} operator to perform operation by
     * @return composed {@link Matcher} operator
     * @throws NullPointerException if matchers is {@code null}
     */
    default Matcher<T> nand(final Matcher<? super T> other) {
        Objects.requireNonNull(other, "Matcher should not be null!");
        return (final T t) -> not(and(other)).matches(t);
    }

    /**
     * Returns composed {@link Matcher} operator that represents a short-circuiting logical "NOR" of this predicate and another
     *
     * @param other - initial input {@link Matcher} operator to perform operation by
     * @return composed {@link Matcher} operator
     * @throws NullPointerException if matchers is {@code null}
     */
    default Matcher<T> nor(final Matcher<? super T> other) {
        Objects.requireNonNull(other, "Matcher should not be null!");
        return (final T t) -> not(or(other)).matches(t);
    }

    /**
     * Returns composed {@link Matcher} operator that represents a short-circuiting logical "XNOR" of this predicate and another
     *
     * @param other - initial input {@link Matcher} operator to perform operation by
     * @return composed {@link Matcher} operator
     * @throws NullPointerException if matchers is {@code null}
     */
    default Matcher<T> xnor(final Matcher<? super T> other) {
        Objects.requireNonNull(other, "Matcher should not be null!");
        return (final T t) -> not(xor(other)).matches(t);
    }

    /**
     * Returns composed {@link Matcher} operator that represents a short-circuiting logical "AND" of {@link Matcher} collection
     *
     * @param matchers - initial input {@link Matcher} operators to perform operation by
     * @return composed {@link Matcher} operator
     * @throws NullPointerException if matchers is {@code null}
     */
    @SuppressWarnings("varargs")
    static <T> Matcher<T> andAll(final Matcher<T>... matchers) {
        Objects.requireNonNull(matchers, "Matchers should not be null!");
        return Arrays.stream(matchers).reduce(Matcher::and).orElseThrow(() -> InvalidParameterException.throwInvalidParameter(formatMessage("Unable to combine matchers = {%s} via logical AND", StringUtils.join(matchers, "|"))));
    }

    /**
     * Returns composed {@link Matcher} operator that represents a short-circuiting logical "OR" of {@link Matcher} collection
     *
     * @param matchers - initial input {@link Matcher} operators to perform operation by
     * @return composed {@link Matcher} operator
     * @throws NullPointerException if matchers is {@code null}
     */
    @SuppressWarnings("varargs")
    static <T> Matcher<T> orAll(final Matcher<T>... matchers) {
        Objects.requireNonNull(matchers, "Matchers should not be null!");
        return Arrays.stream(matchers).reduce(Matcher::or).orElseThrow(() -> InvalidParameterException.throwInvalidParameter(formatMessage("Unable to combine matchers = {%s} via logical OR", StringUtils.join(matchers, "|"))));
    }

    /**
     * Returns composed {@link Matcher} operator that represents a short-circuiting logical "XOR" of {@link Matcher} collection
     *
     * @param matchers - initial input {@link Matcher} operators to perform operation by
     * @return composed {@link Matcher} operator
     * @throws NullPointerException if matchers is {@code null}
     */
    @SuppressWarnings("varargs")
    static <T> Matcher<T> xorAll(final Matcher<T>... matchers) {
        Objects.requireNonNull(matchers, "Matchers should not be null!");
        return Arrays.stream(matchers).reduce(Matcher::xor).orElseThrow(() -> InvalidParameterException.throwInvalidParameter(formatMessage("Unable to combine matchers = {%s} via logical XOR", StringUtils.join(matchers, "|"))));
    }

    /**
     * Returns composed {@link Matcher} operator that represents a short-circuiting logical "NAND" of {@link Matcher} collection
     *
     * @param matchers - initial input {@link Matcher} operators to perform operation by
     * @return composed {@link Matcher} operator
     * @throws NullPointerException if matchers is {@code null}
     */
    @SuppressWarnings("varargs")
    static <T> Matcher<T> nandAll(final Matcher<T>... matchers) {
        Objects.requireNonNull(matchers, "Matchers should not be null!");
        return Arrays.stream(matchers).reduce(Matcher::nand).orElseThrow(() -> InvalidParameterException.throwInvalidParameter(formatMessage("Unable to combine matchers = {%s} via logical NAND", StringUtils.join(matchers, "|"))));
    }

    /**
     * Returns composed {@link Matcher} operator that represents a short-circuiting logical "NOR" of {@link Matcher} collection
     *
     * @param matchers - initial input {@link Matcher} operators to perform operation by
     * @return composed {@link Matcher} operator
     * @throws NullPointerException if matchers is {@code null}
     */
    @SuppressWarnings("varargs")
    static <T> Matcher<T> norAll(final Matcher<T>... matchers) {
        Objects.requireNonNull(matchers, "Matchers should not be null!");
        return Arrays.stream(matchers).reduce(Matcher::nor).orElseThrow(() -> InvalidParameterException.throwInvalidParameter(formatMessage("Unable to combine matchers = {%s} via logical NOR", StringUtils.join(matchers, "|"))));
    }

    /**
     * Returns composed {@link Matcher} operator that represents a short-circuiting logical "XNOR" of {@link Matcher} collection
     *
     * @param matchers - initial input {@link Matcher} operators to perform operation by
     * @return composed {@link Matcher} operator
     * @throws NullPointerException if matchers is {@code null}
     */
    @SuppressWarnings("varargs")
    static <T> Matcher<T> xnorAll(final Matcher<T>... matchers) {
        Objects.requireNonNull(matchers, "Matchers should not be null!");
        return Arrays.stream(matchers).reduce(Matcher::xnor).orElseThrow(() -> InvalidParameterException.throwInvalidParameter(formatMessage("Unable to combine matchers = {%s} via logical XNOR", StringUtils.join(matchers, "|"))));
    }

    /**
     * Returns {@link Matcher} operator that tests if two arguments are equal according
     * to {@link Objects#equals(Object, Object)}.
     *
     * @param value - initial input argument value to be matched {@code T}
     * @return {@link Matcher} operator that tests if two arguments are equal according
     * to {@link Objects#equals(Object, Object)}
     */
    static <T> Matcher<T> isEqual(final T value) {
        return (Objects.isNull(value)) ? Objects::isNull : (final T t) -> value.equals(t);
    }
}
