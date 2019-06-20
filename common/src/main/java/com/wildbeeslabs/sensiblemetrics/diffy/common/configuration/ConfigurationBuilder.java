package com.wildbeeslabs.sensiblemetrics.diffy.common.configuration;

import com.wildbeeslabs.sensiblemetrics.diffy.annotation.Factory;
import com.wildbeeslabs.sensiblemetrics.diffy.validator.iface.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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
    private Validator validator;
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

    public ConfigurationBuilder<T> validateUsing(final Validator<T> validator) {
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
