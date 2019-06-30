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

import lombok.experimental.UtilityClass;

import java.util.Calendar;
import java.util.Date;

@UtilityClass
public class TimeUtils {

    public static long computeStartOfNextSecond(long now) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(now));
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.SECOND, 1);
        return cal.getTime().getTime();
    }

    public static long computeStartOfNextMinute(long now) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(now));
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.SECOND, 0);
        cal.add(Calendar.MINUTE, 1);
        return cal.getTime().getTime();
    }

    public static long computeStartOfNextHour(long now) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(now));
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.add(Calendar.HOUR, 1);
        return cal.getTime().getTime();
    }

    public static long computeStartOfNextDay(long now) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(now));

        cal.add(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        return cal.getTime().getTime();
    }

    public static long computeStartOfNextWeek(long now) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(now));

        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.WEEK_OF_YEAR, 1);
        return cal.getTime().getTime();
    }

    public static long computeStartOfNextMonth(long now) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(now));

        cal.set(Calendar.DATE, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.MONTH, 1);
        return cal.getTime().getTime();
    }
}
