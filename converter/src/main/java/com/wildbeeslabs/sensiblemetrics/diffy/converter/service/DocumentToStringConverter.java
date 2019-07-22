package com.wildbeeslabs.sensiblemetrics.diffy.converter.service;

import com.wildbeeslabs.sensiblemetrics.diffy.converter.interfaces.ContentTypeConverter;
import org.dom4j.Document;

/**
 * Converter that converts XOM Document instances to a String. The Document is written as XML string.
 *
 * @author Jochen Munz
 * @since 2.2
 */
public class DocumentToStringConverter implements ContentTypeConverter<Document, String> {

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
        return original.toString();
    }
}
