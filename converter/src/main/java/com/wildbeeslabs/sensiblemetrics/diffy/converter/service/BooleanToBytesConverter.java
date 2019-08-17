package com.wildbeeslabs.sensiblemetrics.diffy.converter.service;

import com.wildbeeslabs.sensiblemetrics.diffy.converter.interfaces.Converter;

public class BooleanToBytesConverter extends StringBasedConverter implements Converter<Boolean, byte[]> {
    final byte[] _true = fromString("1");
    final byte[] _false = fromString("0");

    @Override
    public byte[] convert(final Boolean source) {
        return source.booleanValue() ? _true : _false;
    }
}
