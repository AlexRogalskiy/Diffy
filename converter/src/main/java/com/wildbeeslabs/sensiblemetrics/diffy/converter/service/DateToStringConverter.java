package com.wildbeeslabs.sensiblemetrics.diffy.converter.service;

import com.wildbeeslabs.sensiblemetrics.diffy.converter.interfaces.Converter;

import javax.annotation.Nullable;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateToStringConverter implements Converter<Object, String> {

    @Nullable
    @Override
    public String convert(final Object value) {
        if (value instanceof Calendar || value instanceof Date) {
            final DateFormat formatter = DateFormat.getDateInstance();
            return formatter.format(value instanceof Date ? ((Date) value) : ((Calendar) value).getTime());
        }
        return value.toString();
    }
}
