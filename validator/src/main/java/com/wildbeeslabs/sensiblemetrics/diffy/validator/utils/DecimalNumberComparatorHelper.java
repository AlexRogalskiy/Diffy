package com.wildbeeslabs.sensiblemetrics.diffy.validator.utils;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.OptionalInt;

/**
 * @author Marko Bekhta
 */
@UtilityClass
public class DecimalNumberComparatorHelper {

    public static int compare(final BigDecimal number, final BigDecimal value) {
        return number.compareTo(value);
    }

    public static int compare(final BigInteger number, final BigDecimal value) {
        return new BigDecimal(number).compareTo(value);
    }

    public static int compare(final Long number, final BigDecimal value) {
        return BigDecimal.valueOf(number).compareTo(value);
    }

    public static int compare(final Number number, final BigDecimal value) {
        return BigDecimal.valueOf(number.doubleValue()).compareTo(value);
    }

    public static int compare(final Number number, final BigDecimal value, final OptionalInt treatNanAs) {
        // In case of comparing numbers we need to check for special cases:
        // 1. Floating point numbers should consider nan/infinity as values hence they should
        // be directed to corresponding overloaded methods:
        if (number instanceof Double) {
            return compare((Double) number, value, treatNanAs);
        }
        if (number instanceof Float) {
            return compare((Float) number, value, treatNanAs);
        }

        // 2. For big numbers we don't want to lose any data so we just cast them and call corresponding methods:
        if (number instanceof BigDecimal) {
            return compare((BigDecimal) number, value);
        }
        if (number instanceof BigInteger) {
            return compare((BigInteger) number, value);
        }

        // 3. For any integer types we convert them to long as we would do that anyway
        // to create a BigDecimal instance. And use corresponding method for longs:
        if (number instanceof Byte || number instanceof Integer || number instanceof Long || number instanceof Short) {
            return compare(number.longValue(), value);
        }

        // 4. As a fallback we convert the number to double:
        return compare(number.doubleValue(), value, treatNanAs);
    }

    public static int compare(final Double number, final BigDecimal value, final OptionalInt treatNanAs) {
        OptionalInt infinity = InfinityNumberComparatorHelper.infinityCheck(number, treatNanAs);
        if (infinity.isPresent()) {
            return infinity.getAsInt();
        }
        return BigDecimal.valueOf(number).compareTo(value);
    }

    public static int compare(final Float number, final BigDecimal value, final OptionalInt treatNanAs) {
        OptionalInt infinity = InfinityNumberComparatorHelper.infinityCheck(number, treatNanAs);
        if (infinity.isPresent()) {
            return infinity.getAsInt();
        }
        return BigDecimal.valueOf(number).compareTo(value);
    }
}
