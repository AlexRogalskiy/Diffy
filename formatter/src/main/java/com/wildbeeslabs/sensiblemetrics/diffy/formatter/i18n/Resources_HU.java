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
 * Default resources bundle [HU]
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Resources_HU extends Resources {
    /**
     * Default {@link Locale} {@code "HU"}
     */
    private static final Locale LOCALE = new Locale("hu");
    /**
     * Default {@link Resources_HU} instance
     */
    private static final Resources_HU INSTANCE = new Resources_HU();

    private Object[][] resources;

    private Resources_HU() {
        this.loadResources();
    }

    private static final Object[][] OBJECTS = new Object[][]{
        {"CenturyPattern", "%n %u"},
        {"CenturyFuturePrefix", ""},
        {"CenturyFutureSuffix", "század múlva"},
        {"CenturyPastPrefix", ""},
        {"CenturyPastSuffix", "százada"},
        {"CenturySingularName", ""},
        {"CenturyPluralName", ""},
        {"DayPattern", "%n %u"},
        {"DayFuturePrefix", ""},
        {"DayFutureSuffix", "nap múlva"},
        {"DayPastPrefix", ""},
        {"DayPastSuffix", "napja"},
        {"DaySingularName", ""},
        {"DayPluralName", ""},
        {"DecadePattern", "%n %u"},
        {"DecadeFuturePrefix", ""},
        {"DecadeFutureSuffix", "évtized múlva"},
        {"DecadePastPrefix", ""},
        {"DecadePastSuffix", "évtizede"},
        {"DecadeSingularName", ""},
        {"DecadePluralName", ""},
        {"HourPattern", "%n %u"},
        {"HourFuturePrefix", ""},
        {"HourFutureSuffix", "óra múlva"},
        {"HourPastPrefix", ""},
        {"HourPastSuffix", "órája"},
        {"HourSingularName", ""},
        {"HourPluralName", ""},
        {"JustNowPattern", "%u"},
        {"JustNowFuturePrefix", ""},
        {"JustNowFutureSuffix", "rögtön"},
        {"JustNowPastPrefix", "nemrég"},
        {"JustNowPastSuffix", ""},
        {"JustNowSingularName", ""},
        {"JustNowPluralName", ""},
        {"MillenniumPattern", "%n %u"},
        {"MillenniumFuturePrefix", ""},
        {"MillenniumFutureSuffix", "évezred múlva"},
        {"MillenniumPastPrefix", ""},
        {"MillenniumPastSuffix", "évezrede"},
        {"MillenniumSingularName", ""},
        {"MillenniumPluralName", ""},
        {"MillisecondPattern", "%n %u"},
        {"MillisecondFuturePrefix", ""},
        {"MillisecondFutureSuffix", "milliszekundum múlva"},
        {"MillisecondPastPrefix", ""},
        {"MillisecondPastSuffix", "milliszekundummal ezelõtt"},
        {"MillisecondSingularName", ""},
        {"MillisecondPluralName", ""},
        {"MinutePattern", "%n %u"},
        {"MinuteFuturePrefix", ""},
        {"MinuteFutureSuffix", "perc múlva"},
        {"MinutePastPrefix", ""},
        {"MinutePastSuffix", "perce"},
        {"MinuteSingularName", ""},
        {"MinutePluralName", ""},
        {"MonthPattern", "%n %u"},
        {"MonthFuturePrefix", ""},
        {"MonthFutureSuffix", "hónap múlva"},
        {"MonthPastPrefix", ""},
        {"MonthPastSuffix", "hónapja"},
        {"MonthSingularName", ""},
        {"MonthPluralName", ""},
        {"SecondPattern", "%n %u"},
        {"SecondFuturePrefix", ""},
        {"SecondFutureSuffix", "másodperc múlva"},
        {"SecondPastPrefix", ""},
        {"SecondPastSuffix", "másodperce"},
        {"SecondSingularName", ""},
        {"SecondPluralName", ""},
        {"WeekPattern", "%n %u"},
        {"WeekFuturePrefix", ""},
        {"WeekFutureSuffix", "hét múlva"},
        {"WeekPastPrefix", ""},
        {"WeekPastSuffix", "hete"},
        {"WeekSingularName", ""},
        {"WeekPluralName", ""},
        {"YearPattern", "%n %u"},
        {"YearFuturePrefix", ""},
        {"YearFutureSuffix", "év múlva"},
        {"YearPastPrefix", ""},
        {"YearPastSuffix", "éve"},
        {"YearSingularName", ""},
        {"YearPluralName", ""},
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
     * Returns new {@link Resources_HU}
     *
     * @return new {@link Resources_HU}
     */
    public static Resources_HU getInstance() {
        return INSTANCE;
    }
}
