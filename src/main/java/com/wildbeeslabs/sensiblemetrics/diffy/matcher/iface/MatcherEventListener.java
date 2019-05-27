package com.wildbeeslabs.sensiblemetrics.diffy.matcher.iface;

import com.wildbeeslabs.sensiblemetrics.diffy.matcher.event.MatcherEvent;

/**
 * {@link MatcherEvent} listener declaration
 *
 * @param <T> type of input element to be matched by operation
 */
public interface MatcherEventListener<T> {

    /**
     * {@link MatcherEventListener} on success {@link MatcherEvent}
     *
     * @param event - initial input {@link MatcherEvent}
     */
    void onSuccess(final MatcherEvent<T> event);

    /**
     * {@link MatcherEventListener} on error {@link MatcherEvent}
     *
     * @param event - initial input {@link MatcherEvent}
     */
    void onError(final MatcherEvent<T> event);
}
