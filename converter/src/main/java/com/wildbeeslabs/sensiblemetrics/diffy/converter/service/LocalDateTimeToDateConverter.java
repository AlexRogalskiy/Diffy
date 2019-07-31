package com.wildbeeslabs.sensiblemetrics.diffy.converter.service;

import com.wildbeeslabs.sensiblemetrics.diffy.converter.interfaces.Converter;

import javax.annotation.Nullable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Objects;

public class LocalDateTimeToDateConverter implements Converter<LocalDateTime, Date> {

    @Nullable
    @Override
    public Date convert(final LocalDateTime value) {
        if (Objects.isNull(value)) {
            return null;
        }
        final Instant instant = value.toInstant(ZoneOffset.UTC);
        return Date.from(instant);
    }
}
