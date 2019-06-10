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
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.enumeration.MatcherModeType;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.wildbeeslabs.sensiblemetrics.diffy.utility.ServiceUtils.*;
import static com.wildbeeslabs.sensiblemetrics.diffy.utility.StringUtils.wrapInBraces;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.ObjectUtils.identityToString;
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
@SuppressWarnings("unchecked")
public interface Matcher<T> extends BaseMatcher<T, T> {

    /**
     * Default {@link Matcher} to {@link BiMatcher}
     */
    Function<Matcher<Object>, Matcher<?>> DEFAULT_MATCHER = (final Matcher<Object> matcher) -> matcher::matches;

    /**
     * Default null {@link Matcher}
     */
    Matcher<?> DEFAULT_NULL_MATCHER = Objects::isNull;
    /**
     * Default non-null {@link Matcher}
     */
    Matcher<?> DEFAULT_NOTNULL_MATCHER = Objects::nonNull;

    /**
     * Default true {@link Matcher}
     */
    Matcher<?> DEFAULT_TRUE_MATCHER = value -> true;
    /**
     * Default false {@link Matcher}
     */
    Matcher<?> DEFAULT_FALSE_MATCHER = value -> false;
    /**
     * Default exception {@link Matcher}
     */
    Matcher<?> DEFAULT_EXCEPTION_MATCHER = value -> {
        throw new MatchOperationException();
    };

    /**
     * Default class {@link Matcher}
     */
    Function<Class<?>, Matcher<?>> DEFAULT_INSTANCE_MATCHER = (final Class<?> clazz) -> clazz::isInstance;
    /**
     * Default equals {@link Matcher}
     */
    Function<Object, Matcher<?>> DEFAULT_EQUALS_MATCHER = (final Object object) -> value -> Objects.equals(object, value);
    /**
     * Default class nestmate {@link Matcher}
     */
    Function<Class<?>, Matcher<?>> DEFAULT_NESTMATE_MATCHER = (final Class<?> clazz) -> value -> value.getClass().isNestmateOf(clazz);
    /**
     * Default assignable {@link Matcher}
     */
    Function<Class<?>, Matcher<?>> DEFAULT_ASSIGNABLE_MATCHER = (final Class<?> clazz) -> value -> value.getClass().isAssignableFrom(clazz);
    /**
     * Default identity {@link Matcher}
     */
    Function<Object, Matcher<?>> DEFAULT_IDENTITY_MATCHER = (final Object identity) -> value -> Objects.equals(identity, identityToString(value));

    /**
     * Default unique {@link Matcher}
     */
    Function<Set, Matcher<?>> DEFAULT_UNIQUE_MATCHER = (final Set set) -> value -> set.add(value);
    /**
     * Default exist {@link Matcher}
     */
    Function<Collection<?>, Matcher<?>> DEFAULT_EXIST_MATCHER = (final Collection<?> collection) -> collection::contains;
    /**
     * Default boolean {@link Matcher}
     */
    Function<Boolean, Matcher<Boolean>> DEFAULT_BOOLEAN_MATCHER = (final Boolean flag) -> flag::equals;

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
    @NonNull
    default MatchDescription getDescription() {
        return MatchDescription.EMPTY_MATCH_DESCRIPTION;
    }

    /**
     * Returns {@link MatcherModeType}
     *
     * @return {@link MatcherModeType}
     */
    @NonNull
    @Override
    default MatcherModeType getMode() {
        return MatcherModeType.STRICT;
    }

    /**
     * Wraps input {@link MatchDescription} by current description
     *
     * @param description - initial input {@link MatchDescription}
     * @throws NullPointerException if description is {@code null}
     */
    default void describeBy(final T value, final MatchDescription description) {
        Objects.requireNonNull(description, "Description should not be null");
        description.append(value).append(wrapInBraces.apply(this.getDescription()));
    }

    /**
     * Returns negated {@link Matcher} operator
     *
     * @return negated {@link Matcher} operator
     */
    @NonNull
    default Matcher<T> negate() {
        return (final T t) -> !this.matches(t);
    }

