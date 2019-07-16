package com.wildbeeslabs.sensiblemetrics.diffy.common.executor.iface;

import com.wildbeeslabs.sensiblemetrics.diffy.common.utils.ValidationUtils;
import lombok.NonNull;

import static com.wildbeeslabs.sensiblemetrics.diffy.common.utils.ServiceUtils.doThrow;

/**
 * Throwing {@link Executor} interface declaration
 *
 * @param <E> type of throwable value
 */
@FunctionalInterface
public interface ThrowingExecutor<E extends Throwable> extends Executor {

    /**
     * Executes any operation safely
     */
    @Override
    default void execute() {
        try {
            this.executeOrThrow();
        } catch (final Throwable t) {
            doThrow(t);
        }
    }

    /**
     * Executes any operation
     *
     * @throws {@code E} if operation produces exception
     */
    void executeOrThrow() throws E;

    /**
     * Executes input {@link ThrowingExecutor} operator
     *
     * @param <E>      type of throwable value
     * @param executor - initial input {@link ThrowingExecutor} operator
     * @throws IllegalArgumentException if consumer is {@code null}
     */
    static <E extends Throwable> void executeOrThrow(final ThrowingExecutor<E> executor) {
        ValidationUtils.notNull(executor, "Executor should not be null");
        try {
            executor.executeOrThrow();
        } catch (Throwable t) {
            throw new IllegalArgumentException(String.format("ERROR: cannot operate on executor = {%s}, message = {%s}", executor, t.getMessage()), t);
        }
    }

    /**
     * Wraps input {@link ThrowingExecutor} by input exception {@link Class}
     *
     * @param <E>      type of throwable value
     * @param executor - initial input {@link ThrowingExecutor}
     * @param clazz    - initial input {@link Class}
     * @return {@link ThrowingExecutor}
     * @throws IllegalArgumentException if executor is {@code null}
     * @throws IllegalArgumentException if clazz is {@code null}
     */
    @NonNull
    static <E extends Exception> ThrowingExecutor<E> wrapConsumer(final ThrowingExecutor<E> executor, final Class<E> clazz) {
        ValidationUtils.notNull(executor, "Executor should not be null");
        ValidationUtils.notNull(clazz, "Class should not be null");

        return () -> {
            try {
                executor.executeOrThrow();
            } catch (Throwable ex) {
                try {
                    doThrow(clazz.cast(ex));
                } catch (ClassCastException eex) {
                    throw new RuntimeException(ex);
                }
            }
        };
    }
}
