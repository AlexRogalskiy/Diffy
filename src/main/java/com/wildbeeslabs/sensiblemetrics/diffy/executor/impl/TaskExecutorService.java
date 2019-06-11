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
package com.wildbeeslabs.sensiblemetrics.diffy.executor.impl;

import com.wildbeeslabs.sensiblemetrics.diffy.exception.BadOperationException;
import com.wildbeeslabs.sensiblemetrics.diffy.exception.ExecutionOperationException;
import com.wildbeeslabs.sensiblemetrics.diffy.exception.InvalidParameterException;
import com.wildbeeslabs.sensiblemetrics.diffy.exception.TimeoutOperationException;
import com.wildbeeslabs.sensiblemetrics.diffy.executor.configuration.TaskExecutorConfiguration;
import com.wildbeeslabs.sensiblemetrics.diffy.executor.handler.DefaultRejectedExecutionHandler;
import com.wildbeeslabs.sensiblemetrics.diffy.executor.iface.Executable;
import com.wildbeeslabs.sensiblemetrics.diffy.executor.iface.ThrowingConsumer;
import com.wildbeeslabs.sensiblemetrics.diffy.executor.iface.ThrowingSupplier;
import com.wildbeeslabs.sensiblemetrics.diffy.executor.property.TaskExecutorProperty;
import lombok.experimental.UtilityClass;

import java.time.Duration;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.*;

/**
 * Task executor service implementation
 */
@UtilityClass
public class TaskExecutorService {

    public static final String DEFAULT_PROPERTY_FILE = "application.yml";
    /**
     * Default {@link ThreadPoolExecutor} instance
     */
    public static final ThreadPoolExecutor INSTANCE = getExecutor(DEFAULT_PROPERTY_FILE);

    /**
     * Returns default {@link ThreadPoolExecutor} by input parameters
     *
     * @param handler - initial input {@link RejectedExecutionHandler}
     * @return {@link ThreadPoolExecutor}
     * @throws NullPointerException if task executor property is {@code null}
     */
    public static ThreadPoolExecutor getExecutor(final TaskExecutorProperty property, final RejectedExecutionHandler handler) {
        Objects.requireNonNull(property, "Task executor property should not be null");
        final ThreadPoolExecutor executor = new ThreadPoolExecutor(
            property.getExecutor().getCorePoolSize(),
            property.getExecutor().getMaxPoolSize(),
            property.getExecutor().getKeepAliveTimeout(),
            TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<>(property.getExecutor().getQueueSize()),
            Optional.ofNullable(handler).orElseGet(() -> new ThreadPoolExecutor.CallerRunsPolicy()));
        return executor;
    }

    /**
     * Returns default {@link ThreadPoolExecutor} by input {@link ThreadFactory}
     *
     * @param threadFactory - initial input {@link ThreadFactory}
     * @return {@link ThreadPoolExecutor}
     * @throws NullPointerException if threadFactory is {@code null}
     */
    public static ThreadPoolExecutor getExecutor(final ThreadFactory threadFactory) {
        Objects.requireNonNull(threadFactory, "Thread factory should not be null");
        final ThreadPoolExecutor executor = getExecutor(DEFAULT_PROPERTY_FILE);
        executor.setThreadFactory(threadFactory);
        return executor;
    }

    /**
     * Returns default {@link ThreadPoolExecutor}
     *
     * @return {@link ThreadPoolExecutor}
     */
    public static ThreadPoolExecutor getExecutor(final String fileName) {
        final TaskExecutorProperty property = TaskExecutorConfiguration.getProperty(fileName);
        return getExecutor(property, new DefaultRejectedExecutionHandler());
    }

    /**
     * Executes input {@link ThrowingSupplier} task with corresponding {@link ThrowingConsumer} by {@link ExecutorService} and {@link Duration} timeout
     *
     * @param timeout  - initial input {@link Duration} timeout
     * @param consumer - initial input {@link ThrowingConsumer} task
     * @param supplier - initial input {@link ThrowingSupplier} task
     */
    public static <T, E extends Throwable> void execute(final Duration timeout, final ThrowingConsumer<T, E> consumer, final ThrowingSupplier<T, E> supplier) {
        final ExecutorService executorService = Executors.newSingleThreadExecutor();
        try {
            execute(timeout, consumer, supplier, executorService);
        } finally {
            executorService.shutdownNow();
        }
    }

    /**
     * Executes input {@link Executable} task by {@link Duration} timeout
     *
     * @param timeout    - initial input {@link Duration} timeout
     * @param executable - initial input {@link Executable} task
     */
    public static void execute(final Duration timeout, final Executable executable) {
        final ExecutorService executorService = Executors.newSingleThreadExecutor();
        try {
            executeSupplier(timeout, () -> {
                executable.execute();
                return null;
            }, executorService);
        } finally {
            executorService.shutdownNow();
        }
    }

    /**
     * Executes input {@link Runnable} task by {@link Duration} timeout
     *
     * @param timeout  - initial input {@link Duration} timeout
     * @param runnable - initial input {@link Runnable} task
     */
    public static void execute(final Duration timeout, final Runnable runnable) {
        final ExecutorService executorService = Executors.newSingleThreadExecutor();
        try {
            executeRunnable(timeout, runnable, executorService);
        } finally {
            executorService.shutdownNow();
        }
    }

