package com.wildbeeslabs.sensiblemetrics.diffy.common.helpers.impl;

public class Timer {
    private long start;
    private long lapStart;

    public Timer() {
        this.start = this.lapStart = System.currentTimeMillis();
    }

    public long getLap() {
        long lap = System.currentTimeMillis() - lapStart;
        lapStart = System.currentTimeMillis();
        return lap;
    }

    public float getLapSeconds() {
        return getLap() / 1000.f;
    }

    public long getTotal() {
        return System.currentTimeMillis() - start;
    }

    public float getTotalSeconds() {
        return getTotal() / 1000.f;
    }
}
