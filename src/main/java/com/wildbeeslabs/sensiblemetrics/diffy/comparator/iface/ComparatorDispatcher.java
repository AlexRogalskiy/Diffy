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
package com.wildbeeslabs.sensiblemetrics.diffy.comparator.iface;

import com.wildbeeslabs.sensiblemetrics.diffy.matcher.iface.BiMatcher;
import com.wildbeeslabs.sensiblemetrics.diffy.sort.SortManager;
import lombok.NonNull;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.BinaryOperator;

/**
 * Sort comparator declaration
 *
 * @param <T> type of input element to be compared by operation
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@FunctionalInterface
public interface ComparatorDispatcher<T> extends Serializable {

    /**
     * Returns {@link Comparator} by input {@link SortManager}
     *
     * @param sortManager - initial input {@link SortManager}
     * @return {@link Comparator}
     */
    @NonNull
    Comparator<? super T> getComparator(final SortManager sortManager);

    /**
     * Returns {@link BiMatcher} by input {@link Comparator}
     *
     * @param sortManager - initial input {@link SortManager}
     * @return {@link BiMatcher}
     * @throws NullPointerException if sortManager is {@code null}
     */
    @NonNull
    default BiMatcher<T> equalBy(final SortManager sortManager) {
        Objects.requireNonNull(sortManager, "SortManager should not be null");
        return (final T a, final T b) -> Objects.compare(a, b, this.getComparator(sortManager)) == 0;
    }

    /**
     * Returns {@link BinaryOperator} which returns the greater of two elements according to the specified {@link SortManager}
     *
     * @param sortManager - initial input {@link SortManager}
     * @return {@link BinaryOperator}
     * @throws NullPointerException if sortManager is {@code null}
     */
    @NonNull
    default BinaryOperator<T> maxBy(final SortManager sortManager) {
        Objects.requireNonNull(sortManager, "SortManager should not be null");
        return (final T a, final T b) -> Objects.compare(a, b, this.getComparator(sortManager)) > 0 ? a : b;
    }

    /**
     * Returns {@link BinaryOperator} which returns the lesser of two elements according to the specified {@link SortManager}
     *
     * @param sortManager - initial input {@link SortManager}
     * @return {@link BinaryOperator}
     * @throws NullPointerException if sortManager is {@code null}
     */
    @NonNull
    default BinaryOperator<T> minBy(final SortManager sortManager) {
        Objects.requireNonNull(sortManager, "SortManager should not be null");
        return (final T a, final T b) -> Objects.compare(a, b, this.getComparator(sortManager)) < 0 ? a : b;
    }
}
