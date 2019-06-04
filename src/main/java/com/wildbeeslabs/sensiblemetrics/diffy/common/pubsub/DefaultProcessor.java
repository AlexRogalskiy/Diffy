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

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow.Processor;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.Thread.currentThread;
import static java.util.concurrent.Executors.newSingleThreadExecutor;

@Slf4j
public class DefaultProcessor<T> implements Processor<Event<T>, String> {

    private static final String LOG_MESSAGE_FORMAT = "Processor >> [%s] %s%n";

    private Subscription publisherSubscription;

    private final ExecutorService executor = Executors.newFixedThreadPool(4);
    private DefaultSubscription subscription;
    private long DEMAND;
    private ConcurrentLinkedQueue<String> dataItems;

    private final CompletableFuture<Void> terminated = new CompletableFuture<>();

    public DefaultProcessor() {
        this.DEMAND = 0;
        this.dataItems = new ConcurrentLinkedQueue<String>();
    }

    public void setDEMAND(long n) {
        this.DEMAND = n;
    }

    @Override
    public void subscribe(final Subscriber<? super String> subscriber) {
        this.subscription = new DefaultSubscription(subscriber, executor);
        subscriber.onSubscribe(this.subscription);
    }

    @Override
    public void onSubscribe(final Subscription subscription) {
        log("Subscribed...");
        this.publisherSubscription = subscription;
        this.requestItems();
    }

    private void requestItems() {
        log("Requesting %d new items...", this.DEMAND);
        this.publisherSubscription.request(this.DEMAND);
    }

    @Override
    public void onNext(final Event<T> item) {
        Objects.requireNonNull(item, "Item should not be null");
        this.dataItems.add("item value = " + item + " after processing");
        log("processing item: [" + item + "] ...");
    }

    @Override
    public void onComplete() {
        log("Complete!");
    }

    @Override
    public void onError(final Throwable t) {
        log("Error >> %s", t);
    }

    private class DefaultSubscription implements Subscription {
        private final ExecutorService executor;
        private Subscriber<? super String> subscriber;
        private AtomicBoolean isCanceled;

        public DefaultSubscription(final Subscriber<? super String> subscriber, final ExecutorService executor) {
            this.executor = executor;
            this.subscriber = subscriber;
            this.isCanceled = new AtomicBoolean(false);
        }

        @Override
        public void request(long n) {
            if (this.isCanceled.get()) {
                return;
            }
            if (n < 0) {
                this.executor.execute(() -> this.subscriber.onError(new IllegalArgumentException()));
            } else if (dataItems.size() > 0) {
                publishItems(n);
            } else if (dataItems.size() == 0) {
                this.subscriber.onComplete();
            }
        }

        private void publishItems(long n) {
            int remainItems = dataItems.size();
            if ((remainItems == n) || (remainItems > n)) {
                for (int i = 0; i < n; i++) {
                    this.executor.execute(() -> {
                        this.subscriber.onNext(dataItems.poll());
                    });
                }
                log("Remaining " + (dataItems.size() - n) + " items to be published to Subscriber!");
            } else if ((remainItems > 0) && (remainItems < n)) {
                for (int i = 0; i < remainItems; i++) {
                    this.executor.execute(() -> {
                        this.subscriber.onNext(dataItems.poll());
                    });
                }
                this.subscriber.onComplete();
            } else {
                log("Processor contains no item!");
            }
        }

        @Override
        public void cancel() {
            this.isCanceled.set(true);
            this.shutdown();
            publisherSubscription.cancel();
        }

        private void shutdown() {
            log("Shut down executor...");
            this.executor.shutdown();
            newSingleThreadExecutor().submit(() -> {
                log("Shutdown complete.");
                terminated.complete(null);
            });
        }
    }

    private void log(final String message, final Object... args) {
        final String fullMessage = String.format(LOG_MESSAGE_FORMAT, currentThread().getName(), message);
        log.debug(String.format(fullMessage, args));
    }
}
