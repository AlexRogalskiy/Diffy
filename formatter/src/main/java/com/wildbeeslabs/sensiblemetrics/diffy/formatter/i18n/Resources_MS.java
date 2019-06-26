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
 * Default resources bundle [MS]
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Resources_MS extends Resources {

    private static final Object[][] OBJECTS = new Object[][]{
        {"CenturyPattern", "%n %u"},
        {"CenturyFuturePrefix", ""},
        {"CenturyFutureSuffix", "kemudian"},
        {"CenturyPastPrefix", ""},
        {"CenturyPastSuffix", "yang lalu"},
        {"CenturySingularName", "abad"},
        {"CenturyPluralName", "abad"},
        {"DayPattern", "%n %u"},
        {"DayFuturePrefix", ""},
        {"DayFutureSuffix", "kemudian"},
        {"DayPastPrefix", ""},
        {"DayPastSuffix", "yang lalu"},
        {"DaySingularName", "hari"},
        {"DayPluralName", "hari"},
        {"DecadePattern", "%n%u"},
        {"DecadeFuturePrefix", ""},
        {"DecadeFutureSuffix", "kemudian"},
        {"DecadePastPrefix", ""},
        {"DecadePastSuffix", "yang lalu"},
        {"DecadeSingularName", "0 tahun"},
        {"DecadePluralName", "0 tahun"},
        {"HourPattern", "%n %u"},
        {"HourFuturePrefix", ""},
        {"HourFutureSuffix", "kemudian"},
        {"HourPastPrefix", ""},
        {"HourPastSuffix", "yang lalu"},
        {"HourSingularName", "jam"},
        {"HourPluralName", "jam"},
        {"JustNowPattern", "%u"},
        {"JustNowFuturePrefix", ""},
        {"JustNowFutureSuffix", "tadi"},
        {"JustNowPastPrefix", "tadi"},
        {"JustNowPastSuffix", ""},
        {"JustNowSingularName", ""},
        {"JustNowPluralName", ""},
        {"MillenniumPattern", "%n %u"},
        {"MillenniumFuturePrefix", ""},
        {"MillenniumFutureSuffix", "kemudian"},
        {"MillenniumPastPrefix", ""},
        {"MillenniumPastSuffix", "yang lalu"},
        {"MillenniumSingularName", "millennium"},
        {"MillenniumPluralName", "millennium"},
        {"MillisecondPattern", "%n %u"},
        {"MillisecondFuturePrefix", ""},
        {"MillisecondFutureSuffix", "kemudian"},
        {"MillisecondPastPrefix", ""},
        {"MillisecondPastSuffix", "yang lalu"},
        {"MillisecondSingularName", "milisaat"},
        {"MillisecondPluralName", "milisaat"},
        {"MinutePattern", "%n %u"},
        {"MinuteFuturePrefix", ""},
        {"MinuteFutureSuffix", "kemudian"},
        {"MinutePastPrefix", ""},
        {"MinutePastSuffix", "yang lalu"},
        {"MinuteSingularName", "minit"},
        {"MinutePluralName", "minit"},
        {"MonthPattern", "%n %u"},
        {"MonthFuturePrefix", ""},
        {"MonthFutureSuffix", "kemudian"},
        {"MonthPastPrefix", ""},
        {"MonthPastSuffix", "yang lalu"},
        {"MonthSingularName", "bulan"},
        {"MonthPluralName", "bulan"},
        {"SecondPattern", "%n %u"},
        {"SecondFuturePrefix", ""},
        {"SecondFutureSuffix", "kemudian"},
        {"SecondPastPrefix", ""},
        {"SecondPastSuffix", "yang lalu"},
        {"SecondSingularName", "saat"},
        {"SecondPluralName", "saat"},
        {"WeekPattern", "%n %u"},
        {"WeekFuturePrefix", ""},
        {"WeekFutureSuffix", "kemudian"},
        {"WeekPastPrefix", ""},
        {"WeekPastSuffix", "yang lalu"},
        {"WeekSingularName", "minggu"},
        {"WeekPluralName", "minggu"},
        {"YearPattern", "%n %u"},
        {"YearFuturePrefix", ""},
        {"YearFutureSuffix", "kemudian"},
        {"YearPastPrefix", ""},
        {"YearPastSuffix", "yang lalu"},
        {"YearSingularName", "tahun"},
        {"YearPluralName", "tahun"},
        {"AbstractTimeUnitPattern", ""},
        {"AbstractTimeUnitFuturePrefix", ""},
        {"AbstractTimeUnitFutureSuffix", ""},
        {"AbstractTimeUnitPastPrefix", ""},
        {"AbstractTimeUnitPastSuffix", ""},
        {"AbstractTimeUnitSingularName", ""},
        {"AbstractTimeUnitPluralName", ""}};

    @Override
    protected Object[][] getResources() {
        return Resources_MS.OBJECTS;
    }
}
