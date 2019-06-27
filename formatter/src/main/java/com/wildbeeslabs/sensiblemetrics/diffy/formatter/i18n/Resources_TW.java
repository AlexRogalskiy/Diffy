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

import com.wildbeeslabs.sensiblemetrics.diffy.common.resources.BaseResourceBundle;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Locale;

/**
 * Default resources bundle [TW]
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Resources_TW extends Resources {
    /**
     * Default {@link Locale} {@code "TW"}
     */
    private static final Locale LOCALE = new Locale("tw");
    /**
     * Default {@link Resources_TW} instance
     */
    private static final Resources_TW INSTANCE = new Resources_TW();

    private Object[][] resources;

    private Resources_TW() {
        this.loadResources();
    }

    private static final Object[][] OBJECTS = new Object[][]{
        {"CenturyPattern", "%n %u"},
        {"CenturyFuturePrefix", ""},
        {"CenturyFutureSuffix", "後"},
        {"CenturyPastPrefix", ""},
        {"CenturyPastSuffix", "前"},
        {"CenturySingularName", "世紀"},
        {"CenturyPluralName", "世紀"},
        {"DayPattern", "%n %u"},
        {"DayFuturePrefix", ""},
        {"DayFutureSuffix", "後"},
        {"DayPastPrefix", ""},
        {"DayPastSuffix", "前"},
        {"DaySingularName", "天"},
        {"DayPluralName", "天"},
        {"DecadePattern", "%n%u"},
        {"DecadeFuturePrefix", ""},
        {"DecadeFutureSuffix", "後"},
        {"DecadePastPrefix", ""},
        {"DecadePastSuffix", "前"},
        {"DecadeSingularName", "0 年"},
        {"DecadePluralName", "0 年"},
        {"HourPattern", "%n %u"},
        {"HourFuturePrefix", ""},
        {"HourFutureSuffix", "後"},
        {"HourPastPrefix", ""},
        {"HourPastSuffix", "前"},
        {"HourSingularName", "小時"},
        {"HourPluralName", "小時"},
        {"JustNowPattern", "%u"},
        {"JustNowFuturePrefix", ""},
        {"JustNowFutureSuffix", "剛剛"},
        {"JustNowPastPrefix", "片刻之前"},
        {"JustNowPastSuffix", ""},
        {"JustNowSingularName", ""},
        {"JustNowPluralName", ""},
        {"MillenniumPattern", "%n %u"},
        {"MillenniumFuturePrefix", ""},
        {"MillenniumFutureSuffix", "後"},
        {"MillenniumPastPrefix", ""},
        {"MillenniumPastSuffix", "前"},
        {"MillenniumSingularName", "千年"},
        {"MillenniumPluralName", "千年"},
        {"MillisecondPattern", "%n %u"},
        {"MillisecondFuturePrefix", ""},
        {"MillisecondFutureSuffix", "後"},
        {"MillisecondPastPrefix", ""},
        {"MillisecondPastSuffix", "前"},
        {"MillisecondSingularName", "毫秒"},
        {"MillisecondPluralName", "毫秒"},
        {"MinutePattern", "%n %u"},
        {"MinuteFuturePrefix", ""},
        {"MinuteFutureSuffix", "後"},
        {"MinutePastPrefix", ""},
        {"MinutePastSuffix", "前"},
        {"MinuteSingularName", "分鐘"},
        {"MinutePluralName", "分鐘"},
        {"MonthPattern", "%n %u"},
        {"MonthFuturePrefix", ""},
        {"MonthFutureSuffix", "後"},
        {"MonthPastPrefix", ""},
        {"MonthPastSuffix", "前"},
        {"MonthSingularName", "個月"},
        {"MonthPluralName", "個月"},
        {"SecondPattern", "%n %u"},
        {"SecondFuturePrefix", ""},
        {"SecondFutureSuffix", "後"},
        {"SecondPastPrefix", ""},
        {"SecondPastSuffix", "前"},
        {"SecondSingularName", "秒"},
        {"SecondPluralName", "秒"},
        {"WeekPattern", "%n %u"},
        {"WeekFuturePrefix", ""},
        {"WeekFutureSuffix", "後"},
        {"WeekPastPrefix", ""},
        {"WeekPastSuffix", "前"},
        {"WeekSingularName", "週"},
        {"WeekPluralName", "週"},
        {"YearPattern", "%n %u"},
        {"YearFuturePrefix", ""},
        {"YearFutureSuffix", "後"},
        {"YearPastPrefix", ""},
        {"YearPastSuffix", "前"},
        {"YearSingularName", "年"},
        {"YearPluralName", "年"},
        {"AbstractTimeUnitPattern", ""},
        {"AbstractTimeUnitFuturePrefix", ""},
        {"AbstractTimeUnitFutureSuffix", ""},
        {"AbstractTimeUnitPastPrefix", ""},
        {"AbstractTimeUnitPastSuffix", ""},
        {"AbstractTimeUnitSingularName", ""},
        {"AbstractTimeUnitPluralName", ""}};

    /**
     * Loads {@link BaseResourceBundle} items
     */
    public void loadResources() {
        this.resources = BaseResourceBundle.getInstance(LOCALE).getResources();
    }

    /**
     * Returns new {@link Resources_TW}
     *
     * @return new {@link Resources_TW}
     */
    public static Resources_TW getInstance() {
        return INSTANCE;
    }
}
