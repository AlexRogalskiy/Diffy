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
import com.wildbeeslabs.sensiblemetrics.diffy.common.helpers.unit.DecadeTimeUnit;
import com.wildbeeslabs.sensiblemetrics.diffy.common.helpers.unit.MillenniumTimeUnit;
import com.wildbeeslabs.sensiblemetrics.diffy.common.resources.BaseResourceBundle;
import com.wildbeeslabs.sensiblemetrics.diffy.formatter.interfaces.TimeFormatProvider;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Default resources bundle [JA]
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-07
 */
@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Resources_JA extends Resources implements TimeFormatProvider {
    /**
     * Default {@link Locale} {@code "JA"}
     */
    private static final Locale LOCALE = new Locale("ja");
    /**
     * Default {@link Resources_JA} instance
     */
    private static final Resources_JA INSTANCE = new Resources_JA();

    private Object[][] resources;

    /**
     * Default resources constructor
     */
    private Resources_JA() {
        this.loadResources();
    }

    private volatile ConcurrentMap<TimeMeasure, TimeFormat> formatMap = new ConcurrentHashMap<>();

    @Override
    public TimeFormat getFormat(final TimeMeasure timeUnit) {
        if (!this.formatMap.containsKey(timeUnit)) {
            this.formatMap.putIfAbsent(timeUnit, new JaTimeFormat(this, timeUnit));
        }
        return this.formatMap.get(timeUnit);
    }

    @Data
    @EqualsAndHashCode
    @ToString
    private static class JaTimeFormat implements TimeFormat {

        private static final String NEGATIVE = "-";
        public static final String SIGN = "%s";
        public static final String QUANTITY = "%n";
        public static final String UNIT = "%u";

        @Getter(AccessLevel.NONE)
        @Setter(AccessLevel.NONE)
        private final ResourceBundle bundle;
        private String singularName = null;
        private String pluralName = null;
        private String futureSingularName = null;
        private String futurePluralName = null;
        private String pastSingularName = null;
        private String pastPluralName = null;

        private String pattern = null;
        private String futurePrefix = null;
        private String futureSuffix = null;
        private String pastPrefix = null;
        private String pastSuffix = null;
        private int roundingTolerance = 50;

        public JaTimeFormat(final ResourceBundle bundle, final TimeMeasure unit) {
            this.bundle = bundle;
            setPattern(bundle.getString(getUnitName(unit) + "Pattern"));
            setFuturePrefix(bundle.getString(getUnitName(unit) + "FuturePrefix"));
            setFutureSuffix(bundle.getString(getUnitName(unit) + "FutureSuffix"));
            setPastPrefix(bundle.getString(getUnitName(unit) + "PastPrefix"));
            setPastSuffix(bundle.getString(getUnitName(unit) + "PastSuffix"));
            setSingularName(bundle.getString(getUnitName(unit) + "SingularName"));
            setPluralName(bundle.getString(getUnitName(unit) + "PluralName"));

            try {
                setFuturePluralName(bundle.getString(this.getUnitName(unit) + "FuturePluralName"));
            } catch (Exception ex) {
                log.error(String.format("ERROR: cannot set future plural name by key=%s", this.getUnitName(unit) + "FuturePluralName"), ex);
            }
            try {
                setFutureSingularName((bundle.getString(this.getUnitName(unit) + "FutureSingularName")));
            } catch (Exception ex) {
                log.error(String.format("ERROR: cannot set future singular name by key=%s", this.getUnitName(unit) + "FutureSingularName"), ex);
            }
            try {
                setPastPluralName((bundle.getString(this.getUnitName(unit) + "PastPluralName")));
            } catch (Exception ex) {
                log.error(String.format("ERROR: cannot set past plural name by key=%s", this.getUnitName(unit) + "PastPluralName"), ex);
            }
            try {
                setPastSingularName((bundle.getString(this.getUnitName(unit) + "PastSingularName")));
            } catch (Exception ex) {
                log.error(String.format("ERROR: cannot set future plural name by key=%s", this.getUnitName(unit) + "PastSingularName"), ex);
            }
        }

        private String getUnitName(final TimeMeasure timeUnit) {
            return timeUnit.getClass().getSimpleName();
        }

        @Override
        public String format(final Duration duration) {
            return this.format(duration, true);
        }

        @Override
        public String formatUnrounded(final Duration duration) {
            return this.format(duration, false);
        }

        private String format(final Duration duration, boolean round) {
            String sign = this.getSign(duration);
            String unit = this.getGramaticallyCorrectName(duration, round);
            long quantity = this.getQuantity(duration, round);
            if (duration.getUnit() instanceof DecadeTimeUnit) {
                quantity *= 10;
            }
            if (duration.getUnit() instanceof MillenniumTimeUnit) {
                quantity *= 1000;
            }
            return applyPattern(sign, unit, quantity);
        }

        private String applyPattern(final String sign, final String unit, long quantity) {
            String result = this.getPattern(quantity).replaceAll(SIGN, sign);
            result = result.replaceAll(QUANTITY, String.valueOf(quantity));
            result = result.replaceAll(UNIT, unit);
            return result;
        }

        protected String getPattern(long quantity) {
            return this.pattern;
        }

        public String getPattern() {
            return this.pattern;
        }

        protected long getQuantity(final Duration duration, boolean round) {
            return Math.abs(round ? duration.getQuantityRounded(roundingTolerance) : duration.getQuantity());
        }

        protected String getGramaticallyCorrectName(final Duration duration, boolean round) {
            String result = getSingularName(duration);
            if ((Math.abs(getQuantity(duration, round)) == 0) || (Math.abs(getQuantity(duration, round)) > 1)) {
                result = getPluralName(duration);
            }
            return result;
        }

        private String getSign(final Duration duration) {
            if (duration.getQuantity() < 0) {
                return NEGATIVE;
            }
            return StringUtils.EMPTY;
        }

        private String getSingularName(final Duration duration) {
            if (duration.isInFuture() && Objects.nonNull(this.futureSingularName) && this.futureSingularName.length() > 0) {
                return this.futureSingularName;
            } else if (duration.isInPast() && Objects.nonNull(this.pastSingularName) && this.pastSingularName.length() > 0) {
                return this.pastSingularName;
            }
            return this.singularName;
        }

        private String getPluralName(final Duration duration) {
            if (duration.isInFuture() && Objects.nonNull(this.futurePluralName) && this.futureSingularName.length() > 0) {
                return this.futurePluralName;
            } else if (duration.isInPast() && Objects.nonNull(this.pastPluralName) && this.pastSingularName.length() > 0) {
                return this.pastPluralName;
            }
            return this.pluralName;
        }

        @Override
        public String decorate(final Duration duration, final String time) {
            final StringBuilder result = new StringBuilder();
            if (duration.isInPast()) {
                result.append(this.pastPrefix).append(time).append(this.pastSuffix);
            } else {
                result.append(this.futurePrefix).append(time).append(this.futureSuffix);
            }
            return result.toString().replaceAll("\\s+", StringUtils.SPACE).trim();
        }

        @Override
        public String decorateUnrounded(final Duration duration, final String time) {
            return decorate(duration, time);
        }

        public JaTimeFormat setPattern(final String pattern) {
            this.pattern = pattern;
            return this;
        }

        public JaTimeFormat setFuturePrefix(final String futurePrefix) {
            this.futurePrefix = futurePrefix.trim();
            return this;
        }

        public JaTimeFormat setFutureSuffix(final String futureSuffix) {
            this.futureSuffix = futureSuffix.trim();
            return this;
        }

        public JaTimeFormat setPastPrefix(final String pastPrefix) {
            this.pastPrefix = pastPrefix.trim();
            return this;
        }

        public JaTimeFormat setPastSuffix(final String pastSuffix) {
            this.pastSuffix = pastSuffix.trim();
            return this;
        }

        /**
         * The percentage of the current tolerance {@link TimeMeasure}.getMillisPerUnit() for
         * which the quantity may be rounded up by one.
         *
         * @param roundingTolerance
         * @return
         */
        public JaTimeFormat setRoundingTolerance(final int roundingTolerance) {
            this.roundingTolerance = roundingTolerance;
            return this;
        }

        public JaTimeFormat setSingularName(final String name) {
            this.singularName = name;
            return this;
        }

        public JaTimeFormat setPluralName(final String pluralName) {
            this.pluralName = pluralName;
            return this;
        }

        public JaTimeFormat setFutureSingularName(final String futureSingularName) {
            this.futureSingularName = futureSingularName;
            return this;
        }

        public JaTimeFormat setFuturePluralName(final String futurePluralName) {
            this.futurePluralName = futurePluralName;
            return this;
        }

        public JaTimeFormat setPastSingularName(final String pastSingularName) {
            this.pastSingularName = pastSingularName;
            return this;
        }

        public JaTimeFormat setPastPluralName(final String pastPluralName) {
            this.pastPluralName = pastPluralName;
            return this;
        }
    }

    /**
     * Loads {@link BaseResourceBundle} items
     */
    public void loadResources() {
        this.resources = BaseResourceBundle.getInstance(LOCALE).getResources();
    }

    /**
     * Returns new {@link Resources_JA}
     *
     * @return new {@link Resources_JA}
     */
    public static Resources_JA getInstance() {
        return INSTANCE;
    }
}
