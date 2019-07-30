package com.wildbeeslabs.sensiblemetrics.diffy.converter.service;

import com.wildbeeslabs.sensiblemetrics.diffy.converter.interfaces.Converter;

import javax.annotation.Nullable;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class StringToPropertiesConverter implements Converter<String, Properties> {

    @Nullable
    @Override
    public Properties convert(final String source) {
        try {
            final Properties props = new Properties();
            props.load(new ByteArrayInputStream(source.getBytes(StandardCharsets.ISO_8859_1)));
            return props;
        } catch (Exception ex) {
            throw new IllegalArgumentException("Failed to parse [" + source + "] into Properties", ex);
        }
    }
}
