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
 * Default resources bundle [AR]
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Resources_AR extends Resources {

    private static final Object[][] OBJECTS = new Object[][]{
        {"CenturyPattern", "%n %u"},
        {"CenturyFuturePrefix", "بعد "},
        {"CenturyFutureSuffix", ""},
        {"CenturyPastPrefix", "منذ "},
        {"CenturyPastSuffix", ""},
        {"CenturySingularName", "قرن"},
        {"CenturyPluralName", "قرون"},
        {"DayPattern", "%n %u"},
        {"DayFuturePrefix", "بعد "},
        {"DayFutureSuffix", ""},
        {"DayPastPrefix", "منذ "},
        {"DayPastSuffix", ""},
        {"DaySingularName", "يوم"},
        {"DayPluralName", "ايام"},
        {"DecadePattern", "%n %u"},
        {"DecadeFuturePrefix", "بعد "},
        {"DecadeFutureSuffix", ""},
        {"DecadePastPrefix", "منذ "},
        {"DecadePastSuffix", ""},
        {"DecadeSingularName", "عقد"},
        {"DecadePluralName", "عقود"},
        {"HourPattern", "%n %u"},
        {"HourFuturePrefix", "بعد "},
        {"HourFutureSuffix", ""},
        {"HourPastPrefix", "منذ "},
        {"HourPastSuffix", ""},
        {"HourSingularName", "ساعة"},
        {"HourPluralName", "ساعات"},
        {"JustNowPattern", "%u"},
        {"JustNowFuturePrefix", ""},
        {"JustNowFutureSuffix", " بعد لحظات"},
        {"JustNowPastPrefix", " منذ لحظات"},
        {"JustNowPastSuffix", ""},
        {"JustNowSingularName", ""},
        {"JustNowPluralName", ""},
        {"MillenniumPattern", "%n %u"},
        {"MillenniumFuturePrefix", "بعد "},
        {"MillenniumFutureSuffix", ""},
        {"MillenniumPastPrefix", "منذ "},
        {"MillenniumPastSuffix", ""},
        {"MillenniumSingularName", "جيل"},
        {"MillenniumPluralName", "اجيال"},
        {"MillisecondPattern", "%n %u"},
        {"MillisecondFuturePrefix", "بعد "},
        {"MillisecondFutureSuffix", ""},
        {"MillisecondPastPrefix", "منذ "},
        {"MillisecondPastSuffix", ""},
        {"MillisecondSingularName", "جزء من الثانية"},
        {"MillisecondPluralName", "اجزاء من الثانية"},
        {"MinutePattern", "%n %u"},
        {"MinuteFuturePrefix", "بعد "},
        {"MinuteFutureSuffix", ""},
        {"MinutePastPrefix", "منذ "},
        {"MinutePastSuffix", ""},
        {"MinuteSingularName", "دقيقة"},
        {"MinutePluralName", "دقائق"},
        {"MonthPattern", "%n %u"},
        {"MonthFuturePrefix", "بعد "},
        {"MonthFutureSuffix", ""},
        {"MonthPastPrefix", "منذ "},
        {"MonthPastSuffix", ""},
        {"MonthSingularName", "شهر"},
        {"MonthPluralName", "أشهر"},
        {"SecondPattern", "%n %u"},
        {"SecondFuturePrefix", "بعد "},
        {"SecondFutureSuffix", ""},
        {"SecondPastPrefix", "منذ "},
        {"SecondPastSuffix", ""},
        {"SecondSingularName", "ثانية"},
        {"SecondPluralName", "ثوان"},
        {"WeekPattern", "%n %u"},
        {"WeekFuturePrefix", "بعد "},
        {"WeekFutureSuffix", ""},
        {"WeekPastPrefix", "منذ "},
        {"WeekPastSuffix", ""},
        {"WeekSingularName", "أسبوع"},
        {"WeekPluralName", "أسابيع"},
        {"YearPattern", "%n %u"},
        {"YearFuturePrefix", "بعد "},
        {"YearFutureSuffix", ""},
        {"YearPastPrefix", "منذ "},
        {"YearPastSuffix", ""},
        {"YearSingularName", "سنة"},
        {"YearPluralName", "سنوات"},
        {"AbstractTimeUnitPattern", ""},
        {"AbstractTimeUnitFuturePrefix", ""},
        {"AbstractTimeUnitFutureSuffix", ""},
        {"AbstractTimeUnitPastPrefix", ""},
        {"AbstractTimeUnitPastSuffix", ""},
        {"AbstractTimeUnitSingularName", ""},
        {"AbstractTimeUnitPluralName", ""}};

    @Override
    public Object[][] getResources() {
        return Resources_AR.OBJECTS;
    }
}
