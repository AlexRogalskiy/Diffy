package com.wildbeeslabs.sensiblemetrics.diffy.converter.service;

import com.wildbeeslabs.sensiblemetrics.diffy.converter.interfaces.Converter;

public class StringToBytesConverter extends StringBasedConverter implements Converter<String, byte[]> {

    @Override
    public byte[] convert(final String source) {
        return fromString(source);
    }
}
