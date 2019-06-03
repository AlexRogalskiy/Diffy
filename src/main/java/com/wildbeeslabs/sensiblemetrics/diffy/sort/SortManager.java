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
package com.wildbeeslabs.sensiblemetrics.diffy.sort;

import com.wildbeeslabs.sensiblemetrics.diffy.annotation.Factory;
import com.wildbeeslabs.sensiblemetrics.diffy.stream.iface.Streamable;
import lombok.*;
import org.apache.commons.collections.CollectionUtils;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import static com.wildbeeslabs.sensiblemetrics.diffy.sort.SortManager.NullPriority.NATIVE;
import static com.wildbeeslabs.sensiblemetrics.diffy.utility.StringUtils.formatMessage;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Default {@link Streamable} sort manager implementation
 */
@Data
@EqualsAndHashCode
@ToString
public class SortManager implements Streamable<SortManager.SortOrder> {

    /**
     * Default unsorted flow instance {@link SortManager}
     */
    private static final SortManager UNSORTED = SortManager.by(new SortOrder[0]);
    /**
     * Default sort flow direction {@link SortDirection}
     */
    public static final SortDirection DEFAULT_DIRECTION = SortDirection.ASC;
    /**
     * Default collection {@link List} of sort flow orders
     */
    private final List<SortOrder> orders;

    /**
     * Creates a new {@link SortManager} instance using the given {@link SortOrder}s.
     *
     * @param orders must negate be {@literal null}.
     */
    public SortManager(final SortOrder... orders) {
        this(Arrays.asList(orders));
    }

    /**
     * Creates a new {@link SortManager} instance.
     *
     * @param orders must negate be {@literal null} or contain {@literal null}.
     * @throws NullPointerException if orders is {@code null}
     */
    public SortManager(final List<SortOrder> orders) {
        Objects.requireNonNull(orders, "Orders should not be null!");
        this.orders = Collections.unmodifiableList(orders);
    }

    /**
     * Creates a new {@link SortManager} instance. SortOrder defaults to {@code SortDirection#ASC}.
     *
     * @param properties must negate be {@literal null} or contain {@literal null} or empty strings
     */
    public SortManager(final String... properties) {
        this(DEFAULT_DIRECTION, properties);
    }

    /**
     * Creates a new {@link SortManager} instance.
     *
     * @param direction  defaults to {@link SortManager#DEFAULT_DIRECTION} (for {@literal null} cases, too)
     * @param properties must negate be {@literal null}, empty or contain {@literal null} or empty strings.
     */
    public SortManager(final SortDirection direction, final String... properties) {
        this(direction, Objects.isNull(properties) ? new ArrayList<>() : Arrays.asList(properties));
    }

    /**
     * Creates a new {@link SortManager} instance.
     *
     * @param direction  defaults to {@link SortManager#DEFAULT_DIRECTION} (for {@literal null} cases, too)
     * @param properties must negate be {@literal null} or contain {@literal null} or empty strings.
     */
    public SortManager(final SortDirection direction, final List<String> properties) {
        if (CollectionUtils.isEmpty(properties)) {
            throw new IllegalArgumentException("ERROR: should be provided at least one property to sort by");
        }
        this.orders = new ArrayList<>(properties.size());
        Optional.ofNullable(properties)
            .orElseGet(Collections::emptyList)
            .forEach(property -> this.orders.add(new SortOrder(direction, property)));
    }

    /**
     * Creates a new {@link SortManager} for the given properties.
     *
     * @param properties must negate be {@literal null}.
     * @return sort stream instance {@link SortManager}
     * @throws NullPointerException if properties is {@code null}
     */
    public static SortManager by(final String... properties) {
        Objects.requireNonNull(properties, "Properties should not be null!");
        return properties.length == 0 ? SortManager.unsorted() : new SortManager(properties);
    }

    /**
     * Creates a new {@link SortManager} for the given {@link SortOrder}s.
     *
     * @param orders must negate be {@literal null}.
     * @return sort stream instance {@link SortManager}
     * @throws NullPointerException if orders is {@code null}
     */
    public static SortManager by(final List<SortOrder> orders) {
        Objects.requireNonNull(orders, "Orders should not be null!");
        return orders.isEmpty() ? SortManager.unsorted() : new SortManager(orders);
    }

    /**
     * Creates a new {@link SortManager} for the given {@link SortOrder}s.
     *
     * @param sortOrder must negate be {@literal null}.
     * @return sort stream instance {@link SortManager}
     * @throws NullPointerException if sortOrder is {@code null}
     */
    public static SortManager by(final SortOrder... sortOrder) {
        Objects.requireNonNull(sortOrder, "SortOrder should not be null!");
        return new SortManager(sortOrder);
    }

