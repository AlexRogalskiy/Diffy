package com.wildbeeslabs.sensiblemetrics.diffy.matcher.service;

import com.wildbeeslabs.sensiblemetrics.diffy.common.event.iface.Event;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.description.iface.MatchDescription;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.interfaces.Matcher;

/**
 * Matcher that matches any message (e.g. Event, Command) who's payload matches the given matcher.
 *
 * @param <T> The type of Message the matcher can match against
 * @author Allard Buijze
 * @since 2.0
 */
public class PayloadMatcher<T> extends AbstractMatcher<Event<T>> {

    private final Matcher<Event<T>> payloadMatcher;

    /**
     * Constructs an instance with the given {@code payloadMatcher}.
     *
     * @param payloadMatcher The matcher that must match the Message's payload.
     */
    public PayloadMatcher(final Matcher<Event<T>> payloadMatcher) {
        this.payloadMatcher = payloadMatcher;
    }

    @Override
    public boolean matches(final Event<T> item) {
        return Event.class.isInstance(item) && this.payloadMatcher.matches(item);
    }

    @Override
    public void describeTo(final MatchDescription description) {
        description.appendText("Message with payload <");
        this.payloadMatcher.describeBy(null, description);
        description.appendText(">");
    }
}
