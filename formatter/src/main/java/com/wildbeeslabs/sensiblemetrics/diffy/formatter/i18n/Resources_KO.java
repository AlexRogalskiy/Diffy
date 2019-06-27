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
 * Default resources bundle [KO]
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Resources_KO extends Resources {
    /**
     * Default {@link Locale} {@code "KO"}
     */
    private static final Locale LOCALE = new Locale("ko");
    /**
     * Default {@link Resources_KO} instance
     */
    private static final Resources_KO INSTANCE = new Resources_KO();

    private Object[][] resources;

    private Resources_KO() {
        this.loadResources();
    }

    private static final Object[][] OBJECTS = new Object[][]{
        {"CenturyPattern", "%n%u"},
        {"CenturyFuturePrefix", ""},
        {"CenturyFutureSuffix", "후"},
        {"CenturyPastPrefix", ""},
        {"CenturyPastSuffix", "전"},
        {"CenturySingularName", "세기"},
        {"CenturyPluralName", "세기"},
        {"DayPattern", "%n%u"},
        {"DayFuturePrefix", ""},
        {"DayFutureSuffix", "후"},
        {"DayPastPrefix", ""},
        {"DayPastSuffix", "전"},
        {"DaySingularName", "일"},
        {"DayPluralName", "일"},
        {"DecadePattern", "%n%u"},
        {"DecadeFuturePrefix", ""},
        {"DecadeFutureSuffix", "후"},
        {"DecadePastPrefix", ""},
        {"DecadePastSuffix", "전"},
        {"DecadeSingularName", "0년"},
        {"DecadePluralName", "0년"},
        {"HourPattern", "%n%u"},
        {"HourFuturePrefix", ""},
        {"HourFutureSuffix", "후"},
        {"HourPastPrefix", ""},
        {"HourPastSuffix", "전"},
        {"HourSingularName", "시간"},
        {"HourPluralName", "시간"},
        {"JustNowPattern", "%u"},
        {"JustNowFuturePrefix", ""},
        {"JustNowFutureSuffix", "지금"},
        {"JustNowPastPrefix", "방금"},
        {"JustNowPastSuffix", ""},
        {"JustNowSingularName", ""},
        {"JustNowPluralName", ""},
        {"MillenniumPattern", "%n%u"},
        {"MillenniumFuturePrefix", ""},
        {"MillenniumFutureSuffix", "후"},
        {"MillenniumPastPrefix", ""},
        {"MillenniumPastSuffix", "전"},
        {"MillenniumSingularName", "세기"},
        {"MillenniumPluralName", "세기"},
        {"MillisecondPattern", "%n%u"},
        {"MillisecondFuturePrefix", ""},
        {"MillisecondFutureSuffix", "후"},
        {"MillisecondPastPrefix", ""},
        {"MillisecondPastSuffix", "전"},
        {"MillisecondSingularName", "밀리초"},
        {"MillisecondPluralName", "밀리초"},
        {"MinutePattern", "%n%u"},
        {"MinuteFuturePrefix", ""},
        {"MinuteFutureSuffix", "후"},
        {"MinutePastPrefix", ""},
        {"MinutePastSuffix", "전"},
        {"MinuteSingularName", "분"},
        {"MinutePluralName", "분"},
        {"MonthPattern", "%n%u"},
        {"MonthFuturePrefix", ""},
        {"MonthFutureSuffix", " 후"},
        {"MonthPastPrefix", ""},
        {"MonthPastSuffix", " 전"},
        {"MonthSingularName", "개월"},
        {"MonthPluralName", "개월"},
        {"SecondPattern", "%n%u"},
        {"SecondFuturePrefix", ""},
        {"SecondFutureSuffix", "후"},
        {"SecondPastPrefix", ""},
        {"SecondPastSuffix", "전"},
        {"SecondSingularName", "초"},
        {"SecondPluralName", "초"},
        {"WeekPattern", "%n%u"},
        {"WeekFuturePrefix", ""},
        {"WeekFutureSuffix", "후"},
        {"WeekPastPrefix", ""},
        {"WeekPastSuffix", "전"},
        {"WeekSingularName", "주"},
        {"WeekPluralName", "주"},
        {"YearPattern", "%n%u"},
        {"YearFuturePrefix", ""},
        {"YearFutureSuffix", "후"},
        {"YearPastPrefix", ""},
        {"YearPastSuffix", "전"},
        {"YearSingularName", "년"},
        {"YearPluralName", "년"},
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
     * Returns new {@link Resources_KO}
     *
     * @return new {@link Resources_KO}
     */
    public static Resources_KO getInstance() {
        return INSTANCE;
    }
}
