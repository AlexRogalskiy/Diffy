package com.wildbeeslabs.sensiblemetrics.diffy.generator.service;

import lombok.RequiredArgsConstructor;

import java.util.Objects;

/**
 * Typed pair of elements.
 *
 * @param <F> type of first element of pair
 * @param <S> type of second element of pair
 */
@RequiredArgsConstructor
public final class Pair<F, S> {
    public final F first;
    public final S second;

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Pair<?, ?>))
            return false;

        Pair<?, ?> other = (Pair<?, ?>) o;
        return Objects.equals(first, other.first)
            && Objects.equals(second, other.second);
    }

    @Override
    public String toString() {
        return String.format("[%s = %s]", first, second);
    }
}
