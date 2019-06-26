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
 * Default resources bundle [TH]
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Resources_TH extends Resources {

    private static final Object[][] OBJECTS = new Object[][]{
        {"CenturyPattern", "%n %u"},
        {"CenturyFuturePrefix", ""},
        {"CenturyFutureSuffix", "ต่อจากนี้"},
        {"CenturyPastPrefix", ""},
        {"CenturyPastSuffix", "ก่อน"},
        {"CenturySingularName", "ศตวรรษ"},
        {"CenturyPluralName", "ศตวรรษ"},
        {"DayPattern", "%n %u"},
        {"DayFuturePrefix", ""},
        {"DayFutureSuffix", "ต่อจากนี้ี้"},
        {"DayPastPrefix", ""},
        {"DayPastSuffix", "ก่อน"},
        {"DaySingularName", "วัน"},
        {"DayPluralName", "วัน"},
        {"DecadePattern", "%n %u"},
        {"DecadeFuturePrefix", ""},
        {"DecadeFutureSuffix", "ต่อจากนี้"},
        {"DecadePastPrefix", ""},
        {"DecadePastSuffix", "ก่อน"},
        {"DecadeSingularName", "ทศวรรษ"},
        {"DecadePluralName", "ทศวรรษ"},
        {"HourPattern", "%n %u"},
        {"HourFuturePrefix", ""},
        {"HourFutureSuffix", "ต่อจากนี้"},
        {"HourPastPrefix", ""},
        {"HourPastSuffix", "ก่อน"},
        {"HourSingularName", "ชั่วโมง"},
        {"HourPluralName", "ชั่วโมง"},
        {"JustNowPattern", "%u"},
        {"JustNowFuturePrefix", ""},
        {"JustNowFutureSuffix", "ชั่วขณะต่อจากนี้้ี้้"},
        {"JustNowPastPrefix", "ชั่วขณะก่อน"},
        {"JustNowPastSuffix", ""},
        {"JustNowSingularName", ""},
        {"JustNowPluralName", ""},
        {"MillenniumPattern", "%n %u"},
        {"MillenniumFuturePrefix", ""},
        {"MillenniumFutureSuffix", "ต่อจากนี้"},
        {"MillenniumPastPrefix", ""},
        {"MillenniumPastSuffix", "ก่อน"},
        {"MillenniumSingularName", "รอบพันปี"},
        {"MillenniumPluralName", "รอบพันปี"},
        {"MillisecondPattern", "%n %u"},
        {"MillisecondFuturePrefix", ""},
        {"MillisecondFutureSuffix", "ต่อจากนี้"},
        {"MillisecondPastPrefix", ""},
        {"MillisecondPastSuffix", "ก่อน"},
        {"MillisecondSingularName", "หนึ่งในพันของวินาที"},
        {"MillisecondPluralName", "หนึ่งในพันของวินาที"},
        {"MinutePattern", "%n %u"},
        {"MinuteFuturePrefix", ""},
        {"MinuteFutureSuffix", "ต่อจากนี้ี้"},
        {"MinutePastPrefix", ""},
        {"MinutePastSuffix", "ก่อน"},
        {"MinuteSingularName", "นาที"},
        {"MinutePluralName", "นาที"},
        {"MonthPattern", "%n %u"},
        {"MonthFuturePrefix", ""},
        {"MonthFutureSuffix", "ต่อจากนี้"},
        {"MonthPastPrefix", ""},
        {"MonthPastSuffix", "ก่อน"},
        {"MonthSingularName", "เดือน"},
        {"MonthPluralName", "เดือน"},
        {"SecondPattern", "%n %u"},
        {"SecondFuturePrefix", ""},
        {"SecondFutureSuffix", "ต่อจากนี้"},
        {"SecondPastPrefix", ""},
        {"SecondPastSuffix", "ก่อน"},
        {"SecondSingularName", "วินาที"},
        {"SecondPluralName", "วินาที"},
        {"WeekPattern", "%n %u"},
        {"WeekFuturePrefix", ""},
        {"WeekFutureSuffix", "ต่อจากนี้ี้"},
        {"WeekPastPrefix", ""},
        {"WeekPastSuffix", "ก่อน"},
        {"WeekSingularName", "อาทิตย์"},
        {"WeekPluralName", "อาทิตย์"},
        {"YearPattern", "%n %u"},
        {"YearFuturePrefix", ""},
        {"YearFutureSuffix", "ต่อจากนี้"},
        {"YearPastPrefix", ""},
        {"YearPastSuffix", "ก่อน"},
        {"YearSingularName", "ปี"},
        {"YearPluralName", "ปี"},
        {"AbstractTimeUnitPattern", ""},
        {"AbstractTimeUnitFuturePrefix", ""},
        {"AbstractTimeUnitFutureSuffix", ""},
        {"AbstractTimeUnitPastPrefix", ""},
        {"AbstractTimeUnitPastSuffix", ""},
        {"AbstractTimeUnitSingularName", ""},
        {"AbstractTimeUnitPluralName", ""}};

    @Override
    public Object[][] getResources() {
        return Resources_TH.OBJECTS;
    }
}
