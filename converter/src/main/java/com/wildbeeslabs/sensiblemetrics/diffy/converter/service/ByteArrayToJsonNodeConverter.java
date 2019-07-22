package com.wildbeeslabs.sensiblemetrics.diffy.converter.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wildbeeslabs.sensiblemetrics.diffy.converter.exception.ConvertOperationException;
import com.wildbeeslabs.sensiblemetrics.diffy.converter.interfaces.ContentTypeConverter;

import java.io.IOException;

/**
 * ContentTypeConverter implementation that converts byte[] containing UTF8 encoded JSON string to a Jackson JsonNode.
 *
 * @author Allard Buijze
 * @since 2.2
 */
public class ByteArrayToJsonNodeConverter implements ContentTypeConverter<byte[], JsonNode> {

    private final ObjectMapper objectMapper;

    /**
     * Initialize the Converter, using given {@code objectMapper} to parse the binary contents
     *
     * @param objectMapper the Jackson ObjectMapper to parse the byte array with
     */
    public ByteArrayToJsonNodeConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Class<byte[]> expectedSourceType() {
        return byte[].class;
    }

    @Override
    public Class<JsonNode> targetType() {
        return JsonNode.class;
    }

    @Override
    public JsonNode convert(byte[] original) {
        try {
            return objectMapper.readTree(original);
        } catch (IOException e) {
            throw new ConvertOperationException("An error occurred while converting a JsonNode to byte[]", e);
        }
    }
}
