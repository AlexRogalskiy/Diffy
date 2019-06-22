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
package com.wildbeeslabs.sensiblemetrics.diffy.common.executor.impl;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

/**
 * Abstract {@link Callable} task implementation
 *
 * @param <T> type of task item
 */
public abstract class AbstractTask<T> implements Callable<T> {

    /**
     * Stores the executor service to be destroyed at the end.
     */
    private final ExecutorService executorService;

    /**
     * Creates a new instance of {@code InitializationTask} and initializes
     * it with the {@code ExecutorService} to be destroyed at the end.
     *
     * @param executorService the {@code ExecutorService}
     */
    public AbstractTask(final ExecutorService executorService) {
        this.executorService = executorService;
    }

    /**
     * Initiates initialization and returns the result.
     *
     * @return the result object
     * @throws Exception if an error occurs
     */
    @Override
    public T call() throws Exception {
        try {
            return initialize();
        } finally {
            if (Objects.nonNull(this.executorService)) {
                this.executorService.shutdown();
            }
        }
    }

    /**
     * Returns result {@code T} by processing current task
     *
     * @return result {@code T}
     * @throws Exception if current task initializes exceptionally
     */
    protected abstract T initialize() throws Exception;
}
