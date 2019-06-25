/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wildbeeslabs.sensiblemetrics.diffy.converter.service;

import com.wildbeeslabs.sensiblemetrics.diffy.converter.interfaces.Converter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;

/**
 * Simple json {@link Converter} implementation
 */
public class JsonConverter implements Converter<String, Object> {
    /**
     * Default number regex pattern {@link String}
     */
    private static final String NUMBER_REGEX = "-?(?:0|[1-9]\\d*)(?:\\.\\d+)?(?:[eE][+-]?\\d+)?";

    /**
     * Takes a JSON string and returns either a {@link org.json.JSONObject} or {@link org.json.JSONArray},
     * depending on whether the string represents an object or an array.
     *
     * @param value Raw JSON string to be parsed
     * @return JSONObject or JSONArray
     * @throws JSONException JSON parsing error
     */
    @Override
    public Object convert(final String value) throws JSONException {
        if (value.trim().startsWith("{")) {
            return new JSONObject(value);
        } else if (value.trim().startsWith("[")) {
            return new JSONArray(value);
        } else if (value.trim().startsWith("\"") || value.trim().matches(NUMBER_REGEX)) {
            return (JSONString) () -> value;
        }
        throw new JSONException(String.format("ERROR: unparsable JSON string = {%s}", value));
    }
}
