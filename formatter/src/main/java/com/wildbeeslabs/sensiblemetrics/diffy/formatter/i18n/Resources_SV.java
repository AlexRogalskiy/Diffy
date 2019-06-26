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
 * Default resources bundle [SV]
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Resources_SV extends Resources {

    private static final Object[][] OBJECTS = new Object[][]{
        {"CenturyPattern", "%n %u"},
        {"CenturyFuturePrefix", "om "},
        {"CenturyFutureSuffix", ""},
        {"CenturyPastPrefix", ""},
        {"CenturyPastSuffix", " sedan"},
        {"CenturySingularName", "århundrade"},
        {"CenturyPluralName", "århundraden"},
        {"DayPattern", "%n %u"},
        {"DayFuturePrefix", "om "},
        {"DayFutureSuffix", ""},
        {"DayPastPrefix", ""},
        {"DayPastSuffix", " sedan"},
        {"DaySingularName", "dag"},
        {"DayPluralName", "dagar"},
        {"DecadePattern", "%n %u"},
        {"DecadeFuturePrefix", "om "},
        {"DecadeFutureSuffix", ""},
        {"DecadePastPrefix", ""},
        {"DecadePastSuffix", " sedan"},
        {"DecadeSingularName", "årtionde"},
        {"DecadePluralName", "årtionden"},
        {"HourPattern", "%n %u"},
        {"HourFuturePrefix", "om "},
        {"HourFutureSuffix", ""},
        {"HourPastPrefix", ""},
        {"HourPastSuffix", " sedan"},
        {"HourSingularName", "timme"},
        {"HourPluralName", "timmar"},
        {"JustNowPattern", "%u"},
        {"JustNowFuturePrefix", "om "},
        {"JustNowFutureSuffix", "en stund"},
        {"JustNowPastPrefix", "en stund"},
        {"JustNowPastSuffix", " sedan"},
        {"JustNowSingularName", ""},
        {"JustNowPluralName", ""},
        {"MillenniumPattern", "%n %u"},
        {"MillenniumFuturePrefix", "om "},
        {"MillenniumFutureSuffix", ""},
        {"MillenniumPastPrefix", ""},
        {"MillenniumPastSuffix", " sedan"},
        {"MillenniumSingularName", "årtusende"},
        {"MillenniumPluralName", "årtusenden"},
        {"MillisecondPattern", "%n %u"},
        {"MillisecondFuturePrefix", "om "},
        {"MillisecondFutureSuffix", ""},
        {"MillisecondPastPrefix", ""},
        {"MillisecondPastSuffix", " sedan"},
        {"MillisecondSingularName", "millisekund"},
        {"MillisecondPluralName", "millisekunder"},
        {"MinutePattern", "%n %u"},
        {"MinuteFuturePrefix", "om "},
        {"MinuteFutureSuffix", ""},
        {"MinutePastPrefix", ""},
        {"MinutePastSuffix", " sedan"},
        {"MinuteSingularName", "minut"},
        {"MinutePluralName", "minuter"},
        {"MonthPattern", "%n %u"},
        {"MonthFuturePrefix", "om "},
        {"MonthFutureSuffix", ""},
        {"MonthPastPrefix", ""},
        {"MonthPastSuffix", " sedan"},
        {"MonthSingularName", "månad"},
        {"MonthPluralName", "månader"},
        {"SecondPattern", "%n %u"},
        {"SecondFuturePrefix", "om "},
        {"SecondFutureSuffix", ""},
        {"SecondPastPrefix", ""},
        {"SecondPastSuffix", " sedan"},
        {"SecondSingularName", "sekund"},
        {"SecondPluralName", "sekunder"},
        {"WeekPattern", "%n %u"},
        {"WeekFuturePrefix", "om "},
        {"WeekFutureSuffix", ""},
        {"WeekPastPrefix", ""},
        {"WeekPastSuffix", " sedan"},
        {"WeekSingularName", "vecka"},
        {"WeekPluralName", "veckor"},
        {"YearPattern", "%n %u"},
        {"YearFuturePrefix", "om "},
        {"YearFutureSuffix", ""},
        {"YearPastPrefix", ""},
        {"YearPastSuffix", " sedan"},
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
        return Resources_SV.OBJECTS;
    }
}
