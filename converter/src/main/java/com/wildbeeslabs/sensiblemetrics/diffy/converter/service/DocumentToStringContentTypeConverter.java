package com.wildbeeslabs.sensiblemetrics.diffy.converter.service;

import com.wildbeeslabs.sensiblemetrics.diffy.converter.interfaces.ContentTypeConverter;
import org.bson.Document;

/**
 * ContentTypeConverter implementation that converts a BSON Document structure into a String containing its JSON
 * representation.
 *
 * @author Allard Buijze
 * @since 2.0
 */
public class DocumentToStringContentTypeConverter implements ContentTypeConverter<Document, String> {

    @Override
    public Class<Document> expectedSourceType() {
        return Document.class;
    }

    @Override
    public Class<String> targetType() {
        return String.class;
    }

    @Override
    public String convert(final Document original) {
        return original.toJson();
    }
}
