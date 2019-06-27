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
 * Default resources bundle [TR]
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Resources_TR extends Resources {
    /**
     * Default {@link Locale} {@code "TR"}
     */
    private static final Locale LOCALE = new Locale("tr");
    /**
     * Default {@link Resources_TR} instance
     */
    private static final Resources_TR INSTANCE = new Resources_TR();

    private Object[][] resources;

    private Resources_TR() {
        this.loadResources();
    }

    private static final Object[][] OBJECTS = new Object[][]{
        {"CenturyPattern", "%n %u"},
        {"CenturyFuturePrefix", ""},
        {"CenturyFutureSuffix", " sonra"},
        {"CenturyPastPrefix", ""},
        {"CenturyPastSuffix", " önce"},
        {"CenturySingularName", "yüzyıl"},
        {"CenturyPluralName", "yüzyıl"},
        {"DayPattern", "%n %u"},
        {"DayFuturePrefix", ""},
        {"DayFutureSuffix", " sonra"},
        {"DayPastPrefix", ""},
        {"DayPastSuffix", " önce"},
        {"DaySingularName", "gün"},
        {"DayPluralName", "gün"},
        {"DecadePattern", "%n %u"},
        {"DecadeFuturePrefix", ""},
        {"DecadeFutureSuffix", " sonra"},
        {"DecadePastPrefix", ""},
        {"DecadePastSuffix", " önce"},
        {"DecadeSingularName", "on yıl"},
        {"DecadePluralName", "on yıl"},
        {"HourPattern", "%n %u"},
        {"HourFuturePrefix", ""},
        {"HourFutureSuffix", " sonra"},
        {"HourPastPrefix", ""},
        {"HourPastSuffix", " önce"},
        {"HourSingularName", "saat"},
        {"HourPluralName", "saat"},
        {"JustNowPattern", "%u"},
        {"JustNowFuturePrefix", ""},
        {"JustNowFutureSuffix", "biraz sonra"},
        {"JustNowPastPrefix", "biraz önce"},
        {"JustNowPastSuffix", ""},
        {"JustNowSingularName", ""},
        {"JustNowPluralName", ""},
        {"MillenniumPattern", "%n %u"},
        {"MillenniumFuturePrefix", ""},
        {"MillenniumFutureSuffix", " sonra"},
        {"MillenniumPastPrefix", ""},
        {"MillenniumPastSuffix", " önce"},
        {"MillenniumSingularName", "milenyum"},
        {"MillenniumPluralName", "milenyum"},
        {"MillisecondPattern", "%n %u"},
        {"MillisecondFuturePrefix", ""},
        {"MillisecondFutureSuffix", " sonra"},
        {"MillisecondPastPrefix", ""},
        {"MillisecondPastSuffix", " önce"},
        {"MillisecondSingularName", "milisaniye"},
        {"MillisecondPluralName", "milisaniye"},
        {"MinutePattern", "%n %u"},
        {"MinuteFuturePrefix", ""},
        {"MinuteFutureSuffix", " sonra"},
        {"MinutePastPrefix", ""},
        {"MinutePastSuffix", " önce"},
        {"MinuteSingularName", "dakika"},
        {"MinutePluralName", "dakika"},
        {"MonthPattern", "%n %u"},
        {"MonthFuturePrefix", ""},
        {"MonthFutureSuffix", " sonra"},
        {"MonthPastPrefix", ""},
        {"MonthPastSuffix", " önce"},
        {"MonthSingularName", "ay"},
        {"MonthPluralName", "ay"},
        {"SecondPattern", "%n %u"},
        {"SecondFuturePrefix", ""},
        {"SecondFutureSuffix", " sonra"},
        {"SecondPastPrefix", ""},
        {"SecondPastSuffix", " önce"},
        {"SecondSingularName", "saniye"},
        {"SecondPluralName", "saniye"},
        {"WeekPattern", "%n %u"},
        {"WeekFuturePrefix", ""},
        {"WeekFutureSuffix", " sonra"},
        {"WeekPastPrefix", ""},
        {"WeekPastSuffix", " önce"},
        {"WeekSingularName", "hafta"},
        {"WeekPluralName", "hafta"},
        {"YearPattern", "%n %u"},
        {"YearFuturePrefix", ""},
        {"YearFutureSuffix", " sonra"},
        {"YearPastPrefix", ""},
        {"YearPastSuffix", " önce"},
        {"YearSingularName", "yıl"},
        {"YearPluralName", "yıl"},
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
     * Returns new {@link Resources_TR}
     *
     * @return new {@link Resources_TR}
     */
    public static Resources_TR getInstance() {
        return INSTANCE;
    }
}
