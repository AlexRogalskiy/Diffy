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
 * Default resources bundle [CA]
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Resources_CA extends Resources {

    private static final Object[][] OBJECTS = new Object[][]{
        {"CenturyPattern", "%n %u"},
        {"CenturyFuturePrefix", "d'aquí a "},
        {"CenturyFutureSuffix", ""},
        {"CenturyPastPrefix", "fa "},
        {"CenturyPastSuffix", ""},
        {"CenturySingularName", "segle"},
        {"CenturyPluralName", "segles"},
        {"DayPattern", "%n %u"},
        {"DayFuturePrefix", "d'aquí a "},
        {"DayFutureSuffix", ""},
        {"DayPastPrefix", "fa "},
        {"DayPastSuffix", ""},
        {"DaySingularName", "dia"},
        {"DayPluralName", "dies"},
        {"DecadePattern", "%n %u"},
        {"DecadeFuturePrefix", "d'aquí a "},
        {"DecadeFutureSuffix", ""},
        {"DecadePastPrefix", "fa "},
        {"DecadePastSuffix", ""},
        {"DecadeSingularName", "deseni"},
        {"DecadePluralName", "desenis"},
        {"HourPattern", "%n %u"},
        {"HourFuturePrefix", "d'aquí a "},
        {"HourFutureSuffix", ""},
        {"HourPastPrefix", "fa "},
        {"HourPastSuffix", ""},
        {"HourSingularName", "hora"},
        {"HourPluralName", "hores"},
        {"JustNowPattern", "%u"},
        {"JustNowFuturePrefix", ""},
        {"JustNowFutureSuffix", "en un instant"},
        {"JustNowPastPrefix", "fa uns instants"},
        {"JustNowPastSuffix", ""},
        {"JustNowSingularName", ""},
        {"JustNowPluralName", ""},
        {"MillenniumPattern", "%n %u"},
        {"MillenniumFuturePrefix", "d'aquí a "},
        {"MillenniumFutureSuffix", ""},
        {"MillenniumPastPrefix", "fa "},
        {"MillenniumPastSuffix", ""},
        {"MillenniumSingularName", "mileni"},
        {"MillenniumPluralName", "milenis"},
        {"MillisecondPattern", "%n %u"},
        {"MillisecondFuturePrefix", "d'aquí a "},
        {"MillisecondFutureSuffix", ""},
        {"MillisecondPastPrefix", "fa "},
        {"MillisecondPastSuffix", ""},
        {"MillisecondSingularName", "mil·lisegon"},
        {"MillisecondPluralName", "mil·lisegons"},
        {"MinutePattern", "%n %u"},
        {"MinuteFuturePrefix", "d'aquí a "},
        {"MinuteFutureSuffix", ""},
        {"MinutePastPrefix", "fa "},
        {"MinutePastSuffix", ""},
        {"MinuteSingularName", "minut"},
        {"MinutePluralName", "minuts"},
        {"MonthPattern", "%n %u"},
        {"MonthFuturePrefix", "d'aquí a "},
        {"MonthFutureSuffix", ""},
        {"MonthPastPrefix", "fa "},
        {"MonthPastSuffix", ""},
        {"MonthSingularName", "mes"},
        {"MonthPluralName", "mesos"},
        {"SecondPattern", "%n %u"},
        {"SecondFuturePrefix", "d'aquí a "},
        {"SecondFutureSuffix", ""},
        {"SecondPastPrefix", "fa "},
        {"SecondPastSuffix", ""},
        {"SecondSingularName", "segon"},
        {"SecondPluralName", "segons"},
        {"WeekPattern", "%n %u"},
        {"WeekFuturePrefix", "d'aquí a "},
        {"WeekFutureSuffix", ""},
        {"WeekPastPrefix", "fa "},
        {"WeekPastSuffix", ""},
        {"WeekSingularName", "setmana"},
        {"WeekPluralName", "setmanes"},
        {"YearPattern", "%n %u"},
        {"YearFuturePrefix", "d'aquí a "},
        {"YearFutureSuffix", ""},
        {"YearPastPrefix", "fa "},
        {"YearPastSuffix", ""},
        {"YearSingularName", "any"},
        {"YearPluralName", "anys"},
        {"AbstractTimeUnitPattern", ""},
        {"AbstractTimeUnitFuturePrefix", ""},
        {"AbstractTimeUnitFutureSuffix", ""},
        {"AbstractTimeUnitPastPrefix", ""},
        {"AbstractTimeUnitPastSuffix", ""},
        {"AbstractTimeUnitSingularName", ""},
        {"AbstractTimeUnitPluralName", ""}};

    @Override
    protected Object[][] getResources() {
        return Resources_CA.OBJECTS;
    }
}
