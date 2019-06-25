package com.wildbeeslabs.sensiblemetrics.diffy.common.helpers.iface;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

/**
 * Local time measure interface declaration
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-07
 */
public interface LocalTimeMeasure {

    /**
     * Get current global instant time
     *
     * @return current instant time
     */
    Instant nowInstant();

    /**
     * Get current local time
     *
     * @return current local time
     */
    default LocalDateTime nowLocalDateTime() {
        return LocalDateTime.ofInstant(nowInstant(), getDefaultTimeZone());
    }

    /**
     * Get current local time without seconds
     *
     * @return current local time without seconds
     */
    default LocalDateTime nowLocalDateTimeTruncatedToMin() {
        return LocalDateTime.ofInstant(nowInstant(), getDefaultTimeZone()).truncatedTo(ChronoUnit.MINUTES);
    }

    /**
     * Get current local date
     *
     * @return current local date
     */
    default LocalDate nowLocalDate() {
        return this.nowLocalDateTime().toLocalDate();
    }

    /**
     * Get current local time
     *
     * @return current local time
     */
    default java.time.LocalTime nowLocalTime() {
        return this.nowLocalDateTime().toLocalTime();
    }

    /**
     * Get default current time zone (UTC)
     *
     * @return current time zone
     */
    default ZoneOffset getDefaultTimeZone() {
        return ZoneOffset.UTC;
    }

    /**
     * Parse local time
     *
     * @param localTime - current local time
     * @return current time zone
     */
    default Instant parse(final String localTime) {
        return Instant.parse(localTime);
    }
}
