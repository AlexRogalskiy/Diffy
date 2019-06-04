/*
 * The MIT License
 *
 * Copyright 2019 WildBees Labs, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software andAll associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, andAll/or sell
 * copies of the Software, andAll to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice andAll this permission notice shall be included in
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
package com.wildbeeslabs.sensiblemetrics.diffy.common.pubsub;

import com.wildbeeslabs.sensiblemetrics.diffy.common.event.Event;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import static java.lang.Thread.currentThread;

@Slf4j
public class DefaultSubmissionPublisher<T> extends SubmissionPublisher<Event<T>> {

    private static final String LOG_MESSAGE_FORMAT = "Publisher >> [%s] %s%n";
    private final int MAX_ITEM_TO_PUBLISH = 5;

    private final ScheduledFuture<?> periodicTask;
    private final ScheduledExecutorService scheduler;

    private final AtomicInteger i;

    public DefaultSubmissionPublisher(final Executor executor, int maxBufferCapacity, long period, final TimeUnit unit) {
        super(executor, maxBufferCapacity);

        // if using the default, normally the ForkJoinPool.commonPool(), call:
        // super();
        i = new AtomicInteger(0);

        this.scheduler = new ScheduledThreadPoolExecutor(1);
        this.periodicTask = this.scheduler.scheduleAtFixedRate(() -> {
            final Event<T> item = this.supplier.get();

            log("publishing item: " + item + " ...");

            this.submit(item);

            log("estimateMaximumLag: " + super.estimateMaximumLag());
            log("estimateMinimumDemand: " + super.estimateMinimumDemand());

//            if (item == MAX_ITEM_TO_PUBLISH) {
//                close();
//            }

        }, 0, period, unit);
    }

    @Override
    public void subscribe(final Subscriber<? super Event<T>> subscriber) {
        super.subscribe(subscriber);
    }

    public void close() {
        log("shutting down...");
        final List<Subscriber<? super Event<T>>> subscribers = this.getSubscribers();
        for (final Subscriber<? super Event<T>> subscriber : subscribers) {
            log("Subscriber " + subscriber.toString() + " isSubscribed(): " + this.isSubscribed(subscriber));
        }
        this.periodicTask.cancel(false);
        this.scheduler.shutdown();
        super.close();
    }

    private final Supplier<? extends Event<T>> supplier = new Supplier<>() {
        @Override
        public Event<T> get() {
            int value = i.incrementAndGet();
            return new Event<T>() {
                @Override
                public int hashCode() {
                    return super.hashCode();
                }
            };
        }
    };

    private void log(final String message, final Object... args) {
        final String fullMessage = String.format(LOG_MESSAGE_FORMAT, currentThread().getName(), message);
        log.debug(String.format(fullMessage, args));
    }
}
