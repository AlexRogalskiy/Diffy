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
 * Default resources bundle [UY]
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Resources_UY extends Resources {
    /**
     * Default {@link Locale} {@code "UY"}
     */
    private static final Locale LOCALE = new Locale("uy");
    /**
     * Default {@link Resources_UY} instance
     */
    private static final Resources_UY INSTANCE = new Resources_UY();

    private Object[][] resources;

    private Resources_UY() {
        this.loadResources();
    }

    private static final Object[][] OBJECTS = new Object[][]{
        {"CenturyPattern", "%n %u"},
        {"CenturyFuturePrefix", ""},
        {"CenturyFutureSuffix", "كىيىن"},
        {"CenturyPastPrefix", ""},
        {"CenturyPastSuffix", "ئىلگىرى"},
        {"CenturySingularName", "ئەسىر"},
        {"CenturyPluralName", "ئەسىر"},
        {"DayPattern", "%n %u"},
        {"DayFuturePrefix", ""},
        {"DayFutureSuffix", "كىيىن"},
        {"DayPastPrefix", ""},
        {"DayPastSuffix", "ئىلگىرى"},
        {"DaySingularName", "كۈن"},
        {"DayPluralName", "كۈن"},
        {"DecadePattern", "%n %u"},
        {"DecadeFuturePrefix", ""},
        {"DecadeFutureSuffix", "كىيىن"},
        {"DecadePastPrefix", ""},
        {"DecadePastSuffix", "ئىلگىرى"},
        {"DecadeSingularName", "0 يىل"},
        {"DecadePluralName", "0 يىل"},
        {"HourPattern", "%n %u"},
        {"HourFuturePrefix", ""},
        {"HourFutureSuffix", "كىيىن"},
        {"HourPastPrefix", ""},
        {"HourPastSuffix", "ئىلگىرى"},
        {"HourSingularName", "سائەت"},
        {"HourPluralName", "سائەت"},
        {"JustNowPattern", "%u"},
        {"JustNowFuturePrefix", ""},
        {"JustNowFutureSuffix", "ھېلىلا"},
        {"JustNowPastPrefix", "ھېلىلا"},
        {"JustNowPastSuffix", ""},
        {"JustNowSingularName", ""},
        {"JustNowPluralName", ""},
        {"MillenniumPattern", "%n %u"},
        {"MillenniumFuturePrefix", ""},
        {"MillenniumFutureSuffix", "كىيىن"},
        {"MillenniumPastPrefix", ""},
        {"MillenniumPastSuffix", "ئىلگىرى"},
        {"MillenniumSingularName", "مىڭ يىل"},
        {"MillenniumPluralName", "مىڭ يىل"},
        {"MillisecondPattern", "%n %u"},
        {"MillisecondFuturePrefix", ""},
        {"MillisecondFutureSuffix", "كىيىن"},
        {"MillisecondPastPrefix", ""},
        {"MillisecondPastSuffix", "ئىلگىرى"},
        {"MillisecondSingularName", "دەقىقە"},
        {"MillisecondPluralName", "دەقىقە"},
        {"MinutePattern", "%n %u"},
        {"MinuteFuturePrefix", ""},
        {"MinuteFutureSuffix", "كىيىن"},
        {"MinutePastPrefix", ""},
        {"MinutePastSuffix", "ئىلگىرى"},
        {"MinuteSingularName", "مىنۇت"},
        {"MinutePluralName", "مىنۇت"},
        {"MonthPattern", "%n %u"},
        {"MonthFuturePrefix", ""},
        {"MonthFutureSuffix", "كىيىن"},
        {"MonthPastPrefix", ""},
        {"MonthPastSuffix", "ئىلگىرى"},
        {"MonthSingularName", "ئاي"},
        {"MonthPluralName", "ئاي"},
        {"SecondPattern", "%n %u"},
        {"SecondFuturePrefix", ""},
        {"SecondFutureSuffix", "كىيىن"},
        {"SecondPastPrefix", ""},
        {"SecondPastSuffix", "ئىلگىرى"},
        {"SecondSingularName", "سېكۇنت"},
        {"SecondPluralName", "سېكۇنت"},
        {"WeekPattern", "%n %u"},
        {"WeekFuturePrefix", ""},
        {"WeekFutureSuffix", "كىيىن"},
        {"WeekPastPrefix", ""},
        {"WeekPastSuffix", "ئىلگىرى"},
        {"WeekSingularName", "ھەپتە"},
        {"WeekPluralName", "ھەپتە"},
        {"YearPattern", "%n %u"},
        {"YearFuturePrefix", ""},
        {"YearFutureSuffix", "كىيىن"},
        {"YearPastPrefix", ""},
        {"YearPastSuffix", "ئىلگىرى"},
        {"YearSingularName", "يىل"},
        {"YearPluralName", "يىل"},
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
     * Returns new {@link Resources_UY}
     *
     * @return new {@link Resources_UY}
     */
    public static Resources_UY getInstance() {
        return INSTANCE;
    }
}
