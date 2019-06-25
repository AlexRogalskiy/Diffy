package com.wildbeeslabs.sensiblemetrics.diffy.common.helpers.impl;

import com.wildbeeslabs.sensiblemetrics.diffy.common.helpers.iface.Duration;
import com.wildbeeslabs.sensiblemetrics.diffy.common.helpers.iface.TimeFormat;
import com.wildbeeslabs.sensiblemetrics.diffy.common.helpers.iface.TimeMeasure;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * Custom simple time format implementation
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-07
 */
@Data
@EqualsAndHashCode
@ToString
public class SimpleTimeFormat implements TimeFormat {

    private static final String NEGATIVE = "-";
    public static final String SIGN = "%s";
    public static final String QUANTITY = "%n";
    public static final String UNIT = "%u";

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

    @Override
    public String format(final Duration duration) {
        return this.format(duration, true);
    }

    @Override
    public String formatUnrounded(final Duration duration) {
        return this.format(duration, false);
    }

    @Override
    public String decorate(final Duration duration, final String time) {
        final StringBuilder result = new StringBuilder();
        if (duration.isInPast()) {
            result.append(pastPrefix).append(" ").append(time).append(" ").append(pastSuffix);
        } else {
            result.append(futurePrefix).append(" ").append(time).append(" ").append(futureSuffix);
        }
        return result.toString().replaceAll("\\s+", " ").trim();
    }

    @Override
    public String decorateUnrounded(final Duration duration, final String time) {
        return this.decorate(duration, time);
    }

    private String format(final Duration duration, boolean round) {
        final String sign = this.getSign(duration);
        final String unit = this.getGramaticallyCorrectName(duration, round);
        long quantity = this.getQuantity(duration, round);
        return this.applyPattern(sign, unit, quantity);
    }

    private String applyPattern(final String sign, final String unit, long quantity) {
        String result = this.getPattern(quantity).replaceAll(SimpleTimeFormat.SIGN, sign);
        result = result.replaceAll(SimpleTimeFormat.QUANTITY, String.valueOf(quantity));
        result = result.replaceAll(SimpleTimeFormat.UNIT, unit);
        return result;
    }

    protected String getPattern(final long quantity) {
        return this.pattern;
    }

    protected long getQuantity(final Duration duration, boolean round) {
        return Math.abs(round ? duration.getQuantityRounded(this.roundingTolerance) : duration.getQuantity());
    }

    protected String getGramaticallyCorrectName(final Duration duration, boolean round) {
        String result = this.getSingularName(duration);
        if ((Math.abs(this.getQuantity(duration, round)) == 0) || (Math.abs(this.getQuantity(duration, round)) > 1)) {
            result = this.getPluralName(duration);
        }
        return result;
    }

    private String getSign(final Duration duration) {
        if (duration.getQuantity() < 0) {
            return SimpleTimeFormat.NEGATIVE;
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

    /*
     * Builder Setters
     */
    public SimpleTimeFormat setPattern(final String pattern) {
        this.pattern = pattern;
        return this;
    }

    public SimpleTimeFormat setFuturePrefix(final String futurePrefix) {
        this.futurePrefix = futurePrefix.trim();
        return this;
    }

    public SimpleTimeFormat setFutureSuffix(final String futureSuffix) {
        this.futureSuffix = futureSuffix.trim();
        return this;
    }

    public SimpleTimeFormat setPastPrefix(final String pastPrefix) {
        this.pastPrefix = pastPrefix.trim();
        return this;
    }

    public SimpleTimeFormat setPastSuffix(final String pastSuffix) {
        this.pastSuffix = pastSuffix.trim();
        return this;
    }

    /**
     * The percentage of the current {@link TimeMeasure}.getMillisPerUnit() for
     * which the quantity may be rounded up by one.
     *
     * @param roundingTolerance
     * @return
     */
    public SimpleTimeFormat setRoundingTolerance(int roundingTolerance) {
        this.roundingTolerance = roundingTolerance;
        return this;
    }

    public SimpleTimeFormat setSingularName(final String name) {
        this.singularName = name;
        return this;
    }

    public SimpleTimeFormat setPluralName(final String pluralName) {
        this.pluralName = pluralName;
        return this;
    }

    public SimpleTimeFormat setFutureSingularName(final String futureSingularName) {
        this.futureSingularName = futureSingularName;
        return this;
    }

    public SimpleTimeFormat setFuturePluralName(final String futurePluralName) {
        this.futurePluralName = futurePluralName;
        return this;
    }

    public SimpleTimeFormat setPastSingularName(final String pastSingularName) {
        this.pastSingularName = pastSingularName;
        return this;
    }

    public SimpleTimeFormat setPastPluralName(final String pastPluralName) {
        this.pastPluralName = pastPluralName;
        return this;
    }
}
