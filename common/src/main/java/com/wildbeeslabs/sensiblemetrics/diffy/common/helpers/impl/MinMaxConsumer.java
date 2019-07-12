package com.wildbeeslabs.sensiblemetrics.diffy.common.helpers.impl;

import com.wildbeeslabs.sensiblemetrics.diffy.common.annotation.Factory;
import lombok.Getter;

import java.util.Comparator;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collector;

/**
 * This <i>consumer</i> class is used for calculating the min and max value
 * according to the given {@code Comparator}.
 * <p>
 * This class is designed to work with (though does not require) streams. For
 * example, you can compute minimum and maximum values with:
 * <pre>{@code
 * final Stream<Integer> stream = ...
 * final MinMaxConsumer<Integer> minMax = stream.collect(
 *         MinMaxConsumer::of,
 *         MinMaxConsumer::accept,
 *         MinMaxConsumer::combine
 *     );
 * }</pre>
 *
 * @author <a href="mailto:franz.wilhelmstoetter@gmail.com">Franz Wilhelmstötter</a>
 * @version 3.7
 * @implNote This implementation is not thread safe. However, it is safe to use on a
 * parallel stream, because the parallel implementation of
 * {@link java.util.stream.Stream#collect Stream.collect()}provides the
 * necessary partitioning, isolation, and merging of results for safe and
 * efficient parallel execution.
 * @since 3.0
 */
@Getter
public final class MinMaxConsumer<C> implements Consumer<C> {

    /**
     * Default {@link Comparator} instance
     */
    private final Comparator<? super C> comparator;

    private C min;
    private C max;
    private long count = 0L;

    private MinMaxConsumer(final Comparator<? super C> comparator) {
        this.comparator = Objects.requireNonNull(comparator);
    }

    /**
     * Accept the element for min-max calculation.
     *
     * @param object the element to use for min-max calculation
     */
    @Override
    public void accept(final C object) {
        this.min = min(this.comparator, this.min, object);
        this.max = max(this.comparator, this.max, object);
        ++this.count;
    }

    /**
     * Combine two {@code MinMaxConsumer} objects.
     *
     * @param other the other {@code MinMaxConsumer} object to combine
     * @return {@code this}
     * @throws NullPointerException if the {@code other} object is
     *                              {@code null}.
     */
    public MinMaxConsumer<C> combine(final MinMaxConsumer<C> other) {
        this.min = min(this.comparator, this.min, other.min);
        this.max = max(this.comparator, this.max, other.max);
        this.count += other.count;
        return this;
    }

    /**
     * Compares the state of two {@code LongMomentStatistics} objects. This is
     * a replacement for the {@link #equals(Object)} which is not advisable to
     * implement for this mutable object. If two object have the same state, it
     * has still the same state when updated with the same value.
     * <pre>{@code
     * final MinMaxConsumer mm1 = ...;
     * final MinMaxConsumer mm2 = ...;
     *
     * if (mm1.sameState(mm2)) {
     *     final long value = random.nextInt(1_000_000);
     *     mm1.accept(value);
     *     mm2.accept(value);
     *
     *     assert mm1.sameState(mm2);
     *     assert mm2.sameState(mm1);
     *     assert mm1.sameState(mm1);
     * }
     * }</pre>
     *
     * @param other the other object for the test
     * @return {@code true} the {@code this} and the {@code other} objects have
     * the same state, {@code false} otherwise
     * @since 3.7
     */
    public boolean sameState(final MinMaxConsumer<C> other) {
        return this == other || Objects.equals(min, other.min) && Objects.equals(max, other.max);
    }

    /* *************************************************************************
     *  Some static helper methods.
     * ************************************************************************/

    /**
     * Return the minimum of two values, according the given comparator.
     * {@code null} values are allowed.
     *
     * @param comp the comparator used for determining the min value
     * @param a    the first value to compare
     * @param b    the second value to compare
     * @param <T>  the type of the compared objects
     * @return the minimum value, or {@code null} if both values are {@code null}.
     * If only one value is {@code null}, the non {@code null} values is
     * returned.
     */
    public static <T> T min(final Comparator<? super T> comp, final T a, final T b) {
        return a != null ? b != null ? comp.compare(a, b) <= 0 ? a : b : a : b;
    }

    /**
     * Return the maximum of two values, according the given comparator.
     * {@code null} values are allowed.
     *
     * @param comp the comparator used for determining the max value
     * @param a    the first value to compare
     * @param b    the second value to compare
     * @param <T>  the type of the compared objects
     * @return the maximum value, or {@code null} if both values are {@code null}.
     * If only one value is {@code null}, the non {@code null} values is
     * returned.
     */
    public static <T> T max(final Comparator<? super T> comp, final T a, final T b) {
        return a != null ? b != null ? comp.compare(a, b) >= 0 ? a : b : a : b;
    }


    /* *************************************************************************
     *  Some static factory methods.
     * ************************************************************************/

    /**
     * Return a {@code Collector} which calculates the minimum and maximum value.
     * The given {@code comparator} is used for comparing two objects.
     *
     * <pre>{@code
     * final Comparator<SomeObject> comparator = ...
     * final Stream<SomeObject> stream = ...
     * final MinMaxConsumer<SomeObject> moments = stream
     *     .collect(doubleMoments.toMinMax(comparator));
     * }</pre>
     *
     * @param comparator the {@code Comparator} to use
     * @param <T>        the type of the input elements
     * @return a {@code Collector} implementing the min-max reduction
     * @throws NullPointerException if the given {@code mapper} is
     *                              {@code null}
     */
    public static <T> Collector<T, ?, MinMaxConsumer<T>> toMinMax(final Comparator<? super T> comparator) {
        Objects.requireNonNull(comparator, "Comparator should not be null");
        return Collector.of(() -> MinMaxConsumer.of(comparator),
            MinMaxConsumer::accept,
            MinMaxConsumer::combine
        );
    }

    /**
     * Return a {@code Collector} which calculates the minimum and maximum value.
     * The <i>reducing</i> objects must be comparable.
     *
     * <pre>{@code
     * final Stream<SomeObject> stream = ...
     * final MinMaxConsumer<SomeObject> moments = stream
     *     .collect(doubleMoments.toMinMax(comparator));
     * }</pre>
     *
     * @param <C> the type of the input elements
     * @return a {@code Collector} implementing the min-max reduction
     * @throws NullPointerException if the given {@code mapper} is
     *                              {@code null}
     */
    public static <C extends Comparable<? super C>> Collector<C, ?, MinMaxConsumer<C>> toMinMax() {
        return toMinMax(Comparator.naturalOrder());
    }

    /**
     * Create a new {@code MinMaxConsumer} <i>consumer</i> with the given
     * {@link Comparator}.
     *
     * @param comparator the comparator used for comparing two elements
     * @param <T>        the element type
     * @return a new {@code MinMaxConsumer} <i>consumer</i>
     * @throws NullPointerException if the {@code comparator} is
     *                              {@code null}.
     */
    @Factory
    public static <T> MinMaxConsumer<T> of(final Comparator<? super T> comparator) {
        return new MinMaxConsumer<>(comparator);
    }

    /**
     * Create a new {@code MinMaxConsumer} <i>consumer</i>.
     *
     * @param <C> the element type
     * @return a new {@code MinMaxConsumer} <i>consumer</i>
     */
    @Factory
    public static <C extends Comparable<? super C>> MinMaxConsumer<C> of() {
        return of(Comparator.naturalOrder());
    }

}
