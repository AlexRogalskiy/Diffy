package com.wildbeeslabs.sensiblemetrics.diffy.converter.utils;

import com.wildbeeslabs.sensiblemetrics.diffy.common.utils.ClassUtils;
import com.wildbeeslabs.sensiblemetrics.diffy.converter.interfaces.Converter;

import javax.annotation.Nonnull;
import java.time.*;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import static java.time.Instant.ofEpochMilli;
import static java.time.LocalDateTime.ofInstant;
import static java.time.ZoneId.systemDefault;
import static java.util.Arrays.asList;

/**
 * Helper class to register JSR-310 specific {@link Converter} implementations
 */
public abstract class Jsr310Converters {

    /**
     * Validates current {@link java.time.LocalDateTime} presence
     */
    private static final boolean JAVA_8_IS_PRESENT = ClassUtils.isPresent("java.time.LocalDateTime", Jsr310Converters.class.getClassLoader());

    /**
     * Returns the converters to be registered. Will only return converters in case we're running on Java 8.
     *
     * @return
     */
    public static Collection<Converter<?, ?>> getConvertersToRegister() {
        if (!JAVA_8_IS_PRESENT) {
            return Collections.emptySet();
        }
        return asList(
            DateToLocalDateTimeConverter.INSTANCE,
            LocalDateTimeToDateConverter.INSTANCE,
            DateToLocalDateConverter.INSTANCE,
            LocalDateToDateConverter.INSTANCE,
            DateToLocalTimeConverter.INSTANCE,
            LocalTimeToDateConverter.INSTANCE,
            DateToInstantConverter.INSTANCE,
            InstantToDateConverter.INSTANCE,
            LocalDateTimeToInstantConverter.INSTANCE,
            InstantToLocalDateTimeConverter.INSTANCE,
            ZoneIdToStringConverter.INSTANCE,
            StringToZoneIdConverter.INSTANCE,
            DurationToStringConverter.INSTANCE,
            StringToDurationConverter.INSTANCE,
            PeriodToStringConverter.INSTANCE,
            StringToPeriodConverter.INSTANCE
        );
    }

    public static boolean supports(final Class<?> type) {
        if (!JAVA_8_IS_PRESENT) {
            return false;
        }
        return asList(LocalDateTime.class, LocalDate.class, LocalTime.class, Instant.class).contains(type);
    }

    public enum DateToLocalDateTimeConverter implements Converter<Date, LocalDateTime> {

        INSTANCE;

        @Nonnull
        @Override
        public LocalDateTime convert(final Date source) {
            return ofInstant(source.toInstant(), systemDefault());
        }
    }

    public enum LocalDateTimeToDateConverter implements Converter<LocalDateTime, Date> {

        INSTANCE;

        @Nonnull
        @Override
        public Date convert(final LocalDateTime source) {
            return Date.from(source.atZone(systemDefault()).toInstant());
        }
    }

    public enum DateToLocalDateConverter implements Converter<Date, LocalDate> {

        INSTANCE;

        @Nonnull
        @Override
        public LocalDate convert(final Date source) {
            return ofInstant(ofEpochMilli(source.getTime()), systemDefault()).toLocalDate();
        }
    }

    public enum LocalDateToDateConverter implements Converter<LocalDate, Date> {

        INSTANCE;

        @Nonnull
        @Override
        public Date convert(final LocalDate source) {
            return Date.from(source.atStartOfDay(systemDefault()).toInstant());
        }
    }

    public enum DateToLocalTimeConverter implements Converter<Date, LocalTime> {

        INSTANCE;

        @Nonnull
        @Override
        public LocalTime convert(final Date source) {
            return ofInstant(ofEpochMilli(source.getTime()), systemDefault()).toLocalTime();
        }
    }

    public enum LocalTimeToDateConverter implements Converter<LocalTime, Date> {

        INSTANCE;

        @Nonnull
        @Override
        public Date convert(final LocalTime source) {
            return Date.from(source.atDate(LocalDate.now()).atZone(systemDefault()).toInstant());
        }
    }

    public enum DateToInstantConverter implements Converter<Date, Instant> {

        INSTANCE;

        @Nonnull
        @Override
        public Instant convert(final Date source) {
            return source.toInstant();
        }
    }

    public enum InstantToDateConverter implements Converter<Instant, Date> {

        INSTANCE;

        @Nonnull
        @Override
        public Date convert(final Instant source) {
            return Date.from(source.atZone(systemDefault()).toInstant());
        }
    }

    public enum LocalDateTimeToInstantConverter implements Converter<LocalDateTime, Instant> {

        INSTANCE;

        @Nonnull
        @Override
        public Instant convert(final LocalDateTime source) {
            return source.atZone(systemDefault()).toInstant();
        }
    }

    public enum InstantToLocalDateTimeConverter implements Converter<Instant, LocalDateTime> {

        INSTANCE;

        @Nonnull
        @Override
        public LocalDateTime convert(final Instant source) {
            return LocalDateTime.ofInstant(source, systemDefault());
        }
    }

    public enum ZoneIdToStringConverter implements Converter<ZoneId, String> {

        INSTANCE;

        @Nonnull
        @Override
        public String convert(final ZoneId source) {
            return source.toString();
        }
    }

    public enum StringToZoneIdConverter implements Converter<String, ZoneId> {

        INSTANCE;

        @Nonnull
        @Override
        public ZoneId convert(final String source) {
            return ZoneId.of(source);
        }
    }

    public enum DurationToStringConverter implements Converter<Duration, String> {

        INSTANCE;

        @Nonnull
        @Override
        public String convert(final Duration duration) {
            return duration.toString();
        }
    }

    public enum StringToDurationConverter implements Converter<String, Duration> {

        INSTANCE;

        @Nonnull
        @Override
        public Duration convert(final String s) {
            return Duration.parse(s);
        }
    }

    public enum PeriodToStringConverter implements Converter<Period, String> {

        INSTANCE;

        @Nonnull
        @Override
        public String convert(final Period period) {
            return period.toString();
        }
    }

    public enum StringToPeriodConverter implements Converter<String, Period> {

        INSTANCE;

        @Nonnull
        @Override
        public Period convert(final String s) {
            return Period.parse(s);
        }
    }
}
