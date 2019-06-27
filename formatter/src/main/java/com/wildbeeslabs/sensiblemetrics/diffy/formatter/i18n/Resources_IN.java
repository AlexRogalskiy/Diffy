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
 * Default resources bundle [IN]
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Resources_IN extends Resources {
    /**
     * Default {@link Locale} {@code "IN"}
     */
    private static final Locale LOCALE = new Locale("in");
    /**
     * Default {@link Resources_IN} instance
     */
    private static final Resources_IN INSTANCE = new Resources_IN();

    private Object[][] resources;

    private Resources_IN() {
        this.loadResources();
    }

    private static final Object[][] OBJECTS = new Object[][]{
        {"CenturyPattern", "%n %u"},
        {"CenturyFuturePrefix", ""},
        {"CenturyFutureSuffix", " dari sekarang"},
        {"CenturyPastPrefix", ""},
        {"CenturyPastSuffix", " yang lalu"},
        {"CenturySingularName", "abad"},
        {"CenturyPluralName", "abad"},
        {"DayPattern", "%n %u"},
        {"DayFuturePrefix", ""},
        {"DayFutureSuffix", " dari sekarang"},
        {"DayPastPrefix", ""},
        {"DayPastSuffix", " yang lalu"},
        {"DaySingularName", "hari"},
        {"DayPluralName", "hari"},
        {"DecadePattern", "%n %u"},
        {"DecadeFuturePrefix", ""},
        {"DecadeFutureSuffix", " dari sekarang"},
        {"DecadePastPrefix", ""},
        {"DecadePastSuffix", " yang lalu"},
        {"DecadeSingularName", "dekade"},
        {"DecadePluralName", "dekade"},
        {"HourPattern", "%n %u"},
        {"HourFuturePrefix", ""},
        {"HourFutureSuffix", " dari sekarang"},
        {"HourPastPrefix", ""},
        {"HourPastSuffix", " yang lalu"},
        {"HourSingularName", "jam"},
        {"HourPluralName", "jam"},
        {"JustNowPattern", "%u"},
        {"JustNowFuturePrefix", ""},
        {"JustNowFutureSuffix", "dari sekarang"},
        {"JustNowPastPrefix", "yang lalu"},
        {"JustNowPastSuffix", ""},
        {"JustNowSingularName", ""},
        {"JustNowPluralName", ""},
        {"MillenniumPattern", "%n %u"},
        {"MillenniumFuturePrefix", ""},
        {"MillenniumFutureSuffix", " dari sekarang"},
        {"MillenniumPastPrefix", ""},
        {"MillenniumPastSuffix", " yang lalu"},
        {"MillenniumSingularName", "ribuan tahun"},
        {"MillenniumPluralName", "ribuan tahun"},
        {"MillisecondPattern", "%n %u"},
        {"MillisecondFuturePrefix", ""},
        {"MillisecondFutureSuffix", " dari sekarang"},
        {"MillisecondPastPrefix", ""},
        {"MillisecondPastSuffix", " yang lalu"},
        {"MillisecondSingularName", "mili detik"},
        {"MillisecondPluralName", "mili detik"},
        {"MinutePattern", "%n %u"},
        {"MinuteFuturePrefix", ""},
        {"MinuteFutureSuffix", " dari sekarang"},
        {"MinutePastPrefix", ""},
        {"MinutePastSuffix", " yang lalu"},
        {"MinuteSingularName", "menit"},
        {"MinutePluralName", "menit"},
        {"MonthPattern", "%n %u"},
        {"MonthFuturePrefix", ""},
        {"MonthFutureSuffix", " dari sekarang"},
        {"MonthPastPrefix", ""},
        {"MonthPastSuffix", " yang lalu"},
        {"MonthSingularName", "bulan"},
        {"MonthPluralName", "bulan"},
        {"SecondPattern", "%n %u"},
        {"SecondFuturePrefix", ""},
        {"SecondFutureSuffix", " dari sekarang"},
        {"SecondPastPrefix", ""},
        {"SecondPastSuffix", " yang lalu"},
        {"SecondSingularName", "detik"},
        {"SecondPluralName", "detik"},
        {"WeekPattern", "%n %u"},
        {"WeekFuturePrefix", ""},
        {"WeekFutureSuffix", " dari sekarang"},
        {"WeekPastPrefix", ""},
        {"WeekPastSuffix", " yang lalu"},
        {"WeekSingularName", "minggu"},
        {"WeekPluralName", "minggu"},
        {"YearPattern", "%n %u"},
        {"YearFuturePrefix", ""},
        {"YearFutureSuffix", " dari sekarang"},
        {"YearPastPrefix", ""},
        {"YearPastSuffix", " yang lalu"},
        {"YearSingularName", "tahun"},
        {"YearPluralName", "tahun"},
        {"AbstractTimeUnitPattern", ""},
        {"AbstractTimeUnitFuturePrefix", ""},
        {"AbstractTimeUnitFutureSuffix", ""},
        {"AbstractTimeUnitPastPrefix", ""},
        {"AbstractTimeUnitPastSuffix", ""},
        {"AbstractTimeUnitSingularName", ""},
        {"AbstractTimeUnitPluralName", ""}
    };

    /**
     * Loads {@link BaseResourceBundle} items
     */
    public void loadResources() {
        this.resources = BaseResourceBundle.getInstance(LOCALE).getResources();
    }

    /**
     * Returns new {@link Resources_IN}
     *
     * @return new {@link Resources_IN}
     */
    public static Resources_IN getInstance() {
        return INSTANCE;
    }
}
