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
import com.wildbeeslabs.sensiblemetrics.diffy.common.helpers.unit.*;
import com.wildbeeslabs.sensiblemetrics.diffy.common.resources.BaseResourceBundle;
import com.wildbeeslabs.sensiblemetrics.diffy.formatter.interfaces.TimeFormatProvider;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.Locale;

/**
 * Default resources bundle [KK]
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Resources_KK extends Resources implements TimeFormatProvider {
    /**
     * Default {@link Locale} {@code "KK"}
     */
    private static final Locale LOCALE = new Locale("kk");
    /**
     * Default {@link Resources_KK} instance
     */
    private static final Resources_KK INSTANCE = new Resources_KK();

    private Object[][] resources;

    private Resources_KK() {
        this.loadResources();
    }

    private static class KkTimeFormat implements TimeFormat {

        private final int tolerance = 50;
        private final String[] forms;

        public KkTimeFormat(final String... plurals) {
            if (plurals.length != 2) {
                throw new IllegalArgumentException("Future and past forms must be provided for kazakh language!");
            }
            this.forms = plurals;
        }

        @Override
        public String format(final Duration duration) {
            long quantity = duration.getQuantityRounded(tolerance);
            final StringBuilder result = new StringBuilder();
            result.append(quantity);
            return result.toString();
        }

        @Override
        public String formatUnrounded(final Duration duration) {
            long quantity = duration.getQuantity();
            final StringBuilder result = new StringBuilder();
            result.append(quantity);
            return result.toString();
        }

        @Override
        public String decorate(final Duration duration, final String time) {
            return performDecoration(
                duration.isInPast(),
                duration.isInFuture(),
                duration.getQuantityRounded(tolerance),
                time);
        }

        @Override
        public String decorateUnrounded(final Duration duration, final String time) {
            return performDecoration(
                duration.isInPast(),
                duration.isInFuture(),
                duration.getQuantity(),
                time);
        }

        private String performDecoration(boolean past, boolean future, long quantity, final String time) {
            final StringBuilder builder = new StringBuilder();
            int formIndex = past ? 0 : 1;

            builder.append(time);
            builder.append(StringUtils.SPACE);
            builder.append(this.forms[formIndex]);
            builder.append(StringUtils.SPACE);

            if (past) {
                builder.append("бұрын");
            }
            if (future) {
                builder.append("кейін");
            }
            return builder.toString();
        }
    }

    @Override
    public TimeFormat getFormat(final TimeMeasure timeUnit) {
        if (timeUnit instanceof NowTimeUnit) {
            return new TimeFormat() {
                @Override
                public String format(final Duration duration) {
                    return this.performFormat(duration);
                }

                @Override
                public String formatUnrounded(final Duration duration) {
                    return this.performFormat(duration);
                }

                private String performFormat(final Duration duration) {
                    if (duration.isInFuture()) {
                        return "дәл қазір";
                    }
                    if (duration.isInPast()) {
                        return "жана ғана";
                    }
                    return null;
                }

                @Override
                public String decorate(final Duration duration, final String time) {
                    return time;
                }

                @Override
                public String decorateUnrounded(final Duration duration, final String time) {
                    return time;
                }
            };
        } else if (timeUnit instanceof CenturyTimeUnit) {
            return new KkTimeFormat("ғасыр", "ғасырдан");
        } else if (timeUnit instanceof DayTimeUnit) {
            return new KkTimeFormat("күн", "күннен");
        } else if (timeUnit instanceof DecadeTimeUnit) {
            return new KkTimeFormat("онжылдық", "онжылдықтан");
        } else if (timeUnit instanceof HourTimeUnit) {
            return new KkTimeFormat("сағат", "сағаттан");
        } else if (timeUnit instanceof MillenniumTimeUnit) {
            return new KkTimeFormat("мыңжылдық", "мыңжылдықтан");
        } else if (timeUnit instanceof MillisecondTimeUnit) {
            return new KkTimeFormat("миллисекунд", "миллисекундтан");
        } else if (timeUnit instanceof MinuteTimeUnit) {
            return new KkTimeFormat("минут", "минуттан");
        } else if (timeUnit instanceof MonthTimeUnit) {
            return new KkTimeFormat("ай", "айдан");
        } else if (timeUnit instanceof SecondTimeUnit) {
            return new KkTimeFormat("секунд", "секундтан");
        } else if (timeUnit instanceof WeekTimeUnit) {
            return new KkTimeFormat("апта", "аптадан");
        } else if (timeUnit instanceof YearTimeUnit) {
            return new KkTimeFormat("жыл", "жылдан");
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
     * Returns new {@link Resources_KK}
     *
     * @return new {@link Resources_KK}
     */
    public static Resources_KK getInstance() {
        return INSTANCE;
    }
}
