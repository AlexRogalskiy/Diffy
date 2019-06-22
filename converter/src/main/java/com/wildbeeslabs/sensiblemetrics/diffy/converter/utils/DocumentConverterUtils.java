/*
 * The MIT License
 *
 * Copyright 2019 WildBees Labs, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.wildbeeslabs.sensiblemetrics.diffy.converter.utils;

import com.google.gson.*;
import com.wildbeeslabs.sensiblemetrics.diffy.common.utils.StringUtils;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.wildbeeslabs.sensiblemetrics.diffy.common.utils.StringUtils.formatMessage;

/**
 * Document converter utilities implementation
 */
@Slf4j
@UtilityClass
public class DocumentConverterUtils {

    public static Document toDocument(final JsonObject jsonObject) {
        final Document document = new Document();
        for (final Map.Entry<String, JsonElement> e : jsonObject.entrySet()) {
            document.append(e.getKey(), fromJsonElement(e.getValue()));
        }
        return document;
    }

    public static JsonElement fromDocument(final Document document) {
        final JsonObject jsonObject = new JsonObject();
        for (final Map.Entry<String, Object> e : document.entrySet()) {
            jsonObject.add(e.getKey(), createJsonElement(e.getValue()));
        }
        return jsonObject;
    }

    private static Object fromJsonElement(final JsonElement jsonElement) {
        if (Objects.equals(jsonElement, JsonNull.INSTANCE)) {
            return null;
        }
        if (jsonElement instanceof JsonObject) {
            return toDocument((JsonObject) jsonElement);
        }
        if (jsonElement instanceof JsonPrimitive) {
            final JsonPrimitive jsonPrimitive = (JsonPrimitive) jsonElement;
            if (jsonPrimitive.isString()) {
                return jsonElement.getAsString();
            }
            if (jsonPrimitive.isNumber() && jsonElement.getAsNumber() instanceof BigDecimal) {
                final BigDecimal value = ((BigDecimal) jsonElement.getAsNumber());
                try {
                    return value.longValueExact();
                } catch (ArithmeticException e) {
                    return value.doubleValue();
                }
            }
            if (jsonPrimitive.isNumber()) {
                return jsonElement.getAsNumber();
            }
            if (jsonPrimitive.isBoolean()) {
                return jsonElement.getAsBoolean();
            }
        }

        if (jsonElement instanceof JsonArray) {
            final List<Object> list = new ArrayList<>();
            for (final JsonElement e : ((JsonArray) jsonElement)) {
                list.add(fromJsonElement(e));
            }
            return list;
        }
        throw new IllegalArgumentException("unsupported JsonElement type - " + jsonElement.getClass().getSimpleName());
    }

    private static JsonElement createJsonElement(final Object dbObject) {
        if (Objects.isNull(dbObject)) {
            return JsonNull.INSTANCE;
        }

        if (dbObject instanceof Document) {
            return fromDocument((Document) dbObject);
        }
        if (dbObject instanceof String) {
            return new JsonPrimitive((String) dbObject);
        }
        if (dbObject instanceof Number) {
            return new JsonPrimitive((Number) dbObject);
        }
        if (dbObject instanceof Boolean) {
            return new JsonPrimitive((Boolean) dbObject);
        }

        if (dbObject instanceof List) {
            JsonArray array = new JsonArray();
            for (Object e : (List) dbObject) {
                array.add(createJsonElement(e));
            }
            return array;
        }

        if (dbObject instanceof ObjectId) {
            JsonObject id = new JsonObject();
            id.addProperty("$oid", dbObject.toString());
            return id;
        }
        throw new IllegalArgumentException(StringUtils.formatMessage("ERROR: unsupported object type: {%s}", dbObject.getClass().getSimpleName()));
    }
}
