package com.wildbeeslabs.sensiblemetrics.diffy.converter.service;

import com.wildbeeslabs.sensiblemetrics.diffy.converter.interfaces.Converter;

import javax.annotation.Nullable;

public class StringToCharacterConverter implements Converter<String, Character> {

    @Nullable
    @Override
    public Character convert(final String source) {
        if (source.isEmpty()) {
            return null;
        }
        if (source.length() > 1) {
            throw new IllegalArgumentException("Can only convert a [String] with length of 1 to a [Character]; string value '" + source + "'  has length of " + source.length());
        }
        return source.charAt(0);
    }
}
