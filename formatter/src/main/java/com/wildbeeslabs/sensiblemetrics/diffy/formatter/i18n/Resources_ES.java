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
 * Default resources bundle [ES]
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Resources_ES extends Resources {
    /**
     * Default {@link Locale} {@code "ES"}
     */
    private static final Locale LOCALE = new Locale("es");
    /**
     * Default {@link Resources_ES} instance
     */
    private static final Resources_ES INSTANCE = new Resources_ES();

    private Object[][] resources;

    private Resources_ES() {
        this.loadResources();
    }

    private static final Object[][] OBJECTS = new Object[][]{
        {"CenturyPattern", "%n %u"},
        {"CenturyFuturePrefix", "dentro de "},
        {"CenturyFutureSuffix", ""},
        {"CenturyPastPrefix", "hace "},
        {"CenturyPastSuffix", ""},
        {"CenturySingularName", "siglo"},
        {"CenturyPluralName", "siglos"},
        {"DayPattern", "%n %u"},
        {"DayFuturePrefix", "dentro de "},
        {"DayFutureSuffix", ""},
        {"DayPastPrefix", "hace "},
        {"DayPastSuffix", ""},
        {"DaySingularName", "día "},
        {"DayPluralName", "días"},
        {"DecadePattern", "%n %u"},
        {"DecadeFuturePrefix", "dentro de "},
        {"DecadeFutureSuffix", ""},
        {"DecadePastPrefix", "hace "},
        {"DecadePastSuffix", ""},
        {"DecadeSingularName", "decenio"},
        {"DecadePluralName", "decenios"},
        {"HourPattern", "%n %u"},
        {"HourFuturePrefix", "dentro de "},
        {"HourFutureSuffix", ""},
        {"HourPastPrefix", "hace "},
        {"HourPastSuffix", ""},
        {"HourSingularName", "hora"},
        {"HourPluralName", "horas"},
        {"JustNowPattern", "%u"},
        {"JustNowFuturePrefix", ""},
        {"JustNowFutureSuffix", "en un instante"},
        {"JustNowPastPrefix", "hace instantes"},
        {"JustNowPastSuffix", ""},
        {"JustNowSingularName", ""},
        {"JustNowPluralName", ""},
        {"MillenniumPattern", "%n %u"},
        {"MillenniumFuturePrefix", "dentro de "},
        {"MillenniumFutureSuffix", ""},
        {"MillenniumPastPrefix", "hace "},
        {"MillenniumPastSuffix", ""},
        {"MillenniumSingularName", "milenio"},
        {"MillenniumPluralName", "milenios"},
        {"MillisecondPattern", "%n %u"},
        {"MillisecondFuturePrefix", "dentro de "},
        {"MillisecondFutureSuffix", ""},
        {"MillisecondPastPrefix", "hace "},
        {"MillisecondPastSuffix", ""},
        {"MillisecondSingularName", "milisegundo"},
        {"MillisecondPluralName", "milisegundo"},
        {"MinutePattern", "%n %u"},
        {"MinuteFuturePrefix", "dentro de "},
        {"MinuteFutureSuffix", ""},
        {"MinutePastPrefix", "hace "},
        {"MinutePastSuffix", ""},
        {"MinuteSingularName", "minuto"},
        {"MinutePluralName", "minutos"},
        {"MonthPattern", "%n %u"},
        {"MonthFuturePrefix", "dentro de "},
        {"MonthFutureSuffix", ""},
        {"MonthPastPrefix", "hace "},
        {"MonthPastSuffix", ""},
        {"MonthSingularName", "mes"},
        {"MonthPluralName", "meses"},
        {"SecondPattern", "%n %u"},
        {"SecondFuturePrefix", "dentro de "},
        {"SecondFutureSuffix", ""},
        {"SecondPastPrefix", "hace "},
        {"SecondPastSuffix", ""},
        {"SecondSingularName", "segundo"},
        {"SecondPluralName", "segundos"},
        {"WeekPattern", "%n %u"},
        {"WeekFuturePrefix", "dentro de "},
        {"WeekFutureSuffix", ""},
        {"WeekPastPrefix", "hace "},
        {"WeekPastSuffix", ""},
        {"WeekSingularName", "semana"},
        {"WeekPluralName", "semanas"},
        {"YearPattern", "%n %u"},
        {"YearFuturePrefix", "dentro de "},
        {"YearFutureSuffix", ""},
        {"YearPastPrefix", "hace "},
        {"YearPastSuffix", ""},
        {"YearSingularName", "año"},
        {"YearPluralName", "años"},
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
     * Returns new {@link Resources_ES}
     *
     * @return new {@link Resources_ES}
     */
    public static Resources_ES getInstance() {
        return INSTANCE;
    }
}
