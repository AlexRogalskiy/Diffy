package com.wildbeeslabs.sensiblemetrics.diffy.configuration;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Objects;

/**
 * Diffy configuration implementation
 */
@Data
@Builder
@EqualsAndHashCode
@ToString
public final class DiffyConfiguration {

    public static final DiffyConfiguration DEFAULT = DiffyConfiguration.builder().build();
    public static final DiffyConfiguration PERMISSIVE = DiffyConfiguration.builder()
        .maxContentLen(100 * 1024 * 1024)
        .maxHeaderCount(-1)
        .maxHeaderLen(-1)
        .maxLineLen(-1)
        .build();
    public static final DiffyConfiguration STRICT = DiffyConfiguration.builder()
        .strictParsing(true)
        .malformedHeaderStartsBody(false)
        .build();

    private final boolean strictParsing;
    private final int maxLineLen;
    private final int maxHeaderCount;
    private final int maxHeaderLen;
    private final long maxContentLen;
    private final boolean countLineNumbers;
    private final String headlessParsing;
    private final boolean malformedHeaderStartsBody;

    public static DiffyConfiguration copy(final DiffyConfiguration configuration) {
        Objects.requireNonNull(configuration, "Configuration should not be null");

        return DiffyConfiguration.builder()
            .strictParsing(configuration.isStrictParsing())
            .maxLineLen(configuration.getMaxLineLen())
            .maxHeaderCount(configuration.getMaxHeaderCount())
            .maxHeaderLen(configuration.getMaxHeaderLen())
            .maxContentLen(configuration.getMaxContentLen())
            .countLineNumbers(configuration.isCountLineNumbers())
            .headlessParsing(configuration.getHeadlessParsing())
            .malformedHeaderStartsBody(configuration.isMalformedHeaderStartsBody())
            .build();
    }
}
