//package com.wildbeeslabs.sensiblemetrics.diffy.common.serializer;
//
//import com.fasterxml.jackson.core.JsonGenerator;
//import com.fasterxml.jackson.databind.JavaType;
//import com.fasterxml.jackson.databind.JsonMappingException;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.SerializerProvider;
//import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
//import com.fasterxml.jackson.databind.ser.std.NonTypedScalarSerializerBase;
//
//import java.io.IOException;
//import java.lang.reflect.Type;
//import java.util.Objects;
//
//public class UnQuotesSerializer extends NonTypedScalarSerializerBase<String> {
//
//    public UnQuotesSerializer() {
//        super(String.class);
//    }
//
//    @Override
//    public void serialize(final String value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException {
//        jgen.writeRawValue(value);
//    }
//
//    @Override
//    public JsonNode getSchema(final SerializerProvider provider, final Type typeHint) {
//        return createSchemaNode("string", true);
//    }
//
//    @Override
//    public void acceptJsonFormatVisitor(final JsonFormatVisitorWrapper visitor, final JavaType typeHint) throws JsonMappingException {
//        if (Objects.nonNull(visitor)) visitor.expectStringFormat(typeHint);
//    }
//}
