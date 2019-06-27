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
 * Default resources bundle [EL]
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Resources_EL extends Resources {
    /**
     * Default {@link Locale} {@code "EL"}
     */
    private static final Locale LOCALE = new Locale("el");
    /**
     * Default {@link Resources_EL} instance
     */
    private static final Resources_EL INSTANCE = new Resources_EL();

    private Object[][] resources;

    private Resources_EL() {
        this.loadResources();
    }

    private static final Object[][] OBJECTS = new Object[][]{
        {"CenturyPattern", "%n %u"},
        {"CenturyFuturePrefix", ""},
        {"CenturyFutureSuffix", "από τώρα "},
        {"CenturyPastPrefix", ""},
        {"CenturyPastSuffix", "Πριν από "},
        {"CenturySingularName", "αιώνας"},
        {"CenturyPluralName", "αιώνες"},
        {"DayPattern", "%n %u"},
        {"DayFuturePrefix", ""},
        {"DayFutureSuffix", "από τώρα "},
        {"DayPastPrefix", ""},
        {"DayPastSuffix", "Πριν από "},
        {"DaySingularName", "ημέρα"},
        {"DayPluralName", "ημέρες"},
        {"DecadePattern", "%n %u"},
        {"DecadeFuturePrefix", ""},
        {"DecadeFutureSuffix", "από τώρα "},
        {"DecadePastPrefix", ""},
        {"DecadePastSuffix", "Πριν από "},
        {"DecadeSingularName", "δεκαετία"},
        {"DecadePluralName", "δεκαετίες"},
        {"HourPattern", "%n %u"},
        {"HourFuturePrefix", ""},
        {"HourFutureSuffix", "από τώρα "},
        {"HourPastPrefix", ""},
        {"HourPastSuffix", "Πριν από "},
        {"HourSingularName", "ώρα"},
        {"HourPluralName", "ώρες"},
        {"JustNowPattern", "%u"},
        {"JustNowFuturePrefix", ""},
        {"JustNowFutureSuffix", "στιγμές από τώρα"},
        {"JustNowPastPrefix", "πριν από στιγμές"},
        {"JustNowPastSuffix", ""},
        {"JustNowSingularName", ""},
        {"JustNowPluralName", ""},
        {"MillenniumPattern", "%n %u"},
        {"MillenniumFuturePrefix", ""},
        {"MillenniumFutureSuffix", "από τώρα "},
        {"MillenniumPastPrefix", ""},
        {"MillenniumPastSuffix", "Πριν από "},
        {"MillenniumSingularName", "χιλιετία"},
        {"MillenniumPluralName", "χιλιετίες"},
        {"MillisecondPattern", "%n %u"},
        {"MillisecondFuturePrefix", ""},
        {"MillisecondFutureSuffix", "από τώρα "},
        {"MillisecondPastPrefix", ""},
        {"MillisecondPastSuffix", "Πριν από "},
        {"MillisecondSingularName", "Χιλιοστό του δευτερολέπτου"},
        {"MillisecondPluralName", "Χιλιοστά του δευτερολέπτου"},
        {"MinutePattern", "%n %u"},
        {"MinuteFuturePrefix", ""},
        {"MinuteFutureSuffix", "από τώρα "},
        {"MinutePastPrefix", ""},
        {"MinutePastSuffix", "Πριν από "},
        {"MinuteSingularName", "λεπτό"},
        {"MinutePluralName", "λεπτά"},
        {"MonthPattern", "%n %u"},
        {"MonthFuturePrefix", ""},
        {"MonthFutureSuffix", "από τώρα "},
        {"MonthPastPrefix", ""},
        {"MonthPastSuffix", "Πριν από "},
        {"MonthSingularName", "μήνας"},
        {"MonthPluralName", "μήνες"},
        {"SecondPattern", "%n %u"},
        {"SecondFuturePrefix", ""},
        {"SecondFutureSuffix", "από τώρα "},
        {"SecondPastPrefix", ""},
        {"SecondPastSuffix", "Πριν από "},
        {"SecondSingularName", "δευτερόλεπτο"},
        {"SecondPluralName", "δευτερόλεπτα"},
        {"WeekPattern", "%n %u"},
        {"WeekFuturePrefix", ""},
        {"WeekFutureSuffix", "από τώρα "},
        {"WeekPastPrefix", ""},
        {"WeekPastSuffix", "Πριν από "},
        {"WeekSingularName", "εβδομάδα"},
        {"WeekPluralName", "εβδομάδες"},
        {"YearPattern", "%n %u"},
        {"YearFuturePrefix", ""},
        {"YearFutureSuffix", "από τώρα "},
        {"YearPastPrefix", ""},
        {"YearPastSuffix", "Πριν από "},
        {"YearSingularName", "έτος"},
        {"YearPluralName", "έτη"},
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
     * Returns new {@link Resources_EL}
     *
     * @return new {@link Resources_EL}
     */
    public static Resources_EL getInstance() {
        return INSTANCE;
    }
}
