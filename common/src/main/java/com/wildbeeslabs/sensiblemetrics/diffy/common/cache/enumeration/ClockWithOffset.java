package com.wildbeeslabs.sensiblemetrics.diffy.common.cache.enumeration;

import com.wildbeeslabs.sensiblemetrics.diffy.common.cache.interfaces.Clock;

/**
 * Default {@link Clock} with offset
 */
public enum ClockWithOffset implements Clock {
    /**
     * Default clock with offset instance
     */
    INSTANCE;

    /**
     * Default offset value
     */
    private volatile long offset = 0L;

    /**
     * Sets the offset for the clock.
     *
     * @param offset Number of milliseconds to add to the current time.
     */
    public void setOffset(final long offset) {
        if (offset >= 0) {
            this.offset = offset;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long now() {
        return this.offset + System.currentTimeMillis();
    }
}
