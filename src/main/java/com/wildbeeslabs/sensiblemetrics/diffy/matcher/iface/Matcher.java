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

import com.wildbeeslabs.sensiblemetrics.diffy.exception.InvalidParameterException;
import com.wildbeeslabs.sensiblemetrics.diffy.exception.MatchOperationException;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.description.iface.MatchDescription;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.enums.MatcherModeType;

import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

import static com.wildbeeslabs.sensiblemetrics.diffy.utils.ServiceUtils.reduceOrThrow;
import static com.wildbeeslabs.sensiblemetrics.diffy.utils.ServiceUtils.streamOf;
import static org.apache.commons.lang3.StringUtils.join;

/**
 * Matcher interface declaration {@link BaseMatcher}
 *
 * @param <T> type of input element to be matched by operation
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@FunctionalInterface
public interface Matcher<T> extends BaseMatcher<T> {

    /**
     * Default true {@link Matcher}
     */
    Matcher<?> DEFAULT_TRUE_MATCHER = (value) -> true;
    /**
     * Default false {@link Matcher}
     */
    Matcher<?> DEFAULT_FALSE_MATCHER = (value) -> false;
    /**
     * Default null {@link Matcher}
     */
    Matcher<?> DEFAULT_NULL_MATCHER = (value) -> Objects.isNull(value);
    /**
     * Default non-null {@link Matcher}
     */
    Matcher<?> DEFAULT_NOTNULL_MATCHER = (value) -> Objects.nonNull(value);
    /**
     * Default class {@link Matcher}
     */
    Function<Class<?>, Matcher<?>> DEFAULT_CLASS_MATCHER = (final Class<?> clazz) -> (value) -> clazz.isInstance(value);
    /**
     * Default unique {@link Matcher}
     */
    Function<Set, Matcher<?>> DEFAULT_UNIQUE_MATCHER = (final Set set) -> (value) -> set.add(value);
    /**
     * Default exception {@link Matcher}
     */
    Matcher DEFAULT_EXCEPTION_MATCHER = (Matcher<?>) value -> {
        throw new MatchOperationException();
    };

    /**
     * Returns binary flag by initial argument {@code T} match comparison
     *
     * @param value - initial input argument value to be matched {@code T}
     * @return true - if initial value matches input argument, false - otherwise
     */
    boolean matches(final T value);

    /**
     * Returns {@link MatchDescription}
     *
     * @return {@link MatchDescription}
     */
    default MatchDescription getDescription() {
        return MatchDescription.EMPTY_MATCH_DESCRIPTION;
    }

    /**
     * Returns {@link MatcherModeType}
     *
     * @return {@link MatcherModeType}
     */
    @Override
    default MatcherModeType getMode() {
        return MatcherModeType.STRICT;
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
     *                              <p>
     *                              Input 1	Input 2	Output
     *                              0		0		0
     *                              0		1		0
     *                              1	 	0		0
     *                              1		1		1
     *                              </p>
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
     *                              <p>
     *                              Input 1	Output
     *                              0		1
     *                              1 		0
     *                              </p>
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
     *                              <p>
     *                              Input 1	Input 2	Output
     *                              0		0		0
     *                              0 		1		1
     *                              1		0 		1
     *                              1		1		1
     *                              </p>
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
     *                              <p>
     *                              Input 1	Input 2	Output
     *                              0		0		0
     *                              0		1		1
     *                              1		0 		1
     *                              1		1		0
     *                              </p>
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
     *                              <p>
     *                              Input 1	Input 2	Output
     *                              0 		0 		1
     *                              0 		1		1
     *                              1		0 		1
     *                              1		1	 	0
     *                              </p>
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
     *                              <p>
     *                              Input 1	Input 2	Output
     *                              0		0 	 	1
     *                              0 		1		0
     *                              1		0		0
     *                              1		1		0
     *                              </p>
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
     *                              <p>
     *                              Input 1	Input 2	Output
     *                              0		0 	 	1
     *                              0 		1	 	0
     *                              1		0		0
     *                              1		1		1
     *                              </p>
     */
    default Matcher<T> xnor(final Matcher<? super T> other) {
        Objects.requireNonNull(other, "Matcher should not be null!");
        return (final T t) -> not(xor(other)).matches(t);
    }

    /**
     * Returns binary flag based on all-match input collection of values {@code T}
     *
     * @param values - initial input collection of values {@code T}
     * @return true - if all input values {@code T} matches, false - otherwise
     */
    default boolean allMatch(final T... values) {
        return streamOf(values).allMatch(this::matches);
    }

    /**
     * Returns binary flag based on non-match input collection of values {@code T}
     *
     * @param values - initial input collection of values {@code T}
     * @return true - if all input values {@code T} matches, false - otherwise
     */
    default boolean noneMatch(final T... values) {
        return streamOf(values).noneMatch(this::matches);
    }

    /**
     * Returns composed {@link Matcher} operator that represents a short-circuiting logical "AND" of {@link Matcher}s collection
     *
     * @param matchers - initial input {@link Matcher} operators to perform operation by
     * @return composed {@link Matcher} operator
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
    static <T> Matcher<T> andAll(final Matcher<T>... matchers) {
        Objects.requireNonNull(matchers, "Matchers should not be null!");
        return reduceOrThrow(matchers, (Matcher<T> m) -> m.getMode().isEnable(), Matcher::and, () -> InvalidParameterException.throwError("Unable to combine matchers = {%s} via logical AND", join(matchers, "|")));
    }

    /**
     * Returns composed {@link Matcher} operator that represents a short-circuiting logical "OR" of {@link Matcher}s collection
     *
     * @param matchers - initial input {@link Matcher} operators to perform operation by
     * @return composed {@link Matcher} operator
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
    static <T> Matcher<T> orAll(final Matcher<T>... matchers) {
        Objects.requireNonNull(matchers, "Matchers should not be null!");
        return reduceOrThrow(matchers, (Matcher<T> m) -> m.getMode().isEnable(), Matcher::or, () -> InvalidParameterException.throwError("Unable to combine matchers = {%s} via logical OR", join(matchers, "|")));
    }

    /**
     * Returns composed {@link Matcher} operator that represents a short-circuiting logical "XOR" of {@link Matcher}s collection
     *
     * @param matchers - initial input {@link Matcher} operators to perform operation by
     * @return composed {@link Matcher} operator
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
    static <T> Matcher<T> xorAll(final Matcher<T>... matchers) {
        Objects.requireNonNull(matchers, "Matchers should not be null!");
        return reduceOrThrow(matchers, (Matcher<T> m) -> m.getMode().isEnable(), Matcher::xor, () -> InvalidParameterException.throwError("Unable to combine matchers = {%s} via logical XOR", join(matchers, "|")));
    }

    /**
     * Returns composed {@link Matcher} operator that represents a short-circuiting logical "NAND" of {@link Matcher}s collection
     *
     * @param matchers - initial input {@link Matcher} operators to perform operation by
     * @return composed {@link Matcher} operator
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
    static <T> Matcher<T> nandAll(final Matcher<T>... matchers) {
        Objects.requireNonNull(matchers, "Matchers should not be null!");
        return reduceOrThrow(matchers, (Matcher<T> m) -> m.getMode().isEnable(), Matcher::nand, () -> InvalidParameterException.throwError("Unable to combine matchers = {%s} via logical NAND", join(matchers, "|")));
    }

    /**
     * Returns composed {@link Matcher} operator that represents a short-circuiting logical "NOR" of {@link Matcher}s collection
     *
     * @param matchers - initial input {@link Matcher} operators to perform operation by
     * @return composed {@link Matcher} operator
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
    static <T> Matcher<T> norAll(final Matcher<T>... matchers) {
        Objects.requireNonNull(matchers, "Matchers should not be null!");
        return reduceOrThrow(matchers, (Matcher<T> m) -> m.getMode().isEnable(), Matcher::nor, () -> InvalidParameterException.throwError("Unable to combine matchers = {%s} via logical NOR", join(matchers, "|")));
    }

    /**
     * Returns composed {@link Matcher} operator that represents a short-circuiting logical "XNOR" of {@link Matcher} collection
     *
     * @param matchers - initial input {@link Matcher} operators to perform operation by
     * @return composed {@link Matcher} operator
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
    static <T> Matcher<T> xnorAll(final Matcher<T>... matchers) {
        Objects.requireNonNull(matchers, "Matchers should not be null!");
        return reduceOrThrow(matchers, (Matcher<T> m) -> m.getMode().isEnable(), Matcher::xnor, () -> InvalidParameterException.throwError("Unable to combine matchers = {%s} via logical XNOR", join(matchers, "|")));
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
        return Objects.isNull(value) ? Objects::isNull : (final T t) -> Objects.equals(value, t);
    }
}
