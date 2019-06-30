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

import com.wildbeeslabs.sensiblemetrics.diffy.common.exception.InvalidFormatException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Convert a string into a URI
 *
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class URIConverter extends AbstractConverter<String, URI> {

    @Override
    public URI valueOf(final String value) {
        try {
            return new URI(value);
        } catch (URISyntaxException e) {
            InvalidFormatException.throwInvalidFormat(value, e);
        }
        return null;
    }
}
