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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * List {@link AbstractConverter} implementation
 */
public class ListConverter extends AbstractConverter<Object, Object> {

    @Override
    public Object valueOf(final Object value) {
        final Class<?> type = value.getClass();
        final List newList = new ArrayList();
        if (type.isArray()) {
            newList.addAll(Arrays.asList(((Object[]) value)));
        } else if (Collection.class.isAssignableFrom(type)) {
            newList.addAll((Collection<Object>) value);
        } else if (Iterable.class.isAssignableFrom(type)) {
            for (final Object o : (Iterable<Object>) value) {
                newList.add(o);
            }
        }
        return newList;
    }

    @Override
    public boolean canConvert(final Class<Object> clazz) {
        return clazz.isArray() || Collection.class.isAssignableFrom(clazz) || Iterable.class.isAssignableFrom(clazz);
    }
}
