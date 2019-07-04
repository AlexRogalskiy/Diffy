package com.wildbeeslabs.sensiblemetrics.diffy.common.helpers.impl;

import com.wildbeeslabs.sensiblemetrics.diffy.common.annotation.Factory;
import com.wildbeeslabs.sensiblemetrics.diffy.common.utils.ValidationUtils;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * Optional {@link Consumer} implementation
 *
 * @param <T> type of optional item
 *            <p>
 *            Consumer<Optional<Integer>> c=OptionalConsumer.of(System.out::println, ()->{System.out.println("Not fit");});
 *            IntStream.range(0, 100).boxed().map(i->Optional.of(i).filter(j->j%2==0)).forEach(c);
 *            </p>
 */
public class OptionalConsumer<T> implements Consumer<Optional<T>> {
    /**
     * Default empty {@link Consumer} instance
     */
    private static final Consumer DEFAULT_EMPTY_CONSUMER = o -> {
    };
    /**
     * Default empty {@link Runnable} instance
     */
    private static final Runnable DEFAULT_EMPTY_RUNNABLE = () -> {
    };

    /**
     * Default {@link Optional} value
     */
    private final Optional<T> optional;
    /**
     * Default {@link Consumer} value
     */
    private final Consumer<? super T> consumer;
    /**
     * Default {@link Runnable} value
     */
    private final Runnable runnable;

    /**
     * Default optional consumer constructor by input arguments
     *
     * @param optional - initial input {@code T} argument
     */
    private OptionalConsumer(final T value) {
        this(Optional.ofNullable(value));
    }

    /**
     * Default optional consumer constructor by input arguments
     *
     * @param optional - initial input {@link Optional} argument
     */
    private OptionalConsumer(final Optional<T> optional) {
        this(optional, null, null);
    }

    /**
     * Default optional consumer constructor by input arguments
     *
     * @param optional - initial input {@link Optional} argument
     * @param optional - initial input {@link Consumer} argument
     * @param optional - initial input {@link Runnable} argument
     */
    private OptionalConsumer(final Optional<T> optional, final Consumer<? super T> consumer, final Runnable runnable) {
        this.optional = Optional.ofNullable(optional).orElseGet(Optional::empty);
        this.consumer = Optional.ofNullable(consumer).orElse(DEFAULT_EMPTY_CONSUMER);
        this.runnable = Optional.ofNullable(runnable).orElse(DEFAULT_EMPTY_RUNNABLE);
    }

    public OptionalConsumer<T> ifPresent(final Consumer<? super T> consumer) {
        ValidationUtils.notNull(consumer, "Consumer should not be null");
        this.optional.ifPresent(consumer);
        return this;
    }

    public OptionalConsumer<T> ifNotPresent(final Runnable runnable) {
        ValidationUtils.notNull(runnable, "Runnable should not be null");
        if (!this.optional.isPresent()) {
            runnable.run();
        }
        return this;
    }

    public void acceptOrElse(final Consumer<? super T> consumer, final Runnable runnable) {
        ValidationUtils.notNull(consumer, "Consumer should not be null");
        ValidationUtils.notNull(runnable, "Runnable should not be null");

        if (this.optional.isPresent()) {
            consumer.accept(this.optional.get());
        } else {
            runnable.run();
        }
    }

    @Override
    public void accept(final Optional<T> optional) {
        if (optional.isPresent()) {
            this.consumer.accept(optional.get());
        } else {
            this.runnable.run();
        }
    }

    @Factory
    public static <T> OptionalConsumer<T> of(final Optional<T> optional) {
        return new OptionalConsumer<>(optional);
    }

    @Factory
    public static <T> OptionalConsumer<T> of(final T value) {
        return of(Optional.of(value));
    }

    @Factory
    public static <T> OptionalConsumer<T> ofNullable(final T value) {
        return of(Optional.ofNullable(value));
    }

    @Factory
    public static <T> OptionalConsumer<T> ofNullable(final T value, final Consumer<? super T> consumer, final Runnable runnable) {
        return new OptionalConsumer<>(Optional.ofNullable(value), consumer, runnable);
    }

    public static <T> void ifPresentOrElse(final Optional<T> optional, final Consumer<? super T> consumer, final Runnable runnable) {
        ValidationUtils.notNull(optional, "Optional should not be null");
        ValidationUtils.notNull(consumer, "Consumer should not be null");
        ValidationUtils.notNull(runnable, "Runnable should not be null");

        if (optional.isPresent()) {
            consumer.accept(optional.get());
        } else {
            runnable.run();
        }
    }
}