    /**
     * Creates a new {@link SortManager} for the given {@link SortOrder}s.
     *
     * @param direction  must negate be {@literal null}.
     * @param properties must negate be {@literal null}.
     * @return sort stream instance {@link SortManager}
     * @throws NullPointerException if direction is {@code null}
     * @throws NullPointerException if properties is {@code null}
     */
    public static SortManager by(final SortDirection direction, final String... properties) {
        Objects.requireNonNull(direction, "Sort direction should not be null!");
        Objects.requireNonNull(properties, "Properties should not be null!");
        assert properties.length > 0 : "At least one property must be given!";
        return SortManager.by(Arrays.stream(properties)
            .map(it -> new SortOrder(direction, it))
            .collect(Collectors.toList()));
    }

    /**
     * Returns a {@link SortManager} instances representing no sorting setup at all.
     *
     * @return sort stream instance {@link SortManager}
     */
    public static SortManager unsorted() {
        return UNSORTED;
    }

    /**
     * Returns a new {@link SortManager} with the current setup but descending order direction.
     *
     * @return sort stream instance {@link SortManager}
     */
    public SortManager descending() {
        return this.withDirection(SortDirection.DESC);
    }

    /**
     * Returns a new {@link SortManager} with the current setup but ascending order direction.
     *
     * @return sort stream instance {@link SortManager}
     */
    public SortManager ascending() {
        return this.withDirection(SortDirection.ASC);
    }

    /**
     * Returns binary flag by orders sorting
     *
     * @return true if orders sorted, false - otherwise
     */
    public boolean isSorted() {
        return !this.getOrders().isEmpty();
    }

    /**
     * Returns binary flag by orders sorting
     *
     * @return true if orders unsorted, false - otherwise
     */
    public boolean isUnsorted() {
        return !this.isSorted();
    }

    /**
     * Returns a new {@link SortManager} consisting of the {@link SortOrder}s of the current {@link SortManager} combined with the given
     * ones.
     *
     * @param sort must negate be {@literal null}.
     * @return sort stream instance {@link SortManager}
     * @throws NullPointerException if sort is {@code null}
     */
    public SortManager and(final SortManager sort) {
        Objects.requireNonNull(sort, "Sort should not be null!");
        final List<SortOrder> these = new ArrayList<>(getOrders());
        for (final SortOrder order : sort) {
            these.add(order);
        }
        return SortManager.by(these);
    }

    /**
     * Returns sort order instance {@link SortOrder} registered for the given property.
     *
     * @param property - initial input property {@link String}
     * @return sort order instance {@link SortOrder}
     */
    @Nullable
    public SortOrder getOrderFor(final String property) {
        for (final SortOrder order : this) {
            if (order.getProperty().equals(property)) {
                return order;
            }
        }
        return null;
    }

    /**
     * Returns sort order iterator instance {@link Iterator}
     *
     * @return sort order iterator instance {@link Iterator}
     */
    public Iterator<SortOrder> iterator() {
        return getOrders().iterator();
    }

    /**
     * Creates a new {@link SortManager} with the current setup but the given order direction.
     *
     * @param direction - initial input direction argument {@link SortDirection}
     * @return sort stream instance {@link SortManager}
     */
    private SortManager withDirection(final SortDirection direction) {
        return SortManager.by(getOrders().stream().map(it -> new SortOrder(direction, it.getProperty())).collect(Collectors.toList()));
    }

    /**
     * Default sort direction enumeration
     */
    @Getter
    @RequiredArgsConstructor
    public enum SortDirection {
        ASC(1),
        DESC(-1),
        EQ(0);

        /**
         * Default sort direction code
         */
        private final int code;

        /**
         * Returns direction {@link SortDirection} by input code code
         *
         * @param code - initial input code direction
         * @return direction {@link SortDirection}
         */
        @Nullable
        public static SortDirection fromCode(final int code) {
            return Arrays.stream(values())
                .filter(type -> type.getCode() == code)
                .findFirst()
                .orElse(null);
        }

        /**
         * Returns whether the direction is ascending {@code ASC}
         *
         * @return true - if direction is {@code ASC}, false - otherwise
         */
        public boolean isAscending() {
            return this.equals(ASC);
        }

        /**
         * Returns whether the direction is descending {@code DESC}
         *
         * @return true - if direction is {@code DESC}, false - otherwise
         */
        public boolean isDescending() {
            return this.equals(DESC);
        }

        /**
         * Returns whether the direction is descending {@code EQ}
         *
         * @return true - if direction is {@code EQ}, false - otherwise
         */
        public boolean isEqual() {
            return this.equals(EQ);
        }

