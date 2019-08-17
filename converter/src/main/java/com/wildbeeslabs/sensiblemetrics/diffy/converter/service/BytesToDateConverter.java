package com.wildbeeslabs.sensiblemetrics.diffy.converter.service;

import com.wildbeeslabs.sensiblemetrics.diffy.converter.interfaces.Converter;
import com.wildbeeslabs.sensiblemetrics.diffy.converter.utils.NumberUtils;
import com.wildbeeslabs.sensiblemetrics.diffy.converter.utils.ObjectUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;

public class BytesToDateConverter extends StringBasedConverter implements Converter<byte[], Date> {

    @Override
    public Date convert(final byte[] source) {
        if (ObjectUtils.isEmpty(source)) {
            return null;
        }
        String value = toString(source);
        try {
            return new Date(NumberUtils.parseNumber(value, Long.class));
        } catch (NumberFormatException nfe) {
        }

        try {
            return DateFormat.getInstance().parse(value);
        } catch (ParseException e) {
        }
        throw new IllegalArgumentException(String.format("Cannot parse date out of %s", Arrays.toString(source)));
    }
}
