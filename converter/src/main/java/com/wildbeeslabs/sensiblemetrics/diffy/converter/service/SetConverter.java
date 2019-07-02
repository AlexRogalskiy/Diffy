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

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Set {@link AbstractConverter} implementation
 */
public class SetConverter extends AbstractConverter<Object, Object> {

    @Override
    public Object valueOf(final Object value) {
        final Class<?> type = value.getClass();
        final Set<Object> newSet = new LinkedHashSet();
        if (type.isArray()) {
            newSet.addAll(Arrays.asList(((Object[]) value)));
        } else if (Collection.class.isAssignableFrom(type)) {
            newSet.addAll((Collection<Object>) value);
        } else if (Iterable.class.isAssignableFrom(type)) {
            for (final Object o : (Iterable<Object>) value) {
                newSet.add(o);
            }
        }
        return newSet;
    }

    @Override
    public boolean canConvert(final Class<Object> clazz) {
        return clazz.isArray() || Collection.class.isAssignableFrom(clazz) || Iterable.class.isAssignableFrom(clazz);
    }
}
