package com.wildbeeslabs.sensiblemetrics.diffy.converter.service;

import com.wildbeeslabs.sensiblemetrics.diffy.converter.exception.ConvertOperationException;
import com.wildbeeslabs.sensiblemetrics.diffy.converter.interfaces.Converter;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Constructor;

@RequiredArgsConstructor
public class StringConverter2 implements Converter<String, String> {
    private final Constructor<?> ctor;

    @Override
    public String convert(final String raw) {
        try {
            return (String) ctor.newInstance(raw);
        } catch (Exception ex) {
            throw new ConvertOperationException(ex);
        }
    }
}
