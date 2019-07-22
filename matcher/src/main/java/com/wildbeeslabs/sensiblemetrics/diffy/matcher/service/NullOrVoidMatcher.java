package com.wildbeeslabs.sensiblemetrics.diffy.matcher.service;

import com.wildbeeslabs.sensiblemetrics.diffy.matcher.description.iface.MatchDescription;

/**
 * Matcher that matches against a {@code null} or {@code void} value. Can be used to make sure no trailing
 * events remain when using an Exact Sequence Matcher.
 *
 * @param <T> The generic type of the mather
 * @author Allard Buijze
 * @since 1.1
 */
public class NullOrVoidMatcher<T> extends AbstractMatcher<T> {

    @Override
    public boolean matches(final T item) {
        return item == null || Void.class.equals(item);
    }

    @Override
    public void describeTo(final MatchDescription description) {
        description.appendText("<nothing>");
    }
}
