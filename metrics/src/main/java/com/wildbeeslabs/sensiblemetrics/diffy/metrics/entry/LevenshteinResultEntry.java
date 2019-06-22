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

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Levenshtein results entry
 */
@Data
@Builder
@EqualsAndHashCode
@ToString
public class LevenshteinResultEntry {
    /**
     * Edit distance.
     */
    private final Integer distance;
    /**
     * Insert character count.
     */
    private final Integer insertCount;
    /**
     * Delete character count.
     */
    private final Integer deleteCount;
    /**
     * Substitute character count.
     */
    private final Integer substituteCount;

    /**
     * Create the results for a detailed Levenshtein distance.
     *
     * @param distance        distance between two character sequences.
     * @param insertCount     insert character count
     * @param deleteCount     delete character count
     * @param substituteCount substitute character count
     */
    public LevenshteinResultEntry(final Integer distance, final Integer insertCount, final Integer deleteCount, final Integer substituteCount) {
        this.distance = distance;
        this.insertCount = insertCount;
        this.deleteCount = deleteCount;
        this.substituteCount = substituteCount;
    }

    public static final LevenshteinResultEntry of(final Integer distance, final Integer insertCount, final Integer deleteCount, final Integer substituteCount) {
        return LevenshteinResultEntry.builder()
            .distance(distance)
            .insertCount(insertCount)
            .deleteCount(deleteCount)
            .substituteCount(substituteCount)
            .build();
    }
}