    /**
     * Returns composed {@link Matcher} operator that represents a short-circuiting logical "AND" of current predicate and another
     *
     * @param matcher - initial input {@link Matcher} operator to perform operation by
     * @return composed {@link Matcher} operator
     * @throws NullPointerException if {@code other} is {@code null}
     *                              <p>
     *                              Input 1	Input 2	Output
     *                              0		0		0
     *                              0		1		0
     *                              1	 	0		0
     *                              1		1		1
     *                              </p>
     */
    @NonNull
    default Matcher<T> and(final Matcher<? super T> matcher) {
        Objects.requireNonNull(matcher, "Matcher should not be null!");
        return (final T t) -> this.matches(t) && matcher.matches(t);
    }

    /**
     * Returns composed {@link Matcher} operator that represents a short-circuiting logical "NOT" of this predicate and another
     *
     * @param matcher - initial input {@link Matcher} operator to perform operation by
     * @return composed {@link Matcher} operator
     * @throws NullPointerException if other is {@code null}
     *                              <p>
     *                              Input 1	Output
     *                              0		1
     *                              1 		0
     *                              </p>
     */
    @NonNull
    default Matcher<T> not(final Matcher<? super T> matcher) {
        Objects.requireNonNull(matcher, "Matcher should not be null!");
        return (Matcher<T>) matcher.negate();
    }

    /**
     * Returns composed {@link Matcher} operator that represents a short-circuiting logical "OR" of this predicate and another
     *
     * @param matcher - initial input {@link Matcher} operator to perform operation by
     * @return composed {@link Matcher} operator
     * @throws NullPointerException if other is {@code null}
     *                              <p>
     *                              Input 1	Input 2	Output
     *                              0		0		0
     *                              0 		1		1
     *                              1		0 		1
     *                              1		1		1
     *                              </p>
     */
    @NonNull
    default Matcher<T> or(final Matcher<? super T> matcher) {
        Objects.requireNonNull(matcher, "Matcher should not be null!");
        return (final T t) -> this.matches(t) || matcher.matches(t);
    }

    /**
     * Returns composed {@link Matcher} operator that represents a short-circuiting logical "XOR" of this predicate and another
     *
     * @param matcher - initial input {@link Matcher} operator to perform operation by
     * @return composed {@link Matcher} operator
     * @throws NullPointerException if other is {@code null}
     *                              <p>
     *                              Input 1	Input 2	Output
     *                              0		0		0
     *                              0		1		1
     *                              1		0 		1
     *                              1		1		0
     *                              </p>
     */
    @NonNull
    default Matcher<T> xor(final Matcher<? super T> matcher) {
        Objects.requireNonNull(matcher, "Matcher should not be null!");
        return (final T t) -> this.matches(t) ^ matcher.matches(t);
    }

    /**
     * Returns composed {@link Matcher} operator that represents a short-circuiting logical "NAND" of this predicate and another
     *
     * @param matcher - initial input {@link Matcher} operator to perform operation by
     * @return composed {@link Matcher} operator
     * @throws NullPointerException if other is {@code null}
     *                              <p>
     *                              Input 1	Input 2	Output
     *                              0 		0 		1
     *                              0 		1		1
     *                              1		0 		1
     *                              1		1	 	0
     *                              </p>
     */
    @NonNull
    default Matcher<T> nand(final Matcher<? super T> matcher) {
        Objects.requireNonNull(matcher, "Matcher should not be null!");
        return (final T t) -> not(and(matcher)).matches(t);
    }

    /**
     * Returns composed {@link Matcher} operator that represents a short-circuiting logical "NOR" of this predicate and another
     *
     * @param matcher - initial input {@link Matcher} operator to perform operation by
     * @return composed {@link Matcher} operator
     * @throws NullPointerException if other is {@code null}
     *                              <p>
     *                              Input 1	Input 2	Output
     *                              0		0 	 	1
     *                              0 		1		0
     *                              1		0		0
     *                              1		1		0
     *                              </p>
     */
    @NonNull
    default Matcher<T> nor(final Matcher<? super T> matcher) {
        Objects.requireNonNull(matcher, "Matcher should not be null!");
        return (final T t) -> not(or(matcher)).matches(t);
    }

