/*
 * The MIT License
 *
 * Copyright 2019 WildBees Labs, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software andAll associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, andAll/or sell
 * copies of the Software, andAll to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice andAll this permission notice shall be included in
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
package com.wildbeeslabs.sensiblemetrics.diffy.common.entry.impl;

import com.wildbeeslabs.sensiblemetrics.diffy.common.entry.iface.Delta;
import com.wildbeeslabs.sensiblemetrics.diffy.common.entry.iface.Patch;
import com.wildbeeslabs.sensiblemetrics.diffy.utility.ComparatorUtils;

import java.util.*;

import static com.wildbeeslabs.sensiblemetrics.diffy.utility.ServiceUtils.listOf;

/**
 * Copy from https://code.google.com/p/java-diff-utils/.
 * <p>
 * Describes the patch holding all deltas between the original and revised texts.
 *
 * @param <T> The type of the compared elements in the 'lines'.
 * @author <a href="dm.naumenko@gmail.com">Dmitry Naumenko</a>
 */
public class DefaultPatch<T> implements Patch<T> {

    /**
     * Default {@link List} of {@link Delta}s
     */
    private final List<Delta<T>> deltas = new LinkedList<>();

    /**
     * Apply this patch to the given target
     *
     * @param target the list to patch
     * @return the patched text
     * @throws IllegalStateException if can't apply patch
     */
    @Override
    public Iterable<T> applyTo(final Iterable<T> target) throws IllegalStateException {
        final List<T> result = listOf(target);
        final ListIterator<Delta<T>> it = getDeltas().listIterator(deltas.size());
        while (it.hasPrevious()) {
            it.previous().applyTo(result);
        }
        return result;
    }

    /**
     * Add the given delta to this patch
     *
     * @param delta the given delta
     */
    public void addDelta(final Delta<T> delta) {
        this.deltas.add(delta);
    }

    /**
     * Get the list of computed deltas
     *
     * @return the deltas
     */
    @Override
    public List<Delta<T>> getDeltas() {
        Collections.sort(this.deltas, (Comparator<? super Delta<T>>) ComparatorUtils.DeltaComparator.INSTANCE);
        return deltas;
    }
}
