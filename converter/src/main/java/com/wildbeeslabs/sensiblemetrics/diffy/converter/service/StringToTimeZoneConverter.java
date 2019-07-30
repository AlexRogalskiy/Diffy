package com.wildbeeslabs.sensiblemetrics.diffy.converter.service;

import com.wildbeeslabs.sensiblemetrics.diffy.converter.interfaces.Converter;
import com.wildbeeslabs.sensiblemetrics.diffy.converter.utils.StringUtils;

import javax.annotation.Nullable;
import java.util.TimeZone;

public class StringToTimeZoneConverter implements Converter<String, TimeZone> {

    @Nullable
    @Override
    public TimeZone convert(final String source) {
        return StringUtils.parseTimeZoneString(source);
    }
}
