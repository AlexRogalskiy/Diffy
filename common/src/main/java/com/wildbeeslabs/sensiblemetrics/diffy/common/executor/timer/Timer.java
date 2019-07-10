package com.wildbeeslabs.sensiblemetrics.diffy.common.executor.timer;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.function.LongSupplier;

import static java.util.Objects.requireNonNull;

/**
 * Timer implementation for measuring execution durations.
 *
 * @author <a href="mailto:franz.wilhelmstoetter@gmail.com">Franz Wilhelmstötter</a>
 * @version 3.1
 * @since 3.0
 */
public final class Timer {

    /**
     * This constants holds the number of nano seconds of one second.
     */
    public static final long NANOS_PER_SECOND = 1_000_000_000;

    private final LongSupplier _nanoClock;

    private long _start;
    private long _stop;

    private Timer(final LongSupplier nanoClock) {
        _nanoClock = requireNonNull(nanoClock);
    }

    /**
     * Start the timer.
     *
     * @return {@code this} timer, for method chaining
     */
    public Timer start() {
        _start = _nanoClock.getAsLong();
        return this;
    }

    /**
     * Stop the timer.
     *
     * @return {@code this} timer, for method chaining
     */
    public Timer stop() {
        _stop = _nanoClock.getAsLong();
        return this;
    }

    /**
     * Return the duration between two consecutive {@link #start()} and
     * {@link #stop()} calls.
     *
     * @return the duration between two {@code start} and {@code stop} calls
     */
    public Duration getTime() {
        return Duration.ofNanos(_stop - _start);
    }

    /**
     * Return an new timer object which uses the given clock for measuring the
     * execution time.
     *
     * @param clock the clock used for measuring the execution time
     * @return a new timer
     */
    public static Timer of(final Clock clock) {
        requireNonNull(clock);
        return clock instanceof NanoClock
            ? new Timer(System::nanoTime)
            : new Timer(() -> nanos(clock));
    }

    private static long nanos(final Clock clock) {
        final Instant now = clock.instant();
        return now.getEpochSecond() * NANOS_PER_SECOND + now.getNano();
    }

    /**
     * Return an new timer object with the default clock implementation.
     *
     * @return a new timer
     */
    public static Timer of() {
        return new Timer(System::nanoTime);
    }

}
