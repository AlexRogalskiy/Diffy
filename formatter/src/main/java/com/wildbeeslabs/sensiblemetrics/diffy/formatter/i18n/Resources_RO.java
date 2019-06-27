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
 * Default resources bundle [RO]
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Resources_RO extends Resources {
    /**
     * Default {@link Locale} {@code "RO"}
     */
    private static final Locale LOCALE = new Locale("ro");
    /**
     * Default {@link Resources_RO} instance
     */
    private static final Resources_RO INSTANCE = new Resources_RO();

    private Object[][] resources;

    private Resources_RO() {
        this.loadResources();
    }

    private static final Object[][] OBJECTS = new Object[][]{
        {"CenturyPattern", "%n %u"},
        {"CenturyFuturePrefix", ""},
        {"CenturyFutureSuffix", " de acum"},
        {"CenturyPastPrefix", ""},
        {"CenturyPastSuffix", " in urma"},
        {"CenturySingularName", "secol"},
        {"CenturyPluralName", "secole"},
        {"DayPattern", "%n %u"},
        {"DayFuturePrefix", ""},
        {"DayFutureSuffix", " de acum"},
        {"DayPastPrefix", ""},
        {"DayPastSuffix", " in urma"},
        {"DaySingularName", "zi"},
        {"DayPluralName", "zile"},
        {"DecadePattern", "%n %u"},
        {"DecadeFuturePrefix", ""},
        {"DecadeFutureSuffix", " de acum"},
        {"DecadePastPrefix", ""},
        {"DecadePastSuffix", " in urma"},
        {"DecadeSingularName", "deceniu"},
        {"DecadePluralName", "decenii"},
        {"HourPattern", "%n %u"},
        {"HourFuturePrefix", ""},
        {"HourFutureSuffix", " de acum"},
        {"HourPastPrefix", ""},
        {"HourPastSuffix", " in urma"},
        {"HourSingularName", "ora"},
        {"HourPluralName", "ore"},
        {"JustNowPattern", "%u"},
        {"JustNowFuturePrefix", ""},
        {"JustNowFutureSuffix", "in cateva clipe"},
        {"JustNowPastPrefix", "cateva clipe in urma"},
        {"JustNowPastSuffix", ""},
        {"JustNowSingularName", ""},
        {"JustNowPluralName", ""},
        {"MillenniumPattern", "%n %u"},
        {"MillenniumFuturePrefix", ""},
        {"MillenniumFutureSuffix", " de acum"},
        {"MillenniumPastPrefix", ""},
        {"MillenniumPastSuffix", " in urma"},
        {"MillenniumSingularName", "mileniu"},
        {"MillenniumPluralName", "milenii"},
        {"MillisecondPattern", "%n %u"},
        {"MillisecondFuturePrefix", ""},
        {"MillisecondFutureSuffix", " de acum"},
        {"MillisecondPastPrefix", ""},
        {"MillisecondPastSuffix", " in urma"},
        {"MillisecondSingularName", "milisecunda"},
        {"MillisecondPluralName", "milisecunde"},
        {"MinutePattern", "%n %u"},
        {"MinuteFuturePrefix", ""},
        {"MinuteFutureSuffix", " de acum"},
        {"MinutePastPrefix", ""},
        {"MinutePastSuffix", " in urma"},
        {"MinuteSingularName", "minuta"},
        {"MinutePluralName", "minute"},
        {"MonthPattern", "%n %u"},
        {"MonthFuturePrefix", ""},
        {"MonthFutureSuffix", " de acum"},
        {"MonthPastPrefix", ""},
        {"MonthPastSuffix", " in urma"},
        {"MonthSingularName", "luna"},
        {"MonthPluralName", "luni"},
        {"SecondPattern", "%n %u"},
        {"SecondFuturePrefix", ""},
        {"SecondFutureSuffix", " de acum"},
        {"SecondPastPrefix", ""},
        {"SecondPastSuffix", " in urma"},
        {"SecondSingularName", "secunda"},
        {"SecondPluralName", "secunde"},
        {"WeekPattern", "%n %u"},
        {"WeekFuturePrefix", ""},
        {"WeekFutureSuffix", " de acum"},
        {"WeekPastPrefix", ""},
        {"WeekPastSuffix", " in urma"},
        {"WeekSingularName", "saptamana"},
        {"WeekPluralName", "saptamani"},
        {"YearPattern", "%n %u"},
        {"YearFuturePrefix", ""},
        {"YearFutureSuffix", " de acum"},
        {"YearPastPrefix", ""},
        {"YearPastSuffix", " in urma"},
        {"YearSingularName", "an"},
        {"YearPluralName", "ani"},
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
     * Returns new {@link Resources_RO}
     *
     * @return new {@link Resources_RO}
     */
    public static Resources_RO getInstance() {
        return INSTANCE;
    }
}
