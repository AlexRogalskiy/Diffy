package com.wildbeeslabs.sensiblemetrics.diffy.common.executor;

import com.wildbeeslabs.sensiblemetrics.diffy.common.event.iface.Event;
import lombok.extern.slf4j.Slf4j;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.function.Consumer;

/**
 * Scheduler that keeps track of (Event processing) tasks that need to be executed sequentially.
 */
@Slf4j
public class EventProcessorTask implements Runnable {

    private final ShutdownCallback shutDownCallback;
    private final Executor executor;
    private final Deque<ProcessingTask> taskQueue;
    private boolean isScheduled = false;
    private volatile boolean cleanedUp;

    private final Object runnerMonitor = new Object();

    /**
     * Initialize a scheduler using the given {@code executor}. This scheduler uses an unbounded queue to schedule
     * events.
     *
     * @param executor         The executor service that will process the events
     * @param shutDownCallback The callback to notify when the scheduler finishes processing events
     */
    public EventProcessorTask(final Executor executor, final ShutdownCallback shutDownCallback) {
        this.taskQueue = new LinkedList<>();
        this.shutDownCallback = shutDownCallback;
        this.executor = executor;
    }

    /**
     * Schedules a batch of events for processing. Will schedule a new invoker task if none is currently active.
     * <p/>
     * If the current scheduler is in the process of being shut down, this method will return false.
     * <p/>
     * This method is thread safe.
     *
     * @param events    the events to schedule
     * @param processor the component that will do the actual processing of the events
     * @return true if the event was scheduled successfully, false if this scheduler is not available to process events
     * @throws IllegalStateException if the queue in this scheduler does not have the capacity to add this event
     */
    public synchronized boolean scheduleEvents(final List<? extends Event<?>> events, final Consumer<List<? extends Event<?>>> processor) {
        if (this.cleanedUp) {
            // this scheduler has been shut down; accept no more events
            return false;
        }
        // add the task to the queue which this scheduler processes
        this.taskQueue.add(new ProcessingTask(events, processor));
        if (!this.isScheduled) {
            this.isScheduled = true;
            this.executor.execute(this);
        }
        return true;
    }

    @Override
    public void run() {
        synchronized (this.runnerMonitor) {
            boolean mayContinue = true;
            int itemsAtStart = this.taskQueue.size();
            int processedItems = 0;
            while (mayContinue) {
                processNextTask();
                processedItems++;
                // Continue processing if there is no rescheduling involved and there are events in the queue, or if yielding failed
                mayContinue = (processedItems < itemsAtStart && !taskQueue.isEmpty()) || !yield();
            }
        }
    }

    private void processNextTask() {
        final ProcessingTask task = this.nextTask();
        task.processor.accept(task.events);
    }

    private synchronized ProcessingTask nextTask() {
        return this.taskQueue.poll();
    }

    /**
     * Tries to yield to other threads by rescheduling processing of any further queued events. If rescheduling fails,
     * this call returns false, indicating that processing should continue in the current thread.
     * <p/>
     * This method is thread safe
     *
     * @return true if yielding succeeded, false otherwise.
     */
    private synchronized boolean yield() {
        if (this.taskQueue.isEmpty()) {
            this.cleanUp();
        } else {
            try {
                this.executor.execute(this);
                if (log.isDebugEnabled()) {
                    log.debug("Processing of event listener yielded.");
                }
            } catch (RejectedExecutionException e) {
                log.info("Processing of event listener could not yield. Executor refused the task.");
                return false;
            }
        }
        return true;
    }

    private synchronized void cleanUp() {
        this.isScheduled = false;
        this.cleanedUp = true;
        this.shutDownCallback.afterShutdown(this);
    }

    /**
     * Callback that allows the SequenceManager to receive a notification when this scheduler finishes processing
     * events.
     */
    @FunctionalInterface
    public interface ShutdownCallback {

        /**
         * Called when event processing is complete. This means that there are no more events waiting and the last
         * transactional batch has been committed successfully.
         *
         * @param scheduler the scheduler that completed processing.
         */
        void afterShutdown(final EventProcessorTask scheduler);
    }

    private static class ProcessingTask {
        private final List<? extends Event<?>> events;
        private final Consumer<List<? extends Event<?>>> processor;

        public ProcessingTask(final List<? extends Event<?>> events, final Consumer<List<? extends Event<?>>> processor) {
            this.events = events;
            this.processor = processor;
        }
    }
}
