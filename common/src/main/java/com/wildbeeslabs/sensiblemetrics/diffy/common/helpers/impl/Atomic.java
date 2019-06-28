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

import java.util.concurrent.atomic.AtomicReference;

/**
 * Atomic implementation
 *
 * @param <T> type of stored value
 */
public abstract class Atomic<T> {
    /**
     * Default value
     */
    private T value;

    /**
     * Default atomic state type
     */
    private enum State {NEW, INITIALIZING, INITIALIZED}

    private final AtomicReference<State> init = new AtomicReference<>(State.NEW);

    /**
     * Creates new atomic instance by input value
     *
     * @param value - initial input {@code T} value to store by
     */
    public Atomic(final T value) {
        this.initialize(value);
    }

    protected final void initialize(final T value) {
        if (!this.init.compareAndSet(State.NEW, State.INITIALIZING)) {
            throw new IllegalStateException("ERROR: unexpected atomic state");
        }
        this.value = value;
        //this.init.set(State.INITIALIZED);
    }

    protected final T getValue() {
        this.checkInit();
        return this.value;
    }

    private void checkInit() {
        if (this.init.get() != State.INITIALIZED) {
            throw new IllegalStateException("ERROR: atomic is not initialized");
        }
    }
}

