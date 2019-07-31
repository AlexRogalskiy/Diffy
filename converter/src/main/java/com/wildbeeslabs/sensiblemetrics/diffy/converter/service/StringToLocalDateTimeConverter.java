package com.wildbeeslabs.sensiblemetrics.diffy.converter.service;

import com.wildbeeslabs.sensiblemetrics.diffy.common.utils.ValidationUtils;
import com.wildbeeslabs.sensiblemetrics.diffy.converter.interfaces.Converter;

import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;
import java.util.Locale;

public class StringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {

    private final Locale locale;

    public StringToLocalDateTimeConverter(final Locale locale) {
        ValidationUtils.notNull(locale, "Locale should not be null");
        this.locale = locale;
    }

    @Nullable
    @Override
    public LocalDateTime convert(final String value) {
        try {
            return LocalDateTime.parse(value, this.getFormatter());
        } catch (DateTimeParseException e) {
            return LocalDateTime.parse(value, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm", locale));
        }
    }

    protected DateTimeFormatter getFormatter() {
        return DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withLocale(locale);
    }
}
