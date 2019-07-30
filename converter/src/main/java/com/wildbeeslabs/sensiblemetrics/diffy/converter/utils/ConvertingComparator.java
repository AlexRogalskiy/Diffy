package com.wildbeeslabs.sensiblemetrics.diffy.converter.utils;

import com.wildbeeslabs.sensiblemetrics.diffy.common.utils.ValidationUtils;
import com.wildbeeslabs.sensiblemetrics.diffy.converter.interfaces.Converter;
import org.apache.commons.collections.comparators.ComparableComparator;

import java.util.Comparator;
import java.util.Map;

/**
 * A {@link Comparator} that converts values before they are compared.
 * The specified {@link Converter} will be used to convert each value
 * before it passed to the underlying {@code Comparator}.
 *
 * @param <S> the source type
 * @param <T> the target type
 * @author Phillip Webb
 * @since 3.2
 */
public class ConvertingComparator<S, T> implements Comparator<S> {

    private final Comparator<T> comparator;

    private final Converter<S, T> converter;


    /**
     * Create a new {@link ConvertingComparator} instance.
     *
     * @param converter the converter
     */
    public ConvertingComparator(final Converter<S, T> converter) {
        this(ComparableComparator.getInstance(), converter);
    }

    /**
     * Create a new {@link ConvertingComparator} instance.
     *
     * @param comparator the underlying comparator used to compare the converted values
     * @param converter  the converter
     */
    public ConvertingComparator(final Comparator<T> comparator, final Converter<S, T> converter) {
        ValidationUtils.notNull(comparator, "Comparator must not be null");
        ValidationUtils.notNull(converter, "Converter must not be null");

        this.comparator = comparator;
        this.converter = converter;
    }

    @Override
    public int compare(final S o1, final S o2) {
        final T c1 = this.converter.convert(o1);
        final T c2 = this.converter.convert(o2);
        return this.comparator.compare(c1, c2);
    }

    /**
     * Create a new {@link ConvertingComparator} that compares {@link java.util.Map.Entry
     * map * entries} based on their {@link java.util.Map.Entry#getKey() keys}.
     *
     * @param comparator the underlying comparator used to compare keys
     * @return a new {@link ConvertingComparator} instance
     */
    public static <K, V> ConvertingComparator<Map.Entry<K, V>, K> mapEntryKeys(final Comparator<K> comparator) {
        return new ConvertingComparator<>(comparator, Map.Entry::getKey);
    }

    /**
     * Create a new {@link ConvertingComparator} that compares {@link java.util.Map.Entry
     * map entries} based on their {@link java.util.Map.Entry#getValue() values}.
     *
     * @param comparator the underlying comparator used to compare values
     * @return a new {@link ConvertingComparator} instance
     */
    public static <K, V> ConvertingComparator<Map.Entry<K, V>, V> mapEntryValues(final Comparator<V> comparator) {
        return new ConvertingComparator<>(comparator, Map.Entry::getValue);
    }
}
