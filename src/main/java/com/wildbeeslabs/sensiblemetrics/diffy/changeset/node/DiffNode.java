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
package com.wildbeeslabs.sensiblemetrics.diffy.changeset.node;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Objects;

/**
 * Diff {@link PathNode} implementation
 *
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class DiffNode extends PathNode {

    /**
     * Constructs a DiffNode.
     * <p>
     * DiffNodes are compressed. That means that
     * the path pointed to by the <code>prev</code> parameter
     * will be followed using {@link PathNode#previousSnake}
     * until a non-diff node is found.
     *
     * @param origPos the position in the original sequence
     * @param revPos  the position in the revised sequence
     * @param prev    the previous node in the path.
     */
    public DiffNode(int origPos, int revPos, final PathNode prev) {
        super(origPos, revPos, (Objects.isNull(prev) ? null : prev.previousSnake()));
    }

    /**
     * {@inheritDoc}
     *
     * @return false, always
     */
    @Override
    public boolean isSnake() {
        return false;
    }
}
