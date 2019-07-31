package com.wildbeeslabs.sensiblemetrics.diffy.converter.service;

import com.wildbeeslabs.sensiblemetrics.diffy.converter.interfaces.Converter;

import javax.annotation.Nullable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

public class DateToLocalDateTimeConverter implements Converter<Date, LocalDateTime> {

    @Nullable
    @Override
    public LocalDateTime convert(final Date value) {
        if (Objects.isNull(value)) {
            return null;
        }
        final Instant instant = Instant.ofEpochMilli(value.getTime());
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }
}
