package com.wildbeeslabs.sensiblemetrics.diffy.metrics.common;

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
