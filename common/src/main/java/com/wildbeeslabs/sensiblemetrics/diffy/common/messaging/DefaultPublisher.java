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
package com.wildbeeslabs.sensiblemetrics.diffy.common.messaging;

import com.wildbeeslabs.sensiblemetrics.diffy.common.enumeration.EventType;
import com.wildbeeslabs.sensiblemetrics.diffy.common.event.iface.Event;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.currentThread;
import static java.util.concurrent.Executors.newSingleThreadExecutor;

@Slf4j
public class DefaultPublisher<T> implements Publisher<Event<T>> {

    private static final String LOG_MESSAGE_FORMAT = "Publisher >> [%s] %s%n";

    private final ExecutorService executor = Executors.newFixedThreadPool(4);
    private List<DefaultSubscription> subscriptions = Collections.synchronizedList(new ArrayList<DefaultSubscription>());

    private final CompletableFuture<Void> terminated = new CompletableFuture<>();

    @Override
    public void subscribe(final Subscriber<? super Event<T>> subscriber) {
        final DefaultSubscription subscription = new DefaultSubscription(subscriber, this.executor);
        this.subscriptions.add(subscription);
        subscriber.onSubscribe(subscription);
    }

    public void waitUntilTerminated() throws InterruptedException {
        try {
            this.terminated.get();
        } catch (ExecutionException e) {
            log.error(String.format("ERROR: execution exception, message = {%s}", e.getMessage()), e);
        }
    }

    private class DefaultSubscription implements Subscription {
        private final ExecutorService executor;
        private Subscriber<? super Event<T>> subscriber;
        private final AtomicInteger value;
        private AtomicBoolean isCanceled;

        public DefaultSubscription(final Subscriber<? super Event<T>> subscriber, final ExecutorService executor) {
            this.subscriber = subscriber;
            this.executor = executor;
            this.value = new AtomicInteger();
            this.isCanceled = new AtomicBoolean(false);
        }

        @Override
        public void request(long n) {
            if (this.isCanceled.get()) {
                return;
            }
            if (n < 0) {
                this.executor.execute(() -> this.subscriber.onError(new IllegalArgumentException()));
            } else {
                this.publishItems(n);
            }
        }

        @Override
        public void cancel() {
            this.isCanceled.set(true);
            synchronized (subscriptions) {
                subscriptions.remove(this);
                if (subscriptions.isEmpty()) {
                    this.shutdown();
                }
            }
        }

        private void publishItems(long n) {
            for (int i = 0; i < n; i++) {
                this.executor.execute(() -> {
                    int v = value.incrementAndGet();
                    log("publish item: [" + v + "] ...");
                    this.subscriber.onNext(new Event<>() {

                        /**
                         * Default {@link String} event identifier
                         */
                        private final String eventId = UUID.randomUUID().toString();

                        @Override
                        public String getEventId() {
                            return this.eventId;
                        }

                        @Override
                        public EventType getType() {
                            return EventType.EMPTY_EVENT;
                        }

                        @Override
                        public Instant getTimeStamp() {
                            return Instant.now();
                        }
                    });
                });
            }
        }

        private void shutdown() {
            log("Shut down executor...");
            this.executor.shutdown();
            newSingleThreadExecutor().submit(() -> {
                log("Shutdown complete.");
                terminated.complete(null);
            });
        }

        private void log(final String message, final Object... args) {
            final String fullMessage = String.format(LOG_MESSAGE_FORMAT, currentThread().getName(), message);
            log.debug(String.format(fullMessage, args));
        }
    }
}
