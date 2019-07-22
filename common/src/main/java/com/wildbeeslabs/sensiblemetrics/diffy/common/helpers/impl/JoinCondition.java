package com.wildbeeslabs.sensiblemetrics.diffy.common.helpers.impl;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public final class JoinCondition {

    private final CountDownLatch joinCountDown = new CountDownLatch(1);
    private volatile boolean success;

    public void await() throws InterruptedException {
        this.joinCountDown.await();
    }

    public void await(final long timeout, final TimeUnit timeUnit) throws InterruptedException {
        this.joinCountDown.await(timeout, timeUnit);
    }

    private void markJoined() {
        this.success = true;
        this.joinCountDown.countDown();
    }

    public boolean isJoined() {
        return this.success;
    }
}
