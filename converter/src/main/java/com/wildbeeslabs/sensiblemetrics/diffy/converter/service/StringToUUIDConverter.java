package com.wildbeeslabs.sensiblemetrics.diffy.converter.service;

import com.wildbeeslabs.sensiblemetrics.diffy.converter.interfaces.Converter;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;

public class StringToUUIDConverter implements Converter<String, UUID> {

    @Nullable
    @Override
    public UUID convert(final String source) {
        return Optional.ofNullable(source).map(StringUtils::trim).map(UUID::fromString).orElse(null);
    }
}
