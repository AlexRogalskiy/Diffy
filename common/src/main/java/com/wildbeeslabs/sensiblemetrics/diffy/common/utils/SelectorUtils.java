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
package com.wildbeeslabs.sensiblemetrics.diffy.common.utils;

import com.wildbeeslabs.sensiblemetrics.diffy.common.helpers.iface.Selector;
import lombok.experimental.UtilityClass;

import java.util.Comparator;
import java.util.stream.Stream;

@UtilityClass
public class SelectorUtils {

    public static <T> Selector<T> roundRobin() {
        return new Selector<>() {
            private int startIndex = 0;

            @Override
            public Integer apply(T[] options) {
                int result = startIndex;
                while (options[result] == null) {
                    result = (result + 1) % options.length;
                }

                startIndex = (result + 1) % options.length;
                return result;
            }
        };
    }

    public static <T extends Comparable<T>> Selector<T> takeMin() {
        return takeMin(Comparator.naturalOrder());
    }

    public static <T> Selector<T> takeMin(final Comparator<? super T> comparator) {
        return new Selector<>() {

            private int startIndex = 0;

            @Override
            public Integer apply(final T[] options) {
                T smallest = Stream.of(options).filter(t -> t != null).min(comparator).get();

                int result = startIndex;
                while (options[result] == null || comparator.compare(smallest, options[result]) != 0) {
                    result = (result + 1) % options.length;
                }

                startIndex = (result + 1) % options.length;
                return result;
            }
        };
    }

    public static <T extends Comparable<T>> Selector<T> takeMax() {
        return takeMax(Comparator.naturalOrder());
    }

    public static <T> Selector<T> takeMax(final Comparator<? super T> comparator) {
        return takeMin(comparator.reversed());
    }
}
