package com.wildbeeslabs.sensiblemetrics.diffy.converter.service;

import com.wildbeeslabs.sensiblemetrics.diffy.converter.interfaces.Converter;

import javax.annotation.Nullable;

public class ObjectToStringConverter implements Converter<Object, String> {

    @Nullable
    @Override
    public String convert(final Object source) {
        return source.toString();
    }
}
