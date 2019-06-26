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

import com.wildbeeslabs.sensiblemetrics.diffy.common.helpers.iface.TimeFormat;
import com.wildbeeslabs.sensiblemetrics.diffy.common.helpers.iface.TimeMeasure;
import com.wildbeeslabs.sensiblemetrics.diffy.common.helpers.unit.*;
import com.wildbeeslabs.sensiblemetrics.diffy.formatter.interfaces.TimeFormatProvider;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Default resources bundle [CS]
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Resources_CS extends Resources implements TimeFormatProvider {

    private static final Object[][] OBJECTS = new Object[][]{
        {"CenturyPattern", "%n %u"},
        {"CenturyFuturePrefix", "za "},
        {"CenturyFutureSuffix", ""},
        {"CenturyPastPrefix", "před "},
        {"CenturyPastSuffix", ""},
        {"CenturySingularName", "století"},
        {"CenturyPluralName", "století"},
        {"CenturyPastSingularName", "stoletím"},
        {"CenturyPastPluralName", "stoletími"},
        {"CenturyFutureSingularName", "století"},
        {"CenturyFuturePluralName", "století"},
        {"DayPattern", "%n %u"},
        {"DayFuturePrefix", "za "},
        {"DayFutureSuffix", ""},
        {"DayPastPrefix", "před "},
        {"DayPastSuffix", ""},
        {"DaySingularName", "den"},
        {"DayPluralName", "dny"},
        {"DecadePattern", "%n %u"},
        {"DecadeFuturePrefix", "za "},
        {"DecadeFutureSuffix", ""},
        {"DecadePastPrefix", "před "},
        {"DecadePastSuffix", ""},
        {"DecadeSingularName", "desetiletí"},
        {"DecadePluralName", "desetiletí"},
        {"DecadePastSingularName", "desetiletím"},
        {"DecadePastPluralName", "desetiletími"},
        {"DecadeFutureSingularName", "desetiletí"},
        {"DecadeFuturePluralName", "desetiletí"},
        {"HourPattern", "%n %u"},
        {"HourFuturePrefix", "za "},
        {"HourFutureSuffix", ""},
        {"HourPastPrefix", "před"},
        {"HourPastSuffix", ""},
        {"HourSingularName", "hodina"},
        {"HourPluralName", "hodiny"},
        {"JustNowPattern", "%u"},
        {"JustNowFuturePrefix", ""},
        {"JustNowFutureSuffix", "za chvíli"},
        {"JustNowPastPrefix", "před chvílí"},
        {"JustNowPastSuffix", ""},
        {"JustNowSingularName", ""},
        {"JustNowPluralName", ""},
        {"MillenniumPattern", "%n %u"},
        {"MillenniumFuturePrefix", "za "},
        {"MillenniumFutureSuffix", ""},
        {"MillenniumPastPrefix", "před "},
        {"MillenniumPastSuffix", ""},
        {"MillenniumSingularName", "tisíciletí"},
        {"MillenniumPluralName", "tisíciletí"},
        {"MillisecondPattern", "%n %u"},
        {"MillisecondFuturePrefix", "za "},
        {"MillisecondFutureSuffix", ""},
        {"MillisecondPastPrefix", "před "},
        {"MillisecondPastSuffix", ""},
        {"MillisecondSingularName", "milisekunda"},
        {"MillisecondPluralName", "milisekundy"},
        {"MillisecondPastSingularName", "milisekundou"},
        {"MillisecondPastPluralName", "milisekundami"},
        {"MillisecondFutureSingularName", "milisekundu"},
        {"MillisecondFuturePluralName", "milisekund"},
        {"MinutePattern", "%n %u"},
        {"MinuteFuturePrefix", "za "},
        {"MinuteFutureSuffix", ""},
        {"MinutePastPrefix", "před "},
        {"MinutePastSuffix", ""},
        {"MinuteSingularName", "minuta"},
        {"MinutePluralName", "minuty"},
        {"MonthPattern", "%n %u"},
        {"MonthFuturePrefix", "za "},
        {"MonthFutureSuffix", ""},
        {"MonthPastPrefix", "před "},
        {"MonthPastSuffix", ""},
        {"MonthSingularName", "měsíc"},
        {"MonthPluralName", "měsíce"},
        {"SecondPattern", "%n %u"},
        {"SecondFuturePrefix", "za "},
        {"SecondFutureSuffix", ""},
        {"SecondPastPrefix", "před "},
        {"SecondPastSuffix", ""},
        {"SecondSingularName", "sekunda"},
        {"SecondPluralName", "sekundy"},
        {"WeekPattern", "%n %u"},
        {"WeekFuturePrefix", "za "},
        {"WeekFutureSuffix", ""},
        {"WeekPastPrefix", "před "},
        {"WeekPastSuffix", ""},
        {"WeekSingularName", "týden"},
        {"WeekPluralName", "týdny"},
        {"YearPattern", "%n %u"},
        {"YearFuturePrefix", "za "},
        {"YearFutureSuffix", ""},
        {"YearPastPrefix", "před "},
        {"YearPastSuffix", ""},
        {"YearSingularName", "rok"},
        {"YearPluralName", "roky"},
        {"AbstractTimeUnitPattern", ""},
        {"AbstractTimeUnitFuturePrefix", ""},
        {"AbstractTimeUnitFutureSuffix", ""},
        {"AbstractTimeUnitPastPrefix", ""},
        {"AbstractTimeUnitPastSuffix", ""},
        {"AbstractTimeUnitSingularName", ""},
        {"AbstractTimeUnitPluralName", ""}};

    @Override
    public Object[][] getResources() {
        return Resources_CS.OBJECTS;
    }

    @Override
    public TimeFormat getFormat(final TimeMeasure timeUnit) {
        if (timeUnit instanceof MinuteTimeUnit) {
            return new CsTimeFormatBuilder<>("Minute")
                .addFutureName("minutu", 1)
                .addFutureName("minuty", 4)
                .addFutureName("minut", Long.MAX_VALUE)
                .addPastName("minutou", 1)
                .addPastName("minutami", Long.MAX_VALUE)
                .build(this);
        } else if (timeUnit instanceof HourTimeUnit) {
            return new CsTimeFormatBuilder<>("Hour")
                .addFutureName("hodinu", 1)
                .addFutureName("hodiny", 4)
                .addFutureName("hodin", Long.MAX_VALUE)
                .addPastName("hodinou", 1)
                .addPastName("hodinami", Long.MAX_VALUE)
                .build(this);
        } else if (timeUnit instanceof DayTimeUnit) {
            return new CsTimeFormatBuilder<>("Day")
                .addFutureName("den", 1)
                .addFutureName("dny", 4)
                .addFutureName("dní", Long.MAX_VALUE)
                .addPastName("dnem", 1)
                .addPastName("dny", Long.MAX_VALUE)
                .build(this);
        } else if (timeUnit instanceof WeekTimeUnit) {
            return new CsTimeFormatBuilder<>("Week")
                .addFutureName("týden", 1)
                .addFutureName("týdny", 4)
                .addFutureName("týdnů", Long.MAX_VALUE)
                .addPastName("týdnem", 1)
                .addPastName("týdny", Long.MAX_VALUE)
                .build(this);
        } else if (timeUnit instanceof MonthTimeUnit) {
            return new CsTimeFormatBuilder<>("Month")
                .addFutureName("měsíc", 1)
                .addFutureName("měsíce", 4)
                .addFutureName("měsíců", Long.MAX_VALUE)
                .addPastName("měsícem", 1)
                .addPastName("měsíci", Long.MAX_VALUE)
                .build(this);
        } else if (timeUnit instanceof YearTimeUnit) {
            return new CsTimeFormatBuilder<>("Year")
                .addFutureName("rok", 1)
                .addFutureName("roky", 4)
                .addFutureName("let", Long.MAX_VALUE)
                .addPastName("rokem", 1)
                .addPastName("roky", Long.MAX_VALUE)
                .build(this);
        }
        return null;
    }
}
