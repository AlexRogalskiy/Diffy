package com.wildbeeslabs.sensiblemetrics.diffy.common.executor.timer;

import java.time.Clock;
import java.time.Duration;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

/**
 * Container class which contains the execution result and the execution time.
 *
 * @author <a href="mailto:franz.wilhelmstoetter@gmail.com">Franz Wilhelmstötter</a>
 * @version 3.0
 * @since 3.0
 */
public final class TimedResult<T> {

    final Duration duration;
    final T result;

    TimedResult(final Duration duration, final T result) {
        this.duration = requireNonNull(duration);
        this.result = requireNonNull(result);
    }

    /**
     * Wraps the given supplier in a supplier which returns a {@code TimedResult}.
     *
     * @param supplier the supplier to wrap
     * @param clock    the clock used for measure the execution time
     * @param <T>      the result type
     * @return the wrapped supplier which returns a {@code TimedResult}
     */
    public static <T> Supplier<TimedResult<T>> of(
        final Supplier<? extends T> supplier,
        final Clock clock
    ) {
        return () -> {
            final Timer timer = Timer.of(clock).start();
            final T result = supplier.get();
            return new TimedResult<>(timer.stop().getTime(), result);
        };
    }

    /**
     * Wraps the given function in a function which returns a
     * {@code TimedResult}.
     *
     * @param function the function to wrap
     * @param clock    the clock used for measure the execution time
     * @param <T>      the functions parameter type
     * @param <R>      the functions return type
     * @return the wrapped function which returns a {@code TimedResult}
     */
    public static <T, R> Function<T, TimedResult<R>> of(
        final Function<? super T, ? extends R> function,
        final Clock clock
    ) {
        return value -> {
            final Timer timer = Timer.of(clock).start();
            final R result = function.apply(value);
            return new TimedResult<>(timer.stop().getTime(), result);
        };
    }

}
