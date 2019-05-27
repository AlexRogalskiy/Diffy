package com.wildbeeslabs.sensiblemetrics.diffy.matcher.iface;

import com.wildbeeslabs.sensiblemetrics.diffy.matcher.event.MatcherEvent;

/**
 * {@link MatcherEventListener} listener declaration
 *
 * @param <T> type of input element to be handled by operation
 */
@FunctionalInterface
public interface MatcherHandler<T> {

    /**
     * {@link MatcherEvent} handler
     *
     * @param event - initial input {@link MatcherEvent} to handle
     */
    void handleEvent(final MatcherEvent<T> event);
}