        /**
         * Returns whether the direction is {@code EQ}
         *
         * @return true - if direction is {@code EQ}, false - otherwise
         */
        public static boolean isEqual(final int code) {
            return Objects.equals(fromCode(code), EQ);
        }

        /**
         * Returns the {@link SortDirection} enum for the given {@link String} code
         *
         * @param value - initial input string code to be converted
         * @return enum for the given {@link SortDirection} string code
         * @throws IllegalArgumentException in case the given code cannot be parsed into an enum code
         */
        public static SortDirection fromString(final String value) {
            return fromString(value, Locale.getDefault());
        }

        /**
         * Returns the {@link SortDirection} enum for the given {@link String} code and locale {@link Locale}
         *
         * @param value  - initial input string code to be converted
         * @param locale - initial input locale code {@link Locale}
         * @return enum for the given {@link SortDirection} string code
         * @throws IllegalArgumentException in case the given code cannot be parsed into an enum code
         */
        public static SortDirection fromString(final String value, final Locale locale) {
            try {
                return SortDirection.valueOf(value.toUpperCase(locale));
            } catch (Exception e) {
                throw new IllegalArgumentException(formatMessage("Invalid code '%s' for orders given! Has to be either 'desc' or 'asc' (case insensitive).", value), e);
            }
        }

        /**
         * Returns the {@link SortDirection} enum for the given {@link String} or null if it cannot be parsed into an enum
         * code.
         *
         * @param value - initial input string code to be converted
         * @return wrapped enum {@link SortDirection} for the given string code
         */
        @NonNull
        public static Optional<SortDirection> fromOptionalString(final String value) {
            try {
                return Optional.of(fromString(value));
            } catch (IllegalArgumentException e) {
                return Optional.empty();
            }
        }
    }

    /**
     * Enumeration for null priority hints that can be used in {@link SortOrder} expressions
     */
    public enum NullPriority {
        /**
         * Lets the data store decide what to do with nulls.
         */
        NATIVE,
        /**
         * A hint to the used data store to order entries with null values before non null entries.
         */
        NULLS_FIRST,
        /**
         * A hint to the used data store to order entries with null values after non null entries.
         */
        NULLS_LAST
    }

    /**
     * PropertyPath implements the pairing of an {@link SortDirection} and a property. It is used to provide input for
     * {@link SortManager}
     */
    @Data
    @EqualsAndHashCode
    @ToString
    public static class SortOrder implements Serializable {

        /**
         * Default explicit serialVersionUID for interoperability
         */
        private static final long serialVersionUID = 4258090812324252424L;

        /**
         * Default ignore case code {@link Boolean}
         */
        private static final boolean DEFAULT_IGNORE_CASE = false;
        /**
         * Default null priority code {@link NullPriority}
         */
        private static final NullPriority DEFAULT_NULL_HANDLING = NATIVE;

        /**
         * {@link SortDirection} instance
         */
        private final SortDirection direction;
        /**
         * Property name
         */
        private final String property;
        /**
         * Ignore case binary flag
         */
        private final boolean ignoreCase;
        /**
         * {@link NullPriority} instance
         */
        private final NullPriority nullPriority;

        /**
         * Creates a new {@link SortOrder} instance. if order is {@literal null} then order defaults to
         * {@link SortManager#DEFAULT_DIRECTION}
         *
         * @param direction can be {@literal null}, will default to {@link SortManager#DEFAULT_DIRECTION}
         * @param property  must negate be {@literal null} or empty.
         */
        public SortOrder(@Nullable final SortDirection direction, final String property) {
            this(direction, property, DEFAULT_IGNORE_CASE, DEFAULT_NULL_HANDLING);
        }

        /**
         * Creates a new {@link SortOrder} instance. if order is {@literal null} then order defaults to
         * {@link SortManager#DEFAULT_DIRECTION}
         *
         * @param direction    can be {@literal null}, will default to {@link SortManager#DEFAULT_DIRECTION}
         * @param property     must negate be {@literal null} or empty.
         * @param nullPriority must negate be {@literal null}.
         */
        public SortOrder(@Nullable final SortDirection direction, final String property, final NullPriority nullPriority) {
            this(direction, property, DEFAULT_IGNORE_CASE, nullPriority);
        }

        /**
         * Creates a new {@link SortOrder} instance. Takes a single property. SortDirection defaults to
         * {@link SortManager#DEFAULT_DIRECTION}.
         *
         * @param property must negate be {@literal null} or empty.
         */
        public SortOrder(final String property) {
            this(DEFAULT_DIRECTION, property);
        }

