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
 * Default resources bundle [PT]
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Resources_PT extends Resources {
    /**
     * Default {@link Locale} {@code "PL"}
     */
    private static final Locale LOCALE = new Locale("pt");
    /**
     * Default {@link Resources_PT} instance
     */
    private static final Resources_PT INSTANCE = new Resources_PT();

    private Object[][] resources;

    private Resources_PT() {
        this.loadResources();
    }

    private static final Object[][] OBJECTS = new Object[][]{
        {"CenturyPattern", "%n %u"},
        {"CenturyFuturePrefix", "daqui a "},
        {"CenturyFutureSuffix", ""},
        {"CenturyPastPrefix", ""},
        {"CenturyPastSuffix", "atrás"},
        {"CenturySingularName", "século"},
        {"CenturyPluralName", "séculos"},
        {"DayPattern", "%n %u"},
        {"DayFuturePrefix", "daqui a "},
        {"DayFutureSuffix", ""},
        {"DayPastPrefix", ""},
        {"DayPastSuffix", "atrás"},
        {"DaySingularName", "dia"},
        {"DayPluralName", "dias"},
        {"DecadePattern", "%n %u"},
        {"DecadeFuturePrefix", "daqui a "},
        {"DecadeFutureSuffix", ""},
        {"DecadePastPrefix", ""},
        {"DecadePastSuffix", "atrás"},
        {"DecadeSingularName", "década"},
        {"DecadePluralName", "décadas"},
        {"HourPattern", "%n %u"},
        {"HourFuturePrefix", "daqui a "},
        {"HourFutureSuffix", ""},
        {"HourPastPrefix", ""},
        {"HourPastSuffix", "atrás"},
        {"HourSingularName", "hora"},
        {"HourPluralName", "horas"},
        {"JustNowPattern", "%u"},
        {"JustNowFuturePrefix", "agora mesmo"},
        {"JustNowFutureSuffix", ""},
        {"JustNowPastPrefix", "agora há pouco"},
        {"JustNowPastSuffix", ""},
        {"JustNowSingularName", ""},
        {"JustNowPluralName", ""},
        {"MillenniumPattern", "%n %u"},
        {"MillenniumFuturePrefix", "daqui a "},
        {"MillenniumFutureSuffix", ""},
        {"MillenniumPastPrefix", ""},
        {"MillenniumPastSuffix", "atrás"},
        {"MillenniumSingularName", "milênio"},
        {"MillenniumPluralName", "milênios"},
        {"MillisecondPattern", "%n %u"},
        {"MillisecondFuturePrefix", "daqui a "},
        {"MillisecondFutureSuffix", ""},
        {"MillisecondPastPrefix", ""},
        {"MillisecondPastSuffix", "atrás"},
        {"MillisecondSingularName", "millisegundo"},
        {"MillisecondPluralName", "millisegundos"},
        {"MinutePattern", "%n %u"},
        {"MinuteFuturePrefix", "daqui a "},
        {"MinuteFutureSuffix", ""},
        {"MinutePastPrefix", ""},
        {"MinutePastSuffix", "atrás"},
        {"MinuteSingularName", "minuto"},
        {"MinutePluralName", "minutos"},
        {"MonthPattern", "%n %u"},
        {"MonthFuturePrefix", "daqui a "},
        {"MonthFutureSuffix", ""},
        {"MonthPastPrefix", ""},
        {"MonthPastSuffix", "atrás"},
        {"MonthSingularName", "mês"},
        {"MonthPluralName", "meses"},
        {"SecondPattern", "%n %u"},
        {"SecondFuturePrefix", "daqui a "},
        {"SecondFutureSuffix", ""},
        {"SecondPastPrefix", ""},
        {"SecondPastSuffix", "atrás"},
        {"SecondSingularName", "segundo"},
        {"SecondPluralName", "segundos"},
        {"WeekPattern", "%n %u"},
        {"WeekFuturePrefix", "daqui a "},
        {"WeekFutureSuffix", ""},
        {"WeekPastPrefix", ""},
        {"WeekPastSuffix", "atrás"},
        {"WeekSingularName", "semana"},
        {"WeekPluralName", "semanas"},
        {"YearPattern", "%n %u"},
        {"YearFuturePrefix", "daqui a "},
        {"YearFutureSuffix", ""},
        {"YearPastPrefix", ""},
        {"YearPastSuffix", "atrás"},
        {"YearSingularName", "ano"},
        {"YearPluralName", "anos"},
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
     * Returns new {@link Resources_PT}
     *
     * @return new {@link Resources_PT}
     */
    public static Resources_PT getInstance() {
        return INSTANCE;
    }
}