    /**
     * Returns composed {@link Matcher} operator that represents a short-circuiting logical "XNOR" of this predicate and another
     *
     * @param matcher - initial input {@link Matcher} operator to perform operation by
     * @return composed {@link Matcher} operator
     * @throws NullPointerException if other is {@code null}
     *                              <p>
     *                              Input 1	Input 2	Output
     *                              0		0 	 	1
     *                              0 		1	 	0
     *                              1		0		0
     *                              1		1		1
     *                              </p>
     */
    @NonNull
    default Matcher<T> xnor(final Matcher<? super T> matcher) {
        Objects.requireNonNull(matcher, "Matcher should not be null!");
        return (final T t) -> not(xor(matcher)).matches(t);
    }

    /**
     * Returns binary flag based on all-match input collection of values {@code T}
     *
     * @param values - initial input collection of values {@code T}
     * @return true - if all input values {@code T} match, false - otherwise
     */
    @NonNull
    @SuppressWarnings("varargs")
    default boolean allMatch(@Nullable final T... values) {
        return streamOf(values).allMatch(this::matches);
    }

    /**
     * Returns binary flag based on non-match input collection of values {@code T}
     *
     * @param values - initial input collection of values {@code T}
     * @return true - if all input values {@code T} match, false - otherwise
     */
    @NonNull
    @SuppressWarnings("varargs")
    default boolean noneMatch(@Nullable final T... values) {
        return streamOf(values).noneMatch(this::matches);
    }

    /**
     * Returns binary flag based on any-match input collection of values {@code T}
     *
     * @param values - initial input collection of values {@code T}
     * @return true - if all input values {@code T} match, false - otherwise
     */
    @NonNull
    @SuppressWarnings("varargs")
    default boolean anyMatch(@Nullable final T... values) {
        return streamOf(values).anyMatch(this::matches);
    }

    /**
     * Returns {@link Collection} of {@code T} filtered by input {@link Matcher}
     *
     * @param <T>     type of input element to be matched by operation
     * @param values  - initial input {@link Iterable} collection of {@code T}
     * @param matcher - initial input {@link Matcher}
     * @return {@link Collection} of {@code T} items
     * @throws NullPointerException if matcher is {@code null}
     */
    @NonNull
    static <T> Collection<T> matchIf(@Nullable final Iterable<T> values, final Matcher<T> matcher) {
        Objects.requireNonNull(matcher, "Matcher should not be null");
        return listOf(values).stream().filter(matcher::matches).collect(Collectors.toList());
    }

    /**
     * Returns {@link Collection} of {@code T} filtered by input array of {@link Matcher}s
     *
     * @param <T>      type of input element to be matched by operation
     * @param values   - initial input {@link Iterable} collection of {@code T}
     * @param matchers - initial input array of {@link Matcher}
     * @return {@link Collection} of {@code T} items
     */
    @NonNull
    static <T> Collection<T> matchIf(@Nullable final Iterable<T> values, final Matcher<T>... matchers) {
        final Matcher<T> matcher = andAll(matchers);
        return matchIf(values, matcher);
    }

    /**
     * Returns {@link Collection} of {@code T} filtered by input {@link Matcher} with skip count
     *
     * @param <T>     type of input element to be matched by operation
     * @param values  - initial input {@link Iterable} collection of {@code T}
     * @param skip    - initial input skip count
     * @param matcher - initial input {@link Matcher}
     * @return {@link Collection} of {@code T} items
     * @throws NullPointerException if matcher is {@code null}
     */
    @NonNull
    static <T> Collection<T> matchIf(@Nullable final Iterable<T> values, final int skip, final Matcher<T> matcher) {
        Objects.requireNonNull(matcher, "Matcher should not be null");
        assert skip >= 0 : "Skip count should be positive or zero";
        return listOf(values).stream().skip(skip).filter(matcher::matches).collect(Collectors.toList());
    }

