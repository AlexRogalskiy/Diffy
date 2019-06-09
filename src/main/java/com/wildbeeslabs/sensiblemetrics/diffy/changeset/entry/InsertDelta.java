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
package com.wildbeeslabs.sensiblemetrics.diffy.changeset.entry;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.wildbeeslabs.sensiblemetrics.diffy.common.entry.iface.Chunk;
import com.wildbeeslabs.sensiblemetrics.diffy.common.entry.iface.Delta;
import com.wildbeeslabs.sensiblemetrics.diffy.common.entry.impl.DefaultDelta;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

import static com.google.common.base.Preconditions.checkState;

/**
 * Default insert {@link DefaultDelta} implementation
 *
 * @param <T> type of delta value
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class InsertDelta<T> extends DefaultDelta<T> {

    /**
     * Creates an insert delta with the two given chunks.
     *
     * @param original The original chunk. Must not be {@code null}.
     * @param revised  The original chunk. Must not be {@code null}.
     */
    public InsertDelta(final Chunk<T> original, final Chunk<T> revised) {
        super(original, revised);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void applyTo(final List<T> target) {
        verify(target);
        int position = this.getOriginal().getPosition();
        final List<T> lines = this.getRevised().getLines();
        for (int i = 0; i < lines.size(); i++) {
            target.add(position + i, lines.get(i));
        }
    }

    @Override
    public void verify(final List<T> target) throws IllegalStateException {
        checkState(getOriginal().getPosition() <= target.size(), "Incorrect patch for delta: delta original position > target size");
    }

    @Override
    public TYPE getType() {
        return Delta.TYPE.INSERT;
    }
}
