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
 * Default resources bundle [GL]
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Resources_GL extends Resources {

    private static final Object[][] OBJECTS = new Object[][]{
        {"CenturyPattern", "%n %u"},
        {"CenturyFuturePrefix", "en "},
        {"CenturyFutureSuffix", ""},
        {"CenturyPastPrefix", "fai "},
        {"CenturyPastSuffix", ""},
        {"CenturySingularName", "século"},
        {"CenturyPluralName", "séculos"},
        {"DayPattern", "%n %u"},
        {"DayFuturePrefix", "en"},
        {"DayFutureSuffix", ""},
        {"DayPastPrefix", "fai "},
        {"DayPastSuffix", ""},
        {"DaySingularName", "día "},
        {"DayPluralName", "días"},
        {"DecadePattern", "%acn %u"},
        {"DecadeFuturePrefix", "en "},
        {"DecadeFutureSuffix", ""},
        {"DecadePastPrefix", "fai "},
        {"DecadePastSuffix", ""},
        {"DecadeSingularName", "década"},
        {"DecadePluralName", "décadas"},
        {"HourPattern", "%n %u"},
        {"HourFuturePrefix", "en "},
        {"HourFutureSuffix", ""},
        {"HourPastPrefix", "fai "},
        {"HourPastSuffix", ""},
        {"HourSingularName", "hora"},
        {"HourPluralName", "horas"},
        {"JustNowPattern", "%u"},
        {"JustNowFuturePrefix", ""},
        {"JustNowFutureSuffix", "fai un momento"},
        {"JustNowPastPrefix", "hai momentos"},
        {"JustNowPastSuffix", ""},
        {"JustNowSingularName", ""},
        {"JustNowPluralName", ""},
        {"MillenniumPattern", "%n %u"},
        {"MillenniumFuturePrefix", "en "},
        {"MillenniumFutureSuffix", ""},
        {"MillenniumPastPrefix", "fai "},
        {"MillenniumPastSuffix", ""},
        {"MillenniumSingularName", "milenio"},
        {"MillenniumPluralName", "milenios"},
        {"MillisecondPattern", "%n %u"},
        {"MillisecondFuturePrefix", "en "},
        {"MillisecondFutureSuffix", ""},
        {"MillisecondPastPrefix", "fai "},
        {"MillisecondPastSuffix", ""},
        {"MillisecondSingularName", "milisegundo"},
        {"MillisecondPluralName", "milisegundos"},
        {"MinutePattern", "%n %u"},
        {"MinuteFuturePrefix", "en "},
        {"MinuteFutureSuffix", ""},
        {"MinutePastPrefix", "fai "},
        {"MinutePastSuffix", ""},
        {"MinuteSingularName", "minuto"},
        {"MinutePluralName", "minutos"},
        {"MonthPattern", "%n %u"},
        {"MonthFuturePrefix", "en "},
        {"MonthFutureSuffix", ""},
        {"MonthPastPrefix", "fai "},
        {"MonthPastSuffix", ""},
        {"MonthSingularName", "mes"},
        {"MonthPluralName", "meses"},
        {"SecondPattern", "%n %u"},
        {"SecondFuturePrefix", "en "},
        {"SecondFutureSuffix", ""},
        {"SecondPastPrefix", "fai "},
        {"SecondPastSuffix", ""},
        {"SecondSingularName", "segundo"},
        {"SecondPluralName", "segundos"},
        {"WeekPattern", "%n %u"},
        {"WeekFuturePrefix", "en "},
        {"WeekFutureSuffix", ""},
        {"WeekPastPrefix", "fai "},
        {"WeekPastSuffix", ""},
        {"WeekSingularName", "semana"},
        {"WeekPluralName", "semanas"},
        {"YearPattern", "%n %u"},
        {"YearFuturePrefix", "en "},
        {"YearFutureSuffix", ""},
        {"YearPastPrefix", "fai "},
        {"YearPastSuffix", ""},
        {"YearSingularName", "ano"},
        {"YearPluralName", "anos"},
        {"AbstractTimeUnitPattern", ""},
        {"AbstractTimeUnitFuturePrefix", ""},
        {"AbstractTimeUnitFutureSuffix", ""},
        {"AbstractTimeUnitPastPrefix", ""},
        {"AbstractTimeUnitPastSuffix", ""},
        {"AbstractTimeUnitSingularName", ""},
        {"AbstractTimeUnitPluralName", ""}};

    @Override
    protected Object[][] getResources() {
        return Resources_GL.OBJECTS;
    }
}
