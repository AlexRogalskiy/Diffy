package com.wildbeeslabs.sensiblemetrics.diffy.common.executor.impl;

/**
 * Runnable implementation which always sets its thread name.
 */
public abstract class NamedRunnable implements Runnable {
    protected final String name;

    public NamedRunnable(final String format, final Object... args) {
        this.name = String.format(format, args);
    }

    @Override
    public final void run() {
        final String oldName = Thread.currentThread().getName();
        Thread.currentThread().setName(name);
        try {
            this.execute();
        } finally {
            Thread.currentThread().setName(oldName);
        }
    }

    protected abstract void execute();
}
