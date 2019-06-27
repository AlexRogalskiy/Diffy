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
 * Default resources bundle [HE]
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Resources_HE extends Resources {
    /**
     * Default {@link Locale} {@code "HE"}
     */
    private static final Locale LOCALE = new Locale("he");
    /**
     * Default {@link Resources_HE} instance
     */
    private static final Resources_HE INSTANCE = new Resources_HE();

    private Object[][] resources;

    private Resources_HE() {
        this.loadResources();
    }

    private static final Object[][] OBJECTS = new Object[][]{
        {"CenturyPattern", "%n %u"},
        {"CenturyFuturePrefix", "בעוד "},
        {"CenturyFutureSuffix", ""},
        {"CenturyPastPrefix", "לפני "},
        {"CenturyPastSuffix", ""},
        {"CenturySingularName", "מאה"},
        {"CenturyPluralName", "מאות"},
        {"DayPattern", "%n %u"},
        {"DayFuturePrefix", "בעוד "},
        {"DayFutureSuffix", ""},
        {"DayPastPrefix", "לפני "},
        {"DayPastSuffix", ""},
        {"DaySingularName", "יום"},
        {"DayPluralName", "ימים"},
        {"DecadePattern", "%n %u"},
        {"DecadeFuturePrefix", "בעוד "},
        {"DecadeFutureSuffix", ""},
        {"DecadePastPrefix", "לפני "},
        {"DecadePastSuffix", ""},
        {"DecadeSingularName", "עשור"},
        {"DecadePluralName", "עשורים"},
        {"HourPattern", "%n %u"},
        {"HourFuturePrefix", "בעוד "},
        {"HourFutureSuffix", ""},
        {"HourPastPrefix", "לפני "},
        {"HourPastSuffix", ""},
        {"HourSingularName", "שעה"},
        {"HourPluralName", "שעות"},
        {"JustNowPattern", "%u"},
        {"JustNowFuturePrefix", ""},
        {"JustNowFutureSuffix", "עוד רגע"},
        {"JustNowPastPrefix", "לפני רגע"},
        {"JustNowPastSuffix", ""},
        {"JustNowSingularName", ""},
        {"JustNowPluralName", ""},
        {"MillenniumPattern", "%n %u"},
        {"MillenniumFuturePrefix", "בעוד "},
        {"MillenniumFutureSuffix", ""},
        {"MillenniumPastPrefix", "לפני "},
        {"MillenniumPastSuffix", ""},
        {"MillenniumSingularName", "מילניום"},
        {"MillenniumPluralName", "מילניומים"},
        {"MillisecondPattern", "%n %u"},
        {"MillisecondFuturePrefix", "בעוד "},
        {"MillisecondFutureSuffix", ""},
        {"MillisecondPastPrefix", "לפני "},
        {"MillisecondPastSuffix", ""},
        {"MillisecondSingularName", "אלפית שנייה"},
        {"MillisecondPluralName", "אלפיות שנייה"},
        {"MinutePattern", "%n %u"},
        {"MinuteFuturePrefix", "בעוד "},
        {"MinuteFutureSuffix", ""},
        {"MinutePastPrefix", "לפני "},
        {"MinutePastSuffix", ""},
        {"MinuteSingularName", "דקה"},
        {"MinutePluralName", "דקות"},
        {"MonthPattern", "%n %u"},
        {"MonthFuturePrefix", "בעוד "},
        {"MonthFutureSuffix", ""},
        {"MonthPastPrefix", "לפני "},
        {"MonthPastSuffix", ""},
        {"MonthSingularName", "חודש"},
        {"MonthPluralName", "חודשים"},
        {"SecondPattern", "%n %u"},
        {"SecondFuturePrefix", "בעוד "},
        {"SecondFutureSuffix", ""},
        {"SecondPastPrefix", "לפני "},
        {"SecondPastSuffix", ""},
        {"SecondSingularName", "שנייה"},
        {"SecondPluralName", "שניות"},
        {"WeekPattern", "%n %u"},
        {"WeekFuturePrefix", "בעוד "},
        {"WeekFutureSuffix", ""},
        {"WeekPastPrefix", "לפני "},
        {"WeekPastSuffix", ""},
        {"WeekSingularName", "שבוע"},
        {"WeekPluralName", "שבועות"},
        {"YearPattern", "%n %u"},
        {"YearFuturePrefix", "בעוד "},
        {"YearFutureSuffix", ""},
        {"YearPastPrefix", "לפני "},
        {"YearPastSuffix", ""},
        {"YearSingularName", "שנה"},
        {"YearPluralName", "שנים"},
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
     * Returns new {@link Resources_HE}
     *
     * @return new {@link Resources_HE}
     */
    public static Resources_HE getInstance() {
        return INSTANCE;
    }
}
