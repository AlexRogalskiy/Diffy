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
package com.wildbeeslabs.sensiblemetrics.diffy.common.entry.impl.node;

import com.google.common.base.Joiner;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Objects;

import static com.wildbeeslabs.sensiblemetrics.diffy.utility.StringUtils.wrapInBraces;

/**
 * Path node implementation
 *
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@Data
@EqualsAndHashCode
public abstract class PathNode {

    /**
     * Position in the original sequence.
     */
    public final int i;
    /**
     * Position in the revised sequence.
     */
    public final int j;
    /**
     * The previous node in the path.
     */
    public final PathNode prev;

    /**
     * Concatenates a new path node with an existing diffpath.
     *
     * @param i    The position in the original sequence for the new node.
     * @param j    The position in the revised sequence for the new node.
     * @param prev The previous node in the path.
     */
    public PathNode(int i, int j, final PathNode prev) {
        this.i = i;
        this.j = j;
        this.prev = prev;
    }

    /**
     * Is this node a {@link SnakeNode SnakeNode node}?
     *
     * @return true if this is a {@link SnakeNode SnakeNode node}
     */
    public abstract boolean isSnake();

    /**
     * Is this a bootstrap node?
     * <p>
     * In bootstrap nodes one of the two coordinates is
     * less than zero.
     *
     * @return tru if this is a bootstrap node.
     */
    public boolean isBootstrap() {
        return i < 0 || j < 0;
    }

    /**
     * Skips sequences of {@link DiffNode DiffNodes} until a
     * {@link SnakeNode} or bootstrap node is found, or the end
     * of the path is reached.
     *
     * @return The next first {@link SnakeNode} or bootstrap node in the path, or
     * <code>null</code>
     * if none found.
     */
    public final PathNode previousSnake() {
        if (this.isBootstrap()) {
            return null;
        }
        if (!this.isSnake() && Objects.nonNull(this.prev)) {
            return this.prev.previousSnake();
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public String toString() {
        final StringBuilder buf = new StringBuilder("[");
        PathNode node = this;
        while (Objects.nonNull(node)) {
            buf.append(wrapInBraces.apply(Joiner.on(",").join(node.i, node.j)));
            node = node.prev;
        }
        buf.append("]");
        return buf.toString();
    }
}
