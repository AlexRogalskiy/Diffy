package com.wildbeeslabs.sensiblemetrics.diffy.converter.service;

import com.wildbeeslabs.sensiblemetrics.diffy.converter.interfaces.Converter;

import javax.annotation.Nullable;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesToStringConverter implements Converter<Properties, String> {

    @Nullable
    @Override
    public String convert(final Properties source) {
        try {
            final ByteArrayOutputStream os = new ByteArrayOutputStream(256);
            source.store(os, null);
            return os.toString("ISO-8859-1");
        } catch (IOException ex) {
            throw new IllegalArgumentException("Failed to store [" + source + "] into String", ex);
        }
    }
}
