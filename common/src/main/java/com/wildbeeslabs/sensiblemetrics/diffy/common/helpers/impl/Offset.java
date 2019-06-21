package com.wildbeeslabs.sensiblemetrics.diffy.common.helpers.impl;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Objects;

/**
 * A positive offset.
 *
 * @param <T> the type of the offset value.
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Data
@EqualsAndHashCode
@ToString
public class Offset<T extends Number> {

    public final T value;
    /**
     * When |actual-expected|=offset and strict is true the assertThat(actual).isCloseTo(expected, offset); assertion will fail.
     */
    public final boolean strict;

    /**
     * Creates a new strict {@link Offset} that let {@code isCloseTo} assertions pass when {@code |actual-expected| == offset value}.
     * <p>
     * Example:
     * <pre><code class='java'> // assertions succeed
     * assertThat(8.1).isCloseTo(8.0, offset(0.2));
     * assertThat(8.1).isCloseTo(8.0, offset(0.1));
     *
     * // assertion fails
     * assertThat(8.1).isCloseTo(8.0, offset(0.01));</code></pre>
     *
     * @param <T>   the type of value of the {@link Offset}.
     * @param value the value of the offset.
     * @return the created {@code Offset}.
     * @throws NullPointerException     if the given value is {@code null}.
     * @throws IllegalArgumentException if the given value is negative.
     */
    public static <T extends Number> Offset<T> offset(final T value) {
        Objects.requireNonNull(value, "Offset should not be null");
        assert value.doubleValue() >= 0d : "An offset value should be greater than or equal to zero";
        return new Offset<>(value, false);
    }

    /**
     * Creates a new strict {@link Offset} that make {@code isCloseTo} assertion fail when {@code |actual-expected| == offset value}.
     * <p>
     * Examples:
     * <pre><code class='java'> // assertion succeeds
     * assertThat(8.1).isCloseTo(8.0, offset(0.2));
     *
     * // assertions fail
     * assertThat(8.1).isCloseTo(8.0, offset(0.1));
     * assertThat(8.1).isCloseTo(8.0, offset(0.01));</code></pre>
     *
     * @param <T>   the type of value of the {@link Offset}.
     * @param value the value of the offset.
     * @return the created {@code Offset}.
     * @throws NullPointerException     if the given value is {@code null}.
     * @throws IllegalArgumentException if the given value is negative.
     */
    public static <T extends Number> Offset<T> strictOffset(final T value) {
        Objects.requireNonNull(value, "Offset should not be null");
        assert value.doubleValue() > 0d : "A strict offset value should be greater than zero";
        return new Offset<>(value, true);
    }

    private Offset(final T value, boolean strict) {
        this.value = value;
        this.strict = strict;
    }
}
