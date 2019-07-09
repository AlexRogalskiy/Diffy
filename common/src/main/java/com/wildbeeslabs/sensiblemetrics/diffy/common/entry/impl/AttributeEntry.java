package com.wildbeeslabs.sensiblemetrics.diffy.common.entry.impl;

import com.wildbeeslabs.sensiblemetrics.diffy.common.helpers.impl.Attributes;

import java.util.List;

/**
 * An {@link AttributeEntry} is a {@link List} which can
 * optionally be mapped to attributes.
 * <p>
 * {@link AttributeEntry}s is a read and written by the {@link Attributes}.
 *
 * @see Attributes
 */
public class AttributeEntry<T, K, V> {

    private final List<T> tokens;
    private final Attributes<K, V> attributes;

    /**
     * Initializes the current instance.
     *
     * @param tokens
     * @param attributes
     */
    public AttributeEntry(final List<T> tokens, final Attributes<K, V> attributes) {
        this.tokens = tokens;
        this.attributes = attributes;
    }

    /**
     * Retrieves the tokens.
     *
     * @return the tokens
     */
    public List<T> getTokens() {
        return this.tokens;
    }

    /**
     * Retrieves the {@link Attributes}.
     *
     * @return the {@link Attributes}
     */
    public Attributes<K, V> getAttributes() {
        return this.attributes;
    }
}
