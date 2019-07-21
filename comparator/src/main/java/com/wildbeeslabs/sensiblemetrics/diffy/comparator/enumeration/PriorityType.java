package com.wildbeeslabs.sensiblemetrics.diffy.comparator.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PriorityType {

    /**
     * Value indicating the annotated member should come last.
     */
    LAST(Integer.MIN_VALUE),
    /**
     * Value indicating the annotated member should be placed at the "lower half".
     */
    LOW(Integer.MIN_VALUE / 2),
    /**
     * Value indicating the annotated member should have medium priority, effectively placing it "in the middle".
     */
    NEUTRAL(0),
    /**
     * Value indicating the annotated member should have high priority, effectively placing it "in the first half".
     */
    HIGH(Integer.MAX_VALUE / 2),

    /**
     * Value indicating the annotated member should be the very first
     */
    FIRST(Integer.MAX_VALUE);

    private final int value;
}
