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

import com.wildbeeslabs.sensiblemetrics.diffy.common.annotation.Factory;
import com.wildbeeslabs.sensiblemetrics.diffy.examples.model.DeliveryInfo;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.interfaces.Matcher;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.interfaces.TypeSafeMatcher;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.service.AbstractFieldMatcher;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.service.DefaultDiffMatcher;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.service.InstanceMatcher;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Objects;
import java.util.function.Function;

/**
 * Custom {@link DeliveryInfo} {@link DefaultDiffMatcher} implementation
 *
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuppressWarnings("unchecked")
public class DeliveryInfoDiffMatcher extends DefaultDiffMatcher<DeliveryInfo> {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 5301800742465906044L;

    /**
     * Default matcher factory implementation
     */
    public static class MatcherFactory {
        /**
         * Determines delivery info "Type" field matcher {@link Matcher}
         */
        public static final Function<Integer, TypeSafeMatcher<? super DeliveryInfo>> DELIVERY_TYPE_MATCHER = type -> value -> Objects.equals(type, value.getType());

        /**
         * Determines delivery info "Gid" field matcher {@link Matcher}
         */
        public static final Function<String, TypeSafeMatcher<? super DeliveryInfo>> DELIVERY_GID_MATCHER = gid -> value -> Objects.equals(gid, value.getGid());

        /**
         * Determines delivery info "Created date" matcher {@link Matcher}
         */
        public static final Function<Date, TypeSafeMatcher<? super DeliveryInfo>> DELIVERY_CREATED_DATE_MATCHER = date -> value -> Objects.equals(date, value.getCreatedAt());

        /**
         * Determines delivery info "Updated date" matcher {@link Matcher}
         */
        public static final Function<Date, TypeSafeMatcher<? super DeliveryInfo>> DELIVERY_UPDATED_DATE_MATCHER = date -> value -> Objects.equals(date, value.getUpdatedAt());

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
         * Determines delivery info "null" field matcher {@link Matcher}
         */
        public static final Function<Matcher<? super String>, Matcher<? super DeliveryInfo>> NULL_MATCHER = matcher -> null;
    }

    /**
     * Default private delivery info constructor
     */
    private DeliveryInfoDiffMatcher() {
        this.include(InstanceMatcher.getMatcher(DeliveryInfo.class));
    }

    public DeliveryInfoDiffMatcher withType(final Integer type) {
        this.include(MatcherFactory.DELIVERY_TYPE_MATCHER.apply(type));
        return this;
    }

    public DeliveryInfoDiffMatcher withGid(final String gid) {
        this.include(MatcherFactory.DELIVERY_GID_MATCHER.apply(gid));
        return this;
    }

    public DeliveryInfoDiffMatcher withCreatedDate(final Date createdDate) {
        this.include(MatcherFactory.DELIVERY_CREATED_DATE_MATCHER.apply(createdDate));
        return this;
    }

    public DeliveryInfoDiffMatcher withUpdatedDate(final Date createdDate) {
        this.include(MatcherFactory.DELIVERY_UPDATED_DATE_MATCHER.apply(createdDate));
        return this;
    }

    public DeliveryInfoDiffMatcher withIdMatcher(final Matcher<? super Long> matcher) {
        this.include(MatcherFactory.FIELD_ID_MATCHER.apply(matcher));
        return this;
    }

    public DeliveryInfoDiffMatcher withCreatedDateMatcher(final Matcher<? super Date> matcher) {
        this.include(MatcherFactory.FIELD_CREATED_DATE_MATCHER.apply(matcher));
        return this;
    }

    public DeliveryInfoDiffMatcher withUpdatedDateMatcher(final Matcher<? super Date> matcher) {
        this.include(MatcherFactory.FIELD_UPDATED_DATE_MATCHER.apply(matcher));
        return this;
    }

    public DeliveryInfoDiffMatcher withTypeMatcher(final Matcher<? super Integer> matcher) {
        this.include(MatcherFactory.FIELD_TYPE_MATCHER.apply(matcher));
        return this;
    }

    public DeliveryInfoDiffMatcher withGidMatcher(final Matcher<? super String> matcher) {
        this.include(MatcherFactory.FIELD_GID_MATCHER.apply(matcher));
        return this;
    }

    /**
     * Returns {@link DeliveryInfoDiffMatcher}
     *
     * @return {@link DeliveryInfoDiffMatcher}
     */
    @Factory
    public static DeliveryInfoDiffMatcher of() {
        return new DeliveryInfoDiffMatcher();
    }
}
