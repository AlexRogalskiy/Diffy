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
package com.wildbeeslabs.sensiblemetrics.diffy.examples.comparator;

import com.wildbeeslabs.sensiblemetrics.diffy.comparator.impl.DefaultDiffComparator;
import com.wildbeeslabs.sensiblemetrics.diffy.examples.model.DeliveryInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;

/**
 * Custom difference comparator implementation by delivery info instance {@link DeliveryInfo}
 *
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DeliveryInfoDiffComparator extends DefaultDiffComparator<DeliveryInfo> {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 436117742331557518L;

    /**
     * Creates delivery information difference comparator
     */
    public DeliveryInfoDiffComparator() {
        super(DeliveryInfo.class);
    }

    /**
     * Creates delivery information difference comparator with comparator instance {@link Comparator}
     *
     * @param comparator - initial comparator instance {@link Comparator}
     */
    public DeliveryInfoDiffComparator(final Comparator<? super DeliveryInfo> comparator) {
        super(DeliveryInfo.class, comparator);
    }
}
