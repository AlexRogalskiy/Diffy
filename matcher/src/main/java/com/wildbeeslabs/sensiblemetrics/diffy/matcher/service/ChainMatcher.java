package com.wildbeeslabs.sensiblemetrics.diffy.matcher.service;

import com.wildbeeslabs.sensiblemetrics.diffy.matcher.interfaces.Matcher;

import java.util.HashSet;
import java.util.Set;

public class ChainMatcher<T> implements Matcher<T> {
    private final Set<Matcher<T>> matchers = new HashSet<>();

    public boolean addFilter(final Matcher<T> matcher) {
        return this.matchers.add(matcher);
    }

    public boolean removeFilter(final Matcher<T> matcher) {
        return this.matchers.remove(matcher);
    }

    @Override
    public boolean matches(final T item) {
        for (final Matcher<T> matcher : this.matchers) {
            if (!matcher.matches(item)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("ChainMatcher %s with: {%s}", super.toString(), this.matchers.toString());
    }
}
