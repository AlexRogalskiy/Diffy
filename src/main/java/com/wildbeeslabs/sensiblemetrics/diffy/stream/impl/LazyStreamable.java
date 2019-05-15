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
package com.wildbeeslabs.sensiblemetrics.diffy.stream.impl;

import com.wildbeeslabs.sensiblemetrics.diffy.stream.iface.Streamable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.util.Iterator;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Default lazy {@link Streamable} implementation for a given {@link Supplier}
 */
@Data
@EqualsAndHashCode
@ToString
@Value(staticConstructor = "from")
public class LazyStreamable<T> implements Streamable<T> {

    /**
     * Default {@link Supplier} instance
     */
    private final Supplier<? extends Stream<T>> supplier;

    /**
     * Returns default {@link Iterator} instance
     *
     * @return default {@link Iterator} instance
     */
    @Override
    public Iterator<T> iterator() {
        return this.stream().iterator();
    }

    /**
     * Returns default {@link Stream} instance
     *
     * @return default {@link Stream} instance
     */
    @Override
    public Stream<T> stream() {
        return this.getSupplier().get();
    }
}