    /**
     * Returns {@link Collection} of {@code T} filtered by input array of {@link Matcher}s with skip count
     *
     * @param <T>      type of input element to be matched by operation
     * @param values   - initial input {@link Iterable} collection of {@code T}
     * @param skip     - initial input skip count
     * @param matchers - initial input array of {@link Matcher}
     * @return {@link Collection} of {@code T} items
     */
    @NonNull
    static <T> Collection<T> matchIf(@Nullable final Iterable<T> values, final int skip, final Matcher<T>... matchers) {
        final Matcher<T> matcher = andAll(matchers);
        return matchIf(values, skip, matcher);
    }

    /**
     * Returns {@link Optional} of first match {@code T} filtered by input {@link Matcher}
     *
     * @param <T>     type of input element to be matched by operation
     * @param values  - initial input {@link Iterable} collection of {@code T}
     * @param matcher - initial input {@link Matcher}
     * @return {@link Optional} of first match {@code T} item
     * @throws NullPointerException if matcher is {@code null}
     */
    @NonNull
    static <T> Optional<T> matchFirstIf(@Nullable final Iterable<T> values, final Matcher<T> matcher) {
        Objects.requireNonNull(matcher, "Matcher should not be null");
        return listOf(values).stream().filter(matcher::matches).findFirst();
    }

    /**
     * Returns {@link Optional} of first match {@code T} filtered by input array of {@link Matcher}s
     *
     * @param <T>      type of input element to be matched by operation
     * @param values   - initial input {@link Iterable} collection of {@code T}
     * @param matchers - initial input array of {@link Matcher}
     * @return {@link Optional} of first match {@code T} item
     */
    @NonNull
    static <T> Optional<T> matchFirstIf(@Nullable final Iterable<T> values, final Matcher<T>... matchers) {
        final Matcher<T> matcher = andAll(matchers);
        return matchFirstIf(values, matcher);
    }

    /**
     * Returns {@link Optional} of last match {@code T} filtered by input {@link Matcher}
     *
     * @param <T>     type of input element to be matched by operation
     * @param values  - initial input {@link Iterable} collection of {@code T}
     * @param matcher - initial input {@link Matcher}
     * @return {@link Optional} of first match {@code T} item
     * @throws NullPointerException if matcher is {@code null}
     */
    @NonNull
    static <T> Optional<T> matchLastIf(@Nullable final Iterable<T> values, final Matcher<T> matcher) {
        Objects.requireNonNull(matcher, "Matcher should not be null");
        return listOf(values).stream().filter(matcher::matches).reduce((first, last) -> last);
    }

    /**
     * Returns {@link Optional} of last match {@code T} filtered by input array of {@link Matcher}s
     *
     * @param <T>      type of input element to be matched by operation
     * @param values   - initial input {@link Iterable} collection of {@code T}
     * @param matchers - initial input array of {@link Matcher}
     * @return {@link Optional} of first match {@code T} item
     */
    @NonNull
    static <T> Optional<T> matchLastIf(@Nullable final Iterable<T> values, final Matcher<T>... matchers) {
        final Matcher<T> matcher = andAll(matchers);
        return matchLastIf(values, matcher);
    }

    /**
     * Returns {@link Collection} of {@code T} filtered by input {@link Matcher}
     *
     * @param <T>     type of input element to be matched by operation
     * @param values  - initial input {@link Iterable} collection of {@code T}
     * @param matcher - initial input {@link Matcher}
     * @return {@link Collection} of {@code T} items
     */
    @NonNull
    static <T> Collection<T> removeIf(@Nullable final Iterable<T> values, final Matcher<T> matcher) {
        Objects.requireNonNull(matcher, "Matcher should not be null");
        return listOf(values).stream().filter(matcher.negate()::matches).collect(Collectors.toList());
    }

    /**
     * Returns {@link Collection} of {@code T} filtered by input array of {@link Matcher}s
     *
     * @param <T>      type of input element to be matched by operation
     * @param values   - initial input {@link Iterable} collection of {@code T}
     * @param matchers - initial input arrays of {@link Matcher}s
     * @return {@link Collection} of {@code T} items
     */
    @NonNull
    static <T> Collection<T> removeIf(@Nullable final Iterable<T> values, final Matcher<T>... matchers) {
        final Matcher<T> matcher = andAll(matchers);
        return removeIf(values, matcher);
    }

