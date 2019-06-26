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
 * Default resources bundle [DA]
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Resources_DA extends Resources {

    private static final Object[][] OBJECTS = new Object[][]{
        {"CenturyPattern", "%n %u"},
        {"CenturyFuturePrefix", ""},
        {"CenturyFutureSuffix", " fra nu"},
        {"CenturyPastPrefix", ""},
        {"CenturyPastSuffix", " siden"},
        {"CenturySingularName", "århundrede"},
        {"CenturyPluralName", "århundreder"},
        {"DayPattern", "%n %u"},
        {"DayFuturePrefix", "om "},
        {"DayFutureSuffix", ""},
        {"DayPastPrefix", ""},
        {"DayPastSuffix", " siden"},
        {"DaySingularName", "dag"},
        {"DayPluralName", "dage"},
        {"DecadePattern", "%n %u"},
        {"DecadeFuturePrefix", ""},
        {"DecadeFutureSuffix", " fra nu"},
        {"DecadePastPrefix", ""},
        {"DecadePastSuffix", " siden"},
        {"DecadeSingularName", "årti"},
        {"DecadePluralName", "årtier"},
        {"HourPattern", "%n %u"},
        {"HourFuturePrefix", "om "},
        {"HourFutureSuffix", ""},
        {"HourPastPrefix", ""},
        {"HourPastSuffix", " siden"},
        {"HourSingularName", "time"},
        {"HourPluralName", "timer"},
        {"JustNowPattern", "%u"},
        {"JustNowFuturePrefix", "straks"},
        {"JustNowFutureSuffix", ""},
        {"JustNowPastPrefix", "et øjeblik siden"},
        {"JustNowPastSuffix", ""},
        {"JustNowSingularName", ""},
        {"JustNowPluralName", ""},
        {"MillenniumPattern", "%n %u"},
        {"MillenniumFuturePrefix", ""},
        {"MillenniumFutureSuffix", " fra nu"},
        {"MillenniumPastPrefix", ""},
        {"MillenniumPastSuffix", " siden"},
        {"MillenniumSingularName", "millennium"},
        {"MillenniumPluralName", "millennier"},
        {"MillisecondPattern", "%n %u"},
        {"MillisecondFuturePrefix", "om "},
        {"MillisecondFutureSuffix", ""},
        {"MillisecondPastPrefix", ""},
        {"MillisecondPastSuffix", " siden"},
        {"MillisecondSingularName", "millisekund"},
        {"MillisecondPluralName", "millisekunder"},
        {"MinutePattern", "%n %u"},
        {"MinuteFuturePrefix", "om "},
        {"MinuteFutureSuffix", ""},
        {"MinutePastPrefix", ""},
        {"MinutePastSuffix", " siden"},
        {"MinuteSingularName", "minut"},
        {"MinutePluralName", "minutter"},
        {"MonthPattern", "%n %u"},
        {"MonthFuturePrefix", "om "},
        {"MonthFutureSuffix", ""},
        {"MonthPastPrefix", ""},
        {"MonthPastSuffix", " siden"},
        {"MonthSingularName", "måned"},
        {"MonthPluralName", "måneder"},
        {"SecondPattern", "%n %u"},
        {"SecondFuturePrefix", "om "},
        {"SecondFutureSuffix", ""},
        {"SecondPastPrefix", ""},
        {"SecondPastSuffix", " siden"},
        {"SecondSingularName", "sekund"},
        {"SecondPluralName", "sekunder"},
        {"WeekPattern", "%n %u"},
        {"WeekFuturePrefix", "om "},
        {"WeekFutureSuffix", ""},
        {"WeekPastPrefix", ""},
        {"WeekPastSuffix", " siden"},
        {"WeekSingularName", "uge"},
        {"WeekPluralName", "uger"},
        {"YearPattern", "%n %u"},
        {"YearFuturePrefix", "om "},
        {"YearFutureSuffix", ""},
        {"YearPastPrefix", ""},
        {"YearPastSuffix", " siden"},
        {"YearSingularName", "år"},
        {"YearPluralName", "år"},
        {"AbstractTimeUnitPattern", ""},
        {"AbstractTimeUnitFuturePrefix", ""},
        {"AbstractTimeUnitFutureSuffix", ""},
        {"AbstractTimeUnitPastPrefix", ""},
        {"AbstractTimeUnitPastSuffix", ""},
        {"AbstractTimeUnitSingularName", ""},
        {"AbstractTimeUnitPluralName", ""}};

    @Override
    public Object[][] getResources() {
        return Resources_DA.OBJECTS;
    }
}
