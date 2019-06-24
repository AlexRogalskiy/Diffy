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
package com.wildbeeslabs.sensiblemetrics.diffy.common.executor.factory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.ThreadFactory;

import static org.apache.commons.lang3.StringUtils.join;

/**
 * Default {@link ThreadFactory} implementation
 */
public class DefaultThreadFactory implements ThreadFactory {
    private int counter;
    private final String name;
    private final Collection<String> stats;

    /**
     * Default thread factory constructor
     *
     * @param name - initial input thread name {@link String}
     */
    public DefaultThreadFactory(final String name) {
        this.counter = 1;
        this.name = name;
        this.stats = new ArrayList<>();
    }

    /**
     * Returns new {@link Thread} by input {@link Runnable}
     *
     * @param runnable - initial input {@link Runnable}
     * @return new {@link Thread}
     */
    @Override
    public Thread newThread(final Runnable runnable) {
        final Thread thread = new Thread(runnable, join(this.name, "-Thread-", this.counter));
        this.counter++;
        this.stats.add(String.format("Created thread {%d} with name = {%s} on {%s} \n", thread.getId(), thread.getName(), new Date()));
        return thread;
    }

    /**
     * Returns thread statistics {@link String}
     *
     * @return thread statistics {@link String}
     */
    public String getStats() {
        final StringBuffer buffer = new StringBuffer();
        final Iterator<String> it = this.stats.iterator();
        while (it.hasNext()) {
            buffer.append(it.next());
        }
        return buffer.toString();
    }
}
