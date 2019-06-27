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
 * Default resources bundle [FA]
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Resources_FA extends Resources {
    /**
     * Default {@link Locale} {@code "FA"}
     */
    private static final Locale LOCALE = new Locale("fa");
    /**
     * Default {@link Resources_FA} instance
     */
    private static final Resources_FA INSTANCE = new Resources_FA();

    private Object[][] resources;

    private Resources_FA() {
        this.loadResources();
    }

    private static final Object[][] OBJECTS = new Object[][]{
        {"CenturyPattern", "%n %u"},
        {"CenturyFuturePrefix", ""},
        {"CenturyFutureSuffix", " دیگر"},
        {"CenturyPastPrefix", ""},
        {"CenturyPastSuffix", " پیش"},
        {"CenturySingularName", "قرن"},
        {"CenturyPluralName", "قرن"},
        {"DayPattern", "%n %u"},
        {"DayFuturePrefix", ""},
        {"DayFutureSuffix", " دیگر"},
        {"DayPastPrefix", ""},
        {"DayPastSuffix", " پیش"},
        {"DaySingularName", "روز"},
        {"DayPluralName", "روز"},
        {"DecadePattern", "%n %u"},
        {"DecadeFuturePrefix", ""},
        {"DecadeFutureSuffix", " دیگر"},
        {"DecadePastPrefix", ""},
        {"DecadePastSuffix", " پیش"},
        {"DecadeSingularName", "دهه"},
        {"DecadePluralName", "دهه"},
        {"HourPattern", "%n %u"},
        {"HourFuturePrefix", ""},
        {"HourFutureSuffix", " دیگر"},
        {"HourPastPrefix", ""},
        {"HourPastSuffix", " پیش"},
        {"HourSingularName", "ساعت"},
        {"HourPluralName", "ساعت"},
        {"JustNowPattern", "%u"},
        {"JustNowFuturePrefix", ""},
        {"JustNowFutureSuffix", "چند لحظه دیگر"},
        {"JustNowPastPrefix", "چند لحظه پیش"},
        {"JustNowPastSuffix", ""},
        {"JustNowSingularName", ""},
        {"JustNowPluralName", ""},
        {"MillenniumPattern", "%n %u"},
        {"MillenniumFuturePrefix", ""},
        {"MillenniumFutureSuffix", " دیگر"},
        {"MillenniumPastPrefix", ""},
        {"MillenniumPastSuffix", " پیش"},
        {"MillenniumSingularName", "هزاره"},
        {"MillenniumPluralName", "هزار سال"},
        {"MillisecondPattern", "%n %u"},
        {"MillisecondFuturePrefix", ""},
        {"MillisecondFutureSuffix", " دیگر"},
        {"MillisecondPastPrefix", ""},
        {"MillisecondPastSuffix", " پیش"},
        {"MillisecondSingularName", "میلی ثانیه"},
        {"MillisecondPluralName", "میلی ثانیه"},
        {"MinutePattern", "%n %u"},
        {"MinuteFuturePrefix", ""},
        {"MinuteFutureSuffix", " دیگر"},
        {"MinutePastPrefix", ""},
        {"MinutePastSuffix", " پیش"},
        {"MinuteSingularName", "دقیقه"},
        {"MinutePluralName", "دقیقه"},
        {"MonthPattern", "%n %u"},
        {"MonthFuturePrefix", ""},
        {"MonthFutureSuffix", " دیگر"},
        {"MonthPastPrefix", ""},
        {"MonthPastSuffix", " پیش"},
        {"MonthSingularName", "ماه"},
        {"MonthPluralName", "ماه"},
        {"SecondPattern", "%n %u"},
        {"SecondFuturePrefix", ""},
        {"SecondFutureSuffix", " دیگر"},
        {"SecondPastPrefix", ""},
        {"SecondPastSuffix", " پیش"},
        {"SecondSingularName", "ثانیه"},
        {"SecondPluralName", "ثانیه"},
        {"WeekPattern", "%n %u"},
        {"WeekFuturePrefix", ""},
        {"WeekFutureSuffix", " دیگر"},
        {"WeekPastPrefix", ""},
        {"WeekPastSuffix", " پیش"},
        {"WeekSingularName", "هفته"},
        {"WeekPluralName", "هفته"},
        {"YearPattern", "%n %u"},
        {"YearFuturePrefix", ""},
        {"YearFutureSuffix", " دیگر"},
        {"YearPastPrefix", ""},
        {"YearPastSuffix", " پیش"},
        {"YearSingularName", "سال"},
        {"YearPluralName", "سال"},
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
     * Returns new {@link Resources_FA}
     *
     * @return new {@link Resources_FA}
     */
    public static Resources_FA getInstance() {
        return INSTANCE;
    }
}