    /**
     * Returns {@link Map} of {@code T} by input {@link Matcher}
     *
     * @param <T>     type of input element to be matched by operation
     * @param values  - initial input {@link Iterable} collection of {@code T}
     * @param matcher - initial input {@link Matcher}
     * @return {@link Map} of {@code T} items
     */
    @NonNull
    static <T> Map<Boolean, List<T>> mapBy(@Nullable final Iterable<T> values, final Matcher<T> matcher) {
        Objects.requireNonNull(matcher, "Matcher should not be null");
        return streamOf(values).collect(Collectors.partitioningBy(matcher::matches));
    }

    /**
     * Returns {@link Map} of {@code T} by input array of {@link Matcher}s
     *
     * @param <T>      type of input element to be matched by operation
     * @param values   - initial input {@link Iterable} collection of {@code T}
     * @param matchers - initial input arrays of {@link Matcher}s
     * @return {@link Map} of {@code T} items
     */
    @NonNull
    static <T> Map<Boolean, List<T>> mapBy(@Nullable final Iterable<T> values, final Matcher<T>... matchers) {
        final Matcher<T> matcher = andAll(matchers);
        return mapBy(values, matcher);
    }

    /**
     * Filters input {@link Collection} of {@code T} items by input {@link Matcher}
     *
     * @param <T>     type of input element to be matched by operation
     * @param values  - initial input {@link Iterable} collection of {@code T}
     * @param matcher - initial input {@link Matcher}
     * @return {@link Collection} of {@code T} items
     */
    @NonNull
    static <T> void remove(@Nullable final Iterable<T> values, final Matcher<T> matcher) {
        Objects.requireNonNull(matcher, "Matcher should not be null");
        final Iterator<T> iterator = listOf(values).iterator();
        while (iterator.hasNext()) {
            if (matcher.matches(iterator.next())) {
                iterator.remove();
            }
        }
    }

    /**
     * Filters input {@link Collection} of {@code T} items by input {@link Matcher} (only first match)
     *
     * @param <T>     type of input element to be matched by operation
     * @param values  - initial input {@link Iterable} collection of {@code T}
     * @param matcher - initial input {@link Matcher}
     * @return {@link Collection} of {@code T} items
     */
    @NonNull
    static <T> void removeFirst(@Nullable final Iterable<T> values, final Matcher<T> matcher) {
        Objects.requireNonNull(matcher, "Matcher should not be null");
        final Iterator<T> iterator = listOf(values).iterator();
        while (iterator.hasNext()) {
            if (matcher.matches(iterator.next())) {
                iterator.remove();
                return;
            }
        }
    }

    /**
     * Tests input {@link Supplier} by {@link Matcher}
     *
     * @param <T>      type of input element to be matched by operation
     * @param supplier - initial input {@link Supplier}
     * @param matcher  - initial input {@link Matcher}
     * @return true - if input {@link Supplier} matches {@link Matcher}, false - otherwise
     * @throws NullPointerException if matcher is {@code null}
     * @throws NullPointerException if supplier is {@code null}
     */
    @NonNull
    static <T> boolean test(final Supplier<T> supplier, final Matcher<T> matcher) {
        Objects.requireNonNull(supplier, "Supplier should not be null!");
        Objects.requireNonNull(matcher, "Matcher should not be null!");

        try {
            return matcher.matches(supplier.get());
        } catch (Throwable t) {
            MatchOperationException.throwIncorrectMatch(supplier, t);
        }
        return false;
    }

