package com.wildbeeslabs.sensiblemetrics.diffy.converter.service.locale;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.locale.BaseLocaleConverter;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Locale;


/**
 * <p>Standard {@link org.apache.commons.beanutils.locale.LocaleConverter}
 * implementation that converts an incoming
 * locale-sensitive String into a <code>java.lang.Number</code> object,
 * optionally using a default value or throwing a
 * {@link org.apache.commons.beanutils.ConversionException}
 * if a conversion error occurs.</p>
 *
 * @version $Id: DecimalLocaleConverter.java 1454606 2013-03-08 22:30:51Z britter $
 * @since 1.7
 */
@Slf4j
public class DecimalLocaleConverter extends BaseLocaleConverter {

    // ----------------------------------------------------------- Constructors

    /**
     * Create a {@link org.apache.commons.beanutils.locale.LocaleConverter}
     * that will throw a {@link org.apache.commons.beanutils.ConversionException}
     * if a conversion error occurs. The locale is the default locale for
     * this instance of the Java Virtual Machine and an unlocalized pattern is used
     * for the convertion.
     */
    public DecimalLocaleConverter() {

        this(false);
    }

    /**
     * Create a {@link org.apache.commons.beanutils.locale.LocaleConverter}
     * that will throw a {@link org.apache.commons.beanutils.ConversionException}
     * if a conversion error occurs. The locale is the default locale for
     * this instance of the Java Virtual Machine.
     *
     * @param locPattern Indicate whether the pattern is localized or not
     */
    public DecimalLocaleConverter(boolean locPattern) {

        this(Locale.getDefault(), locPattern);
    }

    /**
     * Create a {@link org.apache.commons.beanutils.locale.LocaleConverter}
     * that will throw a {@link org.apache.commons.beanutils.ConversionException}
     * if a conversion error occurs. An unlocalized pattern is used for the convertion.
     *
     * @param locale The locale
     */
    public DecimalLocaleConverter(Locale locale) {

        this(locale, false);
    }

    /**
     * Create a {@link org.apache.commons.beanutils.locale.LocaleConverter}
     * that will throw a {@link org.apache.commons.beanutils.ConversionException}
     * if a conversion error occurs.
     *
     * @param locale     The locale
     * @param locPattern Indicate whether the pattern is localized or not
     */
    public DecimalLocaleConverter(Locale locale, boolean locPattern) {

        this(locale, (String) null, locPattern);
    }

    /**
     * Create a {@link org.apache.commons.beanutils.locale.LocaleConverter}
     * that will throw a {@link org.apache.commons.beanutils.ConversionException}
     * if a conversion error occurs. An unlocalized pattern is used for the convertion.
     *
     * @param locale  The locale
     * @param pattern The convertion pattern
     */
    public DecimalLocaleConverter(Locale locale, String pattern) {

        this(locale, pattern, false);
    }

    /**
     * Create a {@link org.apache.commons.beanutils.locale.LocaleConverter}
     * that will throw a {@link org.apache.commons.beanutils.ConversionException}
     * if a conversion error occurs.
     *
     * @param locale     The locale
     * @param pattern    The convertion pattern
     * @param locPattern Indicate whether the pattern is localized or not
     */
    public DecimalLocaleConverter(Locale locale, String pattern, boolean locPattern) {

        super(locale, pattern, locPattern);
    }

    /**
     * Create a {@link org.apache.commons.beanutils.locale.LocaleConverter}
     * that will return the specified default value
     * if a conversion error occurs. The locale is the default locale for
     * this instance of the Java Virtual Machine and an unlocalized pattern is used
     * for the convertion.
     *
     * @param defaultValue The default value to be returned
     */
    public DecimalLocaleConverter(Object defaultValue) {

        this(defaultValue, false);
    }

    /**
     * Create a {@link org.apache.commons.beanutils.locale.LocaleConverter}
     * that will return the specified default value
     * if a conversion error occurs. The locale is the default locale for
     * this instance of the Java Virtual Machine.
     *
     * @param defaultValue The default value to be returned
     * @param locPattern   Indicate whether the pattern is localized or not
     */
    public DecimalLocaleConverter(Object defaultValue, boolean locPattern) {

        this(defaultValue, Locale.getDefault(), locPattern);
    }

    /**
     * Create a {@link org.apache.commons.beanutils.locale.LocaleConverter}
     * that will return the specified default value
     * if a conversion error occurs. An unlocalized pattern is used for the convertion.
     *
     * @param defaultValue The default value to be returned
     * @param locale       The locale
     */
    public DecimalLocaleConverter(Object defaultValue, Locale locale) {

        this(defaultValue, locale, false);
    }

    /**
     * Create a {@link org.apache.commons.beanutils.locale.LocaleConverter}
     * that will return the specified default value
     * if a conversion error occurs.
     *
     * @param defaultValue The default value to be returned
     * @param locale       The locale
     * @param locPattern   Indicate whether the pattern is localized or not
     */
    public DecimalLocaleConverter(Object defaultValue, Locale locale, boolean locPattern) {

        this(defaultValue, locale, null, locPattern);
    }

    /**
     * Create a {@link org.apache.commons.beanutils.locale.LocaleConverter}
     * that will return the specified default value
     * if a conversion error occurs. An unlocalized pattern is used for the convertion.
     *
     * @param defaultValue The default value to be returned
     * @param locale       The locale
     * @param pattern      The convertion pattern
     */
    public DecimalLocaleConverter(Object defaultValue, Locale locale, String pattern) {

        this(defaultValue, locale, pattern, false);
    }

    /**
     * Create a {@link org.apache.commons.beanutils.locale.LocaleConverter}
     * that will return the specified default value
     * if a conversion error occurs.
     *
     * @param defaultValue The default value to be returned
     * @param locale       The locale
     * @param pattern      The convertion pattern
     * @param locPattern   Indicate whether the pattern is localized or not
     */
    public DecimalLocaleConverter(Object defaultValue, Locale locale, String pattern, boolean locPattern) {

        super(defaultValue, locale, pattern, locPattern);

    }

    // --------------------------------------------------------- Methods

    /**
     * Convert the specified locale-sensitive input object into an output
     * object of the specified type.
     *
     * @param value   The input object to be converted
     * @param pattern The pattern is used for the convertion
     * @return The converted value
     * @throws org.apache.commons.beanutils.ConversionException if conversion
     *                                                          cannot be performed successfully
     * @throws ParseException                                   if an error occurs parsing a String to a Number
     */
    @Override
    protected Object parse(Object value, String pattern) throws ParseException {

        if (value instanceof Number) {
            return value;
        }

        // Note that despite the ambiguous "getInstance" name, and despite the
        // fact that objects returned from this method have the same toString
        // representation, each call to getInstance actually returns a new
        // object.
        DecimalFormat formatter = (DecimalFormat) DecimalFormat.getInstance(locale);

        // if some constructors default pattern to null, it makes only sense
        // to handle null pattern gracefully
        if (pattern != null) {
            if (locPattern) {
                formatter.applyLocalizedPattern(pattern);
            } else {
                formatter.applyPattern(pattern);
            }
        } else {
            log.debug("No pattern provided, using default.");
        }
        return formatter.parse((String) value);
    }
}
