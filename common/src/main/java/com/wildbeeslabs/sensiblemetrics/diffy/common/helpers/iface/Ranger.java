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
package com.wildbeeslabs.sensiblemetrics.diffy.common.helpers.iface;

import com.wildbeeslabs.sensiblemetrics.diffy.common.utils.ValidationUtils;
import lombok.NonNull;
import org.apache.commons.collections.comparators.ComparableComparator;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Range interface declaration
 *
 * @param <T> type of range bound element
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-07
 */
@SuppressWarnings("unchecked")
public interface Ranger<T> extends Serializable {

    /**
     * Returns range upper bound {@code T}
     *
     * @return range upper bound {@code T}
     */
    @NonNull
    T getUpperBound();

    /**
     * Returns range lower bound {@code T}
     *
     * @return range lower bound {@code T}
     */
    @NonNull
    T getLowerBound();

    /**
     * Returns binary flag based on lower/upper bounds comparison
     *
     * @return true - if lower/upper bounds are equal, false - otherwise
     */
    default boolean isConstant() {
        return Objects.equals(this.getLowerBound(), this.getUpperBound());
    }

    /**
     * <p>Checks whether the specified element occurs within this range.</p>
     *
     * @param element the element to check for, null returns false
     * @return true if the specified element occurs within this range
     */
    default boolean contains(final T element) {
        if (Objects.isNull(element)) {
            return false;
        }
        return this.getComparator().compare(element, this.getLowerBound()) > -1 && this.getComparator().compare(element, this.getUpperBound()) < 1;
    }

    /**
     * Returns default {@link Comparator}
     *
     * @return {@link Comparator}
     */
    default Comparator<? super T> getComparator() {
        return ComparableComparator.getInstance();
    }

    /**
     * A collector to create {@link Map} from a {@link Stream} of {@link Ranger}s
     *
     * @param <T> type of range bound element
     * @return {@link Collector} from a {@link Stream} of {@link Ranger}s.
     */
    static <T> Collector<Ranger<T>, ?, Map<T, T>> toMap() {
        return Collectors.toMap(Ranger::getLowerBound, Ranger::getUpperBound);
    }

    /**
     * Invokes the given {@link BiConsumer} if all given {@link Optional} are present
     *
     * @param <T>        type of range bound element
     * @param lowerBound - initial input range lower bound {@link Optional}
     * @param upperBound - initial input range upper bound {@link Optional}
     * @param consumer   - initial input {@link BiConsumer} operator
     */
    static <T> void ifAllPresent(final Optional<T> lowerBound, final Optional<T> upperBound, final BiConsumer<T, T> consumer) {
        ValidationUtils.notNull(lowerBound, "Optional lower bound should not be null!");
        ValidationUtils.notNull(upperBound, "Optional upper bound should not be null!");
        ValidationUtils.notNull(consumer, "Binary consumer operator should not be null!");

        mapIfAllPresent(lowerBound, upperBound, (l, u) -> {
            consumer.accept(l, u);
            return null;
        });
    }

    /**
     * Maps the values contained in the given {@link Optional} if both of them are present
     *
     * @param <T>        type of range bound element
     * @param <R>        type of function result
     * @param lowerBound - initial input range lower bound {@link Optional}
     * @param upperBound - initial input range upper bound {@link Optional}
     * @param function   - initial input {@link BiFunction} operator
     * @return {@link Optional} of {@link BiFunction} operator result {@code R}
     */
    static <T, R> Optional<R> mapIfAllPresent(final Optional<T> lowerBound, final Optional<T> upperBound, final BiFunction<T, T, R> function) {
        ValidationUtils.notNull(lowerBound, "Optional lower bound should not be null!");
        ValidationUtils.notNull(upperBound, "Optional upper bound should not be null!");
        ValidationUtils.notNull(function, "Binary function operator should not be null!");

        return lowerBound.flatMap(l -> upperBound.map(u -> function.apply(l, u)));
    }
}
