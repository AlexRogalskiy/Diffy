package com.wildbeeslabs.sensiblemetrics.diffy.converter.service;

import com.wildbeeslabs.sensiblemetrics.diffy.converter.interfaces.Converter;

import javax.annotation.Nullable;

public class EnumToIntegerConverter implements Converter<Enum<?>, Integer> {

    @Nullable
    @Override
    public Integer convert(final Enum<?> source) {
        return source.ordinal();
    }
}
