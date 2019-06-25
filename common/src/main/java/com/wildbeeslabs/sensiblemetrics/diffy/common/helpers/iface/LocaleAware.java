package com.wildbeeslabs.sensiblemetrics.diffy.common.helpers.iface;

import java.util.Locale;

/**
 * An object that behaves differently for various {@link Locale} settings.
 *
 * @param <T>
 * @author Alex
 */
public interface LocaleAware<T> {

    /**
     * Set the {@link Locale} for which this instance should behave in.
     *
     * @param locale
     * @return
     */
    T setLocale(final Locale locale);
}
