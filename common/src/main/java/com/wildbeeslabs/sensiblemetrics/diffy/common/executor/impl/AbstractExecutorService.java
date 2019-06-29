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

import com.wildbeeslabs.sensiblemetrics.diffy.common.utils.ValidationUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Throwables.throwIfUnchecked;
import static com.wildbeeslabs.sensiblemetrics.diffy.common.utils.ServiceUtils.toUnmodifiableList;

/**
 * Abstract {@link ExecutorService} implementation
 */
public abstract class AbstractExecutorService implements ExecutorService {

    /**
     * Default {@link ExecutorService} instance
     */
    private final ExecutorService delegate;

    /**
     * Default abstract executor service constructor
     *
     * @param delegate - initial input {@link ExecutorService} instance
     */
    protected AbstractExecutorService(final ExecutorService delegate) {
        ValidationUtils.notNull(delegate, "Executor service should not be null");
        this.delegate = delegate;
    }

    /**
     * Wraps a {@code Callable} for submission to the underlying executor. This method is also applied
     * to any {@code Runnable} passed to the default implementation of {@link #wrapTask(Runnable)}.
     */
    protected abstract <T> Callable<T> wrapTask(final Callable<T> callable);

    /**
     * Wraps a {@code Runnable} for submission to the underlying executor. The default implementation
     * delegates to {@link #wrapTask(Callable)}.
     */
    protected Runnable wrapTask(final Runnable command) {
        final Callable<Object> wrapped = wrapTask(Executors.callable(command, null));
        return () -> {
            try {
                wrapped.call();
            } catch (Exception e) {
                throwIfUnchecked(e);
                throw new RuntimeException(e);
            }
        };
    }

    /**
     * Wraps a collection of tasks.
     *
     * @throws NullPointerException if any element of {@code tasks} is null
     */
    private <T> List<Callable<T>> wrapTasks(final Collection<? extends Callable<T>> tasks) {
        return Optional.ofNullable(tasks).orElseGet(Collections::emptyList).stream().map(this::wrapTask).collect(toUnmodifiableList());
    }

    // These methods wrap before delegating.
    @Override
    public final void execute(final Runnable command) {
        this.delegate.execute(wrapTask(command));
    }

    @Override
    public final <T> Future<T> submit(final Callable<T> task) {
        return this.delegate.submit(wrapTask(checkNotNull(task)));
    }

    @Override
    public final Future<?> submit(final Runnable task) {
        return this.delegate.submit(wrapTask(task));
    }

    @Override
    public final <T> Future<T> submit(final Runnable task, final T result) {
        return this.delegate.submit(wrapTask(task), result);
    }

    @Override
    public final <T> List<Future<T>> invokeAll(final Collection<? extends Callable<T>> tasks) throws InterruptedException {
        return this.delegate.invokeAll(wrapTasks(tasks));
    }

    @Override
    public final <T> List<Future<T>> invokeAll(final Collection<? extends Callable<T>> tasks, long timeout, final TimeUnit unit) throws InterruptedException {
        return this.delegate.invokeAll(wrapTasks(tasks), timeout, unit);
    }

    @Override
    public final <T> T invokeAny(final Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
        return this.delegate.invokeAny(wrapTasks(tasks));
    }

    @Override
    public final <T> T invokeAny(final Collection<? extends Callable<T>> tasks, long timeout, final TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return this.delegate.invokeAny(wrapTasks(tasks), timeout, unit);
    }

    @Override
    public final void shutdown() {
        this.delegate.shutdown();
    }

    @Override
    public final List<Runnable> shutdownNow() {
        return this.delegate.shutdownNow();
    }

    @Override
    public final boolean isShutdown() {
        return this.delegate.isShutdown();
    }

    @Override
    public final boolean isTerminated() {
        return this.delegate.isTerminated();
    }

    @Override
    public final boolean awaitTermination(long timeout, final TimeUnit unit) throws InterruptedException {
        return this.delegate.awaitTermination(timeout, unit);
    }
}
