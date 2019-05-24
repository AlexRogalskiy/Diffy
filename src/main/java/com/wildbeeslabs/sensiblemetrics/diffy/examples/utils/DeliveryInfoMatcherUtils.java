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
package com.wildbeeslabs.sensiblemetrics.diffy.examples.utils;

import com.wildbeeslabs.sensiblemetrics.diffy.examples.model.DeliveryInfo;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.iface.Matcher;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.iface.TypeSafeMatcher;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.impl.AbstractFieldMatcher;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.impl.AbstractMatcher;
import lombok.experimental.UtilityClass;

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
@UtilityClass
public class DeliveryInfoMatcherUtils {

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

    public static Matcher<DeliveryInfo> withType(final Integer type) {
        return (Matcher<DeliveryInfo>) DELIVERY_TYPE_MATCHER.apply(type);
    }

    public Matcher<DeliveryInfo> withGid(final String gid) {
        return (Matcher<DeliveryInfo>) DELIVERY_GID_MATCHER.apply(gid);
    }

    public Matcher<DeliveryInfo> withCreatedDate(final Date createdDate) {
        return (Matcher<DeliveryInfo>) DELIVERY_CREATED_DATE_MATCHER.apply(createdDate);
    }

    public Matcher<DeliveryInfo> withUpdatedDate(final Date createdDate) {
        return (Matcher<DeliveryInfo>) DELIVERY_UPDATED_DATE_MATCHER.apply(createdDate);
    }

    public Matcher<DeliveryInfo> withIdMatcher(final Matcher<? super Long> matcher) {
        return (Matcher<DeliveryInfo>) FIELD_ID_MATCHER.apply(matcher);
    }

    public Matcher<DeliveryInfo> withCreatedDateMatcher(final Matcher<? super Date> matcher) {
        return (Matcher<DeliveryInfo>) FIELD_CREATED_DATE_MATCHER.apply(matcher);
    }

    public Matcher<DeliveryInfo> withUpdatedDateMatcher(final Matcher<? super Date> matcher) {
        return (Matcher<DeliveryInfo>) FIELD_UPDATED_DATE_MATCHER.apply(matcher);
    }

    public Matcher<DeliveryInfo> withTypeMatcher(final Matcher<? super Integer> matcher) {
        return (Matcher<DeliveryInfo>) FIELD_TYPE_MATCHER.apply(matcher);
    }

    public Matcher<DeliveryInfo> withGidMatcher(final Matcher<? super String> matcher) {
        return (Matcher<DeliveryInfo>) FIELD_GID_MATCHER.apply(matcher);
    }
}