        /**
         * Creates a new {@link SortOrder} instance. Takes a single property. SortDirection defaults to
         * {@link SortManager#DEFAULT_DIRECTION}.
         *
         * @param property must negate be {@literal null} or empty.
         */
        @Factory
        public static SortOrder by(final String property) {
            return new SortOrder(DEFAULT_DIRECTION, property);
        }

        /**
         * Creates a new {@link SortOrder} instance. Takes a single property. SortDirection is {@link SortDirection#ASC} and
         * NullPriority {@link NullPriority#NATIVE}.
         *
         * @param property must negate be {@literal null} or empty.
         */
        @Factory
        public static SortOrder asc(final String property) {
            return new SortOrder(SortDirection.ASC, property, DEFAULT_NULL_HANDLING);
        }

        /**
         * Creates a new {@link SortOrder} instance. Takes a single property. SortDirection is {@link SortDirection#ASC} and
         * NullPriority {@link NullPriority#NATIVE}.
         *
         * @param property must negate be {@literal null} or empty.
         */
        @Factory
        public static SortOrder desc(final String property) {
            return new SortOrder(SortDirection.DESC, property, DEFAULT_NULL_HANDLING);
        }

        /**
         * Creates a new {@link SortOrder} instance. if order is {@literal null} then order defaults to
         * {@link SortManager#DEFAULT_DIRECTION}
         *
         * @param direction    can be {@literal null}, will default to {@link SortManager#DEFAULT_DIRECTION}
         * @param property     must negate be {@literal null} or empty.
         * @param ignoreCase   true if sorting should be case insensitive. false if sorting should be case sensitive.
         * @param nullPriority must negate be {@literal null}.
         */
        private SortOrder(@Nullable final SortDirection direction, final String property, boolean ignoreCase, final NullPriority nullPriority) {
            if (!isNotBlank(property)) {
                throw new IllegalArgumentException("Property must negate null or empty!");
            }
            this.direction = direction == null ? DEFAULT_DIRECTION : direction;
            this.property = property;
            this.ignoreCase = ignoreCase;
            this.nullPriority = nullPriority;
        }

        /**
         * Returns whether sorting for this property shall be ascending.
         *
         * @return true - if direction is ascending, false - otherwise
         */
        public boolean isAscending() {
            return getDirection().isAscending();
        }

        /**
         * Returns whether sorting for this property shall be descending.
         */
        public boolean isDescending() {
            return getDirection().isDescending();
        }

        /**
         * Returns a new {@link SortOrder} with the given {@link SortDirection}.
         *
         * @param direction - initial input sort direction {@link SortDirection}
         * @return sort order instance {@link SortOrder}
         */
        @NonNull
        public SortOrder with(final SortDirection direction) {
            return new SortOrder(direction, getProperty(), isIgnoreCase(), getNullPriority());
        }

        /**
         * Returns a new {@link SortOrder}
         *
         * @param property must negate be {@literal null} or empty.
         */
        @NonNull
        public SortOrder withProperty(final String property) {
            return new SortOrder(getDirection(), property, isIgnoreCase(), getNullPriority());
        }

        /**
         * Returns a new {@link SortManager} instance for the given properties.
         *
         * @param properties - initial input array of properties
         * @return sort stream instance {@link SortManager}
         */
        public SortManager withProperties(final String... properties) {
            return SortManager.by(getDirection(), properties);
        }

        /**
         * Returns a new {@link SortOrder} with case insensitive sorting enabled.
         *
         * @return sort order instance {@link SortOrder}
         */
        @NonNull
        public SortOrder ignoreCase() {
            return new SortOrder(getDirection(), getProperty(), true, getNullPriority());
        }

        /**
         * Returns a {@link SortOrder} with the given {@link NullPriority}.
         *
         * @param nullPriority can be {@literal null}.
         * @return sort order instance {@link SortOrder}
         */
        @NonNull
        public SortOrder with(final NullPriority nullPriority) {
            return new SortOrder(getDirection(), getProperty(), isIgnoreCase(), nullPriority);
        }

        /**
         * Returns a {@link SortOrder} with {@link NullPriority#NULLS_FIRST} as null handling hint.
         *
         * @return sort order instance {@link SortOrder}
         */
        public SortOrder nullsFirst() {
            return with(NullPriority.NULLS_FIRST);
        }

        /**
         * Returns a {@link SortOrder} with {@link NullPriority#NULLS_LAST} as null handling hint.
         *
         * @return sort order instance {@link SortOrder}
         */
        public SortOrder nullsLast() {
            return with(NullPriority.NULLS_LAST);
        }

        /**
         * Returns a {@link SortOrder} with {@link NullPriority#NATIVE} as null handling hint.
         *
         * @return sort order instance {@link SortOrder}
         */
        public SortOrder nullsNative() {
            return with(NullPriority.NATIVE);
        }
    }
}
