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
package com.wildbeeslabs.sensiblemetrics.diffy.processor.service;

import com.wildbeeslabs.sensiblemetrics.diffy.common.exception.BadOperationException;
import com.wildbeeslabs.sensiblemetrics.diffy.common.interfaces.Processor;
import com.wildbeeslabs.sensiblemetrics.diffy.processor.interfaces.ThrowingProcessor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Objects;
import java.util.concurrent.*;

/**
 * <p>
 * Definition of an interface for a wrapper around a calculation that takes a
 * single parameter and returns a result. The results for the calculation will
 * be cached for future requests.
 * </p>
 * <p>
 * This is not a fully functional cache, there is no way of limiting or removing
 * results once they have been generated. However, it is possible to get the
 * implementation to regenerate the result for a given parameter, if an error
 * was thrown during the previous calculation, by setting the option during the
 * construction of the class. If this is not set the class will return the
 * cached exception.
 * </p>
 * <p>
 * Thanks should go to Brian Goetz, Tim Peierls and the members of JCP JSR-166
 * Expert Group for coming up with the original implementation of the class. It
 * was also published within Java Concurrency in Practice as a sample.
 * </p>
 *
 * @param <I> the type of the input to the calculation
 * @param <O> the type of the output of the calculation
 * @since 3.6
 */
@Data
@EqualsAndHashCode
@ToString
public class MemoizerProcessor<I, O> implements ThrowingProcessor<I, O, BadOperationException> {

    private final ConcurrentMap<I, Future<O>> cache = new ConcurrentHashMap<>();
    private final Processor<I, O> computable;
    private final boolean recalculate;

    /**
     * <p>
     * Constructs a MemoizerProcessor for the provided Computable calculation.
     * </p>
     * <p>
     * If a calculation is thrown an exception for any reason, this exception
     * will be cached and returned for all future calls with the provided
     * parameter.
     * </p>
     *
     * @param computable the computation whose results should be memorized
     */
    public MemoizerProcessor(final Processor<I, O> computable) {
        this(computable, false);
    }

    /**
     * <p>
     * Constructs a MemoizerProcessor for the provided Computable calculation, with the
     * option of whether a Computation that experiences an error should
     * recalculate on subsequent calls or return the same cached exception.
     * </p>
     *
     * @param computable  the computation whose results should be memorized
     * @param recalculate determines whether the computation should be recalculated on
     *                    subsequent calls if the previous call failed
     */
    public MemoizerProcessor(final Processor<I, O> computable, final boolean recalculate) {
        this.computable = computable;
        this.recalculate = recalculate;
    }

    /**
     * <p>
     * This method will return the result of the calculation and cache it, if it
     * has not previously been calculated.
     * </p>
     * <p>
     * This cache will also cache exceptions that occur during the computation
     * if the {@code recalculate} parameter is the constructor was set to
     * {@code false}, or not set. Otherwise, if an exception happened on the
     * previous calculation, the method will attempt again to generate a value.
     * </p>
     *
     * @param value the argument for the calculation
     * @return the result of the calculation
     * @throws InterruptedException thrown if the calculation is interrupted
     */
    @Override
    public O processOrThrow(final I value) throws BadOperationException {
        while (true) {
            Future<O> future = this.cache.get(value);
            if (future == null) {
                final Callable<O> eval = () -> (O) this.computable.process(value);
                final FutureTask<O> futureTask = new FutureTask<>(eval);
                future = this.cache.putIfAbsent(value, futureTask);
                if (Objects.isNull(future)) {
                    future = futureTask;
                    futureTask.run();
                }
            }
            try {
                return future.get();
            } catch (final CancellationException e) {
                this.cache.remove(value, future);
            } catch (final ExecutionException e) {
                if (this.recalculate) {
                    this.cache.remove(value, future);
                }
                throw launderException(e.getCause());
            } catch (InterruptedException e) {
                throw new BadOperationException(String.format("ERROR: cannot process input task = {%s}", value), e);
            }
        }
    }

    /**
     * <p>
     * This method launders a Throwable to either a RuntimeException, Error or
     * any other Exception wrapped in an IllegalStateException.
     * </p>
     *
     * @param throwable the throwable to laundered
     * @return a RuntimeException, Error or an IllegalStateException
     */
    private RuntimeException launderException(final Throwable throwable) {
        if (throwable instanceof RuntimeException) {
            return (RuntimeException) throwable;
        } else if (throwable instanceof Error) {
            throw (Error) throwable;
        }
        throw new IllegalStateException("Unchecked exception", throwable);
    }
}
