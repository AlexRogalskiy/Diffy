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

import com.wildbeeslabs.sensiblemetrics.diffy.common.helpers.iface.Duration;
import com.wildbeeslabs.sensiblemetrics.diffy.common.helpers.iface.TimeFormat;
import com.wildbeeslabs.sensiblemetrics.diffy.common.helpers.iface.TimeMeasure;
import com.wildbeeslabs.sensiblemetrics.diffy.common.helpers.unit.DayTimeUnit;
import com.wildbeeslabs.sensiblemetrics.diffy.common.resources.BaseResourceBundle;
import com.wildbeeslabs.sensiblemetrics.diffy.formatter.interfaces.TimeFormatProvider;
import com.wildbeeslabs.sensiblemetrics.diffy.formatter.service.SimpleTimeFormat;
import lombok.*;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Default resources bundle [FI]
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Resources_FI extends Resources implements TimeFormatProvider {
    /**
     * Default tolerance constant
     */
    private static final int tolerance = 50;
    /**
     * Default {@link Locale} {@code "FI"}
     */
    private static final Locale LOCALE = new Locale("fi");
    /**
     * Default {@link Resources_FI} instance
     */
    private static final Resources_FI INSTANCE = new Resources_FI();

    private Object[][] resources;

    /**
     * Default resources constructor
     */
    private Resources_FI() {
        this.loadResources();
    }

    private volatile ConcurrentMap<TimeMeasure, TimeFormat> formatMap = new ConcurrentHashMap<>();

    @Override
    public TimeFormat getFormat(final TimeMeasure timeUnit) {
        if (!this.formatMap.containsKey(timeUnit)) {
            this.formatMap.putIfAbsent(timeUnit, new FiTimeFormat(this, timeUnit));
        }
        return this.formatMap.get(timeUnit);
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    private static class FiTimeFormat extends SimpleTimeFormat {

        @Setter(AccessLevel.NONE)
        @Getter(AccessLevel.NONE)
        private final ResourceBundle bundle;
        private String pastName = null;
        private String futureName = null;
        private String pastPluralName = null;
        private String futurePluralName = null;
        private String pluralPattern = null;

        public FiTimeFormat(final ResourceBundle rb, final TimeMeasure unit) {
            this.bundle = rb;
            if (this.bundle.containsKey(getUnitName(unit) + "PastSingularName")) {
                this.setPastName(bundle.getString(getUnitName(unit) + "PastSingularName"))
                    .setFutureName(bundle.getString(getUnitName(unit) + "FutureSingularName"))
                    .setPastPluralName(bundle.getString(getUnitName(unit) + "PastSingularName"))
                    .setFuturePluralName(bundle.getString(getUnitName(unit) + "FutureSingularName"))
                    .setPluralPattern(bundle.getString(getUnitName(unit) + "Pattern"));

                if (this.bundle.containsKey(getUnitName(unit) + "PastPluralName")) {
                    this.setPastPluralName(bundle.getString(getUnitName(unit) + "PastPluralName"));
                }

                if (this.bundle.containsKey(getUnitName(unit) + "FuturePluralName")) {
                    this.setFuturePluralName(bundle.getString(getUnitName(unit) + "FuturePluralName"));
                }

                if (this.bundle.containsKey(getUnitName(unit) + "PluralPattern")) {
                    this.setPluralPattern(bundle.getString(getUnitName(unit) + "PluralPattern"));
                }

                this.setPattern(bundle.getString(getUnitName(unit) + "Pattern"))
                    .setPastSuffix(bundle.getString(getUnitName(unit) + "PastSuffix"))
                    .setFutureSuffix(bundle.getString(getUnitName(unit) + "FutureSuffix"))
                    .setFuturePrefix("")
                    .setPastPrefix("")
                    .setSingularName("")
                    .setPluralName("");
            }
        }

        public FiTimeFormat setPastName(final String pastName) {
            this.pastName = pastName;
            return this;
        }

        public FiTimeFormat setFutureName(final String futureName) {
            this.futureName = futureName;
            return this;
        }

        public FiTimeFormat setPastPluralName(final String pastName) {
            this.pastPluralName = pastName;
            return this;
        }

        public FiTimeFormat setFuturePluralName(final String futureName) {
            this.futurePluralName = futureName;
            return this;
        }

        public FiTimeFormat setPluralPattern(final String pattern) {
            this.pluralPattern = pattern;
            return this;
        }

        @Override
        protected String getGrammaticallyCorrectName(final Duration duration, boolean round) {
            String result = duration.isInPast() ? this.getPastName() : this.getFutureName();
            if ((Math.abs(getQuantity(duration, round)) == 0) || (Math.abs(getQuantity(duration, round)) > 1)) {
                result = duration.isInPast() ? this.getPastPluralName() : this.getFuturePluralName();
            }
            return result;
        }

        @Override
        protected String getPattern(long quantity) {
            if (Math.abs(quantity) == 1) {
                return this.getPattern();
            }
            return this.getPluralPattern();
        }

        @Override
        public String decorate(final Duration duration, final String time) {
            if (duration.getUnit() instanceof DayTimeUnit && Math.abs(duration.getQuantityRounded(Resources_FI.tolerance)) == 1) {
                return time;
            }
            return super.decorate(duration, time);
        }

        private String getUnitName(final TimeMeasure unit) {
            return unit.getClass().getSimpleName();
        }
    }

    /**
     * Loads {@link BaseResourceBundle} items
     */
    public void loadResources() {
        this.resources = BaseResourceBundle.getInstance(LOCALE).getResources();
    }

    /**
     * Returns new {@link Resources_FI}
     *
     * @return new {@link Resources_FI}
     */
    public static Resources_FI getInstance() {
        return INSTANCE;
    }
}
