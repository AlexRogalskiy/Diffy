package com.wildbeeslabs.sensiblemetrics.diffy.converter.service;

import com.wildbeeslabs.sensiblemetrics.diffy.converter.interfaces.Converter;

import javax.annotation.Nullable;

public class NumberToCharacterConverter implements Converter<Number, Character> {

    @Nullable
    @Override
    public Character convert(final Number source) {
        return (char) source.shortValue();
    }
}
