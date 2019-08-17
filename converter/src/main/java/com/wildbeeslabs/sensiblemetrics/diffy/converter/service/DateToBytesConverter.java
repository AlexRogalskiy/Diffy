package com.wildbeeslabs.sensiblemetrics.diffy.converter.service;

import com.wildbeeslabs.sensiblemetrics.diffy.converter.interfaces.Converter;

import java.util.Date;

public class DateToBytesConverter extends StringBasedConverter implements Converter<Date, byte[]> {

    @Override
    public byte[] convert(final Date source) {
        return fromString(Long.toString(source.getTime()));
    }
}
