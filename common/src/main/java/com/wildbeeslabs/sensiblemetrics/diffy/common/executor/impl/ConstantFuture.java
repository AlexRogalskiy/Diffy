package com.wildbeeslabs.sensiblemetrics.diffy.executor.impl;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Constant {@link Future} implementation
 *
 * @param <T> type of future item
 */
public final class ConstantFuture<T> implements Future<T> {
    /**
     * The constant value.
     */
    private final T value;

    /**
     * Creates a new instance of {@code ConstantFuture} and initializes it
     * with the constant value.
     *
     * @param value the value (may be <b>null</b>)
     */
    ConstantFuture(final T value) {
        this.value = value;
    }

    /**
     * {@inheritDoc} This implementation always returns <b>true</b> because
     * the constant object managed by this {@code Future} implementation is
     * always available.
     */
    @Override
    public boolean isDone() {
        return true;
    }

    /**
     * {@inheritDoc} This implementation just returns the constant value.
     */
    @Override
    public T get() {
        return this.value;
    }

    /**
     * {@inheritDoc} This implementation just returns the constant value; it
     * does not block, therefore the timeout has no meaning.
     */
    @Override
    public T get(final long timeout, final TimeUnit unit) {
        return this.value;
    }

    /**
     * {@inheritDoc} This implementation always returns <b>false</b>; there
     * is no background process which could be cancelled.
     */
    @Override
    public boolean isCancelled() {
        return false;
    }

    /**
     * {@inheritDoc} The cancel operation is not supported. This
     * implementation always returns <b>false</b>.
     */
    @Override
    public boolean cancel(final boolean mayInterruptIfRunning) {
        return false;
    }

    /**
     * Returns constant {@link Future} instance
     *
     * @param <T>   type of future item
     * @param value - initial input value {@code T} to be processed
     * @return constant {@link Future} instance
     */
    public static <T> Future<T> constantFuture(final T value) {
        return new ConstantFuture<>(value);
    }
}
