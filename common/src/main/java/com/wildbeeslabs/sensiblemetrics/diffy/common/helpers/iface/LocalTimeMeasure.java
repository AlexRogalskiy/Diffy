/*
 * The MIT License
 *
 * Copyright 2019 WildBees Labs, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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