    /**
     * Returns composed {@link Matcher} operator that represents a short-circuiting logical "AND" of {@link Matcher}s collection
     *
     * @param <T>      type of input element to be matched by operation
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
    @NonNull
    @SuppressWarnings("varargs")
    static <T> Matcher<T> andAll(final Matcher<T>... matchers) {
        Objects.requireNonNull(matchers, "Matchers should not be null!");
        return reduceOrThrow(matchers, (final Matcher<T> m) -> m.getMode().isEnable(), Matcher::and, () -> InvalidParameterException.throwError("Unable to combine matchers = {%s} via logical AND", join(matchers, "|")));
    }

    /**
     * Returns composed {@link Matcher} operator that represents a short-circuiting logical "OR" of {@link Matcher}s collection
     *
     * @param <T>      type of input element to be matched by operation
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
    @NonNull
    @SuppressWarnings("varargs")
    static <T> Matcher<T> orAll(final Matcher<T>... matchers) {
        Objects.requireNonNull(matchers, "Matchers should not be null!");
        return reduceOrThrow(matchers, (final Matcher<T> m) -> m.getMode().isEnable(), Matcher::or, () -> InvalidParameterException.throwError("Unable to combine matchers = {%s} via logical OR", join(matchers, "|")));
    }

    /**
     * Returns composed {@link Matcher} operator that represents a short-circuiting logical "XOR" of {@link Matcher}s collection
     *
     * @param <T>      type of input element to be matched by operation
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
    @NonNull
    @SuppressWarnings("varargs")
    static <T> Matcher<T> xorAll(final Matcher<T>... matchers) {
        Objects.requireNonNull(matchers, "Matchers should not be null!");
        return reduceOrThrow(matchers, (final Matcher<T> m) -> m.getMode().isEnable(), Matcher::xor, () -> InvalidParameterException.throwError("Unable to combine matchers = {%s} via logical XOR", join(matchers, "|")));
    }

    /**
     * Returns composed {@link Matcher} operator that represents a short-circuiting logical "NAND" of {@link Matcher}s collection
     *
     * @param <T>      type of input element to be matched by operation
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
    @NonNull
    @SuppressWarnings("varargs")
    static <T> Matcher<T> nandAll(final Matcher<T>... matchers) {
        Objects.requireNonNull(matchers, "Matchers should not be null!");
        return reduceOrThrow(matchers, (final Matcher<T> m) -> m.getMode().isEnable(), Matcher::nand, () -> InvalidParameterException.throwError("Unable to combine matchers = {%s} via logical NAND", join(matchers, "|")));
    }

    /**
     * Returns composed {@link Matcher} operator that represents a short-circuiting logical "NOR" of {@link Matcher}s collection
     *
     * @param <T>      type of input element to be matched by operation
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
    @NonNull
    @SuppressWarnings("varargs")
    static <T> Matcher<T> norAll(final Matcher<T>... matchers) {
        Objects.requireNonNull(matchers, "Matchers should not be null!");
        return reduceOrThrow(matchers, (final Matcher<T> m) -> m.getMode().isEnable(), Matcher::nor, () -> InvalidParameterException.throwError("Unable to combine matchers = {%s} via logical NOR", join(matchers, "|")));
    }

    /**
     * Returns composed {@link Matcher} operator that represents a short-circuiting logical "XNOR" of {@link Matcher} collection
     *
     * @param <T>      type of input element to be matched by operation
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
    @NonNull
    @SuppressWarnings("varargs")
    static <T> Matcher<T> xnorAll(final Matcher<T>... matchers) {
        Objects.requireNonNull(matchers, "Matchers should not be null!");
        return reduceOrThrow(matchers, (final Matcher<T> m) -> m.getMode().isEnable(), Matcher::xnor, () -> InvalidParameterException.throwError("Unable to combine matchers = {%s} via logical XNOR", join(matchers, "|")));
    }

    /**
     * Returns {@link Matcher} operator that tests if two arguments are equal according
     * to {@link Objects#equals(Object, Object)}.
     *
     * @param <T>   type of input element to be matched by operation
     * @param value - initial input argument value to be matched {@code T}
     * @return {@link Matcher} operator that tests if two arguments are equal according
     * to {@link Objects#equals(Object, Object)}
     */
    @NonNull
    static <T> Matcher<T> isEqual(final T value) {
        return isNull(value) ? Objects::isNull : (final T other) -> Objects.equals(value, other);
    }

    /**
     * Returns identity {@link Matcher} operator by input {@link Matcher}
     *
     * @param <T>     type of input element to be matched by operation
     * @param matcher - initial input {@link Matcher}
     * @return identity {@link Matcher}
     * @throws NullPointerException if matcher is {@code null}
     */
    @NonNull
    static <T> Matcher<T> identity(final Matcher<T> matcher) {
        Objects.requireNonNull(matcher, "Matcher should not be null");
        return matcher::matches;
    }
}
