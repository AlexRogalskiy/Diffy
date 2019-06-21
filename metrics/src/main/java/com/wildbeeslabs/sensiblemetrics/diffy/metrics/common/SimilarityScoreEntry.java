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
package com.wildbeeslabs.sensiblemetrics.diffy.metrics.common;

import com.wildbeeslabs.sensiblemetrics.diffy.metrics.iface.SimilarityScore;
import com.wildbeeslabs.sensiblemetrics.diffy.utility.ValidationUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Objects;

@Data
@EqualsAndHashCode
@ToString
public class SimilarityScoreEntry<T, R> {

    /**
     * Similarity score.
     */
    private final SimilarityScore<T, R> similarityScore;
    /**
     * Left parameter used in distance function.
     */
    private final T left;

    /**
     * <p>This accepts the similarity score implementation and the "left" string.</p>
     *
     * @param similarityScore This may not be null.
     * @param left            This may be null here,
     *                        but the SimilarityScore#compare(CharSequence left, CharSequence right)
     *                        implementation may not accept nulls.
     */
    public SimilarityScoreEntry(final SimilarityScore<T, R> similarityScore, final T left) {
        ValidationUtils.notNull(similarityScore, "Similarity score should not be null");
        this.similarityScore = similarityScore;
        this.left = left;
    }

    /**
     * <p>
     * This compares "left" field against the "right" parameter
     * using the "similarity score" implementation.
     * </p>
     *
     * @param right the second CharSequence
     * @return the similarity score between two CharSequences
     */
    public R apply(final T right) {
        return this.similarityScore.apply(left, right);
    }
}
