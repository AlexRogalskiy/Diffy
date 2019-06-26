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
 * Default resources bundle [MM]
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Resources_MM extends Resources {

    private static final Object[][] OBJECTS = new Object[][]{
        {"CenturyPattern", "%n %u"},
        {"CenturyFuturePrefix", " ယခုမှ"},
        {"CenturyFutureSuffix", " အကြာ"},
        {"CenturyPastPrefix", ""},
        {"CenturyPastSuffix", " အကြာက"},
        {"CenturySingularName", "ရာစု "},
        {"CenturyPluralName", " ရာစု "},
        {"DayPattern", "%n %u"},
        {"DayFuturePrefix", " ယခုမှ"},
        {"DayFutureSuffix", " အကြာ"},
        {"DayPastPrefix", ""},
        {"DayPastSuffix", " အကြာက"},
        {"DaySingularName", "ရက်"},
        {"DayPluralName", "ရက်"},
        {"DecadePattern", "%u %n"},
        {"DecadeFuturePrefix", " ယခုမှ"},
        {"DecadeFutureSuffix", " နှစ်အကြာ"},
        {"DecadePastPrefix", ""},
        {"DecadePastSuffix", " နှစ်အကြာက"},
        {"DecadeSingularName", "ဆယ်စုနှစ်"},
        {"DecadePluralName", "ဆယ်စုနှစ်"},
        {"HourPattern", "%n %u"},
        {"HourFuturePrefix", " ယခုမှ"},
        {"HourFutureSuffix", " အကြာ"},
        {"HourPastPrefix", ""},
        {"HourPastSuffix", " အကြာက"},
        {"HourSingularName", "နာရီ"},
        {"HourPluralName", "နာရီ"},
        {"JustNowPattern", "%u"},
        {"JustNowFuturePrefix", " ခေတ္တ"},
        {"JustNowFutureSuffix", " မကြာမီ"},
        {"JustNowPastPrefix", " အခု"},
        {"JustNowPastSuffix", " လေးတင်"},
        {"JustNowSingularName", ""},
        {"JustNowPluralName", ""},
        {"MillenniumPattern", "%u %n"},
        {"MillenniumFuturePrefix", " ယခုမှ"},
        {"MillenniumFutureSuffix", " နှစ်အကြာ"},
        {"MillenniumPastPrefix", ""},
        {"MillenniumPastSuffix", " နှစ်အကြာက"},
        {"MillenniumSingularName", "ထောင်စုနှစ်"},
        {"MillenniumPluralName", "ထောင်စုနှစ်"},
        {"MillisecondPattern", "%n %u"},
        {"MillisecondFuturePrefix", " ယခုမှ"},
        {"MillisecondFutureSuffix", " အကြာ"},
        {"MillisecondPastPrefix", ""},
        {"MillisecondPastSuffix", " အကြာက"},
        {"MillisecondSingularName", "မီလီစက္ကန့်"},
        {"MillisecondPluralName", "မီလီစက္ကန့်"},
        {"MinutePattern", "%n %u"},
        {"MinuteFuturePrefix", " ယခုမှ"},
        {"MinuteFutureSuffix", " အကြာ"},
        {"MinutePastPrefix", ""},
        {"MinutePastSuffix", " အကြာက"},
        {"MinuteSingularName", "မိနစ်"},
        {"MinutePluralName", "မိနစ်"},
        {"MonthPattern", "%n %u"},
        {"MonthFuturePrefix", " ယခုမှ"},
        {"MonthFutureSuffix", " အကြာ"},
        {"MonthPastPrefix", ""},
        {"MonthPastSuffix", " အကြာက"},
        {"MonthSingularName", "လ"},
        {"MonthPluralName", "လ"},
        {"SecondPattern", "%n %u"},
        {"SecondFuturePrefix", " ယခုမှ"},
        {"SecondFutureSuffix", " အကြာ"},
        {"SecondPastPrefix", ""},
        {"SecondPastSuffix", " အကြာက"},
        {"SecondSingularName", "စက္ကန့်"},
        {"SecondPluralName", "စက္ကန့်"},
        {"WeekPattern", "%n %u"},
        {"WeekFuturePrefix", " ယခုမှ"},
        {"WeekFutureSuffix", " အကြာ"},
        {"WeekPastPrefix", ""},
        {"WeekPastSuffix", " အကြာက"},
        {"WeekSingularName", "ရက်သတ္တပတ်"},
        {"WeekPluralName", "ရက်သတ္တပတ်"},
        {"YearPattern", "%n %u"},
        {"YearFuturePrefix", " ယခုမှ"},
        {"YearFutureSuffix", " အကြာ"},
        {"YearPastPrefix", ""},
        {"YearPastSuffix", " အကြာက"},
        {"YearSingularName", "နှစ်"},
        {"YearPluralName", "နှစ်"},
        {"AbstractTimeUnitPattern", ""},
        {"AbstractTimeUnitFuturePrefix", " ယခုမှ"},
        {"AbstractTimeUnitFutureSuffix", " အကြာ"},
        {"AbstractTimeUnitPastPrefix", ""},
        {"AbstractTimeUnitPastSuffix", ""},
        {"AbstractTimeUnitSingularName", ""},
        {"AbstractTimeUnitPluralName", ""}};

    @Override
    public Object[][] getResources() {
        return Resources_MM.OBJECTS;
    }
}
