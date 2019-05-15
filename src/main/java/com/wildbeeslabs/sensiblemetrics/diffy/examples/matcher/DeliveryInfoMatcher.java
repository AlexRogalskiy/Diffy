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
package com.wildbeeslabs.sensiblemetrics.diffy.examples.matcher;

import com.wildbeeslabs.sensiblemetrics.diffy.examples.model.DeliveryInfo;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.iface.Matcher;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.impl.AbstractFieldMatcher;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.impl.AbstractMatcher;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.impl.AbstractTypeSafeMatcher;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.impl.InstanceMatcher;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Objects;
import java.util.function.Function;

/**
 * Custom {@link DeliveryInfo} {@link AbstractMatcher} implementation
 *
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DeliveryInfoMatcher extends AbstractMatcher<DeliveryInfo> {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 5301800742465906044L;

    /**
     * Determines delivery info "Type" field matcher {@link Matcher}
     */
    public static final Function<Integer, Matcher<? super DeliveryInfo>> DELIVERY_TYPE_MATCHER = type -> new AbstractTypeSafeMatcher<>() {
        @Override
        public boolean matchesSafe(final DeliveryInfo value) {
            return Objects.equals(type, value.getType());
        }
    };

    /**
     * Determines delivery info "Gid" field matcher {@link Matcher}
     */
    public static final Function<String, Matcher<? super DeliveryInfo>> DELIVERY_GID_MATCHER = gid -> new AbstractTypeSafeMatcher<>() {
        @Override
        public boolean matchesSafe(final DeliveryInfo value) {
            return Objects.equals(gid, value.getGid());
        }
    };

    /**
     * Determines delivery info "Created date" matcher {@link Matcher}
     */
    public static final Function<Date, Matcher<? super DeliveryInfo>> DELIVERY_CREATED_DATE_MATCHER = date -> new AbstractTypeSafeMatcher<>() {
        @Override
        public boolean matchesSafe(final DeliveryInfo value) {
            return Objects.equals(date, value.getCreatedAt());
        }
    };

    /**
     * Determines delivery info "Updated date" matcher {@link Matcher}
     */
    public static final Function<Date, Matcher<? super DeliveryInfo>> DELIVERY_UPDATED_DATE_MATCHER = date -> new AbstractTypeSafeMatcher<>() {
        @Override
        public boolean matchesSafe(final DeliveryInfo value) {
            return Objects.equals(date, value.getUpdatedAt());
        }
    };

    /**
     * Determines delivery info "ID" matcher {@link Matcher}
     */
    public static final Function<Matcher<? super Long>, Matcher<? super DeliveryInfo>> FIELD_ID_MATCHER = matcher -> new AbstractFieldMatcher<DeliveryInfo, Long>(matcher) {
        @Override
        protected Long valueOf(final DeliveryInfo value) {
            return value.getId();
        }
    };

    /**
     * Determines delivery info "Created date" matcher {@link Matcher}
     */
    public static final Function<Matcher<? super Date>, Matcher<? super DeliveryInfo>> FIELD_CREATED_DATE_MATCHER = matcher -> new AbstractFieldMatcher<DeliveryInfo, Date>(matcher) {
        @Override
        protected Date valueOf(final DeliveryInfo value) {
            return value.getCreatedAt();
        }
    };

    /**
     * Determines delivery info "Updated date" matcher {@link Matcher}
     */
    public static final Function<Matcher<? super Date>, Matcher<? super DeliveryInfo>> FIELD_UPDATED_DATE_MATCHER = matcher -> new AbstractFieldMatcher<DeliveryInfo, Date>(matcher) {
        @Override
        protected Date valueOf(final DeliveryInfo value) {
            return value.getUpdatedAt();
        }
    };

    /**
     * Determines delivery info "Type" field matcher {@link Matcher}
     */
    public static final Function<Matcher<? super Integer>, Matcher<? super DeliveryInfo>> FIELD_TYPE_MATCHER = matcher -> new AbstractFieldMatcher<DeliveryInfo, Integer>(matcher) {
        @Override
        protected Integer valueOf(final DeliveryInfo value) {
            return value.getType();
        }
    };

    /**
     * Determines delivery info "Gid" field matcher {@link Matcher}
     */
    public static final Function<Matcher<? super String>, Matcher<? super DeliveryInfo>> FIELD_GID_MATCHER = matcher -> new AbstractFieldMatcher<DeliveryInfo, String>(matcher) {
        @Override
        protected String valueOf(final DeliveryInfo value) {
            return value.getGid();
        }
    };

    /**
     * Returns binary flag depending on initial argument value by comparison {@link DeliveryInfo}
     *
     * @param value - initial input value {@link DeliveryInfo}
     * @return true - if input value matches, false - otherwise
     */
    @Override
    public boolean matches(final DeliveryInfo value) {
        return this.getMatchers().stream().allMatch(matcher -> matcher.matches(value));
    }

    /**
     * Default private delivery info constructor
     */
    private DeliveryInfoMatcher() {
        this.withMatcher(InstanceMatcher.getMatcher(DeliveryInfo.class));
    }

    public DeliveryInfoMatcher withType(final Integer type) {
        this.getMatchers().add(DELIVERY_TYPE_MATCHER.apply(type));
        return this;
    }

    public DeliveryInfoMatcher withGid(final String gid) {
        this.getMatchers().add(DELIVERY_GID_MATCHER.apply(gid));
        return this;
    }

    public DeliveryInfoMatcher withCreatedDate(final Date createdDate) {
        this.getMatchers().add(DELIVERY_CREATED_DATE_MATCHER.apply(createdDate));
        return this;
    }

    public DeliveryInfoMatcher withUpdatedDate(final Date createdDate) {
        this.getMatchers().add(DELIVERY_UPDATED_DATE_MATCHER.apply(createdDate));
        return this;
    }

    public DeliveryInfoMatcher withIdMatcher(final Matcher<? super Long> matcher) {
        this.getMatchers().add(FIELD_ID_MATCHER.apply(matcher));
        return this;
    }

    public DeliveryInfoMatcher withCreatedDateMatcher(final Matcher<? super Date> matcher) {
        this.getMatchers().add(FIELD_CREATED_DATE_MATCHER.apply(matcher));
        return this;
    }

    public DeliveryInfoMatcher withUpdatedDateMatcher(final Matcher<? super Date> matcher) {
        this.getMatchers().add(FIELD_UPDATED_DATE_MATCHER.apply(matcher));
        return this;
    }

    public DeliveryInfoMatcher withTypeMatcher(final Matcher<? super Integer> matcher) {
        this.getMatchers().add(FIELD_TYPE_MATCHER.apply(matcher));
        return this;
    }

    public DeliveryInfoMatcher withGidMatcher(final Matcher<? super String> matcher) {
        this.getMatchers().add(FIELD_GID_MATCHER.apply(matcher));
        return this;
    }

    /**
     * Returns {@link DeliveryInfoMatcher}
     *
     * @return {@link DeliveryInfoMatcher}
     */
    public static DeliveryInfoMatcher of() {
        return new DeliveryInfoMatcher();
    }
}
