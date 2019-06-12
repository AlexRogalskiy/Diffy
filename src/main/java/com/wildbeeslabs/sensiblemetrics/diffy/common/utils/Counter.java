package com.wildbeeslabs.sensiblemetrics.diffy.common.utils;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Data
@EqualsAndHashCode
@ToString
public final class Counter {

    /**
     * Hidden constructor.
     */
    private Counter() {
        super();
    }

    /**
     * It counts how many times each element provided occurred in an array and
     * returns a dict with the element as key and the count as value.
     *
     * @param tokens array of tokens
     * @return dict, where the elements are key, and the count the value
     */
    public static Map<CharSequence, Integer> of(final CharSequence[] tokens) {
        final Map<CharSequence, Integer> innerCounter = new HashMap<>();
        for (final CharSequence token : tokens) {
            int value = innerCounter.getOrDefault(token, 0);
            innerCounter.put(token, ++value);
        }
        return innerCounter;
    }
}
