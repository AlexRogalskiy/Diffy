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
package com.wildbeeslabs.sensiblemetrics.diffy.matcher.interfaces;

import com.codepoetics.protonpack.StreamUtils;
import com.wildbeeslabs.sensiblemetrics.diffy.common.entry.iface.Entry;
import com.wildbeeslabs.sensiblemetrics.diffy.common.exception.InvalidParameterException;
import com.wildbeeslabs.sensiblemetrics.diffy.common.utils.ServiceUtils;
import com.wildbeeslabs.sensiblemetrics.diffy.common.utils.StringUtils;
import com.wildbeeslabs.sensiblemetrics.diffy.common.utils.ValidationUtils;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.description.iface.MatchDescription;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.enumeration.MatcherModeType;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.exception.MatchOperationException;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.wildbeeslabs.sensiblemetrics.diffy.common.utils.ReflectionUtils.getAnnotation;
import static com.wildbeeslabs.sensiblemetrics.diffy.common.utils.ServiceUtils.toUnmodifiableList;
import static java.lang.reflect.Modifier.isStatic;
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
     * Default {@link Matcher} to {@link Predicate}
     */
    Function<Matcher<Object>, Predicate<?>> TO_PREDICATE = matcher -> matcher::matches;
    /**
     * Default {@link Predicate} to {@link Matcher}
     */
    Function<Predicate<Object>, Matcher<?>> TO_MATCHER = predicate -> predicate::test;

    /**
     * Default null {@link Matcher}
     */
    Matcher<?> DEFAULT_NULL_MATCHER = Objects::isNull;
    /**
     * Default non-null {@link Matcher}
     */
    Matcher<?> DEFAULT_NOTNULL_MATCHER = Objects::nonNull;
    /**
     * Default random boolean {@link Matcher}
     */
    Matcher<?> DEFAULT_BOOLEAN_MATCHER = predicate -> new Random().nextBoolean();

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
     * Default non-bridge method {@link Matcher}
     */
    Matcher<Method> DEFAULT_NON_BRIDGED_METHOD_MATCHER = value -> !value.isBridge();
    /**
     * Default user-declared method {@link Matcher}
     */
    Matcher<Method> DEFAULT_USER_DECLARED_METHOD_MATCHER = value -> !value.isBridge() && !value.isSynthetic() && value.getDeclaringClass() != Object.class;
    /**
     * Default copyable field {@link Matcher}
     */
    Matcher<Field> DEFAULT_COPYABLE_FIELD_MATCHER = value -> !(Modifier.isStatic(value.getModifiers()) || Modifier.isFinal(value.getModifiers()));
    /**
     * Default public class {@link Matcher}
     */
    Matcher<Class<?>> DEFAULT_PUBLIC_CLASS_MATCHER = value -> Modifier.isPublic(value.getModifiers());
    /**
     * Default non-static class {@link Matcher}
     */
    Matcher<Class<?>> DEFAULT_NON_STATIC_CLASS_MATCHER = value -> value.isMemberClass() && !isStatic(value.getModifiers());

    /**
     * Default annotation class {@link Matcher}
     */
    Function<Set<Annotation>, Matcher<Class<Annotation>>> DEFAULT_ANNOTATION_MATCHER_FUNC = annotations -> value -> Objects.nonNull(getAnnotation(annotations, value));
    /**
     * Default class {@link Matcher}
     */
    Function<Class<?>, Matcher<?>> DEFAULT_INSTANCE_MATCHER_FUNC = clazz -> clazz::isInstance;
    /**
     * Default equals {@link Matcher}
     */
    Function<Object, Matcher<?>> DEFAULT_EQUALS_MATCHER_FUNC = object -> value -> Objects.equals(object, value);
    /**
     * Default class nestmate {@link Matcher}
     */
    Function<Class<?>, Matcher<?>> DEFAULT_NESTMATE_MATCHER_FUNC = clazz -> value -> value.getClass().isNestmateOf(clazz);
    /**
     * Default assignable {@link Matcher}
     */
    Function<Class<?>, Matcher<?>> DEFAULT_ASSIGNABLE_MATCHER_FUNC = clazz -> value -> value.getClass().isAssignableFrom(clazz);
    /**
     * Default identity {@link Matcher}
     */
    Function<Object, Matcher<?>> DEFAULT_IDENTITY_MATCHER_FUNC = identity -> value -> Objects.equals(identity, identityToString(value));

    /**
     * Default unique {@link Matcher}
     */
    Function<Set, Matcher<?>> DEFAULT_UNIQUE_MATCHER_FUNC = set -> value -> set.add(value);
    /**
     * Default exist {@link Matcher}
     */
    Function<Collection<?>, Matcher<?>> DEFAULT_EXIST_MATCHER_FUNC = collection -> collection::contains;
    /**
     * Default boolean {@link Matcher}
     */
    Function<Boolean, Matcher<Boolean>> DEFAULT_BOOLEAN_MATCHER_FUNC = flag -> flag::equals;

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
        ValidationUtils.notNull(description, "Description should not be null");
        description.append(value).append(StringUtils.wrapInBraces.apply(this.getDescription()));
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
        ValidationUtils.notNull(matcher, "Matcher should not be null!");
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
        ValidationUtils.notNull(matcher, "Matcher should not be null!");
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
        ValidationUtils.notNull(matcher, "Matcher should not be null!");
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
        ValidationUtils.notNull(matcher, "Matcher should not be null!");
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
        ValidationUtils.notNull(matcher, "Matcher should not be null!");
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
        ValidationUtils.notNull(matcher, "Matcher should not be null!");
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
        ValidationUtils.notNull(matcher, "Matcher should not be null!");
        return (final T t) -> not(xor(matcher)).matches(t);
    }

    /**
     * Returns binary flag based on all-matched input collection of values {@code T}
     *
     * @param values - initial input collection of values {@code T}
     * @return true - if all input values {@code T} match, false - otherwise
     */
    @NonNull
    @SuppressWarnings("varargs")
    default boolean allMatch(@Nullable final T... values) {
        return ServiceUtils.streamOf(values).allMatch(this::matches);
    }

    /**
     * Returns binary flag based on non-matched input collection of values {@code T}
     *
     * @param values - initial input collection of values {@code T}
     * @return true - if all input values {@code T} match, false - otherwise
     */
    @NonNull
    @SuppressWarnings("varargs")
    default boolean noneMatch(@Nullable final T... values) {
        return ServiceUtils.streamOf(values).noneMatch(this::matches);
    }

    /**
     * Returns binary flag based on any-matched input collection of values {@code T}
     *
     * @param values - initial input collection of values {@code T}
     * @return true - if all input values {@code T} match, false - otherwise
     */
    @NonNull
    @SuppressWarnings("varargs")
    default boolean anyMatch(@Nullable final T... values) {
        return ServiceUtils.streamOf(values).anyMatch(this::matches);
    }

    /**
     * Returns {@link BinaryOperator} by input {@link Matcher} parameters
     *
     * @param <T> type of input element to be matched by operation
     * @return {@link BinaryOperator}
     */
    @NonNull
    static <T> BinaryOperator<Matcher<T>> combiner() {
        return (matcher1, matcher2) -> andAll(matcher1, matcher2);
    }

    /**
     * Returns {@link Collection} of {@code T} items filtered by input {@link Matcher}
     *
     * @param <T>     type of input element to be matched by operation
     * @param values  - initial input {@link Iterable} collection of {@code T}
     * @param matcher - initial input {@link Matcher}
     * @return {@link Collection} of {@code T} items
     * @throws NullPointerException if matcher is {@code null}
     */
    @NonNull
    static <T> Collection<T> matchIf(@Nullable final Iterable<T> values, final Matcher<T> matcher) {
        ValidationUtils.notNull(matcher, "Matcher should not be null");
        return ServiceUtils.listOf(values).stream().filter(matcher::matches).collect(toUnmodifiableList());
    }

    /**
     * Returns {@link Collection} of {@code T} items filtered by input array of {@link Matcher}s
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
     * Returns {@link Collection} of {@code T} items filtered by input {@link Matcher} with skip count
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
        ValidationUtils.notNull(matcher, "Matcher should not be null");
        ValidationUtils.isTrue(skip >= 0, "Skip count should be positive or zero");
        return ServiceUtils.listOf(values).stream().skip(skip).filter(matcher::matches).collect(toUnmodifiableList());
    }

    /**
     * Returns {@link Collection} of {@code T} items filtered by input array of {@link Matcher}s with skip count
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
     * Returns {@link Optional} of first matched {@code T} item filtered by input {@link Matcher}
     *
     * @param <T>     type of input element to be matched by operation
     * @param values  - initial input {@link Iterable} collection of {@code T}
     * @param matcher - initial input {@link Matcher}
     * @return {@link Optional} of first match {@code T} item
     * @throws NullPointerException if matcher is {@code null}
     */
    @NonNull
    static <T> Optional<T> matchFirstIf(@Nullable final Iterable<T> values, final Matcher<T> matcher) {
        ValidationUtils.notNull(matcher, "Matcher should not be null");
        return ServiceUtils.listOf(values).stream().filter(matcher::matches).findFirst();
    }

    /**
     * Returns {@link Optional} of first matched {@code T} item filtered by input array of {@link Matcher}s
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
     * Returns {@link Optional} of last matched {@code T} item filtered by input {@link Matcher}
     *
     * @param <T>     type of input element to be matched by operation
     * @param values  - initial input {@link Iterable} collection of {@code T}
     * @param matcher - initial input {@link Matcher}
     * @return {@link Optional} of first match {@code T} item
     * @throws NullPointerException if matcher is {@code null}
     */
    @NonNull
    static <T> Optional<T> matchLastIf(@Nullable final Iterable<T> values, final Matcher<T> matcher) {
        ValidationUtils.notNull(matcher, "Matcher should not be null");
        return ServiceUtils.listOf(values).stream().filter(matcher::matches).reduce((first, last) -> last);
    }

    /**
     * Returns {@link Optional} of last matched {@code T} item filtered by input array of {@link Matcher}s
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
     * Returns {@link Collection} of {@code T} items filtered by input {@link Matcher}
     *
     * @param <T>     type of input element to be matched by operation
     * @param values  - initial input {@link Iterable} collection of {@code T}
     * @param matcher - initial input {@link Matcher}
     * @return {@link Collection} of {@code T} items
     */
    @NonNull
    static <T> Collection<T> removeIf(@Nullable final Iterable<T> values, final Matcher<T> matcher) {
        ValidationUtils.notNull(matcher, "Matcher should not be null");
        return ServiceUtils.listOf(values).stream().filter(matcher.negate()::matches).collect(toUnmodifiableList());
    }

    /**
     * Returns {@link Collection} of {@code T} items filtered by input array of {@link Matcher}s
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
     * Generates collection of {@code T} items by input parameters
     *
     * @param <T>      type of stream item
     * @param supplier - initial input {@link Supplier}
     * @param matcher  - initial input {@link Matcher}
     * @param consumer - initial input {@link Consumer}
     */
    @NonNull
    static <T> void process(final Supplier<Iterable<T>> supplier, final Matcher<T> matcher, final Consumer<T> consumer) {
        ValidationUtils.notNull(supplier, "Supplier should not be null");
        ValidationUtils.notNull(matcher, "Matcher should not be null");
        ValidationUtils.notNull(consumer, "Consumer should not be null");

        ServiceUtils.streamOf(supplier.get()).filter(matcher::matches).forEach(consumer::accept);
    }

    /**
     * Generates collection of {@code T} items by input parameters
     *
     * @param <T>      type of stream item
     * @param supplier - initial input {@link Supplier}
     * @param limit    - initial input stream limit
     * @param matcher  - initial input {@link Matcher}
     * @param consumer - initial input {@link Consumer}
     */
    @NonNull
    static <T> void generate(final Supplier<T> supplier, final int limit, final Matcher<T> matcher, final Consumer<T> consumer) {
        ValidationUtils.notNull(supplier, "Supplier should not be null");
        ValidationUtils.notNull(matcher, "Matcher should not be null");
        ValidationUtils.notNull(consumer, "Consumer should not be null");

        assert limit >= 0 : "Limit count should be positive or zero";
        Stream.generate(() -> supplier.get()).limit(limit).filter(matcher::matches).forEach(consumer::accept);
    }

    /**
     * Aggregates {@link Iterable} collection of {@code T} items by input parameters
     *
     * @param <T>        type of stream item
     * @param iterable   - initial input {@link Iterable} collection of {@code T} items
     * @param comparator - initial input {@link Comparator}
     */
    static <T> Stream<List<T>> groupBy(final Iterable<T> iterable, final Comparator<T> comparator) {
        ValidationUtils.notNull(comparator, "Matcher should not be null");
        return StreamUtils.groupRuns(ServiceUtils.streamOf(iterable), comparator);
    }

    /**
     * Returns {@link Map} of {@code T} items filtered by input {@link Matcher}
     *
     * @param <T>       type of input element to be matched by operation
     * @param values    - initial input {@link Iterable} collection of {@code T}
     * @param matcher   - initial input {@link Matcher}
     * @param collector - initial input {@link Collector}
     * @return {@link Map} of {@code T} items
     */
    @NonNull
    static <T, A, M extends Collection<T>> Map<Boolean, M> mapBy(@Nullable final Iterable<T> values, final Matcher<T> matcher, final Collector<T, A, M> collector) {
        ValidationUtils.notNull(matcher, "Matcher should not be null");
        ValidationUtils.notNull(collector, "Collector should not be null");

        return ServiceUtils.streamOf(values).collect(Collectors.partitioningBy(matcher::matches, collector));
    }

    /**
     * Returns {@link Map} of {@code T} items filtered by input array of {@link Matcher}s
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
     * Returns {@link List} by input {@link Stream} of {@code T} items filtered by {@link Matcher}
     *
     * @param <T>     type of input element to be matched by operation
     * @param stream  - initial input {@link Stream}
     * @param matcher - initial input {@link Matcher}
     * @return {@link List} of {@code T} items
     */
    @NonNull
    static <T> List<T> skipUntil(final Stream<T> stream, final Matcher<T> matcher) {
        ValidationUtils.notNull(stream, "Stream should not be null");
        ValidationUtils.notNull(matcher, "Matcher should not be null");
        return StreamUtils.skipUntil(stream, matcher::matches).collect(toUnmodifiableList());
    }

    /**
     * Returns {@link List} by input {@link Stream} of {@code T} items filtered by {@link Matcher}
     *
     * @param <T>     type of input element to be matched by operation
     * @param stream  - initial input {@link Stream}
     * @param matcher - initial input {@link Matcher}
     * @return {@link List} of {@code T} items
     */
    @NonNull
    static <T> List<T> skipUntilInclusive(final Stream<T> stream, final Matcher<T> matcher) {
        ValidationUtils.notNull(stream, "Stream should not be null");
        ValidationUtils.notNull(matcher, "Matcher should not be null");
        return StreamUtils.skipUntilInclusive(stream, matcher::matches).collect(toUnmodifiableList());
    }

    /**
     * Returns {@link List} by input {@link Stream} of {@code T} items filtered by {@link Matcher}
     *
     * @param <T>     type of input element to be matched by operation
     * @param stream  - initial input {@link Stream}
     * @param matcher - initial input {@link Matcher}
     * @return {@link List} of {@code T} items
     */
    @NonNull
    static <T> List<T> skipWhile(final Stream<T> stream, final Matcher<T> matcher) {
        ValidationUtils.notNull(stream, "Stream should not be null");
        ValidationUtils.notNull(matcher, "Matcher should not be null");
        return StreamUtils.skipWhile(stream, matcher::matches).collect(toUnmodifiableList());
    }

    /**
     * Returns {@link List} by input {@link Stream} of {@code T} items filtered by {@link Matcher}
     *
     * @param <T>     type of input element to be matched by operation
     * @param stream  - initial input {@link Stream}
     * @param matcher - initial input {@link Matcher}
     * @return {@link List} of {@code T} items
     */
    @NonNull
    static <T> List<T> skipWhileInclusive(final Stream<T> stream, final Matcher<T> matcher) {
        ValidationUtils.notNull(stream, "Stream should not be null");
        ValidationUtils.notNull(matcher, "Matcher should not be null");
        return StreamUtils.skipWhileInclusive(stream, matcher::matches).collect(toUnmodifiableList());
    }

    /**
     * Returns {@link List} by input {@link Stream} of {@code T} items filtered by {@link Matcher}
     *
     * @param <T>     type of input element to be matched by operation
     * @param stream  - initial input {@link Stream}
     * @param matcher - initial input {@link Matcher}
     * @return {@link List} of {@code T} items
     */
    @NonNull
    static <T> List<T> takeWhile(final Stream<T> stream, final Matcher<T> matcher) {
        ValidationUtils.notNull(stream, "Stream should not be null");
        ValidationUtils.notNull(matcher, "Matcher should not be null");
        return StreamUtils.takeWhile(stream, matcher::matches).collect(toUnmodifiableList());
    }

    /**
     * Returns {@link List} by input {@link Stream} of {@code T} items filtered by {@link Matcher}
     *
     * @param <T>     type of input element to be matched by operation
     * @param stream  - initial input {@link Stream}
     * @param matcher - initial input {@link Matcher}
     * @return {@link List} of {@code T} items
     */
    @NonNull
    static <T> List<T> takeWhileInclusive(final Stream<T> stream, final Matcher<T> matcher) {
        ValidationUtils.notNull(stream, "Stream should not be null");
        ValidationUtils.notNull(matcher, "Matcher should not be null");
        return StreamUtils.takeWhileInclusive(stream, matcher::matches).collect(toUnmodifiableList());
    }

    /**
     * Returns {@link List} by input {@link Stream} of {@code T} items filtered by {@link Matcher}
     *
     * @param <T>     type of input element to be matched by operation
     * @param stream  - initial input {@link Stream}
     * @param matcher - initial input {@link Matcher}
     * @return {@link List} of {@code T} items
     */
    @NonNull
    static <T> List<T> takeUntil(final Stream<T> stream, final Matcher<T> matcher) {
        ValidationUtils.notNull(stream, "Stream should not be null");
        ValidationUtils.notNull(matcher, "Matcher should not be null");
        return StreamUtils.takeUntil(stream, matcher::matches).collect(toUnmodifiableList());
    }

    /**
     * Returns {@link List} by input {@link Stream} of {@code T} items filtered by {@link Matcher}
     *
     * @param <T>     type of input element to be matched by operation
     * @param stream  - initial input {@link Stream}
     * @param matcher - initial input {@link Matcher}
     * @return {@link List} of {@code T} items
     */
    @NonNull
    static <T> List<T> takeUntilInclusive(final Stream<T> stream, final Matcher<T> matcher) {
        ValidationUtils.notNull(stream, "Stream should not be null");
        ValidationUtils.notNull(matcher, "Matcher should not be null");
        return StreamUtils.takeUntilInclusive(stream, matcher::matches).collect(toUnmodifiableList());
    }

    /**
     * Returns {@link Stream} of {@link List} by input {@link Stream} of {@code T} items grouped by {@link Comparator}
     *
     * @param <T>        type of input element to be matched by operation
     * @param stream     - initial input {@link Stream}
     * @param comparator - initial input {@link Matcher}
     * @return {@link Stream} of {@link List} with {@code T} items
     */
    @NonNull
    static <T> Stream<List<T>> groupBy(final Stream<T> stream, final Comparator<T> comparator) {
        return StreamUtils.groupRuns(stream, comparator);
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
    static <T> boolean remove(@Nullable final Iterable<T> values, final Matcher<T> matcher) {
        ValidationUtils.notNull(matcher, "Matcher should not be null");
        return ServiceUtils.listOf(values).removeIf(matcher::matches);
    }

    /**
     * Filters input {@link Collection} of {@code T} items by input array of {@link Matcher}s
     *
     * @param <T>      type of input element to be matched by operation
     * @param values   - initial input {@link Iterable} collection of {@code T}
     * @param matchers - initial input array of {@link Matcher}s
     * @return {@link Collection} of {@code T} items
     */
    @NonNull
    static <T> boolean remove(@Nullable final Iterable<T> values, final Matcher<T>... matchers) {
        final Matcher<T> matcher = andAll(matchers);
        return remove(values, matcher);
    }

    /**
     * Filters input {@link Collection} of {@code T} items by input {@link Matcher} (only first match)
     *
     * @param <T>     type of input element to be matched by operation
     * @param values  - initial input {@link Iterable} collection of {@code T}
     * @param matcher - initial input {@link Matcher}
     */
    @NonNull
    static <T> void removeFirst(@Nullable final Iterable<T> values, final Matcher<T> matcher) {
        ValidationUtils.notNull(matcher, "Matcher should not be null");
        matchFirstIf(values, matcher).ifPresent(entry -> ServiceUtils.listOf(values).remove(entry));
    }

    /**
     * Filters input {@link Collection} of {@code T} items by input array of {@link Matcher}s (only first match)
     *
     * @param <T>      type of input element to be matched by operation
     * @param values   - initial input {@link Iterable} collection of {@code T}
     * @param matchers - initial input array of {@link Matcher}s
     */
    @NonNull
    static <T> void removeFirst(@Nullable final Iterable<T> values, final Matcher<T>... matchers) {
        final Matcher<T> matcher = andAll(matchers);
        removeFirst(values, matcher);
    }

    /**
     * Filters input {@link Collection} of {@link Entry}s items by input {@link Matcher} (only last match)
     *
     * @param <T>     type of input element to be matched by operation
     * @param values  - initial input {@link Iterable} collection of {@link Entry}s
     * @param matcher - initial input {@link Matcher}
     */
    @NonNull
    static <T> void removeLast(@Nullable final Iterable<T> values, final Matcher<T> matcher) {
        ValidationUtils.notNull(matcher, "Matcher should not be null");
        matchLastIf(values, matcher).ifPresent(entry -> ServiceUtils.listOf(values).remove(entry));
    }

    /**
     * Filters input {@link Collection} of {@link Entry}s items by input array of {@link Matcher}s (only last match)
     *
     * @param <T>      type of input element to be matched by operation
     * @param values   - initial input {@link Iterable} collection of {@link Entry}s
     * @param matchers - initial input array of {@link Matcher}s
     */
    @NonNull
    static <T> void removeLast(@Nullable final Iterable<T> values, final Matcher<T>... matchers) {
        final Matcher<T> matcher = andAll(matchers);
        removeLast(values, matcher);
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
        ValidationUtils.notNull(supplier, "Supplier should not be null!");
        ValidationUtils.notNull(matcher, "Matcher should not be null!");

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
        ValidationUtils.notNull(matchers, "Matchers should not be null!");
        return ServiceUtils.reduceOrThrow(matchers, (final Matcher<T> m) -> m.getMode().isEnable(), Matcher::and, () -> InvalidParameterException.throwError("Unable to combine matchers = {%s} via logical AND", join(matchers, "|")));
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
        ValidationUtils.notNull(matchers, "Matchers should not be null!");
        return ServiceUtils.reduceOrThrow(matchers, (final Matcher<T> m) -> m.getMode().isEnable(), Matcher::or, () -> InvalidParameterException.throwError("Unable to combine matchers = {%s} via logical OR", join(matchers, "|")));
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
        ValidationUtils.notNull(matchers, "Matchers should not be null!");
        return ServiceUtils.reduceOrThrow(matchers, (final Matcher<T> m) -> m.getMode().isEnable(), Matcher::xor, () -> InvalidParameterException.throwError("Unable to combine matchers = {%s} via logical XOR", join(matchers, "|")));
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
        ValidationUtils.notNull(matchers, "Matchers should not be null!");
        return ServiceUtils.reduceOrThrow(matchers, (final Matcher<T> m) -> m.getMode().isEnable(), Matcher::nand, () -> InvalidParameterException.throwError("Unable to combine matchers = {%s} via logical NAND", join(matchers, "|")));
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
        ValidationUtils.notNull(matchers, "Matchers should not be null!");
        return ServiceUtils.reduceOrThrow(matchers, (final Matcher<T> m) -> m.getMode().isEnable(), Matcher::nor, () -> InvalidParameterException.throwError("Unable to combine matchers = {%s} via logical NOR", join(matchers, "|")));
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
        ValidationUtils.notNull(matchers, "Matchers should not be null!");
        return ServiceUtils.reduceOrThrow(matchers, (final Matcher<T> m) -> m.getMode().isEnable(), Matcher::xnor, () -> InvalidParameterException.throwError("Unable to combine matchers = {%s} via logical XNOR", join(matchers, "|")));
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
        ValidationUtils.notNull(matcher, "Matcher should not be null");
        return matcher::matches;
    }
}
