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
 * Default resources bundle [HI]
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Resources_HI extends Resources {
    /**
     * Default {@link Locale} {@code "HI"}
     */
    private static final Locale LOCALE = new Locale("hi");
    /**
     * Default {@link Resources_HI} instance
     */
    private static final Resources_HI INSTANCE = new Resources_HI();

    private Object[][] resources;

    private Resources_HI() {
        this.loadResources();
    }

    private static final Object[][] OBJECTS = new Object[][]{
        {"CenturyPattern", "%n %u"},
        {"CenturyFuturePrefix", ""},
        {"CenturyFutureSuffix", " बाद"},
        {"CenturyPastPrefix", ""},
        {"CenturyPastSuffix", " पहले"},
        {"CenturySingularName", "सदी"},
        {"CenturyPluralName", "सदियों"},
        {"DayPattern", "%n %u"},
        {"DayFuturePrefix", ""},
        {"DayFutureSuffix", " बाद"},
        {"DayPastPrefix", ""},
        {"DayPastSuffix", " पहले"},
        {"DaySingularName", "दिन"},
        {"DayPluralName", "दिन"},
        {"DecadePattern", "%n %u"},
        {"DecadeFuturePrefix", ""},
        {"DecadeFutureSuffix", " बाद"},
        {"DecadePastPrefix", ""},
        {"DecadePastSuffix", " पहले"},
        {"DecadeSingularName", "दशक"},
        {"DecadePluralName", "दशक"},
        {"HourPattern", "%n %u"},
        {"HourFuturePrefix", ""},
        {"HourFutureSuffix", " बाद"},
        {"HourPastPrefix", ""},
        {"HourPastSuffix", " पहले"},
        {"HourSingularName", "घंटा"},
        {"HourPluralName", "घंटे"},
        {"JustNowPattern", "%u"},
        {"JustNowFuturePrefix", ""},
        {"JustNowFutureSuffix", "अभी"},
        {"JustNowPastPrefix", "अभी"},
        {"JustNowPastSuffix", ""},
        {"JustNowSingularName", ""},
        {"JustNowPluralName", ""},
        {"MillenniumPattern", "%n %u"},
        {"MillenniumFuturePrefix", ""},
        {"MillenniumFutureSuffix", " बाद"},
        {"MillenniumPastPrefix", ""},
        {"MillenniumPastSuffix", " पहले"},
        {"MillenniumSingularName", "सहस्राब्दी"},
        {"MillenniumPluralName", "सदियों"},
        {"MillisecondPattern", "%n %u"},
        {"MillisecondFuturePrefix", ""},
        {"MillisecondFutureSuffix", " बाद"},
        {"MillisecondPastPrefix", ""},
        {"MillisecondPastSuffix", " पहले"},
        {"MillisecondSingularName", "मिलीसेकंड"},
        {"MillisecondPluralName", "मिलीसेकंड"},
        {"MinutePattern", "%n %u"},
        {"MinuteFuturePrefix", ""},
        {"MinuteFutureSuffix", " बाद"},
        {"MinutePastPrefix", ""},
        {"MinutePastSuffix", " पहले"},
        {"MinuteSingularName", "मिनट"},
        {"MinutePluralName", "मिनट"},
        {"MonthPattern", "%n %u"},
        {"MonthFuturePrefix", ""},
        {"MonthFutureSuffix", " बाद"},
        {"MonthPastPrefix", ""},
        {"MonthPastSuffix", " पहले"},
        {"MonthSingularName", "महीना"},
        {"MonthPluralName", "महीने"},
        {"SecondPattern", "%n %u"},
        {"SecondFuturePrefix", ""},
        {"SecondFutureSuffix", " बाद"},
        {"SecondPastPrefix", ""},
        {"SecondPastSuffix", " पहले"},
        {"SecondSingularName", "सेकण्ड"},
        {"SecondPluralName", "सेकंड्स"},
        {"WeekPattern", "%n %u"},
        {"WeekFuturePrefix", ""},
        {"WeekFutureSuffix", " बाद"},
        {"WeekPastPrefix", ""},
        {"WeekPastSuffix", " पहले"},
        {"WeekSingularName", "सप्ताह"},
        {"WeekPluralName", "सप्ताह"},
        {"YearPattern", "%n %u"},
        {"YearFuturePrefix", ""},
        {"YearFutureSuffix", " बाद"},
        {"YearPastPrefix", ""},
        {"YearPastSuffix", " पहले"},
        {"YearSingularName", "वर्ष"},
        {"YearPluralName", "वर्ष"},
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
     * Returns new {@link Resources_HI}
     *
     * @return new {@link Resources_HI}
     */
    public static Resources_HI getInstance() {
        return INSTANCE;
    }
}
