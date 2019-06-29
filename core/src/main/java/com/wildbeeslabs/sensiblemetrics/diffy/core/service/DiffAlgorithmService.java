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
package com.wildbeeslabs.sensiblemetrics.diffy.core.service;

import com.wildbeeslabs.sensiblemetrics.diffy.common.entry.iface.Chunk;
import com.wildbeeslabs.sensiblemetrics.diffy.common.entry.iface.Delta;
import com.wildbeeslabs.sensiblemetrics.diffy.common.entry.impl.DefaultChunk;
import com.wildbeeslabs.sensiblemetrics.diffy.common.entry.impl.DefaultPatch;
import com.wildbeeslabs.sensiblemetrics.diffy.common.utils.ValidationUtils;
import com.wildbeeslabs.sensiblemetrics.diffy.core.entry.delta.ChangeDelta;
import com.wildbeeslabs.sensiblemetrics.diffy.core.entry.delta.DeleteDelta;
import com.wildbeeslabs.sensiblemetrics.diffy.core.entry.delta.InsertDelta;
import com.wildbeeslabs.sensiblemetrics.diffy.core.entry.node.DiffNode;
import com.wildbeeslabs.sensiblemetrics.diffy.core.entry.node.PathNode;
import com.wildbeeslabs.sensiblemetrics.diffy.core.entry.node.SnakeNode;
import com.wildbeeslabs.sensiblemetrics.diffy.core.interfaces.DiffAlgorithm;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.interfaces.BiMatcher;

import java.util.List;
import java.util.Objects;

import static com.wildbeeslabs.sensiblemetrics.diffy.common.utils.ServiceUtils.copyOf;
import static com.wildbeeslabs.sensiblemetrics.diffy.common.utils.ServiceUtils.listOf;

/**
 * {@link DiffAlgorithm} service implementation
 *
 * @param <T> type of difference value
 */
public class DiffAlgorithmService<T> implements DiffAlgorithm<T> {

    /**
     * Default {@link BiMatcher}
     */
    private final BiMatcher<T> matcher;

    /**
     * Constructs an instance of the Myers differencing algorithm.
     */
    public DiffAlgorithmService() {
        this.matcher = Objects::equals;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Return empty diff if get the error while procession the difference.
     * @throws IllegalArgumentException if original is {@code null}
     * @throws IllegalArgumentException if revised is {@code null}
     */
    @Override
    public DefaultPatch<T> diff(final Iterable<T> original, final Iterable<T> revised) {
        ValidationUtils.notNull(original, "Original list must not be null");
        ValidationUtils.notNull(revised, "Revised list must not be null");

        try {
            final List<T> first = listOf(original);
            final List<T> last = listOf(revised);
            final PathNode path = this.buildPath(first, last);
            return this.buildRevision(path, first, last);
        } catch (IllegalStateException e) {
            return new DefaultPatch<>();
        }
    }

    /**
     * Computes the minimum diffpath that expresses de differences
     * between the original and revised sequences, according
     * to Gene Myers differencing algorithm.
     *
     * @param original The original sequence.
     * @param revised  The revised sequence.
     * @return A minimum {@link PathNode Path} across the differences graph.
     * @throws IllegalStateException if original is {@code null}
     * @throws IllegalArgumentException if revised is {@code null}
     */
    protected PathNode buildPath(final List<T> original, final List<T> revised) {
        ValidationUtils.notNull(original, "Original sequence should not be null");
        ValidationUtils.notNull(revised, "Revised sequence should not be null");

        final int N = original.size();
        final int M = revised.size();

        final int MAX = N + M + 1;
        final int size = 1 + 2 * MAX;
        final int middle = size / 2;
        final PathNode diagonal[] = new PathNode[size];

        diagonal[middle + 1] = new SnakeNode(0, -1, null);
        for (int d = 0; d < MAX; d++) {
            for (int k = -d; k <= d; k += 2) {
                final int kmiddle = middle + k;
                final int kplus = kmiddle + 1;
                final int kminus = kmiddle - 1;
                PathNode prev;

                int i;
                if ((k == -d) || (k != d && diagonal[kminus].origPos < diagonal[kplus].origPos)) {
                    i = diagonal[kplus].origPos;
                    prev = diagonal[kplus];
                } else {
                    i = diagonal[kminus].origPos + 1;
                    prev = diagonal[kminus];
                }

                diagonal[kminus] = null;
                int j = i - k;
                PathNode node = new DiffNode(i, j, prev);
                while (i < N && j < M && this.matcher.matches(original.get(i), revised.get(j))) {
                    i++;
                    j++;
                }
                if (i > node.origPos) node = new SnakeNode(i, j, node);
                diagonal[kmiddle] = node;
                if (i >= N && j >= M) return diagonal[kmiddle];
            }
            diagonal[middle + d - 1] = null;
        }
        throw new IllegalStateException("could not find a diff path");
    }

    /**
     * Constructs {@link DefaultPatch} from a difference path
     *
     * @param path     The path.
     * @param original The original sequence.
     * @param revised  The revised sequence.
     * @return A {@link DefaultPatch} script corresponding to the path.
     * @throws IllegalStateException if path is {@code null}
     * @throws IllegalStateException if original is {@code null}
     * @throws IllegalStateException if revised is {@code null}
     */
    protected DefaultPatch<T> buildRevision(final PathNode path, final List<T> original, final List<T> revised) {
        ValidationUtils.notNull(path, "Path node should not be null");
        ValidationUtils.notNull(original, "Original sequence should not be null");
        ValidationUtils.notNull(revised, "Revised sequence should not be null");

        PathNode root = path;
        final DefaultPatch<T> patch = new DefaultPatch<>();
        if (root.isSnake()) {
            root = root.prev;
        }
        while (root != null && root.prev != null && root.prev.revPos >= 0) {
            if (root.isSnake()) {
                throw new IllegalStateException("ERROR: found snake when looking for diff");
            }
            int i = root.origPos;
            int j = root.revPos;

            root = root.prev;
            int ianchor = root.origPos;
            int janchor = root.revPos;

            final Chunk<T> orig = new DefaultChunk<>(ianchor, copyOf(original, ianchor, i));
            final Chunk<T> rev = new DefaultChunk<>(janchor, copyOf(revised, janchor, j));
            Delta<T> delta;
            if (orig.size() == 0 && rev.size() != 0) {
                delta = new InsertDelta<>(orig, rev);
            } else if (orig.size() > 0 && rev.size() == 0) {
                delta = new DeleteDelta<>(orig, rev);
            } else {
                delta = new ChangeDelta<>(orig, rev);
            }

            patch.addDelta(delta);
            if (root.isSnake()) {
                root = root.prev;
            }
        }
        return patch;
    }
}
