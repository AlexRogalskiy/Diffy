package com.wildbeeslabs.sensiblemetrics.diffy.converter.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wildbeeslabs.sensiblemetrics.diffy.converter.exception.ConvertOperationException;
import com.wildbeeslabs.sensiblemetrics.diffy.converter.interfaces.ContentTypeConverter;

/**
 * ContentTypeConverter implementation that converts a JsonNode object into a byte[]. The byte[] will contain the UTF8
 * encoded JSON string.
 *
 * @author Allard Buijze
 * @since 2.2
 */
public class JsonNodeToByteArrayConverter implements ContentTypeConverter<JsonNode, byte[]> {

    private final ObjectMapper objectMapper;

    /**
     * Initialize the converter, using given {@code objectMapper} to convert the JSonNode into bytes. Typically,
     * this would be the objectMapper used by the Serializer that serializes objects into JsonNode.
     *
     * @param objectMapper The objectMapper to serialize the JsonNode with.
     */
    public JsonNodeToByteArrayConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Class<JsonNode> expectedSourceType() {
        return JsonNode.class;
    }

    @Override
    public Class<byte[]> targetType() {
        return byte[].class;
    }

    @Override
    public byte[] convert(JsonNode original) {
        try {
            return objectMapper.writeValueAsBytes(original);
        } catch (JsonProcessingException e) {
            throw new ConvertOperationException("An error occurred while converting a JsonNode to byte[]", e);
        }
    }
}
