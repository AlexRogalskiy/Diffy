package com.wildbeeslabs.sensiblemetrics.diffy.matcher.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

/**
 * Matcher mode type {@link Enum} to process malformed and unexpected data
 * <p>
 * 2 basic implementations are provided:
 * <ul>
 * <li>{@link #STRICT} return "true" on any occurrence</li>
 * <li>{@link #SILENT} ignores any problem</li>
 * </ul>
 */
@Getter
@RequiredArgsConstructor
public enum MatcherMode {
    STRICT(true),
    SILENT(false);

    /**
     * Binary flag based on current status
     */
    private final boolean enable;

    /**
     * Return binary flag based on current status {@code STRICT}
     *
     * @return true - if current status is {@code STRICT}, false - otherwise
     */
    public boolean isStrict() {
        return Objects.equals(this, STRICT);
    }
}
