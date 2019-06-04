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
import java.util.Random;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

import static java.lang.Thread.currentThread;

@Slf4j
public class DefaultSubscriber<T> implements Subscriber<Event<T>> {

    private static final String LOG_MESSAGE_FORMAT = "Subscriber %s >> [%s] %s%n";

    private static final int DEMAND = 3;
    private static final Random RANDOM = new Random();

    private String name;
    private Subscription subscription;

    private int count;

    public DefaultSubscriber(final String name) {
        this.name = name;
    }

    @Override
    public void onSubscribe(final Subscription subscription) {
        log("Subscribed");
        this.subscription = subscription;
        this.count = DEMAND;
        this.requestItems(DEMAND);
    }

    private void requestItems(int n) {
        log.debug("Requesting %d new items...", n);
        subscription.request(n);
    }

    @Override
    public void onNext(final Event<T> item) {
        if (Objects.nonNull(item)) {
            log(item.toString());
            synchronized (this) {
                this.count--;
                if (count == 0) {
                    if (RANDOM.nextBoolean()) {
                        this.count = DEMAND;
                        requestItems(count);
                    } else {
                        this.count = 0;
                        log("Cancelling subscription...");
                        this.subscription.cancel();
                    }
                }
            }
        } else {
            log("Null Item!");
        }
    }

    @Override
    public void onComplete() {
        log("Complete!");
    }

    @Override
    public void onError(final Throwable t) {
        log("Subscriber Error >> %s", t);
    }

    private void log(final String message, final Object... args) {
        final String fullMessage = String.format(LOG_MESSAGE_FORMAT, this.name, currentThread().getName(), message);
        log.debug(String.format(fullMessage, args));
    }
}
