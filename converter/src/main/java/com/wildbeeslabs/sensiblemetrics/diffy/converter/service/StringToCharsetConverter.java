package com.wildbeeslabs.sensiblemetrics.diffy.converter.service;

import com.wildbeeslabs.sensiblemetrics.diffy.converter.interfaces.Converter;

import javax.annotation.Nullable;
import java.nio.charset.Charset;

public class StringToCharsetConverter implements Converter<String, Charset> {

    @Nullable
    @Override
    public Charset convert(final String source) {
        return Charset.forName(source);
    }
}
