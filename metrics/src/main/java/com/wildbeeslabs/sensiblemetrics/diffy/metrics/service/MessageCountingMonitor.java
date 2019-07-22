package com.wildbeeslabs.sensiblemetrics.diffy.metrics.service;

import com.codahale.metrics.Counter;
import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricSet;
import com.google.common.collect.ImmutableMap;
import com.wildbeeslabs.sensiblemetrics.diffy.common.event.iface.Event;
import com.wildbeeslabs.sensiblemetrics.diffy.common.event.iface.MessageMonitor;

import java.util.Map;

/**
 * Counts the number of ingested, successful, failed and processed messages
 *
 * @author Marijn van Zelst
 * @since 3.0
 */
public class MessageCountingMonitor<T> implements MessageMonitor<T, Event<T>>, MetricSet {
    private final Counter ingestedCounter = new Counter();
    private final Counter successCounter = new Counter();
    private final Counter failureCounter = new Counter();
    private final Counter processedCounter = new Counter();
    private final Counter ignoredCounter = new Counter();

    @Override
    public MessageMonitor.MonitorCallback onEventIngested(final Event<T> event) {
        ingestedCounter.inc();
        return new MessageMonitor.MonitorCallback() {
            @Override
            public void reportSuccess() {
                processedCounter.inc();
                successCounter.inc();
            }

            @Override
            public void reportFailure(final Throwable cause) {
                processedCounter.inc();
                failureCounter.inc();
            }

            @Override
            public void reportIgnored() {
                ignoredCounter.inc();
            }
        };
    }

    @Override
    public Map<String, Metric> getMetrics() {
        return ImmutableMap.<String, Metric>builder()
            .put("ingestedCounter", this.ingestedCounter)
            .put("processedCounter", this.processedCounter)
            .put("successCounter", this.successCounter)
            .put("failureCounter", this.failureCounter)
            .put("ignoredCounter", this.ignoredCounter)
            .build();
    }
}
