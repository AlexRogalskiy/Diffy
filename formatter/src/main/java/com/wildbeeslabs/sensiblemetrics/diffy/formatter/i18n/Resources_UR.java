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
 * Default resources bundle [UR]
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Resources_UR extends Resources {
    /**
     * Default {@link Locale} {@code "UR"}
     */
    private static final Locale LOCALE = new Locale("ur");
    /**
     * Default {@link Resources_UR} instance
     */
    private static final Resources_UR INSTANCE = new Resources_UR();

    private Object[][] resources;

    private Resources_UR() {
        this.loadResources();
    }

    private static final Object[][] OBJECTS = new Object[][]{
        {"CenturyPattern", "%n %u"},
        {"CenturyFuturePrefix", ""},
        {"CenturyFutureSuffix", " ابھی سے"},
        {"CenturyPastPrefix", ""},
        {"CenturyPastSuffix", " پہلے"},
        {"CenturySingularName", "صدی"},
        {"CenturyPluralName", "صدیوں"},
        {"DayPattern", "%n %u"},
        {"DayFuturePrefix", ""},
        {"DayFutureSuffix", " ابھی سے"},
        {"DayPastPrefix", ""},
        {"DayPastSuffix", " پہلے"},
        {"DaySingularName", "دن"},
        {"DayPluralName", "دنوں"},
        {"DecadePattern", "%n %u"},
        {"DecadeFuturePrefix", ""},
        {"DecadeFutureSuffix", " ابھی سے"},
        {"DecadePastPrefix", ""},
        {"DecadePastSuffix", " پہلے"},
        {"DecadeSingularName", "دہائی"},
        {"DecadePluralName", "دہائی"},
        {"HourPattern", "%n %u"},
        {"HourFuturePrefix", ""},
        {"HourFutureSuffix", " ابھی سے"},
        {"HourPastPrefix", ""},
        {"HourPastSuffix", " پہلے"},
        {"HourSingularName", "گھنٹہ"},
        {"HourPluralName", "گھنٹے"},
        {"JustNowPattern", "%u"},
        {"JustNowFuturePrefix", ""},
        {"JustNowFutureSuffix", "اب سے لمحات"},
        {"JustNowPastPrefix", "لمحات پہلے"},
        {"JustNowPastSuffix", ""},
        {"JustNowSingularName", ""},
        {"JustNowPluralName", ""},
        {"MillenniumPattern", "%n %u"},
        {"MillenniumFuturePrefix", ""},
        {"MillenniumFutureSuffix", " ابھی سے"},
        {"MillenniumPastPrefix", ""},
        {"MillenniumPastSuffix", " پہلے"},
        {"MillenniumSingularName", "ہزار سال"},
        {"MillenniumPluralName", "صدیوں"},
        {"MillisecondPattern", "%n %u"},
        {"MillisecondFuturePrefix", ""},
        {"MillisecondFutureSuffix", " ابھی سے"},
        {"MillisecondPastPrefix", ""},
        {"MillisecondPastSuffix", " پہلے"},
        {"MillisecondSingularName", "ملی سیکنڈ"},
        {"MillisecondPluralName", "ملی سیکنڈ"},
        {"MinutePattern", "%n %u"},
        {"MinuteFuturePrefix", ""},
        {"MinuteFutureSuffix", " ابھی سے"},
        {"MinutePastPrefix", ""},
        {"MinutePastSuffix", " پہلے"},
        {"MinuteSingularName", "منٹ"},
        {"MinutePluralName", "منٹس "},
        {"MonthPattern", "%n %u"},
        {"MonthFuturePrefix", ""},
        {"MonthFutureSuffix", " ابھی سے"},
        {"MonthPastPrefix", ""},
        {"MonthPastSuffix", " پہلے"},
        {"MonthSingularName", "مہینہ "},
        {"MonthPluralName", "مہینے"},
        {"SecondPattern", "%n %u"},
        {"SecondFuturePrefix", ""},
        {"SecondFutureSuffix", " ابھی سے"},
        {"SecondPastPrefix", ""},
        {"SecondPastSuffix", " پہلے"},
        {"SecondSingularName", "سیکنڈ"},
        {"SecondPluralName", " سیکنڈز"},
        {"WeekPattern", "%n %u"},
        {"WeekFuturePrefix", ""},
        {"WeekFutureSuffix", " ابھی سے"},
        {"WeekPastPrefix", ""},
        {"WeekPastSuffix", " پہلے"},
        {"WeekSingularName", "ہفتہ"},
        {"WeekPluralName", "ہفتے"},
        {"YearPattern", "%n %u"},
        {"YearFuturePrefix", ""},
        {"YearFutureSuffix", " ابھی سے"},
        {"YearPastPrefix", ""},
        {"YearPastSuffix", " پہلے"},
        {"YearSingularName", "سال"},
        {"YearPluralName", "سالو"},
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
     * Returns new {@link Resources_UR}
     *
     * @return new {@link Resources_UR}
     */
    public static Resources_UR getInstance() {
        return INSTANCE;
    }
}
