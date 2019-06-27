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
 * Default resources bundle [NO]
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Resources_NO extends Resources {
    /**
     * Default {@link Locale} {@code "NO"}
     */
    private static final Locale LOCALE = new Locale("no");
    /**
     * Default {@link Resources_NO} instance
     */
    private static final Resources_NO INSTANCE = new Resources_NO();

    private Object[][] resources;

    private Resources_NO() {
        this.loadResources();
    }

    private static final Object[][] OBJECTS = new Object[][]{
        {"CenturyPattern", "%n %u"},
        {"CenturyFuturePrefix", ""},
        {"CenturyFutureSuffix", " fra nå"},
        {"CenturyPastPrefix", ""},
        {"CenturyPastSuffix", " siden"},
        {"CenturySingularName", "århundre"},
        {"CenturyPluralName", "århundre"},
        {"DayPattern", "%n %u"},
        {"DayFuturePrefix", "om "},
        {"DayFutureSuffix", ""},
        {"DayPastPrefix", ""},
        {"DayPastSuffix", " siden"},
        {"DaySingularName", "dag"},
        {"DayPluralName", "dager"},
        {"DecadePattern", "%n %u"},
        {"DecadeFuturePrefix", ""},
        {"DecadeFutureSuffix", " fra nå"},
        {"DecadePastPrefix", ""},
        {"DecadePastSuffix", " siden"},
        {"DecadeSingularName", "tiår"},
        {"DecadePluralName", "tiår"},
        {"HourPattern", "%n %u"},
        {"HourFuturePrefix", "om "},
        {"HourFutureSuffix", ""},
        {"HourPastPrefix", ""},
        {"HourPastSuffix", " siden"},
        {"HourSingularName", "time"},
        {"HourPluralName", "timer"},
        {"JustNowPattern", "%u"},
        {"JustNowFuturePrefix", ""},
        {"JustNowFutureSuffix", "straks"},
        {"JustNowPastPrefix", "et øyeblikk siden"},
        {"JustNowPastSuffix", ""},
        {"JustNowSingularName", ""},
        {"JustNowPluralName", ""},
        {"MillenniumPattern", "%n %u"},
        {"MillenniumFuturePrefix", ""},
        {"MillenniumFutureSuffix", " fra nå"},
        {"MillenniumPastPrefix", ""},
        {"MillenniumPastSuffix", " siden"},
        {"MillenniumSingularName", "millennium"},
        {"MillenniumPluralName", "millennier"},
        {"MillisecondPattern", "%n %u"},
        {"MillisecondFuturePrefix", ""},
        {"MillisecondFutureSuffix", " fra nå"},
        {"MillisecondPastPrefix", ""},
        {"MillisecondPastSuffix", " siden"},
        {"MillisecondSingularName", "millisekund"},
        {"MillisecondPluralName", "millisekunder"},
        {"MinutePattern", "%n %u"},
        {"MinuteFuturePrefix", "om "},
        {"MinuteFutureSuffix", ""},
        {"MinutePastPrefix", ""},
        {"MinutePastSuffix", " siden"},
        {"MinuteSingularName", "minutt"},
        {"MinutePluralName", "minutter"},
        {"MonthPattern", "%n %u"},
        {"MonthFuturePrefix", "om "},
        {"MonthFutureSuffix", ""},
        {"MonthPastPrefix", ""},
        {"MonthPastSuffix", " siden"},
        {"MonthSingularName", "måned"},
        {"MonthPluralName", "måneder"},
        {"SecondPattern", "%n %u"},
        {"SecondFuturePrefix", ""},
        {"SecondFutureSuffix", " fra nå"},
        {"SecondPastPrefix", ""},
        {"SecondPastSuffix", " siden"},
        {"SecondSingularName", "sekund"},
        {"SecondPluralName", "sekunder"},
        {"WeekPattern", "%n %u"},
        {"WeekFuturePrefix", "om "},
        {"WeekFutureSuffix", ""},
        {"WeekPastPrefix", ""},
        {"WeekPastSuffix", " siden"},
        {"WeekSingularName", "uke"},
        {"WeekPluralName", "uker"},
        {"YearPattern", "%n %u"},
        {"YearFuturePrefix", "om "},
        {"YearFutureSuffix", ""},
        {"YearPastPrefix", ""},
        {"YearPastSuffix", " siden"},
        {"YearSingularName", "år"},
        {"YearPluralName", "år"},
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
     * Returns new {@link Resources_NO}
     *
     * @return new {@link Resources_NO}
     */
    public static Resources_NO getInstance() {
        return INSTANCE;
    }
}
