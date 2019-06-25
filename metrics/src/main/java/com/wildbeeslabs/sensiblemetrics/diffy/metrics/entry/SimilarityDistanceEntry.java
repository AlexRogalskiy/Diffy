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
package com.wildbeeslabs.sensiblemetrics.diffy.metrics.entry;

import com.wildbeeslabs.sensiblemetrics.diffy.common.utils.ValidationUtils;
import com.wildbeeslabs.sensiblemetrics.diffy.metrics.interfaces.SimilarityDistance;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Similarity distance entry
 *
 * @param <T> type of left item
 * @param <R> type of right item
 */
@Data
@EqualsAndHashCode
@ToString
public class SimilarityDistanceEntry<T, R> {
    /**
     * Default edit distance
     */
    private final SimilarityDistance<T, R> similarityDistance;
    /**
     * Default left parameter
     */
    private final T left;

    /**
     * <p>This accepts the edit distance implementation and the "left" string.</p>
     *
     * @param similarityDistance This may not be null.
     * @param left               This may be null here,
     *                           but the EditDistance#compare(CharSequence left, CharSequence right)
     *                           implementation may not accept nulls.
     */
    public SimilarityDistanceEntry(final SimilarityDistance<T, R> similarityDistance, final T left) {
        ValidationUtils.notNull(similarityDistance, "Similarity distance should not be null");
        this.similarityDistance = similarityDistance;
        this.left = left;
    }

    /**
     * <p>
     * This compares "left" field against the "right" parameter
     * using the "edit distance" implementation.
     * </p>
     *
     * @param right the second CharSequence
     * @return the similarity score between two CharSequences
     */
    public R apply(final T right) {
        return this.similarityDistance.apply(this.left, right);
    }
}
