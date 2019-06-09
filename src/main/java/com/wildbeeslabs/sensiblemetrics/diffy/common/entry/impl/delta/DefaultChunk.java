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
package com.wildbeeslabs.sensiblemetrics.diffy.common.entry.impl.delta;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.wildbeeslabs.sensiblemetrics.diffy.common.entry.iface.Chunk;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

import static com.google.common.base.Preconditions.checkState;

/**
 * Default {@link Chunk} implementation
 *
 * @param <T> type of delta value
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@Data
@EqualsAndHashCode
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DefaultChunk<T> implements Chunk<T> {

    /**
     * Default chunk position
     */
    private final int position;
    /**
     * Default {@link List} chunk lines
     */
    private List<T> lines;

    /**
     * Creates a chunk and saves a copy of affected lines
     *
     * @param position the start position
     * @param lines    the affected lines
     */
    public DefaultChunk(int position, final List<T> lines) {
        this.position = position;
        this.lines = lines;
    }

    /**
     * Verifies that this chunk's saved text matches the corresponding text in
     * the given sequence.
     *
     * @param target the sequence to verify against.
     */
    @Override
    public void verify(final List<T> target) throws IllegalStateException {
        checkState(last() <= target.size(), "Incorrect DefaultChunk: the position of chunk > target size");
        for (int i = 0; i < size(); i++) {
            checkState(target.get(this.position + i).equals(this.lines.get(i)), "Incorrect DefaultChunk: the chunk content doesn't match the target");
        }
    }

    /**
     * Return the chunk size
     *
     * @return the chunk size
     */
    @Override
    public int size() {
        return this.lines.size();
    }

    /**
     * Returns the index of the last line of the chunk.
     *
     * @return the index of the last line of the chunk.
     */
    @Override
    public int last() {
        return this.getPosition() + this.size() - 1;
    }
}
