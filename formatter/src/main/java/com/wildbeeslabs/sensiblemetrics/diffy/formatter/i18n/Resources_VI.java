/*
 * The MIT License
 *
 * Copyright 2018 WildBees Labs.
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
package com.wildbeeslabs.sensiblemetrics.diffy.formatter.i18n;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Default resources bundle [VI]
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Resources_VI extends Resources {

    private static final Object[][] OBJECTS = new Object[][]{
        {"CenturyPattern", "%n %u"},
        {"CenturyFuturePrefix", ""},
        {"CenturyFutureSuffix", " sau"},
        {"CenturyPastPrefix", "cách đây "},
        {"CenturyPastSuffix", ""},
        {"CenturySingularName", "thế kỷ"},
        {"CenturyPluralName", "thế kỷ"},
        {"DayPattern", "%n %u"},
        {"DayFuturePrefix", ""},
        {"DayFutureSuffix", " sau"},
        {"DayPastPrefix", "cách đây "},
        {"DayPastSuffix", ""},
        {"DaySingularName", "ngày"},
        {"DayPluralName", "ngày"},
        {"DecadePattern", "%n %u"},
        {"DecadeFuturePrefix", ""},
        {"DecadeFutureSuffix", " sau"},
        {"DecadePastPrefix", "cách đây "},
        {"DecadePastSuffix", ""},
        {"DecadeSingularName", "thập kỷ"},
        {"DecadePluralName", "thập kỷ"},
        {"HourPattern", "%n %u"},
        {"HourFuturePrefix", ""},
        {"HourFutureSuffix", " sau"},
        {"HourPastPrefix", "cách đây "},
        {"HourPastSuffix", ""},
        {"HourSingularName", "giờ"},
        {"HourPluralName", "giờ"},
        {"JustNowPattern", "%u"},
        {"JustNowFuturePrefix", ""},
        {"JustNowFutureSuffix", " khắc sau"},
        {"JustNowPastPrefix", "cách đây "},
        {"JustNowPastSuffix", " khắc"},
        {"JustNowSingularName", ""},
        {"JustNowPluralName", ""},
        {"MillenniumPattern", "%n %u"},
        {"MillenniumFuturePrefix", ""},
        {"MillenniumFutureSuffix", " sau"},
        {"MillenniumPastPrefix", "cách đây "},
        {"MillenniumPastSuffix", ""},
        {"MillenniumSingularName", "thiên niên kỷ"},
        {"MillenniumPluralName", "thiên niên kỷ"},
        {"MillisecondPattern", "%n %u"},
        {"MillisecondFuturePrefix", ""},
        {"MillisecondFutureSuffix", " sau"},
        {"MillisecondPastPrefix", "cách đây "},
        {"MillisecondPastSuffix", ""},
        {"MillisecondSingularName", "mili giây"},
        {"MillisecondPluralName", "mili giây"},
        {"MinutePattern", "%n %u"},
        {"MinuteFuturePrefix", ""},
        {"MinuteFutureSuffix", " sau"},
        {"MinutePastPrefix", "cách đây "},
        {"MinutePastSuffix", ""},
        {"MinuteSingularName", "phút"},
        {"MinutePluralName", "phút"},
        {"MonthPattern", "%n %u"},
        {"MonthFuturePrefix", ""},
        {"MonthFutureSuffix", " sau"},
        {"MonthPastPrefix", "cách đây "},
        {"MonthPastSuffix", ""},
        {"MonthSingularName", "tháng"},
        {"MonthPluralName", "tháng"},
        {"SecondPattern", "%n %u"},
        {"SecondFuturePrefix", ""},
        {"SecondFutureSuffix", " sau"},
        {"SecondPastPrefix", "cách đây "},
        {"SecondPastSuffix", ""},
        {"SecondSingularName", "giây"},
        {"SecondPluralName", "giây"},
        {"WeekPattern", "%n %u"},
        {"WeekFuturePrefix", ""},
        {"WeekFutureSuffix", " sau"},
        {"WeekPastPrefix", "cách đây "},
        {"WeekPastSuffix", ""},
        {"WeekSingularName", "tuần"},
        {"WeekPluralName", "tuần"},
        {"YearPattern", "%n %u"},
        {"YearFuturePrefix", ""},
        {"YearFutureSuffix", " sau"},
        {"YearPastPrefix", "cách đay "},
        {"YearPastSuffix", ""},
        {"YearSingularName", "năm"},
        {"YearPluralName", "năm"},
        {"AbstractTimeUnitPattern", ""},
        {"AbstractTimeUnitFuturePrefix", ""},
        {"AbstractTimeUnitFutureSuffix", ""},
        {"AbstractTimeUnitPastPrefix", ""},
        {"AbstractTimeUnitPastSuffix", ""},
        {"AbstractTimeUnitSingularName", ""},
        {"AbstractTimeUnitPluralName", ""}};

    @Override
    public Object[][] getResources() {
        return Resources_VI.OBJECTS;
    }
}
