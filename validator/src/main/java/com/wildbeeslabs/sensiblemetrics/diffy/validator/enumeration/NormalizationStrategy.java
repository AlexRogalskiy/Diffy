package com.wildbeeslabs.sensiblemetrics.diffy.validator.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.text.Normalizer;

/**
 * Strategy for normalization.
 *
 * @see Normalizer
 * @see Normalizer.Form
 */
@Getter
@RequiredArgsConstructor
public enum NormalizationStrategy {
    /**
     * No normalization.
     */
    NONE(null),

    /**
     * Normalization by canonical decomposition.
     */
    NFD(Normalizer.Form.NFD),

    /**
     * Normalization by canonical decomposition, followed by canonical composition.
     */
    NFC(Normalizer.Form.NFC),

    /**
     * Normalization by compatibility decomposition.
     */
    NFKD(Normalizer.Form.NFKD),

    /**
     * Normalization by compatibility decomposition, followed by canonical composition.
     */
    NFKC(Normalizer.Form.NFKC);

    private final Normalizer.Form form;

    /**
     * Normalize a specified character sequence.
     *
     * @param value target value
     * @return normalized value
     */
    public CharSequence normalize(final CharSequence value) {
        if (this.form == null || value == null || value.length() == 0) {
            return value;
        }
        return Normalizer.normalize(value, this.form);
    }
}
