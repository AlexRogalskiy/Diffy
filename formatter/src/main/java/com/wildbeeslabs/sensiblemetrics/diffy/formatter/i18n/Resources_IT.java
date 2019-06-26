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
 * Default resources bundle [IT]
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Resources_IT extends Resources {

    private static final Object[][] OBJECTS = new Object[][]{
        {"CenturyPattern", "%n %u"},
        {"CenturyFuturePrefix", "fra"},
        {"CenturyFutureSuffix", ""},
        {"CenturyPastPrefix", ""},
        {"CenturyPastSuffix", "fa"},
        {"CenturySingularName", "secolo"},
        {"CenturyPluralName", "secoli"},
        {"DayPattern", "%n %u"},
        {"DayFuturePrefix", "fra"},
        {"DayFutureSuffix", ""},
        {"DayPastPrefix", ""},
        {"DayPastSuffix", "fa"},
        {"DaySingularName", "giorno"},
        {"DayPluralName", "giorni"},
        {"DayFutureSingularName", "giorno"},
        {"DayFuturePluralName", "giorni"},
        {"DayPastSingularName", "giorno"},
        {"DayPastPluralName", "giorni"},
        {"DecadePattern", "%n %u"},
        {"DecadeFuturePrefix", "fra"},
        {"DecadeFutureSuffix", ""},
        {"DecadePastPrefix", ""},
        {"DecadePastSuffix", "fa"},
        {"DecadeSingularName", "decennio"},
        {"DecadePluralName", "decenni"},
        {"HourPattern", "%n %u"},
        {"HourFuturePrefix", "fra"},
        {"HourFutureSuffix", ""},
        {"HourPastPrefix", ""},
        {"HourPastSuffix", "fa"},
        {"HourSingularName", "ora"},
        {"HourPluralName", "ore"},
        {"JustNowPattern", "%u"},
        {"JustNowFuturePrefix", ""},
        {"JustNowFutureSuffix", "fra poco"},
        {"JustNowPastPrefix", "poco fa"},
        {"JustNowPastSuffix", ""},
        {"JustNowSingularName", ""},
        {"JustNowPluralName", ""},
        {"MillenniumPattern", "%n %u"},
        {"MillenniumFuturePrefix", "fra"},
        {"MillenniumFutureSuffix", ""},
        {"MillenniumPastPrefix", ""},
        {"MillenniumPastSuffix", "fa"},
        {"MillenniumSingularName", "millennio"},
        {"MillenniumPluralName", "millenni"},
        {"MillisecondPattern", "%n %u"},
        {"MillisecondFuturePrefix", "fra"},
        {"MillisecondFutureSuffix", ""},
        {"MillisecondPastPrefix", ""},
        {"MillisecondPastSuffix", "fa"},
        {"MillisecondSingularName", "millisecondo"},
        {"MillisecondPluralName", "millisecondi"},
        {"MinutePattern", "%n %u"},
        {"MinuteFuturePrefix", "fra"},
        {"MinuteFutureSuffix", ""},
        {"MinutePastPrefix", ""},
        {"MinutePastSuffix", "fa"},
        {"MinuteSingularName", "minuto"},
        {"MinutePluralName", "minuti"},
        {"MonthPattern", "%n %u"},
        {"MonthFuturePrefix", "fra"},
        {"MonthFutureSuffix", ""},
        {"MonthPastPrefix", ""},
        {"MonthPastSuffix", "fa"},
        {"MonthSingularName", "mese"},
        {"MonthPluralName", "mesi"},
        {"SecondPattern", "%n %u"},
        {"SecondFuturePrefix", "fra"},
        {"SecondFutureSuffix", ""},
        {"SecondPastPrefix", ""},
        {"SecondPastSuffix", "fa"},
        {"SecondSingularName", "secondo"},
        {"SecondPluralName", "secondi"},
        {"WeekPattern", "%n %u"},
        {"WeekFuturePrefix", "fra"},
        {"WeekFutureSuffix", ""},
        {"WeekPastPrefix", ""},
        {"WeekPastSuffix", "fa"},
        {"WeekSingularName", "settimana"},
        {"WeekPluralName", "settimane"},
        {"YearPattern", "%n %u"},
        {"YearFuturePrefix", "fra"},
        {"YearFutureSuffix", ""},
        {"YearPastPrefix", ""},
        {"YearPastSuffix", "fa"},
        {"YearSingularName", "anno"},
        {"YearPluralName", "anni"},
        {"AbstractTimeUnitPattern", ""},
        {"AbstractTimeUnitFuturePrefix", ""},
        {"AbstractTimeUnitFutureSuffix", ""},
        {"AbstractTimeUnitPastPrefix", ""},
        {"AbstractTimeUnitPastSuffix", ""},
        {"AbstractTimeUnitSingularName", ""},
        {"AbstractTimeUnitPluralName", ""}};

    @Override
    protected Object[][] getResources() {
        return Resources_IT.OBJECTS;
    }
}
