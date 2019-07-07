package com.wildbeeslabs.sensiblemetrics.diffy.common.executor.impl;

import com.google.common.collect.ImmutableList;
import com.wildbeeslabs.sensiblemetrics.diffy.common.utils.ValidationUtils;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

/**
 * An abstract {@code ExecutorService} that allows subclasses to
 * {@linkplain #wrapTask(Callable) wrap} tasks before they are submitted
 * to the underlying executor.
 *
 * <p>Note that task wrapping may occur even if the task is never executed.
 *
 * <p>For delegation without task-wrapping, see
 *
 * @author Chris Nokleberg
 */
public abstract class WrappingExecutorService implements ExecutorService {
    private final ExecutorService delegate;

    protected WrappingExecutorService(ExecutorService delegate) {
        ValidationUtils.notNull(delegate);
        this.delegate = delegate;
    }

    /**
     * Wraps a {@code Callable} for submission to the underlying executor. This
     * method is also applied to any {@code Runnable} passed to the default
     * implementation of
     */
    protected abstract <T> Callable<T> wrapTask(Callable<T> callable);

    /**
     * Wraps a {@code Runnable} for submission to the underlying executor. The
     * default implementation delegates to {@link #wrapTask(Callable)}.
     */
    protected Runnable wrapTask(Runnable command) {
        final Callable<Object> wrapped = wrapTask(
            Executors.callable(command, null));
        return new Runnable() {
            @Override
            public void run() {
                try {
                    wrapped.call();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

    /**
     * Wraps a collection of tasks.
     *
     * @throws NullPointerException if any element of {@code tasks} is null
     */
    private final <T> ImmutableList<Callable<T>> wrapTasks(final Collection<? extends Callable<T>> tasks) {
        final ImmutableList.Builder<Callable<T>> builder = ImmutableList.builder();
        for (final Callable<T> task : tasks) {
            builder.add(wrapTask(task));
        }
        return builder.build();
    }

    // These methods wrap before delegating.
    @Override
    public final void execute(Runnable command) {
        delegate.execute(wrapTask(command));
    }

    @Override
    public final <T> Future<T> submit(Callable<T> task) {
        ValidationUtils.notNull(task);
        return delegate.submit(wrapTask(task));
    }

    @Override
    public final Future<?> submit(Runnable task) {
        return delegate.submit(wrapTask(task));
    }

    @Override
    public final <T> Future<T> submit(Runnable task, T result) {
        return delegate.submit(wrapTask(task), result);
    }

    @Override
    public final <T> List<Future<T>> invokeAll(
        Collection<? extends Callable<T>> tasks) throws InterruptedException {
        return delegate.invokeAll(wrapTasks(tasks));
    }

    @Override
    public final <T> List<Future<T>> invokeAll(
        Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
        throws InterruptedException {
        return delegate.invokeAll(wrapTasks(tasks), timeout, unit);
    }

    @Override
    public final <T> T invokeAny(Collection<? extends Callable<T>> tasks)
        throws InterruptedException, ExecutionException {
        return delegate.invokeAny(wrapTasks(tasks));
    }

    @Override
    public final <T> T invokeAny(
        Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
        throws InterruptedException, ExecutionException, TimeoutException {
        return delegate.invokeAny(wrapTasks(tasks), timeout, unit);
    }

    // The remaining methods just delegate.

    @Override
    public final void shutdown() {
        delegate.shutdown();
    }

    @Override
    public final List<Runnable> shutdownNow() {
        return delegate.shutdownNow();
    }

    @Override
    public final boolean isShutdown() {
        return delegate.isShutdown();
    }

    @Override
    public final boolean isTerminated() {
        return delegate.isTerminated();
    }

    @Override
    public final boolean awaitTermination(long timeout, TimeUnit unit)
        throws InterruptedException {
        return delegate.awaitTermination(timeout, unit);
    }
}
