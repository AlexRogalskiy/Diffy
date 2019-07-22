package com.wildbeeslabs.sensiblemetrics.diffy.converter.service;

import com.wildbeeslabs.sensiblemetrics.diffy.converter.exception.ConvertOperationException;
import com.wildbeeslabs.sensiblemetrics.diffy.converter.interfaces.ContentTypeConverter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Converter that converts an InputStream to a byte array. This converter simply reads all contents from the
 * input stream and returns that as an array.
 *
 * @author Allard Buijze
 * @since 2.0
 */
public class InputStreamToByteArrayConverter implements ContentTypeConverter<InputStream, byte[]> {

    @Override
    public Class<InputStream> expectedSourceType() {
        return InputStream.class;
    }

    @Override
    public Class<byte[]> targetType() {
        return byte[].class;
    }

    @Override
    public byte[] convert(final InputStream original) {
        try {
            return bytesFrom(original);
        } catch (IOException e) {
            throw new ConvertOperationException("Unable to convert InputStream to byte[]. Error while reading from Stream.", e);
        }
    }

    private byte[] bytesFrom(final InputStream original) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream(1024); // NOSONAR - There is no point in closing BAOS
        byte[] buffer = new byte[1024];
        int n;
        while (-1 != (n = original.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }
}
