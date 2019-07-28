package com.wildbeeslabs.sensiblemetrics.diffy.comparator.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;

import java.util.function.Predicate;

@UtilityClass
public class Comparables {

    public static <T extends Comparable<? super T>> Predicate<T> inRange(final T min, final T max) {
        return c -> {
            if (min == null && max == null)
                return true;
            if (min == null)
                return c.compareTo(max) <= 0;
            if (max == null)
                return c.compareTo(min) >= 0;
            return c.compareTo(min) >= 0 && c.compareTo(max) <= 0;
        };
    }

    public static <T extends Comparable<? super T>> T leastMagnitude(final T min, final T max, final T zero) {
        if (min == null && max == null)
            return zero;

        if (min == null)
            return max.compareTo(zero) <= 0 ? max : zero;
        if (max == null)
            return min.compareTo(zero) >= 0 ? min : zero;

        if (min.compareTo(zero) > 0)
            return min;
        if (max.compareTo(zero) < 0)
            return max;
        return zero;
    }

    @UtilityClass
    public static class Ranges {
        @Getter
        @RequiredArgsConstructor
        public enum Type {
            CHARACTER("c"),
            INTEGRAL("d"),
            FLOAT("f"),
            STRING("s");

            private final String pattern;
        }

        public static <T extends Comparable<? super T>> int checkRange(final Type type, final T min, final T max) {
            final int comparison = min.compareTo(max);
            if (comparison > 0) {
                throw new IllegalArgumentException(
                    String.format(
                        "bad range, %" + type.pattern + " > %" + type.pattern,
                        min,
                        max));
            }
            return comparison;
        }
    }

    public static long findNextPowerOfTwoLong(long positiveLong) {
        return isPowerOfTwoLong(positiveLong)
            ? positiveLong
            : ((long) 1) << (64 - Long.numberOfLeadingZeros(positiveLong));
    }

    private static boolean isPowerOfTwoLong(long positiveLong) {
        return (positiveLong & (positiveLong - 1)) == 0;
    }
}
