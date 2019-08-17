package com.wildbeeslabs.sensiblemetrics.diffy.converter.service;

import com.wildbeeslabs.sensiblemetrics.diffy.converter.interfaces.Converter;

import java.util.UUID;

public class UuidToBytesConverter extends StringBasedConverter implements Converter<UUID, byte[]> {

    @Override
    public byte[] convert(final UUID source) {
        return fromString(source.toString());
    }
}
