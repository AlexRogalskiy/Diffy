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
 * Default resources bundle [BG]
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Resources_BG extends Resources {

    private static final Object[][] OBJECTS = new Object[][]{
        {"CenturyPattern", "%n %u"},
        {"CenturyFuturePrefix", "след "},
        {"CenturyFutureSuffix", ""},
        {"CenturyPastPrefix", "преди "},
        {"CenturyPastSuffix", ""},
        {"CenturySingularName", "век"},
        {"CenturyPluralName", "века"},
        {"DayPattern", "%n %u"},
        {"DayFuturePrefix", "след "},
        {"DayFutureSuffix", ""},
        {"DayPastPrefix", "преди "},
        {"DayPastSuffix", ""},
        {"DaySingularName", "ден"},
        {"DayPluralName", "дни"},
        {"DecadePattern", "%n %u"},
        {"DecadeFuturePrefix", "след "},
        {"DecadeFutureSuffix", ""},
        {"DecadePastPrefix", "преди "},
        {"DecadePastSuffix", ""},
        {"DecadeSingularName", "десетилетие"},
        {"DecadePluralName", "десетилетия"},
        {"HourPattern", "%n %u"},
        {"HourFuturePrefix", "след "},
        {"HourFutureSuffix", ""},
        {"HourPastPrefix", "преди "},
        {"HourPastSuffix", ""},
        {"HourSingularName", "час"},
        {"HourPluralName", "часа"},
        {"JustNowPattern", "%u"},
        {"JustNowFuturePrefix", ""},
        {"JustNowFutureSuffix", "в момента"},
        {"JustNowPastPrefix", "току що"},
        {"JustNowPastSuffix", ""},
        {"JustNowSingularName", ""},
        {"JustNowPluralName", ""},
        {"MillenniumPattern", "%n %u"},
        {"MillenniumFuturePrefix", "след "},
        {"MillenniumFutureSuffix", ""},
        {"MillenniumPastPrefix", "преди "},
        {"MillenniumPastSuffix", ""},
        {"MillenniumSingularName", "хилядолетие"},
        {"MillenniumPluralName", "хилядолетия"},
        {"MillisecondPattern", "%n %u"},
        {"MillisecondFuturePrefix", "след "},
        {"MillisecondFutureSuffix", ""},
        {"MillisecondPastPrefix", "преди "},
        {"MillisecondPastSuffix", ""},
        {"MillisecondSingularName", "милисекунда"},
        {"MillisecondPluralName", "милисекунди"},
        {"MinutePattern", "%n %u"},
        {"MinuteFuturePrefix", "след "},
        {"MinuteFutureSuffix", ""},
        {"MinutePastPrefix", "преди "},
        {"MinutePastSuffix", ""},
        {"MinuteSingularName", "минута"},
        {"MinutePluralName", "минути"},
        {"MonthPattern", "%n %u"},
        {"MonthFuturePrefix", "след "},
        {"MonthFutureSuffix", ""},
        {"MonthPastPrefix", "преди "},
        {"MonthPastSuffix", ""},
        {"MonthSingularName", "месец"},
        {"MonthPluralName", "месеца"},
        {"SecondPattern", "%n %u"},
        {"SecondFuturePrefix", "след "},
        {"SecondFutureSuffix", ""},
        {"SecondPastPrefix", "преди "},
        {"SecondPastSuffix", ""},
        {"SecondSingularName", "секунда"},
        {"SecondPluralName", "секунди"},
        {"WeekPattern", "%n %u"},
        {"WeekFuturePrefix", "след "},
        {"WeekFutureSuffix", ""},
        {"WeekPastPrefix", "преди "},
        {"WeekPastSuffix", ""},
        {"WeekSingularName", "седмица"},
        {"WeekPluralName", "седмици"},
        {"YearPattern", "%n %u"},
        {"YearFuturePrefix", "след "},
        {"YearFutureSuffix", ""},
        {"YearPastPrefix", "преди "},
        {"YearPastSuffix", ""},
        {"YearSingularName", "година"},
        {"YearPluralName", "години"},
        {"AbstractTimeUnitPattern", ""},
        {"AbstractTimeUnitFuturePrefix", ""},
        {"AbstractTimeUnitFutureSuffix", ""},
        {"AbstractTimeUnitPastPrefix", ""},
        {"AbstractTimeUnitPastSuffix", ""},
        {"AbstractTimeUnitSingularName", ""},
        {"AbstractTimeUnitPluralName", ""}};

    @Override
    public Object[][] getResources() {
        return Resources_BG.OBJECTS;
    }
}
