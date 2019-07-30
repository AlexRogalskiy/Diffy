package com.wildbeeslabs.sensiblemetrics.diffy.converter.service;

import com.wildbeeslabs.sensiblemetrics.diffy.converter.interfaces.Converter;
import com.wildbeeslabs.sensiblemetrics.diffy.converter.utils.StringUtils;

import javax.annotation.Nullable;
import java.util.Locale;

public class StringToLocaleConverter implements Converter<String, Locale> {

    @Nullable
    @Override
    public Locale convert(final String source) {
        return StringUtils.parseLocale(source);
    }
}
