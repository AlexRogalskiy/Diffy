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
import com.wildbeeslabs.sensiblemetrics.diffy.common.resources.BaseResourceBundle;
import com.wildbeeslabs.sensiblemetrics.diffy.formatter.interfaces.TimeFormatProvider;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Locale;

/**
 * Default resources bundle [SK]
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Resources_SK extends Resources implements TimeFormatProvider {
    /**
     * Default {@link Locale} {@code "SK"}
     */
    private static final Locale LOCALE = new Locale("sk");
    /**
     * Default {@link Resources_SK} instance
     */
    private static final Resources_SK INSTANCE = new Resources_SK();

    private Object[][] resources;

    /**
     * Default resources constructor
     */
    private Resources_SK() {
        this.loadResources();
    }

    @Override
    public TimeFormat getFormat(final TimeMeasure timeUnit) {
        if (timeUnit instanceof MinuteTimeUnit) {
            return new Resources_SK.CsTimeFormatBuilder("Minute")
                .addFutureName("minútu", 1)
                .addFutureName("minúty", 4)
                .addFutureName("minút", Long.MAX_VALUE)
                .addPastName("minútou", 1)
                .addPastName("minútami", Long.MAX_VALUE)
                .build(this);
        } else if (timeUnit instanceof HourTimeUnit) {
            return new Resources_SK.CsTimeFormatBuilder("Hour")
                .addFutureName("hodinu", 1)
                .addFutureName("hodiny", 4)
                .addFutureName("hodín", Long.MAX_VALUE)
                .addPastName("hodinou", 1)
                .addPastName("hodinami", Long.MAX_VALUE)
                .build(this);
        } else if (timeUnit instanceof DayTimeUnit) {
            return new Resources_SK.CsTimeFormatBuilder("Day")
                .addFutureName("deň", 1)
                .addFutureName("dni", 4)
                .addFutureName("dní", Long.MAX_VALUE)
                .addPastName("dňom", 1)
                .addPastName("dňami", Long.MAX_VALUE)
                .build(this);
        } else if (timeUnit instanceof WeekTimeUnit) {
            return new Resources_SK.CsTimeFormatBuilder("Week")
                .addFutureName("týždeň", 1)
                .addFutureName("týždne", 4)
                .addFutureName("týždňov", Long.MAX_VALUE)
                .addPastName("týždňom", 1)
                .addPastName("týždňami", Long.MAX_VALUE)
                .build(this);
        } else if (timeUnit instanceof MonthTimeUnit) {
            return new Resources_SK.CsTimeFormatBuilder("Month")
                .addFutureName("mesiac", 1)
                .addFutureName("mesiace", 4)
                .addFutureName("mesiacov", Long.MAX_VALUE)
                .addPastName("mesiacom", 1)
                .addPastName("mesiacmi", Long.MAX_VALUE)
                .build(this);
        } else if (timeUnit instanceof YearTimeUnit) {
            return new Resources_SK.CsTimeFormatBuilder("Year")
                .addFutureName("rok", 1)
                .addFutureName("roky", 4)
                .addFutureName("rokov", Long.MAX_VALUE)
                .addPastName("rokom", 1)
                .addPastName("rokmi", Long.MAX_VALUE)
                .build(this);
        }
        return null;
    }

    /**
     * Loads {@link BaseResourceBundle} items
     */
    public void loadResources() {
        this.resources = BaseResourceBundle.getInstance(LOCALE).getResources();
    }

    /**
     * Returns new {@link Resources_SK}
     *
     * @return new {@link Resources_SK}
     */
    public static Resources_SK getInstance() {
        return INSTANCE;
    }
}
