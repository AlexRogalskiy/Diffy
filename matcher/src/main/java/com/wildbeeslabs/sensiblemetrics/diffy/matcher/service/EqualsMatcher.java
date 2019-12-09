package com.wildbeeslabs.sensiblemetrics.diffy.matcher.service;

import com.wildbeeslabs.sensiblemetrics.diffy.matcher.description.iface.MatchDescription;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.interfaces.Matcher;

import java.util.Objects;

/**
 * Matcher testing for object equality as per {@link Objects#equals(Object o)}
 *
 * @author bliessens
 * @since 3.3
 */
public class EqualsMatcher<T> extends AbstractMatcher<T> {

    private final T expectedValue;

    public static <T> Matcher<T> equalTo(final T item) {
        return new EqualsMatcher<T>(item);
    }

    public EqualsMatcher(final T expected) {
        this.expectedValue = expected;
    }

    @Override
    public boolean matches(T item) {
        return Objects.equals(this.expectedValue, item);
    }

    @Override
    public void describeTo(final MatchDescription description) {
        description.append(this.expectedValue);
    }
}
