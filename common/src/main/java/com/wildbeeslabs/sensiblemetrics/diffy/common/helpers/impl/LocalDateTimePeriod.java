/*
 * The MIT License
 *
 * Copyright 2018 WildBees Labs.
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
package com.wildbeeslabs.sensiblemetrics.diffy.common.helpers.impl;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 *
 * Custom local datetime time implementation
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-07
 */
@Data
@EqualsAndHashCode
@ToString
public class LocalDateTimePeriod {

    private final LocalDateTime start;
    private final LocalDateTime end;

    public LocalDateTimePeriod(final LocalDateTime start, final LocalDateTime end) {
        this.start = start;
        this.end = end;
    }

    public static List<LocalDateTimePeriod> getIntersections(final List<LocalDateTimePeriod> periods1, final List<LocalDateTimePeriod> periods2) {
        if (Objects.isNull(periods1) || periods1.isEmpty() || Objects.isNull(periods2) || periods2.isEmpty()) {
            return Collections.<LocalDateTimePeriod>emptyList();
        }
        final List<LocalDateTimePeriod> commonList = new ArrayList<>();
        if (!periods1.isEmpty()) {
            periods1.stream().forEach(s -> {
                periods2.stream().map((s2) -> LocalDateTimePeriod.getIntersection(s, s2)).filter(Objects::nonNull).forEach((interval) -> {
                    commonList.add(interval);
                });
            });
        }
        return commonList;
    }

    public static Set<LocalDateTimePeriod> getOverlap(final List<LocalDateTimePeriod> periods) {
        if (Objects.isNull(periods) || periods.isEmpty()) {
            return Collections.<LocalDateTimePeriod>emptySet();
        }
        final Set<LocalDateTimePeriod> overlaps = new HashSet<>();
        for (int i = 0; i < periods.size() - 1; i++) {
            final LocalDateTimePeriod leftPeriod = periods.get(i);
            for (int j = i + 1; j < periods.size(); j++) {
                final LocalDateTimePeriod rightPeriod = periods.get(j);
                if (rightPeriod.getStart().isBefore(leftPeriod.getEnd()) && rightPeriod.getEnd().isAfter(leftPeriod.getStart())) {
                    overlaps.add(new LocalDateTimePeriod(rightPeriod.getStart(), LocalDateTimePeriod.getMin(leftPeriod.getEnd(), rightPeriod.getEnd())));
                }
            }
        }
        return overlaps;
    }

    public static LocalDateTimePeriod getIntersection(final LocalDateTimePeriod leftPeriod, final LocalDateTimePeriod rightPeriod) {
        if (Objects.isNull(leftPeriod) || Objects.isNull(rightPeriod)) {
            return null;
        }
        final LocalDateTime maxStartDate = LocalDateTimePeriod.getMax(leftPeriod.getStart(), rightPeriod.getStart());
        final LocalDateTime minEndDate = LocalDateTimePeriod.getMin(leftPeriod.getEnd(), rightPeriod.getEnd());
        if (maxStartDate.isBefore(minEndDate)) {
            return new LocalDateTimePeriod(maxStartDate, minEndDate);
        }
        return null;
    }

    public static LocalDateTime getMin(@NonNull final LocalDateTime dateTime1, @NonNull final LocalDateTime dateTime2) {
        return Collections.min(Arrays.asList(dateTime1, dateTime2));
    }

    public static LocalDateTime getMax(@NonNull final LocalDateTime dateTime1, @NonNull final LocalDateTime dateTime2) {
        return Collections.max(Arrays.asList(dateTime1, dateTime2));
    }

    public LocalDatePeriod toDateInterval() {
        return new LocalDatePeriod(
                Objects.isNull(this.start) ? null : this.start.toLocalDate(),
                Objects.isNull(this.end) ? null : this.end.toLocalDate());
    }

    public long seconds() {
        return ChronoUnit.SECONDS.between(this.start, this.end);
    }
}
