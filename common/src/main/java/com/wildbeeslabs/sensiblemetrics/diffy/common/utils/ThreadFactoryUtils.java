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
package com.wildbeeslabs.sensiblemetrics.diffy.common.utils;

import com.google.common.base.Supplier;
import lombok.experimental.UtilityClass;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Simple utility class to create thread factories
 */
@UtilityClass
public class ThreadFactoryUtils {
    /**
     * Default {@link ThreadFactory} instance
     */
    private static final ThreadFactory INSTANCE = Executors.defaultThreadFactory();

    /**
     * Create a new {@link ThreadFactory} that produces daemon threads with a given name format.
     *
     * @param messageFormat String format: for example foo-%d
     * @return a new {@link ThreadFactory}
     */
    public static ThreadFactory withName(final String messageFormat) {
        return new ThreadFactory() {
            /**
             * Default {@link AtomicLong} counter
             */
            private final AtomicLong count = new AtomicLong(0);

            @Override
            public Thread newThread(final Runnable runnable) {
                final Thread t = INSTANCE.newThread(runnable);
                t.setDaemon(true);
                t.setName(String.format(messageFormat, this.count.getAndIncrement()));
                return t;
            }
        };
    }

    public static <T> Callable<T> threadRenaming(final Callable<T> task, final Supplier<String> nameSupplier) {
        ValidationUtils.notNull(task, "Callable task should not be null");
        ValidationUtils.notNull(nameSupplier, "Name supplier should not be null");
        return () -> {
            final Thread currentThread = Thread.currentThread();
            final String oldName = currentThread.getName();
            final boolean restoreName = trySetName(nameSupplier.get(), currentThread);
            try {
                return task.call();
            } finally {
                if (restoreName) {
                    trySetName(oldName, currentThread);
                }
            }
        };
    }

    /**
     * Wraps the given runnable such that for the duration of {@link Runnable#run} the thread that is
     * running with have the given name.
     *
     * @param task         The Runnable to wrap
     * @param nameSupplier The supplier of thread names, {@link Supplier#get get} will be called once
     *                     for each invocation of the wrapped callable.
     */
    public static Runnable threadRenaming(final Runnable task, final Supplier<String> nameSupplier) {
        ValidationUtils.notNull(task, "Runnable task should not be null");
        ValidationUtils.notNull(nameSupplier, "Name supplier should not be null");
        return () -> {
            final Thread currentThread = Thread.currentThread();
            final String oldName = currentThread.getName();
            final boolean restoreName = trySetName(nameSupplier.get(), currentThread);
            try {
                task.run();
            } finally {
                if (restoreName) {
                    trySetName(oldName, currentThread);
                }
            }
        };
    }

    /**
     * Tries to set name of the given {@link Thread}, returns true if successful.
     */
    private static boolean trySetName(final String threadName, final Thread currentThread) {
        try {
            currentThread.setName(threadName);
            return true;
        } catch (SecurityException e) {
            return false;
        }
    }

    /**
     * Wraps input {@code T} value in {@link Callable}
     *
     * @param <T>   type of value to be wrapped
     * @param value - initial input {@code T} value
     * @return {@link Callable}
     */
    public static <T> Callable<T> wrap(final T value) {
        return () -> value;
    }
}
