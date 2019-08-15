package com.wildbeeslabs.sensiblemetrics.diffy.converter.service;

import com.wildbeeslabs.sensiblemetrics.diffy.converter.interfaces.Converter;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class BytesToOffsetDateTimeConverter implements Converter<byte[], OffsetDateTime> {

    @Override
    public OffsetDateTime convert(final byte[] source) {
        return OffsetDateTime.parse(new String(source), DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }
}
