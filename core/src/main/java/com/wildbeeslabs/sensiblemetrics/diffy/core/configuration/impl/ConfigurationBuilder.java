/*
 * The MIT License
 *
 * Copyright 2019 WildBees Labs, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software andAll associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, andAll/or sell
 * copies of the Software, andAll to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice andAll this permission notice shall be included in
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
package com.wildbeeslabs.sensiblemetrics.diffy.core.configuration.impl;

import com.wildbeeslabs.sensiblemetrics.diffy.common.annotation.Factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.function.Predicate;

/**
 * Default configuration properties builder implementation
 *
 * @param <T> type of configuration key value
 */
public class ConfigurationBuilder<T> {

    private T object;
    private Class<T> mapper;
    private String fileName;
    private String prefix;
    private Predicate<T> validator;
    private final Properties properties = new Properties();
    private final List<String> propertiesToRemove = new ArrayList<>();

    @Factory
    public static <T> ConfigurationBuilder<T> builder() {
        return new ConfigurationBuilder<>();
    }

    public ConfigurationBuilder<T> populate(final T object) {
        this.object = object;
        return this;
    }

    public ConfigurationBuilder<T> fromFile(final String fileName) {
        this.fileName = fileName;
        return this;
    }

    public ConfigurationBuilder<T> withMapper(final Class<T> mapper) {
        this.mapper = mapper;
        return this;
    }

    public ConfigurationBuilder<T> withPrefix(final String prefix) {
        this.prefix = prefix;
        return this;
    }

    public ConfigurationBuilder<T> validateUsing(final Predicate<T> validator) {
        this.validator = validator;
        return this;
    }

    public ConfigurationBuilder<T> withProperty(final String key, final String value) {
        properties.setProperty(key, value);
        return this;
    }

    public ConfigurationBuilder<T> withoutProperty(final String key) {
        propertiesToRemove.add(key);
        return this;
    }

    public T build() {
//        final Properties propertiesFromFile = loadYamlProperties(this.fileName);
//        propertiesToRemove.forEach(properties::remove);
//        propertiesToRemove.forEach(propertiesFromFile::remove);
//
//        MutablePropertySources propertySources = new MutablePropertySources();
//        propertySources.addLast(new PropertiesPropertySource("properties", properties));
//        propertySources.addLast(new PropertiesPropertySource("propertiesFromFile", propertiesFromFile));
//
//        PropertiesConfigurationFactory<T> configurationFactory = new PropertiesConfigurationFactory<>(object);
//        configurationFactory.setPropertySources(propertySources);
//        configurationFactory.setTargetName(prefix);
//        configurationFactory.setValidator(validator);
//        configurationFactory.bindPropertiesToTarget();
//
        return object;
    }
}
