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
 * Default resources bundle [HR]
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Resources_HR extends Resources implements TimeFormatProvider {
    /**
     * Default {@link Locale} {@code "HR"}
     */
    private static final Locale LOCALE = new Locale("hr");
    /**
     * Default {@link Resources_HK} instance
     */
    private static final Resources_HR INSTANCE = new Resources_HR();

    private Object[][] resources;

    /**
     * Default resources constructor
     */
    private Resources_HR() {
        this.loadResources();
    }

    @Override
    public TimeFormat getFormat(final TimeMeasure timeUnit) {
        if (timeUnit instanceof MinuteTimeUnit) {
            return new CsTimeFormatBuilder<>("Minute").addNames("minutu", 1)
                .addNames("minute", 4).addNames("minuta", Long.MAX_VALUE)
                .build(this);
        } else if (timeUnit instanceof HourTimeUnit) {
            return new CsTimeFormatBuilder<>("Hour").addNames("sat", 1)
                .addNames("sata", 4).addNames("sati", Long.MAX_VALUE)
                .build(this);
        } else if (timeUnit instanceof DayTimeUnit) {
            return new CsTimeFormatBuilder<>("Day").addNames("dan", 1)
                .addNames("dana", 4).addNames("dana", Long.MAX_VALUE)
                .build(this);
        } else if (timeUnit instanceof WeekTimeUnit) {
            return new CsTimeFormatBuilder<>("Week").addNames("tjedan", 1)
                .addNames("tjedna", 4).addNames("tjedana", Long.MAX_VALUE)
                .build(this);
        } else if (timeUnit instanceof MonthTimeUnit) {
            return new CsTimeFormatBuilder<>("Month").addNames("mjesec", 1)
                .addNames("mjeseca", 4).addNames("mjeseci", Long.MAX_VALUE)
                .build(this);
        } else if (timeUnit instanceof YearTimeUnit) {
            return new CsTimeFormatBuilder<>("Year").addNames("godinu", 1)
                .addNames("godine", 4).addNames("godina", Long.MAX_VALUE)
                .build(this);
        } else if (timeUnit instanceof MillenniumTimeUnit) {
            return new CsTimeFormatBuilder<>("Millennium")
                .addNames("tisućljeće", 1).addNames("tisućljeća", Long.MAX_VALUE)
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
     * Returns new {@link Resources_HR}
     *
     * @return new {@link Resources_HR}
     */
    public static Resources_HR getInstance() {
        return INSTANCE;
    }
}
