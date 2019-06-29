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

import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Scheduled {@link AbstractExecutorService} implementation
 */
public abstract class AbstractScheduledExecutorService extends AbstractExecutorService implements ScheduledExecutorService {

    /**
     * Default {@link ScheduledExecutorService} instance
     */
    private final ScheduledExecutorService delegate;

    /**
     * Default abstract scheduled executor service constructor
     *
     * @param delegate - initial input {@link ScheduledExecutorService} instance
     */
    protected AbstractScheduledExecutorService(final ScheduledExecutorService delegate) {
        super(delegate);
        this.delegate = delegate;
    }

    @Override
    public final ScheduledFuture<?> schedule(final Runnable command, long delay, final TimeUnit unit) {
        return this.delegate.schedule(wrapTask(command), delay, unit);
    }

    @Override
    public final <V> ScheduledFuture<V> schedule(final Callable<V> task, long delay, final TimeUnit unit) {
        return this.delegate.schedule(wrapTask(task), delay, unit);
    }

    @Override
    public final ScheduledFuture<?> scheduleAtFixedRate(final Runnable command, long initialDelay, long period, final TimeUnit unit) {
        return this.delegate.scheduleAtFixedRate(wrapTask(command), initialDelay, period, unit);
    }

    @Override
    public final ScheduledFuture<?> scheduleWithFixedDelay(final Runnable command, long initialDelay, long delay, final TimeUnit unit) {
        return this.delegate.scheduleWithFixedDelay(wrapTask(command), initialDelay, delay, unit);
    }
}
