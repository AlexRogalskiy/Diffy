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
package com.wildbeeslabs.sensiblemetrics.diffy.formatter.service;

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
public class ResourcesTimeFormat extends SimpleTimeFormat implements ITimeFormat, ILocaleAware<ResourcesTimeFormat> {

    private ResourceBundle bundle;
    private final ResourcesTimeUnit unit;
    private ITimeFormat override;
    private String resourceBundle;

    public ResourcesTimeFormat(final ResourcesTimeUnit unit) {
        this.unit = unit;
    }

    public ResourcesTimeFormat(final ResourcesTimeUnit unit, final String resourceBundle) {
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

        if (this.bundle instanceof ITimeFormatProvider) {
            final ITimeFormat format = ((ITimeFormatProvider) this.bundle).getFormat(this.unit);
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
    public String decorate(final IDuration duration, final String time) {
        return Objects.isNull(this.override) ? super.decorate(duration, time) : this.override.decorate(duration, time);
    }

    @Override
    public String decorateUnrounded(final IDuration duration, final String time) {
        return Objects.isNull(this.override) ? super.decorateUnrounded(duration, time) : this.override.decorateUnrounded(duration, time);
    }

    @Override
    public String format(final IDuration duration) {
        return Objects.isNull(this.override) ? super.format(duration) : this.override.format(duration);
    }

    @Override
    public String formatUnrounded(final IDuration duration) {
        return Objects.isNull(this.override) ? super.formatUnrounded(duration) : this.override.formatUnrounded(duration);
    }
}
