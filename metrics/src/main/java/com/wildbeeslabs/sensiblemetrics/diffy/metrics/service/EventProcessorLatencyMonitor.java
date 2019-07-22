package com.wildbeeslabs.sensiblemetrics.diffy.metrics.service;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricSet;
import com.wildbeeslabs.sensiblemetrics.diffy.common.event.iface.Event;
import com.wildbeeslabs.sensiblemetrics.diffy.common.event.iface.MessageMonitor;
import com.wildbeeslabs.sensiblemetrics.diffy.common.event.monitor.NoOpMessageMonitor;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Measures the difference in message timestamps between the last ingested and the last processed message.
 *
 * @author Marijn van Zelst
 * @since 3.0
 */
public class EventProcessorLatencyMonitor<T> implements MessageMonitor<T, Event<T>>, MetricSet {

    private final AtomicLong lastReceivedTime = new AtomicLong(-1);
    private final AtomicLong lastProcessedTime = new AtomicLong(-1);

    @Override
    public MessageMonitor.MonitorCallback onEventIngested(final Event<T> message) {
        if (message == null) {
            return NoOpMessageMonitor.NoOpMessageMonitorCallback.INSTANCE;
        }
        updateIfMaxValue(lastReceivedTime, message.getTimeStamp().toEpochMilli());
        return new MonitorCallback() {
            @Override
            public void reportSuccess() {
                update();
            }

            @Override
            public void reportFailure(Throwable cause) {
                update();
            }

            @Override
            public void reportIgnored() {
                update();
            }

            private void update() {
                updateIfMaxValue(lastProcessedTime, message.getTimeStamp().toEpochMilli());
            }
        };
    }

    @Override
    public Map<String, Metric> getMetrics() {
        long lastProcessedTime = this.lastProcessedTime.longValue();
        long lastReceivedTime = this.lastReceivedTime.longValue();
        long processTime;
        if (lastReceivedTime == -1 || lastProcessedTime == -1) {
            processTime = 0;
        } else {
            processTime = lastReceivedTime - lastProcessedTime;
        }
        Map<String, Metric> metrics = new HashMap<>();
        metrics.put("latency", (Gauge<Long>) () -> processTime);
        return metrics;
    }

    private void updateIfMaxValue(AtomicLong atomicLong, long timestamp) {
        atomicLong.accumulateAndGet(timestamp, (currentValue, newValue) -> newValue > currentValue ? newValue : currentValue);
    }
}
