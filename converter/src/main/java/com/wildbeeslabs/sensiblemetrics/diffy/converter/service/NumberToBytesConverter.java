package com.wildbeeslabs.sensiblemetrics.diffy.converter.service;

import com.wildbeeslabs.sensiblemetrics.diffy.converter.interfaces.Converter;

public class NumberToBytesConverter extends StringBasedConverter implements Converter<Number, byte[]> {

    @Override
    public byte[] convert(final Number source) {
        return fromString(source.toString());
    }
}
