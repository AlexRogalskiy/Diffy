package com.wildbeeslabs.sensiblemetrics.diffy.common.helpers.impl;

import com.wildbeeslabs.sensiblemetrics.diffy.common.helpers.iface.Duration;
import com.wildbeeslabs.sensiblemetrics.diffy.common.helpers.iface.LocaleAware;
import com.wildbeeslabs.sensiblemetrics.diffy.common.helpers.iface.TimeFormat;
import com.wildbeeslabs.sensiblemetrics.diffy.common.helpers.iface.TimeFormatProvider;
import lombok.extern.slf4j.Slf4j;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Custom resources time format implementation
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-07
 */
@Slf4j
public class ResourcesTimeFormat extends SimpleTimeFormat implements TimeFormat, LocaleAware<ResourcesTimeFormat> {

    private ResourceBundle bundle;
    private final ResourcesTimeMeasure unit;
    private TimeFormat override;
    private String resourceBundle;

    public ResourcesTimeFormat(final ResourcesTimeMeasure unit) {
        this.unit = unit;
    }

    public ResourcesTimeFormat(final ResourcesTimeMeasure unit, final String resourceBundle) {
        this.unit = unit;
        this.resourceBundle = resourceBundle;
    }

    @Override
    public ResourcesTimeFormat setLocale(final Locale locale) {
        if (Objects.nonNull(this.resourceBundle)) {
            try {
                this.bundle = ResourceBundle.getBundle(this.resourceBundle, locale);
            } catch (Exception ex) {
                log.error(String.format("ERROR: cannot load bundle %s", this.resourceBundle), ex);
            }
        }

        if (Objects.isNull(this.bundle)) {
            this.bundle = ResourceBundle.getBundle(this.unit.getResourceBundleName(), locale);
        }

        if (this.bundle instanceof TimeFormatProvider) {
            final TimeFormat format = ((TimeFormatProvider) this.bundle).getFormat(this.unit);
            if (Objects.nonNull(format)) {
                this.override = format;
            }
        } else {
            this.override = null;
        }

        if (Objects.isNull(this.override)) {
            setPattern(this.bundle.getString(this.unit.getResourceKeyPrefix() + "Pattern"));
            setFuturePrefix(this.bundle.getString(this.unit.getResourceKeyPrefix() + "FuturePrefix"));
            setFutureSuffix(this.bundle.getString(this.unit.getResourceKeyPrefix() + "FutureSuffix"));
            setPastPrefix(this.bundle.getString(this.unit.getResourceKeyPrefix() + "PastPrefix"));
            setPastSuffix(this.bundle.getString(this.unit.getResourceKeyPrefix() + "PastSuffix"));
            setSingularName(this.bundle.getString(this.unit.getResourceKeyPrefix() + "SingularName"));
            setPluralName(this.bundle.getString(this.unit.getResourceKeyPrefix() + "PluralName"));

            try {
                setFuturePluralName(this.bundle.getString(this.unit.getResourceKeyPrefix() + "FuturePluralName"));
            } catch (Exception ex) {
                log.error(String.format("ERROR: cannot set future plural name by key=%s", this.unit.getResourceKeyPrefix() + "FuturePluralName"), ex);
            }
            try {
                setFutureSingularName((this.bundle.getString(this.unit.getResourceKeyPrefix() + "FutureSingularName")));
            } catch (Exception ex) {
                log.error(String.format("ERROR: cannot set future singular name by key=%s", this.unit.getResourceKeyPrefix() + "FutureSingularName"), ex);
            }
            try {
                setPastPluralName((this.bundle.getString(this.unit.getResourceKeyPrefix() + "PastPluralName")));
            } catch (Exception ex) {
                log.error(String.format("ERROR: cannot set past plural name by key=%s", this.unit.getResourceKeyPrefix() + "PastPluralName"), ex);
            }
            try {
                setPastSingularName((this.bundle.getString(this.unit.getResourceKeyPrefix() + "PastSingularName")));
            } catch (Exception ex) {
                log.error(String.format("ERROR: cannot set future plural name by key=%s", this.unit.getResourceKeyPrefix() + "PastSingularName"), ex);
            }

        }
        return this;
    }

    @Override
    public String decorate(final Duration duration, final String time) {
        return Objects.isNull(this.override) ? super.decorate(duration, time) : this.override.decorate(duration, time);
    }

    @Override
    public String decorateUnrounded(final Duration duration, final String time) {
        return Objects.isNull(this.override) ? super.decorateUnrounded(duration, time) : this.override.decorateUnrounded(duration, time);
    }

    @Override
    public String format(final Duration duration) {
        return Objects.isNull(this.override) ? super.format(duration) : this.override.format(duration);
    }

    @Override
    public String formatUnrounded(final Duration duration) {
        return Objects.isNull(this.override) ? super.formatUnrounded(duration) : this.override.formatUnrounded(duration);
    }
}
