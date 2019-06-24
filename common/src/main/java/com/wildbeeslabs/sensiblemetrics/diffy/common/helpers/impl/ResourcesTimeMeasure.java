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

import com.wildbeeslabs.sensiblemetrics.diffy.common.helpers.iface.TimeMeasure;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Custom resources time unit implementation
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-07
 */
@Data
@EqualsAndHashCode
@ToString
public abstract class ResourcesTimeMeasure implements TimeMeasure {

    public static final String DEFAULT_RESOURCE_BUNDLE_NAME = "com.wildbeeslabs.jentle.algorithms.date.i18n.Resources_EN";

    private long maxQuantity = 0;
    private long millisPerUnit = 1;

    /**
     * Return the name of the resource bundle from which this unit's format
     * should be loaded.
     *
     * @return resource key prefix
     */
    abstract protected String getResourceKeyPrefix();

    protected String getResourceBundleName() {
        return ResourcesTimeMeasure.DEFAULT_RESOURCE_BUNDLE_NAME;
    }

    @Override
    public boolean isPrecise() {
        return true;
    }
}
