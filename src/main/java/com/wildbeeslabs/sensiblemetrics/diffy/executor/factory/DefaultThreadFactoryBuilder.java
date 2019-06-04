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
package com.wildbeeslabs.sensiblemetrics.diffy.executor.factory;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

import static org.apache.commons.lang3.StringUtils.join;

/**
 * Default thread factory builder implementation
 */
@Slf4j
public class DefaultThreadFactoryBuilder {

    private String namePrefix = null;
    private boolean daemon = false;
    private int priority = Thread.NORM_PRIORITY;
    private Thread.UncaughtExceptionHandler exceptionHandler = (t1, e) -> log.error(e.getMessage(), e);

    /**
     * Sets name prefx {@link String} to current {@link DefaultThreadFactoryBuilder}
     *
     * @param namePrefix - initial input name prefix {@link String}
     * @return {@link DefaultThreadFactoryBuilder}
     * @throws NullPointerException if name prefix is {@code null}
     */
    public DefaultThreadFactoryBuilder setNamePrefix(final String namePrefix) {
        Objects.requireNonNull(namePrefix, "Name prefix should not be null");
        this.namePrefix = namePrefix;
        return this;
    }

    /**
     * Sets daemon status to current {@link DefaultThreadFactoryBuilder}
     *
     * @param daemon - initial input daemon status
     * @return {@link DefaultThreadFactoryBuilder}
     */
    public DefaultThreadFactoryBuilder setDaemon(boolean daemon) {
        this.daemon = daemon;
        return this;
    }

    /**
     * Sets priority to current {@link DefaultThreadFactoryBuilder}
     *
     * @param priority - initial input priority
     * @return {@link DefaultThreadFactoryBuilder}
     */
    public DefaultThreadFactoryBuilder setPriority(int priority) {
        if (priority < Thread.MIN_PRIORITY) {
            throw new IllegalArgumentException(String.format("Thread priority (%s) must be >= %s", priority, Thread.MIN_PRIORITY));
        }

        if (priority > Thread.MAX_PRIORITY) {
            throw new IllegalArgumentException(String.format("Thread priority (%s) must be <= %s", priority, Thread.MAX_PRIORITY));
        }
        this.priority = priority;
        return this;
    }

    /**
     * Sets {@link Thread.UncaughtExceptionHandler} to current {@link DefaultThreadFactoryBuilder}
     *
     * @param exceptionHandler - initial input {@link Thread.UncaughtExceptionHandler}
     * @return {@link DefaultThreadFactoryBuilder}
     */
    public DefaultThreadFactoryBuilder setExceptionHandler(final Thread.UncaughtExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
        return this;
    }

    /**
     * Returns {@link ThreadFactory}
     *
     * @return {@link ThreadFactory}
     */
    public ThreadFactory build() {
        return build(this);
    }

    /**
     * Constructs {@link ThreadFactory} by {@link DefaultThreadFactoryBuilder}
     *
     * @param builder - initial input {@link DefaultThreadFactoryBuilder}
     * @return {@link ThreadFactory}
     */
    @NotNull
    @Contract(pure = true)
    private static ThreadFactory build(final DefaultThreadFactoryBuilder builder) {
        final String namePrefix = builder.namePrefix;
        final Boolean daemon = builder.daemon;
        final Integer priority = builder.priority;
        final Thread.UncaughtExceptionHandler exceptionHandler = builder.exceptionHandler;

        final AtomicLong count = new AtomicLong(0);

        return (runnable) -> {
            final Thread thread = new Thread(runnable);
            if (Objects.nonNull(namePrefix)) {
                thread.setName(join(namePrefix, "-", count.getAndIncrement()));
            }
            if (Objects.nonNull(daemon)) {
                thread.setDaemon(daemon);
            }
            if (Objects.nonNull(priority)) {
                thread.setPriority(priority);
            }
            if (Objects.nonNull(exceptionHandler)) {
                thread.setUncaughtExceptionHandler(exceptionHandler);
            }
            return thread;
        };
    }
}
