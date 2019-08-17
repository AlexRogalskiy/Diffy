package com.wildbeeslabs.sensiblemetrics.diffy.converter.service;

import com.wildbeeslabs.sensiblemetrics.diffy.converter.interfaces.Converter;

public class EnumToBytesConverter extends StringBasedConverter implements Converter<Enum<?>, byte[]> {

    @Override
    public byte[] convert(final Enum<?> source) {
        return fromString(source.name());
    }
}
