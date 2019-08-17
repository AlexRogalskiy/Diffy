package com.wildbeeslabs.sensiblemetrics.diffy.converter.service;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class StringBasedConverter {

    /**
     * Use {@literal UTF-8} as default charset.
     */
    public static final Charset CHARSET = StandardCharsets.UTF_8;

    public byte[] fromString(final String source) {
        return source.getBytes(CHARSET);
    }

    public String toString(final byte[] source) {
        return new String(source, CHARSET);
    }
}
