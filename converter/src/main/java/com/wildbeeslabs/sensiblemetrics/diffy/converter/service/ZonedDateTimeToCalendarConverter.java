package com.wildbeeslabs.sensiblemetrics.diffy.converter.service;

import com.wildbeeslabs.sensiblemetrics.diffy.converter.interfaces.Converter;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class ZonedDateTimeToCalendarConverter implements Converter<ZonedDateTime, Calendar> {

    @Nullable
    @Override
    public Calendar convert(final ZonedDateTime source) {
        return GregorianCalendar.from(source);
    }
}
