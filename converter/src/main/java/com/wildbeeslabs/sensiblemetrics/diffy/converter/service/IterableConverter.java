package com.wildbeeslabs.sensiblemetrics.diffy.converter.service;

import com.wildbeeslabs.sensiblemetrics.diffy.converter.interfaces.Converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Converter capable of transforming a given {@link Iterable} into a collection type.
 */
public final class IterableConverter<T> implements Converter<Iterable<T>, List<T>> {

    /**
     * Converts a given {@link Iterable} into a {@link List}
     *
     * @param source
     * @return {@link Collections#emptyList()} when source is {@literal null}.
     */
    @Override
    public List<T> convert(final Iterable<T> source) {
        if (source == null) {
            return Collections.emptyList();
        }
        if (source instanceof List) {
            return (List<T>) source;
        }
        if (source instanceof Collection) {
            return new ArrayList<>((Collection<T>) source);
        }
        final List<T> result = new ArrayList<>();
        source.forEach(result::add);
        return result;
    }
}
