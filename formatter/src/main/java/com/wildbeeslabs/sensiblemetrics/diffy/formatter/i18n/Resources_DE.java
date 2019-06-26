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
 * Default resources bundle [DE]
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Resources_DE extends Resources {

    private static final Object[][] OBJECTS = new Object[][]{
        {"CenturyPattern", "%n %u"},
        {"CenturyFuturePrefix", "in "},
        {"CenturyFutureSuffix", ""},
        {"CenturyPastPrefix", "vor "},
        {"CenturyPastSuffix", ""},
        {"CenturySingularName", "Jahrhundert"},
        {"CenturyPluralName", "Jahrhunderten"},
        {"DayPattern", "%n %u"},
        {"DayFuturePrefix", "in "},
        {"DayFutureSuffix", ""},
        {"DayPastPrefix", "vor "},
        {"DayPastSuffix", ""},
        {"DaySingularName", "Tag"},
        {"DayPluralName", "Tagen"},
        {"DecadePattern", "%n %u"},
        {"DecadeFuturePrefix", "in "},
        {"DecadeFutureSuffix", ""},
        {"DecadePastPrefix", "vor "},
        {"DecadePastSuffix", ""},
        {"DecadeSingularName", "Jahrzehnt"},
        {"DecadePluralName", "Jahrzehnten"},
        {"HourPattern", "%n %u"},
        {"HourFuturePrefix", "in "},
        {"HourFutureSuffix", ""},
        {"HourPastPrefix", "vor "},
        {"HourPastSuffix", ""},
        {"HourSingularName", "Stunde"},
        {"HourPluralName", "Stunden"},
        {"JustNowPattern", "%u"},
        {"JustNowFuturePrefix", "Jetzt"},
        {"JustNowFutureSuffix", ""},
        {"JustNowPastPrefix", "gerade eben"},
        {"JustNowPastSuffix", ""},
        {"JustNowSingularName", ""},
        {"JustNowPluralName", ""},
        {"MillenniumPattern", "%n %u"},
        {"MillenniumFuturePrefix", "in "},
        {"MillenniumFutureSuffix", ""},
        {"MillenniumPastPrefix", "vor "},
        {"MillenniumPastSuffix", ""},
        {"MillenniumSingularName", "Jahrtausend"},
        {"MillenniumPluralName", "Jahrtausenden"},
        {"MillisecondPattern", "%n %u"},
        {"MillisecondFuturePrefix", "in "},
        {"MillisecondFutureSuffix", ""},
        {"MillisecondPastPrefix", "vor "},
        {"MillisecondPastSuffix", ""},
        {"MillisecondSingularName", "Millisekunde"},
        {"MillisecondPluralName", "Millisekunden"},
        {"MinutePattern", "%n %u"},
        {"MinuteFuturePrefix", "in "},
        {"MinuteFutureSuffix", ""},
        {"MinutePastPrefix", "vor "},
        {"MinutePastSuffix", ""},
        {"MinuteSingularName", "Minute"},
        {"MinutePluralName", "Minuten"},
        {"MonthPattern", "%n %u"},
        {"MonthFuturePrefix", "in "},
        {"MonthFutureSuffix", ""},
        {"MonthPastPrefix", "vor "},
        {"MonthPastSuffix", ""},
        {"MonthSingularName", "Monat"},
        {"MonthPluralName", "Monaten"},
        {"SecondPattern", "%n %u"},
        {"SecondFuturePrefix", "in "},
        {"SecondFutureSuffix", ""},
        {"SecondPastPrefix", "vor "},
        {"SecondPastSuffix", ""},
        {"SecondSingularName", "Sekunde"},
        {"SecondPluralName", "Sekunden"},
        {"WeekPattern", "%n %u"},
        {"WeekFuturePrefix", "in "},
        {"WeekFutureSuffix", ""},
        {"WeekPastPrefix", "vor "},
        {"WeekPastSuffix", ""},
        {"WeekSingularName", "Woche"},
        {"WeekPluralName", "Wochen"},
        {"YearPattern", "%n %u"},
        {"YearFuturePrefix", "in "},
        {"YearFutureSuffix", ""},
        {"YearPastPrefix", "vor "},
        {"YearPastSuffix", ""},
        {"YearSingularName", "Jahr"},
        {"YearPluralName", "Jahren"},
        {"AbstractTimeUnitPattern", ""},
        {"AbstractTimeUnitFuturePrefix", ""},
        {"AbstractTimeUnitFutureSuffix", ""},
        {"AbstractTimeUnitPastPrefix", ""},
        {"AbstractTimeUnitPastSuffix", ""},
        {"AbstractTimeUnitSingularName", ""},
        {"AbstractTimeUnitPluralName", ""}};

    @Override
    protected Object[][] getResources() {
        return Resources_DE.OBJECTS;
    }
}
