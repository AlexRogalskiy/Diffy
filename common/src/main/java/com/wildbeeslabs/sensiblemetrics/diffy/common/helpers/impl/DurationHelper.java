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
package com.wildbeeslabs.sensiblemetrics.diffy.common.helpers.impl;

import lombok.Data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.StringUtils.join;

/**
 * Duration helper implementation
 */
@Data
public class DurationHelper {
    /**
     * Default duration formats
     */
    private final static String DOUBLE_PART = "([0-9]*(.[0-9]+)?)";
    private final static int DOUBLE_GROUP = 1;

    private final static String UNIT_PART = "(|milli(second)?|second(e)?|minute|hour|day)s?";
    private final static int UNIT_GROUP = 3;

    /**
     * Default {@link Pattern} formats
     */
    private static final Pattern DURATION_PATTERN = Pattern.compile(DOUBLE_PART + "\\s*" + UNIT_PART, Pattern.CASE_INSENSITIVE);

    private static final long SECONDS_COEFFICIENT = 1000;
    private static final long MINUTES_COEFFICIENT = 60 * SECONDS_COEFFICIENT;
    private static final long HOURS_COEFFICIENT = 60 * MINUTES_COEFFICIENT;
    private static final long DAYS_COEFFICIENT = 24 * HOURS_COEFFICIENT;

    private final long millis;

    public DurationHelper(final long millis) {
        this.millis = millis;
    }

    public static DurationHelper buildByMilliseconds(double value) {
        return new DurationHelper((long) (value));
    }

    public static DurationHelper buildBySeconds(double value) {
        return new DurationHelper((long) (SECONDS_COEFFICIENT * value));
    }

    public static DurationHelper buildByMinutes(double value) {
        return new DurationHelper((long) (MINUTES_COEFFICIENT * value));
    }

    public static DurationHelper buildByHours(double value) {
        return new DurationHelper((long) (HOURS_COEFFICIENT * value));
    }

    public static DurationHelper buildByDays(double value) {
        return new DurationHelper((long) (DAYS_COEFFICIENT * value));
    }

    public static DurationHelper buildUnbounded() {
        return new DurationHelper(Long.MAX_VALUE);
    }

    public long getMilliseconds() {
        return millis;
    }

    public static DurationHelper valueOf(final String durationStr) {
        Matcher matcher = DURATION_PATTERN.matcher(durationStr);

        if (matcher.matches()) {
            String doubleStr = matcher.group(DOUBLE_GROUP);
            String unitStr = matcher.group(UNIT_GROUP);

            double doubleValue = Double.valueOf(doubleStr);
            if (unitStr.equalsIgnoreCase("milli") || unitStr.equalsIgnoreCase("millisecond") || unitStr.length() == 0) {
                return buildByMilliseconds(doubleValue);
            } else if (unitStr.equalsIgnoreCase("second") || unitStr.equalsIgnoreCase("seconde")) {
                return buildBySeconds(doubleValue);
            } else if (unitStr.equalsIgnoreCase("minute")) {
                return buildByMinutes(doubleValue);
            } else if (unitStr.equalsIgnoreCase("hour")) {
                return buildByHours(doubleValue);
            } else if (unitStr.equalsIgnoreCase("day")) {
                return buildByDays(doubleValue);
            } else {
                throw new IllegalStateException("Unexpected " + unitStr);
            }
        }
        throw new IllegalArgumentException("String value [" + durationStr + "] is not in the expected format.");
    }

    @Override
    public String toString() {
        if (this.millis < SECONDS_COEFFICIENT) {
            return join(this.millis, " milliseconds");
        } else if (this.millis < MINUTES_COEFFICIENT) {
            return join(this.millis / SECONDS_COEFFICIENT, " seconds");
        } else if (this.millis < HOURS_COEFFICIENT) {
            return join(this.millis / MINUTES_COEFFICIENT, " minutes");
        }
        return join(this.millis / HOURS_COEFFICIENT, " hours");
    }
}
