package com.wildbeeslabs.sensiblemetrics.diffy.generator.utils;

import java.util.function.Predicate;

public final class Comparables {
    private Comparables() {
        throw new UnsupportedOperationException();
    }

    public static <T extends Comparable<? super T>> Predicate<T> inRange(
        T min,
        T max) {

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

  /**
   * @return the value with the lowest magnitude between the min, max and zero.
   * <ul>
   * <li>[-10, 5] = 0</li>
   * <li>[-10, -5] = -5</li>
   * <li>[5, 10] = 5</li>
   * <li>[-5, 0] = 0</li>
   * <li>[0, 5] = 0</li>
   * </ul>
   */
    public static <T extends Comparable<? super T>> T leastMagnitude(
        T min,
        T max,
        T zero) {

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
}
