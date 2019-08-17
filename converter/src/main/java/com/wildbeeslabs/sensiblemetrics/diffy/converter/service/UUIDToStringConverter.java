package com.wildbeeslabs.sensiblemetrics.diffy.converter.service;

import com.wildbeeslabs.sensiblemetrics.diffy.converter.interfaces.Converter;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;

public class UUIDToStringConverter implements Converter<UUID, String> {

    @Override
    @Nullable
    public String convert(final UUID source) {
        return Optional.ofNullable(source).map(UUID::toString).orElse(null);
    }
}
