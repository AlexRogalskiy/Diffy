package com.wildbeeslabs.sensiblemetrics.diffy.core.helpers;

import com.wildbeeslabs.sensiblemetrics.diffy.common.annotation.Factory;
import com.wildbeeslabs.sensiblemetrics.diffy.common.executor.iface.Executor;
import com.wildbeeslabs.sensiblemetrics.diffy.common.executor.iface.ThrowingExecutor;
import com.wildbeeslabs.sensiblemetrics.diffy.common.utils.ValidationUtils;
import com.wildbeeslabs.sensiblemetrics.diffy.core.interfaces.ThrowingConsumer;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

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
     * Default empty {@link Executor} instance
     */
    private static final Executor DEFAULT_EMPTY_EXECUTOR = () -> {
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
     * Default {@link Executor} value
     */
    private final Executor executor;

    /**
     * Default optional consumer constructor by input arguments
     *
     * @param value - initial input {@code T} argument
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
     * @param consumer - initial input {@link Consumer} argument
     * @param executor - initial input {@link Executor} argument
     */
    private OptionalConsumer(final Optional<T> optional, final Consumer<? super T> consumer, final Executor executor) {
        this.optional = Optional.ofNullable(optional).orElseGet(Optional::empty);
        this.consumer = Optional.ofNullable(consumer).orElse(DEFAULT_EMPTY_CONSUMER);
        this.executor = Optional.ofNullable(executor).orElse(DEFAULT_EMPTY_EXECUTOR);
    }

    public OptionalConsumer<T> ifPresent(final Consumer<? super T> consumer) {
        ValidationUtils.notNull(consumer, "Consumer should not be null");
        this.optional.ifPresent(consumer);
        return this;
    }

    public OptionalConsumer<T> ifNotPresent(final Executor executor) {
        ValidationUtils.notNull(executor, "Executor should not be null");
        if (!this.optional.isPresent()) {
            executor.execute();
        }
        return this;
    }

    public void acceptOrElse(final Consumer<? super T> consumer, final Executor executor) {
        ValidationUtils.notNull(consumer, "Consumer should not be null");
        ValidationUtils.notNull(executor, "Executor should not be null");

        if (this.optional.isPresent()) {
            consumer.accept(this.optional.get());
        } else {
            executor.execute();
        }
    }

    @Override
    public void accept(final Optional<T> optional) {
        if (optional.isPresent()) {
            this.consumer.accept(optional.get());
        } else {
            this.executor.execute();
        }
    }

    public OptionalConsumer<T> filter(final Predicate<T> predicate) {
        ValidationUtils.notNull(predicate, "Predicate should not be null");
        return of(this.optional.filter(predicate));
    }

    public <U> OptionalConsumer<U> map(final Function<? super T, ? extends U> mapper) {
        ValidationUtils.notNull(mapper, "Function should not be null");
        return of(this.optional.map(mapper));
    }

    public <E extends Throwable> OptionalConsumer<T> ifPresent(final ThrowingConsumer<T, E> consumer) {
        ValidationUtils.notNull(consumer, "Consumer should not be null");
        this.optional.ifPresent(consumer);
        return this;
    }

    public <E extends Throwable> OptionalConsumer<T> ifNotPresent(final ThrowingExecutor<E> executor) {
        ValidationUtils.notNull(executor, "Executor should not be null");
        if (!this.optional.isPresent()) {
            executor.execute();
        }
        return this;
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
    public static <T> OptionalConsumer<T> ofNullable(final T value, final Consumer<? super T> consumer, final Executor executor) {
        return new OptionalConsumer<>(Optional.ofNullable(value), consumer, executor);
    }

    public static <T> void ifPresentOrElse(final Optional<T> optional, final Consumer<? super T> consumer, final Executor executor) {
        ValidationUtils.notNull(optional, "Optional should not be null");
        ValidationUtils.notNull(consumer, "Consumer should not be null");
        ValidationUtils.notNull(executor, "Executor should not be null");

        if (optional.isPresent()) {
            consumer.accept(optional.get());
        } else {
            executor.execute();
        }
    }
}
