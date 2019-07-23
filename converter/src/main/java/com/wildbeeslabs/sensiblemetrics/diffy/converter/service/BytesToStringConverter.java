package com.wildbeeslabs.sensiblemetrics.diffy.converter.service;


import lombok.RequiredArgsConstructor;

import java.nio.charset.Charset;

@RequiredArgsConstructor
public class BytesToStringConverter extends AbstractConverter<byte[], String> {

    private final Charset charset;

    @Override
    protected String valueOf(final byte[] value) {
        return new String(value, this.charset);
    }
}
