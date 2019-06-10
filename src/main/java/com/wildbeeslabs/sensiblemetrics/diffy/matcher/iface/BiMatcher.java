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

import com.wildbeeslabs.sensiblemetrics.diffy.common.entry.iface.Entry;
import com.wildbeeslabs.sensiblemetrics.diffy.exception.BiMatchOperationException;
import com.wildbeeslabs.sensiblemetrics.diffy.exception.InvalidParameterException;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.description.iface.MatchDescription;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.enumeration.BiMatcherModeType;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.wildbeeslabs.sensiblemetrics.diffy.common.entry.impl.DefaultEntry.of;
import static com.wildbeeslabs.sensiblemetrics.diffy.utility.ServiceUtils.*;
import static com.wildbeeslabs.sensiblemetrics.diffy.utility.StringUtils.wrapInBraces;
import static org.apache.commons.lang3.ObjectUtils.identityToString;
import static org.apache.commons.lang3.StringUtils.join;

/**
 * Binary matcher interface declaration {@link BaseMatcher}
 *
 * @param <T> type of input element to be matched by operation
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@FunctionalInterface
public interface BiMatcher<T> extends BaseMatcher<T, Entry<T, T>> {

    /**
     * Default {@link Matcher} to {@link BiMatcher}
     */
    Function<Matcher, BiMatcher> DEFAULT_BIMATCHER = (final Matcher matcher) -> (value1, value2) -> matcher.allMatch(value1, value2);

    /**
     * Default null {@link BiMatcher}
     */
    BiMatcher<?> DEFAULT_NULL_BIMATCHER = DEFAULT_BIMATCHER.apply(Objects::isNull);
    /**
     * Default non-null {@link BiMatcher}
     */
    BiMatcher<?> DEFAULT_NON_NULL_BIMATCHER = DEFAULT_BIMATCHER.apply(Objects::nonNull);

    /**
     * Default true {@link BiMatcher}
     */
    BiMatcher<?> DEFAULT_TRUE_BIMATCHER = (value1, value2) -> true;
    /**
     * Default false {@link BiMatcher}
     */
    BiMatcher<?> DEFAULT_FALSE_BIMATCHER = (value1, value2) -> false;
    /**
     * Default equals {@link BiMatcher}
     */
    BiMatcher<?> DEFAULT_DEEP_BIMATCHER = Objects::deepEquals;
    /**
     * Default exception {@link BiMatcher}
     */
    BiMatcher<?> DEFAULT_EXCEPTION_BIMATCHER = (value1, value2) -> {
        throw new BiMatchOperationException();
    };

    /**
     * Default class {@link BiMatcher}
     */
    Function<Class<?>, BiMatcher<?>> DEFAULT_INSTANCE_BIMATCHER = (final Class<?> clazz) -> DEFAULT_BIMATCHER.apply(clazz::isInstance);
    /**
     * Default equals {@link BiMatcher}
     */
    Function<Object, BiMatcher<?>> DEFAULT_EQUALS_BIMATCHER = (final Object object) -> DEFAULT_BIMATCHER.apply(value -> Objects.equals(object, value));
    /**
     * Default class nestmate {@link BiMatcher}
     */
    Function<Class<?>, BiMatcher<?>> DEFAULT_NESTMATE_BIMATCHER = (final Class<?> clazz) -> DEFAULT_BIMATCHER.apply(value -> value.getClass().isNestmateOf(clazz));
    /**
     * Default assignable {@link BiMatcher}
     */
    Function<Class<?>, BiMatcher<?>> DEFAULT_ASSIGNABLE_BIMATCHER = (final Class<?> clazz) -> DEFAULT_BIMATCHER.apply(value -> clazz.isAssignableFrom(value.getClass()));
    /**
     * Default identity {@link BiMatcher}
     */
    Function<Object, BiMatcher<?>> DEFAULT_IDENTITY_BIMATCHER = (final Object identity) -> DEFAULT_BIMATCHER.apply(value -> Objects.equals(identity, identityToString(value)));

    /**
     * Default unique {@link BiMatcher}
     */
    Function<Set<Entry<?, ?>>, BiMatcher<?>> DEFAULT_UNIQUE_BIMATCHER = (final Set<Entry<?, ?>> set) -> (value1, value2) -> set.add(of(value1, value2));
    /**
     * Default exist {@link BiMatcher}
     */
    Function<Collection<?>, BiMatcher<?>> DEFAULT_EXIST_BIMATCHER = (final Collection<?> collection) -> (value1, value2) -> collection.contains(of(value1, value2));

    /**
     * Compares provided objects by equality constraint
     *
     * @param first - initial input first value {@code T}
     * @param last  - initial input last value {@code T}
     * @return true - if objects {@code T} are equal, false - otherwise
     */
    boolean matches(final T first, final T last);

    /**
     * Returns {@link BiMatcherModeType}
     *
     * @return {@link BiMatcherModeType}
     */
    @NonNull
    @Override
    default BiMatcherModeType getMode() {
        return BiMatcherModeType.STRICT;
    }

    /**
     * Wraps input {@link MatchDescription} by current description
     *
     * @param description - initial input {@link MatchDescription}
     * @throws NullPointerException if description is {@code null}
     */
    default void describeBy(final Entry<T, T> value, final MatchDescription description) {
        Objects.requireNonNull(description, "Description should not be null");
        description.append(value).append(wrapInBraces.apply(this.getDescription()));
    }

    /**
     * Returns negated {@link BiMatcher} operator
     *
     * @return negated {@link BiMatcher} operator
     */
    @NonNull
    default BiMatcher<T> negate() {
        return (final T t1, final T t2) -> !matches(t1, t2);
    }

    /**
     * Returns composed {@link BiMatcher} operator that represents a short-circuiting logical "AND" of current predicate and another
     *
     * @param matcher - initial input {@link BiMatcher} operator to perform operation by
     * @return composed {@link BiMatcher} operator
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
    default BiMatcher<T> and(final BiMatcher<? super T> matcher) {
        Objects.requireNonNull(matcher, "BiMatcher should not be null!");
        return (final T t1, final T t2) -> matches(t1, t2) && matcher.matches(t1, t2);
    }

    /**
     * Returns composed {@link BiMatcher} operator that represents a short-circuiting logical "NOT" of this predicate and another
     *
     * @param matcher - initial input {@link BiMatcher} operator to perform operation by
     * @return composed {@link BiMatcher} operator
     * @throws NullPointerException if other is {@code null}
     *                              <p>
     *                              Input 1	Output
     *                              0		1
     *                              1 		0
     *                              </p>
     */
    @NonNull
    default BiMatcher<T> not(final BiMatcher<? super T> matcher) {
        Objects.requireNonNull(matcher, "BiMatcher should not be null!");
        return (BiMatcher<T>) matcher.negate();
    }

    /**
     * Returns composed {@link BiMatcher} operator that represents a short-circuiting logical "OR" of this predicate and another
     *
     * @param matcher - initial input {@link BiMatcher} operator to perform operation by
     * @return composed {@link BiMatcher} operator
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
    default BiMatcher<T> or(final BiMatcher<? super T> matcher) {
        Objects.requireNonNull(matcher, "BiMatcher should not be null!");
        return (final T t1, final T t2) -> matches(t1, t2) || matcher.matches(t1, t2);
    }

    /**
     * Returns composed {@link BiMatcher} operator that represents a short-circuiting logical "XOR" of this predicate and another
     *
     * @param matcher - initial input {@link BiMatcher} operator to perform operation by
     * @return composed {@link BiMatcher} operator
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
    default BiMatcher<T> xor(final BiMatcher<? super T> matcher) {
        Objects.requireNonNull(matcher, "BiMatcher should not be null!");
        return (final T t1, final T t2) -> matches(t1, t2) ^ matcher.matches(t1, t2);
    }

    /**
     * Returns composed {@link BiMatcher} operator that represents a short-circuiting logical "NAND" of this predicate and another
     *
     * @param matcher - initial input {@link BiMatcher} operator to perform operation by
     * @return composed {@link BiMatcher} operator
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
    default BiMatcher<T> nand(final BiMatcher<? super T> matcher) {
        Objects.requireNonNull(matcher, "BiMatcher should not be null!");
        return (final T t1, final T t2) -> not(and(matcher)).matches(t1, t2);
    }

    /**
     * Returns composed {@link BiMatcher} operator that represents a short-circuiting logical "NOR" of this predicate and another
     *
     * @param matcher - initial input {@link BiMatcher} operator to perform operation by
     * @return composed {@link BiMatcher} operator
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
    default BiMatcher<T> nor(final BiMatcher<? super T> matcher) {
        Objects.requireNonNull(matcher, "BiMatcher should not be null!");
        return (final T t1, final T t2) -> not(or(matcher)).matches(t1, t2);
    }

    /**
     * Returns composed {@link BiMatcher} operator that represents a short-circuiting logical "XNOR" of this predicate and another
     *
     * @param matcher - initial input {@link BiMatcher} operator to perform operation by
     * @return composed {@link BiMatcher} operator
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
    default BiMatcher<T> xnor(final BiMatcher<? super T> matcher) {
        Objects.requireNonNull(matcher, "Matcher should not be null!");
        return (final T t1, final T t2) -> not(xor(matcher)).matches(t1, t2);
    }

    /**
     * Returns binary flag based on all-match input collection of {@link Iterable} collection of {@link Entry} values
     *
     * @param values - initial input {@link Iterable} collection of {@link Entry} values
     * @return true - if all input values {@link Entry} match, false - otherwise
     */
    @NonNull
    default boolean allMatch(@Nullable final Iterable<Entry<T, T>> values) {
        return listOf(values).stream().allMatch(v -> this.matches(v.getFirst(), v.getLast()));
    }

    /**
     * Returns binary flag based on non-match input collection of {@link Iterable} collection of {@link Entry} values
     *
     * @param values - initial input {@link Iterable} collection of {@link Entry} values
     * @return true - if all input values {@link Entry} match, false - otherwise
     */
    @NonNull
    default boolean noneMatch(@Nullable final Iterable<Entry<T, T>> values) {
        return listOf(values).stream().noneMatch(v -> this.matches(v.getFirst(), v.getLast()));
    }

    /**
     * Returns binary flag based on any-match input collection of {@link Iterable} collection of {@link Entry} values
     *
     * @param values - initial input {@link Iterable} collection of {@link Entry} values
     * @return true - if all input values {@link Entry} match, false - otherwise
     */
    @NonNull
    default boolean anyMatch(@Nullable final Iterable<Entry<T, T>> values) {
        return listOf(values).stream().anyMatch(v -> this.matches(v.getFirst(), v.getLast()));
    }

    /**
     * Returns {@link Collection} of {@link Entry}s by input {@link BiMatcher}
     *
     * @param <T>     type of input element to be matched by operation {#link filter}
     * @param values  - initial input {@link Iterable} collection of {@link Entry}s
     * @param matcher - initial input {@link BiMatcher}
     * @return {@link Collection} of {@link Entry}s
     * @throws NullPointerException if matcher is {@code null}
     */
    @NonNull
    static <T> Collection<Entry<T, T>> matchIf(@Nullable final Iterable<Entry<T, T>> values, final BiMatcher<T> matcher) {
        Objects.requireNonNull(matcher, "Matcher should not be null");
        return listOf(values).stream().filter((entry -> matcher.matches(entry.getFirst(), entry.getLast()))).collect(Collectors.toList());
    }

    /**
     * Returns {@link Collection} of {@link Entry}s by input array of {@link BiMatcher}s
     *
     * @param <T>      type of input element to be matched by operation {#link filter}
     * @param values   - initial input {@link Iterable} collection of {@link Entry}s
     * @param matchers - initial input array of {@link BiMatcher}s
     * @return {@link Collection} of {@link Entry}s
     */
    @NonNull
    static <T> Collection<Entry<T, T>> matchIf(@Nullable final Iterable<Entry<T, T>> values, final BiMatcher<T>... matchers) {
        final BiMatcher<T> matcher = andAll(matchers);
        return matchIf(values, matcher);
    }

    /**
     * Returns {@link Collection} of {@link Entry}s filtered by input {@link BiMatcher}
     *
     * @param <T>     type of input element to be matched by operation
     * @param values  - initial input {@link Iterable} collection of {@link Entry}s
     * @param matcher - initial input {@link BiMatcher}
     * @return {@link Collection} of {@link Entry}s
     */
    @NonNull
    static <T> Collection<Entry<T, T>> removeIf(@Nullable final Iterable<Entry<T, T>> values, final BiMatcher<T> matcher) {
        Objects.requireNonNull(matcher, "Matcher should not be null");
        return listOf(values).stream().filter(entry -> matcher.negate().matches(entry.getFirst(), entry.getLast())).collect(Collectors.toList());
    }

    /**
     * Returns {@link Collection} of {@link Entry}s filtered by input array of {@link BiMatcher}s
     *
     * @param <T>      type of input element to be matched by operation
     * @param values   - initial input {@link Iterable} collection of {@link Entry}s
     * @param matchers - initial input arrays of {@link BiMatcher}s
     * @return {@link Collection} of {@link Entry}s
     */
    @NonNull
    static <T> Collection<Entry<T, T>> removeIf(@Nullable final Iterable<Entry<T, T>> values, final BiMatcher<T>... matchers) {
        final BiMatcher<T> matcher = andAll(matchers);
        return removeIf(values, matcher);
    }

    /**
     * Returns {@link Map} of {@code T} by input {@link BiMatcher}
     *
     * @param <T>     type of input element to be matched by operation
     * @param values  - initial input {@link Iterable} collection of {@link Entry}s
     * @param matcher - initial input {@link BiMatcher}
     * @return {@link Map} of {@link Entry}s
     */
    @NonNull
    static <T> Map<Boolean, List<Entry<T, T>>> mapBy(@Nullable final Iterable<Entry<T, T>> values, final BiMatcher<T> matcher) {
        Objects.requireNonNull(matcher, "Matcher should not be null");
        return streamOf(values).collect(Collectors.partitioningBy(entry -> matcher.matches(entry.getFirst(), entry.getLast())));
    }

    /**
     * Returns {@link Map} of {@code T} by input array of {@link BiMatcher}s
     *
     * @param <T>      type of input element to be matched by operation
     * @param values   - initial input {@link Iterable} collection of {@link Entry}s
     * @param matchers - initial input arrays of {@link BiMatcher}s
     * @return {@link Map} of {@link Entry}s
     */
    @NonNull
    static <T> Map<Boolean, List<Entry<T, T>>> mapBy(@Nullable final Iterable<Entry<T, T>> values, final BiMatcher<T>... matchers) {
        final BiMatcher<T> matcher = andAll(matchers);
        return mapBy(values, matcher);
    }

    /**
     * Filters input {@link Collection} of {@link Entry}s items by {@link BiMatcher}
     *
     * @param <T>     type of input element to be matched by operation
     * @param values  - initial input {@link Iterable} collection of {@link Entry}
     * @param matcher - initial input {@link BiMatcher}
     * @return {@link Collection} of {@link Entry}s
     */
    @NonNull
    static <T> void remove(@Nullable final Iterable<Entry<T, T>> values, final BiMatcher<T> matcher) {
        Objects.requireNonNull(matcher, "Matcher should not be null");
        final Iterator<Entry<T, T>> iterator = listOf(values).iterator();
        while (iterator.hasNext()) {
            final Entry<T, T> entry = iterator.next();
            if (matcher.matches(entry.getFirst(), entry.getLast())) {
                iterator.remove();
            }
        }
    }

    /**
     * Filters input {@link Collection} of {@link Entry}s items by input {@link BiMatcher} (only first match)
     *
     * @param <T>     type of input element to be matched by operation
     * @param values  - initial input {@link Iterable} collection of {@link Entry}s
     * @param matcher - initial input {@link BiMatcher}
     * @return {@link Collection} of {@link Entry}s
     */
    @NonNull
    static <T> void removeFirst(@Nullable final Iterable<Entry<T, T>> values, final BiMatcher<T> matcher) {
        Objects.requireNonNull(matcher, "Matcher should not be null");
        final Iterator<Entry<T, T>> iterator = listOf(values).iterator();
        while (iterator.hasNext()) {
            final Entry<T, T> entry = iterator.next();
            if (matcher.matches(entry.getFirst(), entry.getLast())) {
                iterator.remove();
                return;
            }
        }
    }


    /**
     * Tests input {@link Supplier} by {@link Matcher}
     *
     * @param <T>           type of input element to be matched by operation
     * @param matcher       - initial input {@link BiMatcher}
     * @param firstSupplier - initial input first {@link Supplier}
     * @param lastSupplier  - initial input last {@link Supplier}
     * @return true - if input {@link Supplier}s matches {@link BiMatcher}, false - otherwise
     * @throws NullPointerException if matcher is {@code null}
     * @throws NullPointerException if firstSupplier are {@code null}
     * @throws NullPointerException if lastSupplier are {@code null}
     */
    @NonNull
    static <T> boolean test(final Supplier<T> firstSupplier, final Supplier<T> lastSupplier, final BiMatcher<T> matcher) {
        Objects.requireNonNull(firstSupplier, "First supplier should not be null!");
        Objects.requireNonNull(lastSupplier, "Last supplier should not be null!");
        Objects.requireNonNull(matcher, "Matcher should not be null!");

        try {
            return matcher.matches(firstSupplier.get(), lastSupplier.get());
        } catch (Throwable t) {
            BiMatchOperationException.throwIncorrectMatch(firstSupplier, lastSupplier, t);
        }
        return false;
    }

    /**
     * Returns {@link BiMatcher} by input {@link Comparator}
     *
     * @param <T>        type of input element to be compared by operation
     * @param comparator - initial input {@link Comparator}
     * @return {@link BiMatcher}
     * @throws NullPointerException if comparator is {@code null}
     */
    @NonNull
    static <T> BiMatcher<T> equalBy(final Comparator<? super T> comparator) {
        Objects.requireNonNull(comparator, "Comparator should not be null");
        return (final T a, final T b) -> Objects.compare(a, b, comparator) == 0;
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
    @NonNull
    @SuppressWarnings("varargs")
    static <T> BiMatcher<T> andAll(final BiMatcher<T>... matchers) {
        Objects.requireNonNull(matchers, "Matchers should not be null!");
        return reduceOrThrow(matchers, (final BiMatcher<T> m) -> m.getMode().isEnable(), BiMatcher::and, () -> InvalidParameterException.throwError("Unable to combine matchers = {%s} via logical AND", join(matchers, "|")));
    }

    /**
     * Returns composed {@link BiMatcher} operator that represents a short-circuiting logical "OR" of {@link BiMatcher}s collection
     *
     * @param <T>      type of input element to be matched by operation
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
    @NonNull
    @SuppressWarnings("varargs")
    static <T> BiMatcher<T> orAll(final BiMatcher<T>... matchers) {
        Objects.requireNonNull(matchers, "Matchers should not be null!");
        return reduceOrThrow(matchers, (final BiMatcher<T> m) -> m.getMode().isEnable(), BiMatcher::or, () -> InvalidParameterException.throwError("Unable to combine matchers = {%s} via logical OR", join(matchers, "|")));
    }

    /**
     * Returns composed {@link BiMatcher} operator that represents a short-circuiting logical "XOR" of {@link BiMatcher}s collection
     *
     * @param <T>      type of input element to be matched by operation
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
    @NonNull
    @SuppressWarnings("varargs")
    static <T> BiMatcher<T> xorAll(final BiMatcher<T>... matchers) {
        Objects.requireNonNull(matchers, "Matchers should not be null!");
        return reduceOrThrow(matchers, (final BiMatcher<T> m) -> m.getMode().isEnable(), BiMatcher::xor, () -> InvalidParameterException.throwError("Unable to combine matchers = {%s} via logical XOR", join(matchers, "|")));
    }

    /**
     * Returns composed {@link BiMatcher} operator that represents a short-circuiting logical "NAND" of {@link BiMatcher}s collection
     *
     * @param <T>      type of input element to be matched by operation
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
    @NonNull
    @SuppressWarnings("varargs")
    static <T> BiMatcher<T> nandAll(final BiMatcher<T>... matchers) {
        Objects.requireNonNull(matchers, "Matchers should not be null!");
        return reduceOrThrow(matchers, (final BiMatcher<T> m) -> m.getMode().isEnable(), BiMatcher::nand, () -> InvalidParameterException.throwError("Unable to combine matchers = {%s} via logical NAND", join(matchers, "|")));
    }

    /**
     * Returns composed {@link BiMatcher} operator that represents a short-circuiting logical "NOR" of {@link BiMatcher}s collection
     *
     * @param <T>      type of input element to be matched by operation
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
    @NonNull
    @SuppressWarnings("varargs")
    static <T> BiMatcher<T> norAll(final BiMatcher<T>... matchers) {
        Objects.requireNonNull(matchers, "Matchers should not be null!");
        return reduceOrThrow(matchers, (final BiMatcher<T> m) -> m.getMode().isEnable(), BiMatcher::nor, () -> InvalidParameterException.throwError("Unable to combine matchers = {%s} via logical NOR", join(matchers, "|")));
    }

    /**
     * Returns composed {@link BiMatcher} operator that represents a short-circuiting logical "XNOR" of {@link BiMatcher} collection
     *
     * @param <T>      type of input element to be matched by operation
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
    @NonNull
    @SuppressWarnings("varargs")
    static <T> BiMatcher<T> xnorAll(final BiMatcher<T>... matchers) {
        Objects.requireNonNull(matchers, "Matchers should not be null!");
        return reduceOrThrow(matchers, (final BiMatcher<T> m) -> m.getMode().isEnable(), BiMatcher::xnor, () -> InvalidParameterException.throwError("Unable to combine matchers = {%s} via logical XNOR", join(matchers, "|")));
    }

    /**
     * Returns identity {@link BiMatcher} operator by input {@link BiMatcher}
     *
     * @param <T>     type of input element to be matched by operation
     * @param matcher - initial input {@link BiMatcher}
     * @return identity {@link BiMatcher}
     * @throws NullPointerException if matcher is {@code null}
     */
    @NonNull
    static <T> BiMatcher<T> identity(final BiMatcher<T> matcher) {
        Objects.requireNonNull(matcher, "Matcher should not be null");
        return matcher::matches;
    }
}
