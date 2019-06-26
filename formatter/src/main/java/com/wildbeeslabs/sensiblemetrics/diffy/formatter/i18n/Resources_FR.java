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
 * Default resources bundle [FR]
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Resources_FR extends Resources {

    private static final Object[][] OBJECTS = new Object[][]{
        {"CenturyPattern", "%n %u"},
        {"CenturyFuturePrefix", "dans "},
        {"CenturyFutureSuffix", ""},
        {"CenturyPastPrefix", "il y a "},
        {"CenturyPastSuffix", ""},
        {"CenturySingularName", "siècle"},
        {"CenturyPluralName", "siècles"},
        {"DayPattern", "%n %u"},
        {"DayFuturePrefix", "dans "},
        {"DayFutureSuffix", ""},
        {"DayPastPrefix", "il y a "},
        {"DayPastSuffix", ""},
        {"DaySingularName", "jour"},
        {"DayPluralName", "jours"},
        {"DecadePattern", "%n %u"},
        {"DecadeFuturePrefix", "dans "},
        {"DecadeFutureSuffix", ""},
        {"DecadePastPrefix", "il y a "},
        {"DecadePastSuffix", ""},
        {"DecadeSingularName", "décennie"},
        {"DecadePluralName", "décennies"},
        {"HourPattern", "%n %u"},
        {"HourFuturePrefix", "dans "},
        {"HourFutureSuffix", ""},
        {"HourPastPrefix", "il y a "},
        {"HourPastSuffix", ""},
        {"HourSingularName", "heure"},
        {"HourPluralName", "heures"},
        {"JustNowPattern", "%u"},
        {"JustNowFuturePrefix", ""},
        {"JustNowFutureSuffix", "à l'instant"},
        {"JustNowPastPrefix", "à l'instant"},
        {"JustNowPastSuffix", ""},
        {"JustNowSingularName", ""},
        {"JustNowPluralName", ""},
        {"MillenniumPattern", "%n %u"},
        {"MillenniumFuturePrefix", "dans "},
        {"MillenniumFutureSuffix", ""},
        {"MillenniumPastPrefix", "il y a "},
        {"MillenniumPastSuffix", ""},
        {"MillenniumSingularName", "millénaire"},
        {"MillenniumPluralName", "millénaires"},
        {"MillisecondPattern", "%n %u"},
        {"MillisecondFuturePrefix", "dans "},
        {"MillisecondFutureSuffix", ""},
        {"MillisecondPastPrefix", "il y a "},
        {"MillisecondPastSuffix", ""},
        {"MillisecondSingularName", "milliseconde"},
        {"MillisecondPluralName", "millisecondes"},
        {"MinutePattern", "%n %u"},
        {"MinuteFuturePrefix", "dans "},
        {"MinuteFutureSuffix", ""},
        {"MinutePastPrefix", "il y a "},
        {"MinutePastSuffix", ""},
        {"MinuteSingularName", "minute"},
        {"MinutePluralName", "minutes"},
        {"MonthPattern", "%n %u"},
        {"MonthFuturePrefix", "dans "},
        {"MonthFutureSuffix", ""},
        {"MonthPastPrefix", "il y a "},
        {"MonthPastSuffix", ""},
        {"MonthSingularName", "mois"},
        {"MonthPluralName", "mois"},
        {"SecondPattern", "%n %u"},
        {"SecondFuturePrefix", "dans "},
        {"SecondFutureSuffix", ""},
        {"SecondPastPrefix", "il y a "},
        {"SecondPastSuffix", ""},
        {"SecondSingularName", "seconde"},
        {"SecondPluralName", "secondes"},
        {"WeekPattern", "%n %u"},
        {"WeekFuturePrefix", "dans "},
        {"WeekFutureSuffix", ""},
        {"WeekPastPrefix", "il y a "},
        {"WeekPastSuffix", ""},
        {"WeekSingularName", "semaine"},
        {"WeekPluralName", "semaines"},
        {"YearPattern", "%n %u"},
        {"YearFuturePrefix", "dans "},
        {"YearFutureSuffix", ""},
        {"YearPastPrefix", "il y a "},
        {"YearPastSuffix", ""},
        {"YearSingularName", "an"},
        {"YearPluralName", "ans"},
        {"AbstractTimeUnitPattern", ""},
        {"AbstractTimeUnitFuturePrefix", ""},
        {"AbstractTimeUnitFutureSuffix", ""},
        {"AbstractTimeUnitPastPrefix", ""},
        {"AbstractTimeUnitPastSuffix", ""},
        {"AbstractTimeUnitSingularName", ""},
        {"AbstractTimeUnitPluralName", ""}};

    @Override
    protected Object[][] getResources() {
        return Resources_FR.OBJECTS;
    }
}
