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
 * Default resources bundle [NL]
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Resources_NL extends Resources {

    private static final Object[][] OBJECTS = new Object[][]{
        {"CenturyPattern", "%n %u"},
        {"CenturyFuturePrefix", "over "},
        {"CenturyFutureSuffix", ""},
        {"CenturyPastPrefix", ""},
        {"CenturyPastSuffix", "geleden"},
        {"CenturySingularName", "eeuw"},
        {"CenturyPluralName", "eeuwen"},
        {"DayPattern", "%n %u"},
        {"DayFuturePrefix", "over "},
        {"DayFutureSuffix", ""},
        {"DayPastPrefix", ""},
        {"DayPastSuffix", "geleden"},
        {"DaySingularName", "dag"},
        {"DayPluralName", "dagen"},
        {"DecadePattern", "%n %u"},
        {"DecadeFuturePrefix", "over "},
        {"DecadeFutureSuffix", ""},
        {"DecadePastPrefix", ""},
        {"DecadePastSuffix", "geleden"},
        {"DecadeSingularName", "decennium"},
        {"DecadePluralName", "decennia"},
        {"HourPattern", "%n %u"},
        {"HourFuturePrefix", "over "},
        {"HourFutureSuffix", ""},
        {"HourPastPrefix", ""},
        {"HourPastSuffix", "geleden"},
        {"HourSingularName", "uur"},
        {"HourPluralName", "uur"},
        {"JustNowPattern", "%u"},
        {"JustNowFuturePrefix", "op dit moment"},
        {"JustNowFutureSuffix", ""},
        {"JustNowPastPrefix", "een ogenblik geleden"},
        {"JustNowPastSuffix", ""},
        {"JustNowSingularName", ""},
        {"JustNowPluralName", ""},
        {"MillenniumPattern", "%n %u"},
        {"MillenniumFuturePrefix", "over "},
        {"MillenniumFutureSuffix", ""},
        {"MillenniumPastPrefix", ""},
        {"MillenniumPastSuffix", "geleden"},
        {"MillenniumSingularName", "millennium"},
        {"MillenniumPluralName", "millennia"},
        {"MillisecondPattern", "%n %u"},
        {"MillisecondFuturePrefix", "over "},
        {"MillisecondFutureSuffix", ""},
        {"MillisecondPastPrefix", ""},
        {"MillisecondPastSuffix", "geleden"},
        {"MillisecondSingularName", "milliseconde"},
        {"MillisecondPluralName", "milliseconden"},
        {"MinutePattern", "%n %u"},
        {"MinuteFuturePrefix", "over "},
        {"MinuteFutureSuffix", ""},
        {"MinutePastPrefix", ""},
        {"MinutePastSuffix", "geleden"},
        {"MinuteSingularName", "minuut"},
        {"MinutePluralName", "minuten"},
        {"MonthPattern", "%n %u"},
        {"MonthFuturePrefix", "over "},
        {"MonthFutureSuffix", ""},
        {"MonthPastPrefix", ""},
        {"MonthPastSuffix", "geleden"},
        {"MonthSingularName", "maand"},
        {"MonthPluralName", "maanden"},
        {"SecondPattern", "%n %u"},
        {"SecondFuturePrefix", "over "},
        {"SecondFutureSuffix", ""},
        {"SecondPastPrefix", ""},
        {"SecondPastSuffix", "geleden"},
        {"SecondSingularName", "seconde"},
        {"SecondPluralName", "seconden"},
        {"WeekPattern", "%n %u"},
        {"WeekFuturePrefix", "over "},
        {"WeekFutureSuffix", ""},
        {"WeekPastPrefix", ""},
        {"WeekPastSuffix", "geleden"},
        {"WeekSingularName", "week"},
        {"WeekPluralName", "weken"},
        {"YearPattern", "%n %u"},
        {"YearFuturePrefix", "over "},
        {"YearFutureSuffix", ""},
        {"YearPastPrefix", ""},
        {"YearPastSuffix", "geleden"},
        {"YearSingularName", "jaar"},
        {"YearPluralName", "jaar"},
        {"AbstractTimeUnitPattern", ""},
        {"AbstractTimeUnitFuturePrefix", ""},
        {"AbstractTimeUnitFutureSuffix", ""},
        {"AbstractTimeUnitPastPrefix", ""},
        {"AbstractTimeUnitPastSuffix", ""},
        {"AbstractTimeUnitSingularName", ""},
        {"AbstractTimeUnitPluralName", ""}};

    @Override
    protected Object[][] getResources() {
        return Resources_NL.OBJECTS;
    }
}
