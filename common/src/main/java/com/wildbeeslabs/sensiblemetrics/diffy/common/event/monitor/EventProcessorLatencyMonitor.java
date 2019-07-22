package com.wildbeeslabs.sensiblemetrics.diffy.common.event.monitor;

import com.wildbeeslabs.sensiblemetrics.diffy.common.event.iface.Event;
import com.wildbeeslabs.sensiblemetrics.diffy.common.event.iface.MessageMonitor;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Measures the difference in message timestamps between the last ingested and the last processed message.
 *
 * @author Marijn van Zelst
 * @since 4.1
 */
public class EventProcessorLatencyMonitor<T> implements MessageMonitor<T, Event<T>> {

    private final AtomicLong lastReceivedTime = new AtomicLong(-1);
    private final AtomicLong lastProcessedTime = new AtomicLong(-1);

    private EventProcessorLatencyMonitor() {
    }

    /**
     * Creates an event processor latency monitor
     *
     * @param meterNamePrefix The prefix for the meter name that will be created in the given meterRegistry
     * @param meterRegistry   The meter registry used to create and register the meters
     * @return the created event processor latency monitor
     */
    public static EventProcessorLatencyMonitor buildMonitor(String meterNamePrefix, MeterRegistry meterRegistry) {
        EventProcessorLatencyMonitor eventProcessorLatencyMonitor = new EventProcessorLatencyMonitor();
        Gauge.builder(meterNamePrefix + ".latency",
            eventProcessorLatencyMonitor,
            EventProcessorLatencyMonitor::calculateLatency).register(meterRegistry);
        return eventProcessorLatencyMonitor;
    }

    @Override
    public MonitorCallback onEventIngested(final Event<T> event) {
        if (event == null) {
            return NoOpMessageMonitor.NoOpMessageMonitorCallback.INSTANCE;
        }
        updateIfMaxValue(lastReceivedTime, event.getTimeStamp().toEpochMilli());
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
                updateIfMaxValue(lastProcessedTime, event.getTimeStamp().toEpochMilli());
            }
        };
    }

    private long calculateLatency() {
        long lastProcessedTime = this.lastProcessedTime.longValue();
        long lastReceivedTime = this.lastReceivedTime.longValue();
        long processTime;
        if (lastReceivedTime == -1 || lastProcessedTime == -1) {
            processTime = 0;
        } else {
            processTime = lastReceivedTime - lastProcessedTime;
        }
        return processTime;
    }

    private void updateIfMaxValue(AtomicLong atomicLong, long timestamp) {
        atomicLong.accumulateAndGet(timestamp, (currentValue, newValue) ->
            newValue > currentValue ? newValue : currentValue);
    }
}
