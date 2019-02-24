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
package com.wildbeeslabs.sensiblemetrics.comparalyzer.interfaces.impl;

import com.wildbeeslabs.sensiblemetrics.comparalyzer.interfaces.Streamable;
import lombok.Value;

import java.util.Iterator;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Default lazy {@link Streamable} implementation from a given {@link Supplier}
 */
@Value(staticConstructor = "from")
public class LazyStream<T> implements Streamable<T> {

    private final Supplier<? extends Stream<T>> stream;

    /**
     * Returns default iterator instance {@link Iterator}
     *
     * @return default iterator instance {@link Iterator}
     */
    @Override
    public Iterator<T> iterator() {
        return stream().iterator();
    }

    /**
     * Returns default stream instance {@link Stream}
     *
     * @return default stream instance {@link Stream}
     */
    @Override
    public Stream<T> stream() {
        return stream.get();
    }
}
