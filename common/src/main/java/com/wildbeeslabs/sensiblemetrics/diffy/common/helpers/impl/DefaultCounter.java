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
package com.wildbeeslabs.sensiblemetrics.diffy.common.utils.impl;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
@EqualsAndHashCode
@ToString
public final class DefaultCounter implements Serializable {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = -8069421485492009310L;

    /**
     * Hidden constructor.
     */
    private DefaultCounter() {
        super();
    }

    /**
     * It counts how many times each element provided occurred in an array and
     * returns a dict with the element as key and the count as value.
     *
     * @param tokens array of tokens
     * @return dict, where the elements are key, and the count the value
     */
    public static Map<CharSequence, Integer> of(final CharSequence[] tokens) {
        final Map<CharSequence, Integer> innerCounter = new HashMap<>();
        for (final CharSequence token : tokens) {
            int value = innerCounter.getOrDefault(token, 0);
            innerCounter.put(token, ++value);
        }
        return innerCounter;
    }
}
