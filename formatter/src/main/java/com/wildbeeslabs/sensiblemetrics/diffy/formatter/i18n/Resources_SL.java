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
 * Default resources bundle [SL]
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Resources_SL extends Resources {
    /**
     * Default {@link Locale} {@code "SL"}
     */
    private static final Locale LOCALE = new Locale("sl");
    /**
     * Default {@link Resources_SL} instance
     */
    private static final Resources_SL INSTANCE = new Resources_SL();

    private Object[][] resources;

    private Resources_SL() {
        this.loadResources();
    }

    private static final Object[][] OBJECTS = new Object[][]{
        {"CenturyPattern", "%n %u"},
        {"CenturyFuturePrefix", "čez "},
        {"CenturyFutureSuffix", ""},
        {"CenturyPastPrefix", ""},
        {"CenturyPastSuffix", "nazaj"},
        {"CenturySingularName", "stoletje"},
        {"CenturyPluralName", "stoletij"},
        {"DayPattern", "%n %u"},
        {"DayFuturePrefix", "čez "},
        {"DayFutureSuffix", ""},
        {"DayPastPrefix", ""},
        {"DayPastSuffix", "nazaj"},
        {"DaySingularName", "dan"},
        {"DayPluralName", "dni"},
        {"DecadePattern", "%n %u"},
        {"DecadeFuturePrefix", "čez "},
        {"DecadeFutureSuffix", ""},
        {"DecadePastPrefix", ""},
        {"DecadePastSuffix", "nazaj"},
        {"DecadeSingularName", "desetletje"},
        {"DecadePluralName", "desetletij"},
        {"HourPattern", "%n %u"},
        {"HourFuturePrefix", "čez "},
        {"HourFutureSuffix", ""},
        {"HourPastPrefix", ""},
        {"HourPastSuffix", "nazaj"},
        {"HourSingularName", "uro"},
        {"HourPluralName", "ur"},
        {"JustNowPattern", "%u"},
        {"JustNowFuturePrefix", "čez "},
        {"JustNowFutureSuffix", "pravkar"},
        {"JustNowPastPrefix", "trenutkov nazaj"},
        {"JustNowPastSuffix", ""},
        {"JustNowSingularName", ""},
        {"JustNowPluralName", ""},
        {"MillenniumPattern", "%n %u"},
        {"MillenniumFuturePrefix", "čez "},
        {"MillenniumFutureSuffix", ""},
        {"MillenniumPastPrefix", ""},
        {"MillenniumPastSuffix", "nazaj"},
        {"MillenniumSingularName", "tisočletje"},
        {"MillenniumPluralName", "tisočletij"},
        {"MillisecondPattern", "%n %u"},
        {"MillisecondFuturePrefix", "čez "},
        {"MillisecondFutureSuffix", ""},
        {"MillisecondPastPrefix", ""},
        {"MillisecondPastSuffix", "nazaj"},
        {"MillisecondSingularName", "milisekundo"},
        {"MillisecondPluralName", "milisekund"},
        {"MinutePattern", "%n %u"},
        {"MinuteFuturePrefix", "čez "},
        {"MinuteFutureSuffix", ""},
        {"MinutePastPrefix", ""},
        {"MinutePastSuffix", "nazaj"},
        {"MinuteSingularName", "minuto"},
        {"MinutePluralName", "minut"},
        {"MonthPattern", "%n %u"},
        {"MonthFuturePrefix", "čez "},
        {"MonthFutureSuffix", ""},
        {"MonthPastPrefix", ""},
        {"MonthPastSuffix", "nazaj"},
        {"MonthSingularName", "mesec"},
        {"MonthPluralName", "mesecev"},
        {"SecondPattern", "%n %u"},
        {"SecondFuturePrefix", "čez "},
        {"SecondFutureSuffix", ""},
        {"SecondPastPrefix", ""},
        {"SecondPastSuffix", "nazaj"},
        {"SecondSingularName", "sekundo"},
        {"SecondPluralName", "sekund"},
        {"WeekPattern", "%n %u"},
        {"WeekFuturePrefix", "čez "},
        {"WeekFutureSuffix", ""},
        {"WeekPastPrefix", ""},
        {"WeekPastSuffix", "nazaj"},
        {"WeekSingularName", "teden"},
        {"WeekPluralName", "tednov"},
        {"YearPattern", "%n %u"},
        {"YearFuturePrefix", "čez "},
        {"YearFutureSuffix", ""},
        {"YearPastPrefix", ""},
        {"YearPastSuffix", "nazaj"},
        {"YearSingularName", "leto"},
        {"YearPluralName", "let"},
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
     * Returns new {@link Resources_SL}
     *
     * @return new {@link Resources_SL}
     */
    public static Resources_SL getInstance() {
        return INSTANCE;
    }
}
