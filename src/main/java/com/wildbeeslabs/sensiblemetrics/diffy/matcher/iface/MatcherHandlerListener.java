package com.wildbeeslabs.sensiblemetrics.diffy.matcher.iface;

import com.wildbeeslabs.sensiblemetrics.diffy.matcher.handler.MatcherHandler;

import java.io.Serializable;

/**
 * {@link MatcherHandler} listener declaration
 *
 * @param <T> type of input element to be matched by operation
 */
public interface MatcherHandlerListener<T> extends Serializable {

    /**
     * Removes {@link MatcherHandler} from current {@link Matcher}
     *
     * @param handler - initial input {@link MatcherHandler} to remove
     */
    void removeHandler(final MatcherHandler<? super T> handler);

    /**
     * Adds {@link MatcherHandler} to current {@link Matcher}
     *
     * @param handler - initial input {@link MatcherHandler} to add
     */
    void addHandler(final MatcherHandler<? super T> handler);

    /**
     * Removes {@link MatcherHandler}s from current {@link Matcher}
     */
    void removeAllHandlers();
}
