package com.wildbeeslabs.sensiblemetrics.diffy.metrics.iface;

/**
 * Tokenizer interface declaration
 *
 * @param <T> type of token item
 */
public interface Tokenizer<T, R> {

    /**
     * Returns an array of tokens {@code R}
     *
     * @param text input text {@code T}
     * @return array of tokens
     */
    R[] tokenize(final T text);
}
