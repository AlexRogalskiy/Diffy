package com.wildbeeslabs.sensiblemetrics.diffy.validator.utils;

import lombok.experimental.UtilityClass;

import java.util.OptionalInt;

/**
 * @author Marko Bekhta
 */
@UtilityClass
public class InfinityNumberComparatorHelper {
    public static final OptionalInt LESS_THAN = OptionalInt.of(-1);
    public static final OptionalInt FINITE_VALUE = OptionalInt.empty();
    public static final OptionalInt GREATER_THAN = OptionalInt.of(1);

    public static OptionalInt infinityCheck(final Double number, final OptionalInt treatNanAs) {
        OptionalInt result = FINITE_VALUE;
        if (number == Double.NEGATIVE_INFINITY) {
            result = LESS_THAN;
        } else if (number.isNaN()) {
            result = treatNanAs;
        } else if (number == Double.POSITIVE_INFINITY) {
            result = GREATER_THAN;
        }
        return result;
    }

    public static OptionalInt infinityCheck(final Float number, final OptionalInt treatNanAs) {
        OptionalInt result = FINITE_VALUE;
        if (number == Float.NEGATIVE_INFINITY) {
            result = LESS_THAN;
        } else if (number.isNaN()) {
            result = treatNanAs;
        } else if (number == Float.POSITIVE_INFINITY) {
            result = GREATER_THAN;
        }
        return result;
    }
}