    /**
     * Executes input {@link ThrowingSupplier} task by {@link Duration} timeout
     *
     * @param <T>      type of executable task result
     * @param timeout  - initial input {@link Duration} timeout
     * @param supplier - initial input {@link ThrowingSupplier} task
     * @return {@link ThrowingSupplier} task result {@code T}
     */
    public static <T, E extends Throwable> T execute(final Duration timeout, final ThrowingSupplier<T, E> supplier) {
        final ExecutorService executorService = Executors.newSingleThreadExecutor();
        try {
            return executeSupplier(timeout, supplier, executorService);
        } finally {
            executorService.shutdownNow();
        }
    }

    /**
     * Executes input {@link Callable} task by {@link Duration} timeout
     *
     * @param <T>      type of executable task result
     * @param timeout  - initial input {@link Duration} timeout
     * @param callable - initial input {@link Callable} task
     * @return {@link Callable} task result {@code T}
     */
    public static <T> T execute(final Duration timeout, final Callable<T> callable) {
        final ExecutorService executorService = Executors.newSingleThreadExecutor();
        try {
            return executeCallable(timeout, callable, executorService);
        } finally {
            executorService.shutdownNow();
        }
    }

    /**
     * Executes input {@link Executable} task by {@link ExecutorService} and {@link Duration} timeout
     *
     * @param timeout    - initial input {@link Duration} timeout
     * @param executable - initial input {@link Executable} task
     * @param executor   - initial input {@link ExecutorService}
     */
    public static void execute(final Duration timeout, final Executable executable, final ExecutorService executor) {
        executeSupplier(timeout, () -> {
            executable.execute();
            return null;
        }, executor);
    }

    /**
     * Executes input {@link Runnable} task by {@link ExecutorService} and {@link Duration} timeout
     *
     * @param timeout  - initial input {@link Duration} timeout
     * @param runnable - initial input {@link Runnable} task
     * @param executor - initial input {@link ExecutorService}
     */
    public static void executeRunnable(final Duration timeout, final Runnable runnable, final ExecutorService executor) {
        execute(timeout, executor.submit(runnable));
    }

    /**
     * Executes input {@link Callable} task by {@link ExecutorService} and {@link Duration} timeout
     *
     * @param <T>      type of executable task result
     * @param timeout  - initial input {@link Duration} timeout
     * @param callable - initial input {@link Callable} task
     * @param executor - initial input {@link ExecutorService}
     * @return {@link Callable} task result {@code T}
     */
    public static <T> T executeCallable(final Duration timeout, final Callable<T> callable, final ExecutorService executor) {
        return execute(timeout, executor.submit(callable));
    }

    /**
     * Executes input {@link ThrowingSupplier} task by {@link ExecutorService} and {@link Duration} timeout
     *
     * @param <T>      type of executable task result
     * @param timeout  - initial input {@link Duration} timeout
     * @param supplier - initial input {@link ThrowingSupplier} task
     * @param executor - initial input {@link ExecutorService}
     * @return {@link ThrowingSupplier} task result {@code T}
     */
    private static <T, E extends Throwable> T executeSupplier(final Duration timeout, final ThrowingSupplier<T, E> supplier, final ExecutorService executor) {
        final Callable<T> callable = () -> {
            try {
                return supplier.get();
            } catch (Throwable throwable) {
                throw InvalidParameterException.throwError(String.format("ERROR: cannot operate on input supplier = {%s}", supplier), throwable);
            }
        };
        return execute(timeout, executor.submit(callable));
    }

    /**
     * Executes input {@link ThrowingConsumer} task by {@link ExecutorService} and {@link Duration} timeout
     *
     * @param <T>      type of executable task result
     * @param timeout  - initial input {@link Duration} timeout
     * @param consumer - initial input {@link ThrowingConsumer} task
     * @param supplier - initial input {@link ThrowingSupplier} task
     * @param executor - initial input {@link ExecutorService}
     * @return {@link ThrowingConsumer} task result {@code T}
     */
    private static <T, E extends Throwable> void execute(final Duration timeout, final ThrowingConsumer<T, E> consumer, final ThrowingSupplier<T, E> supplier, final ExecutorService executor) {
        final Runnable runnable = () -> {
            try {
                consumer.accept(supplier.get());
            } catch (Throwable throwable) {
                throw InvalidParameterException.throwError(String.format("ERROR: cannot operate on input consumer = {%s}, supplier = {%s}", consumer, supplier), throwable);
            }
        };
        execute(timeout, executor.submit(runnable));
    }

    /**
     * Executes input {@link ThrowingSupplier} task by {@link ExecutorService} and {@link Duration} timeout
     *
     * @param <T>     type of executable task result
     * @param timeout - initial input {@link Duration} timeout
     * @param future  - initial input {@link Future} task
     * @return {@link Future} task result {@code T}
     * @throws NullPointerException timeout is {@code null}
     */
    private static <T> T execute(final Duration timeout, final Future<T> future) {
        Objects.requireNonNull(timeout, "Timeout should not be null");
        long timeoutInMillis = timeout.toMillis();
        try {
            return future.get(timeoutInMillis, TimeUnit.MILLISECONDS);
        } catch (TimeoutException ex) {
            TimeoutOperationException.throwTimeoutError(String.format("ERROR: execution timed out on target = {%s} after = {%s} ms", future, timeoutInMillis), ex);
        } catch (ExecutionException ex) {
            ExecutionOperationException.throwExecutionError(String.format("ERROR: cannot operate on executor service, message = {%s}", ex.getMessage()), ex);
        } catch (Throwable ex) {
            BadOperationException.throwError(String.format("ERROR: cannot operate on future = {%s}, message = {%s}", future, ex.getMessage()), ex);
        }
        return null;
    }
}
