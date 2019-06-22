/*
 * The MIT License
 *
 * Copyright 2019 WildBees Labs, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.wildbeeslabs.sensiblemetrics.diffy.common.utils;

import com.wildbeeslabs.sensiblemetrics.diffy.common.exception.InvalidParameterException;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Calculation utilities implementation
 */
@UtilityClass
public class CalcUtils {

    /**
     * Negates the input throwing an exception if it can't negate it.
     *
     * @param value the value to negate
     * @return the negated value
     * @throws ArithmeticException if the value is Integer.MIN_VALUE
     * @since 1.1
     */
    public static int safeNegate(int value) {
        if (value == Integer.MIN_VALUE) {
            throw new ArithmeticException("Integer.MIN_VALUE cannot be negated");
        }
        return -value;
    }

    /**
     * Add two values throwing an exception if overflow occurs.
     *
     * @param val1 the first value
     * @param val2 the second value
     * @return the new total
     * @throws ArithmeticException if the value is too big or too small
     */
    public static int safeAdd(int val1, int val2) {
        int sum = val1 + val2;
        // If there is a sign change, but the two values have the same sign...
        if ((val1 ^ sum) < 0 && (val1 ^ val2) >= 0) {
            throw new ArithmeticException("The calculation caused an overflow: " + val1 + " + " + val2);
        }
        return sum;
    }

    /**
     * Add two values throwing an exception if overflow occurs.
     *
     * @param val1 the first value
     * @param val2 the second value
     * @return the new total
     * @throws ArithmeticException if the value is too big or too small
     */
    public static long safeAdd(long val1, long val2) {
        long sum = val1 + val2;
        // If there is a sign change, but the two values have the same sign...
        if ((val1 ^ sum) < 0 && (val1 ^ val2) >= 0) {
            throw new ArithmeticException("The calculation caused an overflow: " + val1 + " + " + val2);
        }
        return sum;
    }

    /**
     * Subtracts two values throwing an exception if overflow occurs.
     *
     * @param val1 the first value, to be taken away from
     * @param val2 the second value, the amount to take away
     * @return the new total
     * @throws ArithmeticException if the value is too big or too small
     */
    public static long safeSubtract(long val1, long val2) {
        long diff = val1 - val2;
        // If there is a sign change, but the two values have different signs...
        if ((val1 ^ diff) < 0 && (val1 ^ val2) < 0) {
            throw new ArithmeticException("The calculation caused an overflow: " + val1 + " - " + val2);
        }
        return diff;
    }

    /**
     * Multiply two values throwing an exception if overflow occurs.
     *
     * @param val1 the first value
     * @param val2 the second value
     * @return the new total
     * @throws ArithmeticException if the value is too big or too small
     * @since 1.2
     */
    public static int safeMultiply(int val1, int val2) {
        long total = (long) val1 * (long) val2;
        if (total < Integer.MIN_VALUE || total > Integer.MAX_VALUE) {
            throw new ArithmeticException("Multiplication overflows an int: " + val1 + " * " + val2);
        }
        return (int) total;
    }

    /**
     * Multiply two values throwing an exception if overflow occurs.
     *
     * @param val1 the first value
     * @param val2 the second value
     * @return the new total
     * @throws ArithmeticException if the value is too big or too small
     * @since 1.2
     */
    public static long safeMultiply(long val1, int val2) {
        switch (val2) {
            case -1:
                if (val1 == Long.MIN_VALUE) {
                    throw new ArithmeticException("Multiplication overflows a long: " + val1 + " * " + val2);
                }
                return -val1;
            case 0:
                return 0L;
            case 1:
                return val1;
        }
        long total = val1 * val2;
        if (total / val2 != val1) {
            throw new ArithmeticException("Multiplication overflows a long: " + val1 + " * " + val2);
        }
        return total;
    }

    /**
     * Multiply two values throwing an exception if overflow occurs.
     *
     * @param val1 the first value
     * @param val2 the second value
     * @return the new total
     * @throws ArithmeticException if the value is too big or too small
     */
    public static long safeMultiply(long val1, long val2) {
        if (val2 == 1) {
            return val1;
        }
        if (val1 == 1) {
            return val2;
        }
        if (val1 == 0 || val2 == 0) {
            return 0;
        }
        long total = val1 * val2;
        if (total / val2 != val1 || val1 == Long.MIN_VALUE && val2 == -1 || val2 == Long.MIN_VALUE && val1 == -1) {
            throw new ArithmeticException("Multiplication overflows a long: " + val1 + " * " + val2);
        }
        return total;
    }

