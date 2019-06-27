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
 * Default resources bundle [ET]
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Resources_ET extends Resources {
    /**
     * Default {@link Locale} {@code "ET"}
     */
    private static final Locale LOCALE = new Locale("et");
    /**
     * Default {@link Resources_ET} instance
     */
    private static final Resources_ET INSTANCE = new Resources_ET();

    private Object[][] resources;

    private Resources_ET() {
        this.loadResources();
    }

    private static final Object[][] OBJECTS = new Object[][]{
        {"CenturyPattern", "%u"},
        {"CenturyPluralPattern", "%n %u"},
        {"CenturyPastSingularName", "sajand"},
        {"CenturyPastPluralName", "sajandit"},
        {"CenturyFutureSingularName", "sajandi"},
        {"CenturyPastSuffix", "tagasi"},
        {"CenturyFutureSuffix", "pärast"},
        {"DayPattern", "%u"},
        {"DayPluralPattern", "%n %u"},
        {"DayPastSingularName", "eile"},
        {"DayPastPluralName", "päeva"},
        {"DayFutureSingularName", "homme"},
        {"DayFuturePluralName", "päeva"},
        {"DayPastSuffix", "tagasi"},
        {"DayFutureSuffix", "pärast"},
        {"DecadePattern", "%u"},
        {"DecadePluralPattern", "%n %u"},
        {"DecadePastSingularName", "aastakümme"},
        {"DecadePastPluralName", "aastakümmet"},
        {"DecadeFutureSingularName", "aastakümne"},
        {"DecadePastSuffix", "tagasi"},
        {"DecadeFutureSuffix", "pärast"},
        {"HourPattern", "%u"},
        {"HourPluralPattern", "%n %u"},
        {"HourPastSingularName", "tund"},
        {"HourPastPluralName", "tundi"},
        {"HourFutureSingularName", "tunni"},
        {"HourPastSuffix", "tagasi"},
        {"HourFutureSuffix", "pärast"},
        {"JustNowPattern", "%u"},
        {"JustNowPastSingularName", "hetk"},
        {"JustNowFutureSingularName", "hetke"},
        {"JustNowPastSuffix", "tagasi"},
        {"JustNowFutureSuffix", "pärast"},
        {"MillenniumPattern", "%u"},
        {"MillenniumPluralPattern", "%n %u"},
        {"MillenniumPastSingularName", "aastatuhat"},
        {"MillenniumPastPluralName", "aastatuhandet"},
        {"MillenniumFutureSingularName", "aastatuhande"},
        {"MillenniumPastSuffix", "tagasi"},
        {"MillenniumFutureSuffix", "pärast"},
        {"MillisecondPattern", "%u"},
        {"MillisecondPluralPattern", "%n %u"},
        {"MillisecondPastSingularName", "millisekund"},
        {"MillisecondPastPluralName", "millisekundit"},
        {"MillisecondFutureSingularName", "millisekundi"},
        {"MillisecondFuturePluralName", "millisekundi"},
        {"MillisecondPastSuffix", "tagasi"},
        {"MillisecondFutureSuffix", "pärast"},
        {"MinutePattern", "%u"},
        {"MinutePluralPattern", "%n %u"},
        {"MinutePastSingularName", "minut"},
        {"MinutePastPluralName", "minutit"},
        {"MinuteFutureSingularName", "minuti"},
        {"MinuteFuturePluralName", "minuti"},
        {"MinutePastSuffix", "tagasi"},
        {"MinuteFutureSuffix", "pärast"},
        {"MonthPattern", "%u"},
        {"MonthPluralPattern", "%n %u"},
        {"MonthPastSingularName", "kuu"},
        {"MonthPastPluralName", "kuud"},
        {"MonthFutureSingularName", "kuu"},
        {"MonthPastSuffix", "tagasi"},
        {"MonthFutureSuffix", "pärast"},
        {"SecondPattern", "%u"},
        {"SecondPluralPattern", "%n %u"},
        {"SecondPastSingularName", "sekund"},
        {"SecondPastPluralName", "sekundit"},
        {"SecondFutureSingularName", "sekundi"},
        {"SecondFuturePluralName", "sekundi"},
        {"SecondPastSuffix", "tagasi"},
        {"SecondFutureSuffix", "pärast"},
        {"WeekPattern", "%u"},
        {"WeekPluralPattern", "%n %u"},
        {"WeekPastSingularName", "nädal"},
        {"WeekPastPluralName", "nädalat"},
        {"WeekFutureSingularName", "nädala"},
        {"WeekFuturePluralName", "nädala"},
        {"WeekPastSuffix", "tagasi"},
        {"WeekFutureSuffix", "pärast"},
        {"YearPattern", "%u"},
        {"YearPluralPattern", "%n %u"},
        {"YearPastSingularName", "aasta"},
        {"YearPastPluralName", "aastat"},
        {"YearFutureSingularName", "aasta"},
        {"YearFuturePluralName", "aasta"},
        {"YearPastSuffix", "tagasi"},
        {"YearFutureSuffix", "pärast"},
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
     * Returns new {@link Resources_ET}
     *
     * @return new {@link Resources_ET}
     */
    public static Resources_ET getInstance() {
        return INSTANCE;
    }
}
