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
package com.wildbeeslabs.sensiblemetrics.diffy.common.helpers.impl;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Map;
import java.util.function.BiConsumer;

/**
 * A positive index.
 *
 * @author Alex Ruiz
 */
@Data
@EqualsAndHashCode
@ToString
public class Index {

    public final int value;

    /**
     * Creates a new {@link Index}.
     *
     * @param value the value of the index.
     * @return the created {@code Index}.
     * @throws IllegalArgumentException if the given value is negative.
     */
    public static Index atIndex(int value) {
        assert value >= 0 : "The value of the index should not be negative";
        return new Index(value);
    }

    public BiConsumer<Map<Integer, Integer>, Double> accumulator(final Integer size) {
        return (map, value) -> map.merge((int) (value / size), 1, (a, b) -> a + 1);
    }

    private Index(int value) {
        this.value = value;
    }
}
