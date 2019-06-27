package com.wildbeeslabs.sensiblemetrics.diffy.formatter.i18n;

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

import com.wildbeeslabs.sensiblemetrics.diffy.common.resources.BaseResourceBundle;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Locale;

/**
 * Default resources bundle [EN]
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Resources_EN extends Resources {
    /**
     * Default {@link Locale} {@code "EN"}
     */
    private static final Locale LOCALE = new Locale("en");
    /**
     * Default {@link Resources_EN} instance
     */
    private static final Resources_EN INSTANCE = new Resources_EN();

    private Object[][] resources;

    private Resources_EN() {
        this.loadResources();
    }

    private static final Object[][] OBJECTS = new Object[][]{
        {"CenturyPattern", "%n %u"},
        {"CenturyFuturePrefix", ""},
        {"CenturyFutureSuffix", " from now"},
        {"CenturyPastPrefix", ""},
        {"CenturyPastSuffix", " ago"},
        {"CenturySingularName", "century"},
        {"CenturyPluralName", "centuries"},
        {"DayPattern", "%n %u"},
        {"DayFuturePrefix", ""},
        {"DayFutureSuffix", " from now"},
        {"DayPastPrefix", ""},
        {"DayPastSuffix", " ago"},
        {"DaySingularName", "day"},
        {"DayPluralName", "days"},
        {"DecadePattern", "%n %u"},
        {"DecadeFuturePrefix", ""},
        {"DecadeFutureSuffix", " from now"},
        {"DecadePastPrefix", ""},
        {"DecadePastSuffix", " ago"},
        {"DecadeSingularName", "decade"},
        {"DecadePluralName", "decades"},
        {"HourPattern", "%n %u"},
        {"HourFuturePrefix", ""},
        {"HourFutureSuffix", " from now"},
        {"HourPastPrefix", ""},
        {"HourPastSuffix", " ago"},
        {"HourSingularName", "hour"},
        {"HourPluralName", "hours"},
        {"JustNowPattern", "%u"},
        {"JustNowFuturePrefix", ""},
        {"JustNowFutureSuffix", "moments from now"},
        {"JustNowPastPrefix", "moments ago"},
        {"JustNowPastSuffix", ""},
        {"JustNowSingularName", ""},
        {"JustNowPluralName", ""},
        {"MillenniumPattern", "%n %u"},
        {"MillenniumFuturePrefix", ""},
        {"MillenniumFutureSuffix", " from now"},
        {"MillenniumPastPrefix", ""},
        {"MillenniumPastSuffix", " ago"},
        {"MillenniumSingularName", "millennium"},
        {"MillenniumPluralName", "millennia"},
        {"MillisecondPattern", "%n %u"},
        {"MillisecondFuturePrefix", ""},
        {"MillisecondFutureSuffix", " from now"},
        {"MillisecondPastPrefix", ""},
        {"MillisecondPastSuffix", " ago"},
        {"MillisecondSingularName", "millisecond"},
        {"MillisecondPluralName", "milliseconds"},
        {"MinutePattern", "%n %u"},
        {"MinuteFuturePrefix", ""},
        {"MinuteFutureSuffix", " from now"},
        {"MinutePastPrefix", ""},
        {"MinutePastSuffix", " ago"},
        {"MinuteSingularName", "minute"},
        {"MinutePluralName", "minutes"},
        {"MonthPattern", "%n %u"},
        {"MonthFuturePrefix", ""},
        {"MonthFutureSuffix", " from now"},
        {"MonthPastPrefix", ""},
        {"MonthPastSuffix", " ago"},
        {"MonthSingularName", "month"},
        {"MonthPluralName", "months"},
        {"SecondPattern", "%n %u"},
        {"SecondFuturePrefix", ""},
        {"SecondFutureSuffix", " from now"},
        {"SecondPastPrefix", ""},
        {"SecondPastSuffix", " ago"},
        {"SecondSingularName", "second"},
        {"SecondPluralName", "seconds"},
        {"WeekPattern", "%n %u"},
        {"WeekFuturePrefix", ""},
        {"WeekFutureSuffix", " from now"},
        {"WeekPastPrefix", ""},
        {"WeekPastSuffix", " ago"},
        {"WeekSingularName", "week"},
        {"WeekPluralName", "weeks"},
        {"YearPattern", "%n %u"},
        {"YearFuturePrefix", ""},
        {"YearFutureSuffix", " from now"},
        {"YearPastPrefix", ""},
        {"YearPastSuffix", " ago"},
        {"YearSingularName", "year"},
        {"YearPluralName", "years"},
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
     * Returns new {@link Resources_EN}
     *
     * @return new {@link Resources_EN}
     */
    public static Resources_EN getInstance() {
        return INSTANCE;
    }
}
