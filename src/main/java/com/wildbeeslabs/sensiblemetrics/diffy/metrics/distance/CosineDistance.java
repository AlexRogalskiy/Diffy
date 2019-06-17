package com.wildbeeslabs.sensiblemetrics.diffy.metrics.distance;

import com.wildbeeslabs.sensiblemetrics.diffy.common.utility.impl.DefaultCounter;
import com.wildbeeslabs.sensiblemetrics.diffy.metrics.common.RegexTokenizer;
import com.wildbeeslabs.sensiblemetrics.diffy.metrics.iface.SimilarityDistance;
import com.wildbeeslabs.sensiblemetrics.diffy.metrics.iface.Tokenizer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.text.similarity.CosineSimilarity;

import java.util.Map;

/**
 * Cosine {@link SimilarityDistance} implementation
 */
@Data
@EqualsAndHashCode
@ToString
public class CosineDistance implements SimilarityDistance<CharSequence, Double> {
    /**
     * Tokenizer used to convert the character sequence into a vector.
     */
    private final Tokenizer<CharSequence, CharSequence> tokenizer = new RegexTokenizer();
    /**
     * Cosine similarity.
     */
    private final CosineSimilarity cosineSimilarity = new CosineSimilarity();

    @Override
    public Double apply(final CharSequence left, final CharSequence right) {
        final CharSequence[] leftTokens = this.tokenizer.tokenize(left);
        final CharSequence[] rightTokens = this.tokenizer.tokenize(right);

        final Map<CharSequence, Integer> leftVector = DefaultCounter.of(leftTokens);
        final Map<CharSequence, Integer> rightVector = DefaultCounter.of(rightTokens);
        final double similarity = this.cosineSimilarity.cosineSimilarity(leftVector, rightVector);
        return 1.0 - similarity;
    }
}