    /**
     * Divides the dividend by the divisor throwing an exception if
     * overflow occurs or the divisor is zero.
     *
     * @param dividend the dividend
     * @param divisor  the divisor
     * @return the new total
     * @throws ArithmeticException if the operation overflows or the divisor is zero
     */
    public static long safeDivide(long dividend, long divisor) {
        if (dividend == Long.MIN_VALUE && divisor == -1L) {
            throw new ArithmeticException("Multiplication overflows a long: " + dividend + " / " + divisor);
        }
        return dividend / divisor;
    }

    /**
     * Divides the dividend by divisor. Rounding of result occurs
     * as per the roundingMode.
     *
     * @param dividend     the dividend
     * @param divisor      the divisor
     * @param roundingMode the desired rounding mode
     * @return the division result as per the specified rounding mode
     * @throws ArithmeticException if the operation overflows or the divisor is zero
     */
    public static long safeDivide(long dividend, long divisor, RoundingMode roundingMode) {
        if (dividend == Long.MIN_VALUE && divisor == -1L) {
            throw new ArithmeticException("Multiplication overflows a long: " + dividend + " / " + divisor);
        }
        final BigDecimal dividendBigDecimal = new BigDecimal(dividend);
        final BigDecimal divisorBigDecimal = new BigDecimal(divisor);
        return dividendBigDecimal.divide(divisorBigDecimal, roundingMode).longValue();
    }

    /**
     * Casts to an int throwing an exception if overflow occurs.
     *
     * @param value the value
     * @return the value as an int
     * @throws ArithmeticException if the value is too big or too small
     */
    public static int safeToInt(long value) {
        if (Integer.MIN_VALUE <= value && value <= Integer.MAX_VALUE) {
            return (int) value;
        }
        throw new ArithmeticException("Value cannot fit in an int: " + value);
    }

    /**
     * Multiply two values to return an int throwing an exception if overflow occurs.
     *
     * @param val1 the first value
     * @param val2 the second value
     * @return the new total
     * @throws ArithmeticException if the value is too big or too small
     */
    public static int safeMultiplyToInt(long val1, long val2) {
        long val = safeMultiply(val1, val2);
        return safeToInt(val);
    }

    /**
     * Verify that input values are within specified bounds.
     *
     * @param value      the value to check
     * @param lowerBound the lower bound allowed for value
     * @param upperBound the upper bound allowed for value
     * @throws InvalidParameterException if value is not in the specified bounds
     */
    public static void verifyValueBounds(int value, int lowerBound, int upperBound) {
        verifyValueBounds("Invalid input parameters: value={%s}, lower bound={%s}, upper bound={%s}", value, lowerBound, upperBound);
    }

    /**
     * Verify that input values are within specified bounds.
     *
     * @param value      the value to check
     * @param lowerBound the lower bound allowed for value
     * @param upperBound the upper bound allowed for value
     * @throws InvalidParameterException if value is not in the specified bounds
     */
    public static void verifyValueBounds(final String message, int value, int lowerBound, int upperBound) {
        if ((value < lowerBound) || (value > upperBound)) {
            throw new InvalidParameterException(String.format(message, value, lowerBound, upperBound));
        }
    }

    /**
     * Utility method used by addWrapField implementations to ensure the new
     * value lies within the field's legal value range.
     *
     * @param currentValue the current value of the data, which may lie outside
     *                     the wrapped value range
     * @param wrapValue    the value to add to current value before
     *                     wrapping.  This may be negative.
     * @param minValue     the wrap range minimum value.
     * @param maxValue     the wrap range maximum value.  This must be
     *                     greater than minValue (checked by the method).
     * @return the wrapped value
     * @throws IllegalArgumentException if minValue is greater
     *                                  than or equal to maxValue
     */
    public static int getWrappedValue(int currentValue, int wrapValue, int minValue, int maxValue) {
        return getWrappedValue(currentValue + wrapValue, minValue, maxValue);
    }

    /**
     * Utility method that ensures the given value lies within the field's
     * legal value range.
     *
     * @param value    the value to fit into the wrapped value range
     * @param minValue the wrap range minimum value.
     * @param maxValue the wrap range maximum value.  This must be
     *                 greater than minValue (checked by the method).
     * @return the wrapped value
     * @throws IllegalArgumentException if minValue is greater
     *                                  than or equal to maxValue
     */
    public static int getWrappedValue(int value, int minValue, int maxValue) {
        if (minValue >= maxValue) {
            throw new IllegalArgumentException("MIN > MAX");
        }

        int wrapRange = maxValue - minValue + 1;
        value -= minValue;

        if (value >= 0) {
            return (value % wrapRange) + minValue;
        }

        int remByRange = (-value) % wrapRange;

        if (remByRange == 0) {
            return 0 + minValue;
        }
        return (wrapRange - remByRange) + minValue;
    }
}
