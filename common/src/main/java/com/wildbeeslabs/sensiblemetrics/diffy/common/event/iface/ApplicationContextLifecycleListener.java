package com.wildbeeslabs.sensiblemetrics.diffy.common.event.iface;

public interface ApplicationContextLifecycleListener {
    /**
     * Invoked when the <code>WorkContext</code> instance was successfully
     * set as the execution context for the <code>Work</code> instance.
     *
     * @since 1.6
     */
    void contextSetupComplete();

    /**
     * Invoked when the <code>WorkContext</code> instance was set as the
     * execution context for the <code>Work</code> instance it was associated
     * with.
     *
     * @param errorCode - One of the error-codes
     */
    void contextSetupFailed(final String errorCode);
}
