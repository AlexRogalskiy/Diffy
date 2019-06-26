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
 * Default resources bundle [PL]
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Resources_PL extends Resources {

    private static final Object[][] OBJECTS = new Object[][]{
        {"CenturyPattern", "%n %u"},
        {"CenturyFuturePrefix", "za "},
        {"CenturyFutureSuffix", ""},
        {"CenturyPastPrefix", ""},
        {"CenturyPastSuffix", " temu"},
        {"CenturySingularName", "wiek"},
        {"CenturyPluralName", "wiek(i/ów)"},
        {"DayPattern", "%n %u"},
        {"DayFuturePrefix", "za "},
        {"DayFutureSuffix", ""},
        {"DayPastPrefix", ""},
        {"DayPastSuffix", " temu"},
        {"DaySingularName", "dzień"},
        {"DayPluralName", "dni"},
        {"DecadePattern", "%n %u"},
        {"DecadeFuturePrefix", "za "},
        {"DecadeFutureSuffix", ""},
        {"DecadePastPrefix", ""},
        {"DecadePastSuffix", " temu"},
        {"DecadeSingularName", "dekadę"},
        {"DecadePluralName", "dekad"},
        {"HourPattern", "%n %u"},
        {"HourFuturePrefix", "za "},
        {"HourFutureSuffix", ""},
        {"HourPastPrefix", ""},
        {"HourPastSuffix", " temu"},
        {"HourSingularName", "godz."},
        {"HourPluralName", "godz."},
        {"JustNowPattern", "%u"},
        {"JustNowFuturePrefix", ""},
        {"JustNowFutureSuffix", "za chwilę"},
        {"JustNowPastPrefix", "przed chwilą"},
        {"JustNowPastSuffix", ""},
        {"JustNowSingularName", ""},
        {"JustNowPluralName", ""},
        {"MillenniumPattern", "%n %u"},
        {"MillenniumFuturePrefix", "za "},
        {"MillenniumFutureSuffix", ""},
        {"MillenniumPastPrefix", ""},
        {"MillenniumPastSuffix", " temu"},
        {"MillenniumSingularName", "milenium"},
        {"MillenniumPluralName", "milenia"},
        {"MillisecondPattern", "%n %u"},
        {"MillisecondFuturePrefix", "za "},
        {"MillisecondFutureSuffix", ""},
        {"MillisecondPastPrefix", ""},
        {"MillisecondPastSuffix", " temu"},
        {"MillisecondSingularName", "milisek."},
        {"MillisecondPluralName", "milisek."},
        {"MinutePattern", "%n %u"},
        {"MinuteFuturePrefix", "za "},
        {"MinuteFutureSuffix", ""},
        {"MinutePastPrefix", ""},
        {"MinutePastSuffix", " temu"},
        {"MinuteSingularName", "min"},
        {"MinutePluralName", "min"},
        {"MonthPattern", "%n %u"},
        {"MonthFuturePrefix", "za "},
        {"MonthFutureSuffix", ""},
        {"MonthPastPrefix", ""},
        {"MonthPastSuffix", " temu"},
        {"MonthSingularName", "mies."},
        {"MonthPluralName", "mies."},
        {"SecondPattern", "%n %u"},
        {"SecondFuturePrefix", "za "},
        {"SecondFutureSuffix", ""},
        {"SecondPastPrefix", ""},
        {"SecondPastSuffix", " temu"},
        {"SecondSingularName", "sek."},
        {"SecondPluralName", "sek."},
        {"WeekPattern", "%n %u"},
        {"WeekFuturePrefix", "za "},
        {"WeekFutureSuffix", ""},
        {"WeekPastPrefix", ""},
        {"WeekPastSuffix", " temu"},
        {"WeekSingularName", "tydzień"},
        {"WeekPluralName", "tygodni(e)"},
        {"YearPattern", "%n %u"},
        {"YearFuturePrefix", "za "},
        {"YearFutureSuffix", ""},
        {"YearPastPrefix", ""},
        {"YearPastSuffix", " temu"},
        {"YearSingularName", "rok"},
        {"YearPluralName", "lat(a)"},
        {"AbstractTimeUnitPattern", ""},
        {"AbstractTimeUnitFuturePrefix", ""},
        {"AbstractTimeUnitFutureSuffix", ""},
        {"AbstractTimeUnitPastPrefix", ""},
        {"AbstractTimeUnitPastSuffix", ""},
        {"AbstractTimeUnitSingularName", ""},
        {"AbstractTimeUnitPluralName", ""}};

    @Override
    public Object[][] getResources() {
        return Resources_PL.OBJECTS;
    }
}
