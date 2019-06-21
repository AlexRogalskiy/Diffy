package com.wildbeeslabs.sensiblemetrics.diffy.common.helpers.impl;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * A positive index.
 *
 * @author Alex Ruiz
 */
@Data
@EqualsAndHashCode
@ToString
public class Index {

    public final int value;

    /**
     * Creates a new {@link Index}.
     *
     * @param value the value of the index.
     * @return the created {@code Index}.
     * @throws IllegalArgumentException if the given value is negative.
     */
    public static Index atIndex(int value) {
        assert value >= 0 : "The value of the index should not be negative";
        return new Index(value);
    }

    private Index(int value) {
        this.value = value;
    }
}
