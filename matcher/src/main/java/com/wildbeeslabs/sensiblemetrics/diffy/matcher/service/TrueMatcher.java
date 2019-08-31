package com.wildbeeslabs.sensiblemetrics.diffy.matcher.service;

import com.wildbeeslabs.sensiblemetrics.diffy.matcher.interfaces.Matcher;

/**
 * Matches any type.
 */
public enum TrueMatcher implements Matcher<Object> {
    INSTANCE {
        @Override
        public boolean matches(final Object value) {
            return true;
        }
    }
}
