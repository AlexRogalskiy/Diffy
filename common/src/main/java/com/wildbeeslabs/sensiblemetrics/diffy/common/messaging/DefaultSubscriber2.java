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

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.currentThread;

/**
 * Default {@link Subscriber} implementation
 */
@Slf4j
public class DefaultSubscriber2 implements Subscriber<Object> {

    private static final String LOG_MESSAGE_FORMAT = "~~ Subscriber %s >> [%s] %s%n";
    private static final Random RANDOM = new Random();

    private Subscription subscription;
    private AtomicInteger count;

    private String name;
    private int DEMAND = 0;

    public DefaultSubscriber2(final String name) {
        this.name = name;
    }

    @Override
    public void onSubscribe(final Subscription subscription) {
        log("Subscribed...");
        this.subscription = subscription;
        this.request(DEMAND);
    }

    public void setDEMAND(int n) {
        this.DEMAND = n;
        this.count = new AtomicInteger(this.DEMAND);
    }

    private void request(int n) {
        log("request new " + n + " items...");
        this.subscription.request(n);
    }

    @Override
    public void onNext(final Object item) {
        log("itemValue: " + item);
        if (count.decrementAndGet() == 0) {
            if (RANDOM.nextBoolean()) {
                this.request(DEMAND);
                this.count.set(DEMAND);
            } else {
                log("Cancel subscribe...");
                this.subscription.cancel();
            }
        }
    }

    @Override
    public void onComplete() {
        log("Complete!");
    }

    @Override
    public void onError(Throwable t) {
        log("Error: " + t.getMessage());
    }

    private void log(final String message, final Object... args) {
        final String fullMessage = String.format(LOG_MESSAGE_FORMAT, this.name, currentThread().getName(), message);
        log.debug(String.format(fullMessage, args));
    }
}
