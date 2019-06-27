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
 * Default resources bundle [TM]
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Resources_TM extends Resources {
    /**
     * Default {@link Locale} {@code "TM"}
     */
    private static final Locale LOCALE = new Locale("tm");
    /**
     * Default {@link Resources_TM} instance
     */
    private static final Resources_TM INSTANCE = new Resources_TM();

    private Object[][] resources;

    private Resources_TM() {
        this.loadResources();
    }

    private static final Object[][] OBJECTS = new Object[][]{
        {"CenturyPattern", "%n %u"},
        {"CenturyFuturePrefix", ""},
        {"CenturyFutureSuffix", " soňra"},
        {"CenturyPastPrefix", ""},
        {"CenturyPastSuffix", " öň"},
        {"CenturySingularName", "asyr"},
        {"CenturyPluralName", "asyr"},
        {"DayPattern", "%n %u"},
        {"DayFuturePrefix", ""},
        {"DayFutureSuffix", " soňra"},
        {"DayPastPrefix", ""},
        {"DayPastSuffix", " öň"},
        {"DaySingularName", "gün"},
        {"DayPluralName", "gün"},
        {"DecadePattern", "%n %u"},
        {"DecadeFuturePrefix", ""},
        {"DecadeFutureSuffix", " soňra"},
        {"DecadePastPrefix", ""},
        {"DecadePastSuffix", " öň"},
        {"DecadeSingularName", "on ýyl"},
        {"DecadePluralName", "on ýyl"},
        {"HourPattern", "%n %u"},
        {"HourFuturePrefix", ""},
        {"HourFutureSuffix", " soňra"},
        {"HourPastPrefix", ""},
        {"HourPastSuffix", " öň"},
        {"HourSingularName", "sagat"},
        {"HourPluralName", "sagat"},
        {"JustNowPattern", "%u"},
        {"JustNowFuturePrefix", ""},
        {"JustNowFutureSuffix", "biraz soňra"},
        {"JustNowPastPrefix", "biraz öň"},
        {"JustNowPastSuffix", ""},
        {"JustNowSingularName", ""},
        {"JustNowPluralName", ""},
        {"MillenniumPattern", "%n %u"},
        {"MillenniumFuturePrefix", ""},
        {"MillenniumFutureSuffix", " soňra"},
        {"MillenniumPastPrefix", ""},
        {"MillenniumPastSuffix", " öň"},
        {"MillenniumSingularName", "millenýum"},
        {"MillenniumPluralName", "millenýum"},
        {"MillisecondPattern", "%n %u"},
        {"MillisecondFuturePrefix", ""},
        {"MillisecondFutureSuffix", " soňra"},
        {"MillisecondPastPrefix", ""},
        {"MillisecondPastSuffix", " öň"},
        {"MillisecondSingularName", "millisekunt"},
        {"MillisecondPluralName", "millisekunt"},
        {"MinutePattern", "%n %u"},
        {"MinuteFuturePrefix", ""},
        {"MinuteFutureSuffix", " soňra"},
        {"MinutePastPrefix", ""},
        {"MinutePastSuffix", " öň"},
        {"MinuteSingularName", "minut"},
        {"MinutePluralName", "minut"},
        {"MonthPattern", "%n %u"},
        {"MonthFuturePrefix", ""},
        {"MonthFutureSuffix", " soňra"},
        {"MonthPastPrefix", ""},
        {"MonthPastSuffix", " öň"},
        {"MonthSingularName", "aý"},
        {"MonthPluralName", "aý"},
        {"SecondPattern", "%n %u"},
        {"SecondFuturePrefix", ""},
        {"SecondFutureSuffix", " soňra"},
        {"SecondPastPrefix", ""},
        {"SecondPastSuffix", " öň"},
        {"SecondSingularName", "sekunt"},
        {"SecondPluralName", "sekunt"},
        {"WeekPattern", "%n %u"},
        {"WeekFuturePrefix", ""},
        {"WeekFutureSuffix", " soňra"},
        {"WeekPastPrefix", ""},
        {"WeekPastSuffix", " öň"},
        {"WeekSingularName", "hepde"},
        {"WeekPluralName", "hepde"},
        {"YearPattern", "%n %u"},
        {"YearFuturePrefix", ""},
        {"YearFutureSuffix", " soňra"},
        {"YearPastPrefix", ""},
        {"YearPastSuffix", " öň"},
        {"YearSingularName", "ýyl"},
        {"YearPluralName", "ýyl"},
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
     * Returns new {@link Resources_TM}
     *
     * @return new {@link Resources_TM}
     */
    public static Resources_TM getInstance() {
        return INSTANCE;
    }
}
