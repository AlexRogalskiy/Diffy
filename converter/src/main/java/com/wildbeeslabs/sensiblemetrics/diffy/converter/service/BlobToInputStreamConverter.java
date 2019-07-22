package com.wildbeeslabs.sensiblemetrics.diffy.converter.service;

import com.wildbeeslabs.sensiblemetrics.diffy.converter.exception.ConvertOperationException;
import com.wildbeeslabs.sensiblemetrics.diffy.converter.interfaces.ContentTypeConverter;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

/**
 * @author Allard Buijze
 */
public class BlobToInputStreamConverter implements ContentTypeConverter<Blob, InputStream> {

    @Override
    public Class<Blob> expectedSourceType() {
        return Blob.class;
    }

    @Override
    public Class<InputStream> targetType() {
        return InputStream.class;
    }

    @Override
    public InputStream convert(final Blob original) {
        try {
            return original.getBinaryStream();
        } catch (SQLException e) {
            throw new ConvertOperationException("Error while attempting to read data from Blob", e);
        }
    }
}
