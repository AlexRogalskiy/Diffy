package com.wildbeeslabs.sensiblemetrics.diffy.converter.service;

import com.wildbeeslabs.sensiblemetrics.diffy.converter.interfaces.Converter;

import javax.annotation.Nullable;
import java.time.ZoneId;
import java.util.TimeZone;

public class ZoneIdToTimeZoneConverter implements Converter<ZoneId, TimeZone> {

    @Nullable
    @Override
    public TimeZone convert(final ZoneId source) {
        return TimeZone.getTimeZone(source);
    }
}
