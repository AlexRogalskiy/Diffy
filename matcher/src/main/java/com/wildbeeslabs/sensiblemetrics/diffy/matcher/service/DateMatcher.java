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
package com.wildbeeslabs.sensiblemetrics.diffy.matcher.service;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;
import java.util.Objects;

/**
 * Date {@link AbstractMatcher} implementation
 *
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuppressWarnings("unchecked")
public final class DateMatcher extends AbstractMatcher<Date> {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 4257752341048054337L;

    /**
     * Default date interval
     */
    private final int interval;

    /**
     * Default date matcher constructor
     *
     * @param interval - initial input number of seconds before of current time
     */
    public DateMatcher(int interval) {
        this.interval = interval;
    }

    /**
     * Returns binary flag by initial argument {@link Date} match comparison
     *
     * @param date - initial input {@link Date} value to be matched
     * @return true - if initial value matches input {@link Date}, false - otherwise
     */
    @Override
    public boolean matches(final Date date) {
        if (Objects.isNull(date)) {
            return false;
        }
        return date.getTime() <= System.currentTimeMillis() - ((long) interval * 1000);
    }
}
