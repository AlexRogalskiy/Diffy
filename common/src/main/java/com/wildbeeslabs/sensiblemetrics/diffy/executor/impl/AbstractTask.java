package com.wildbeeslabs.sensiblemetrics.diffy.executor.impl;

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
     * @param exec the {@code ExecutorService}
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
