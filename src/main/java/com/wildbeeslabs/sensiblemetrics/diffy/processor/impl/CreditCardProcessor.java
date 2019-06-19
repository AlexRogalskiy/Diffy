/*
 * The MIT License
 *
 * Copyright 2019 WildBees Labs, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
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
package com.wildbeeslabs.sensiblemetrics.diffy.processor.impl;

import com.wildbeeslabs.sensiblemetrics.diffy.exception.InvalidFormatException;
import com.wildbeeslabs.sensiblemetrics.diffy.processor.iface.GenericProcessor;
import com.wildbeeslabs.sensiblemetrics.diffy.utility.ValidationUtils;
import com.wildbeeslabs.sensiblemetrics.diffy.validator.impl.CodeValidator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.wildbeeslabs.sensiblemetrics.diffy.validator.impl.CreditCardValidator.*;

/**
 * Perform credit card validations.
 *
 * <p>
 * By default, all supported card types are allowed.  You can specify which
 * cards should pass validation by configuring the validation options. For
 * example,
 * </p>
 *
 * <pre>
 * <code>CreditCardProcessor ccv = new CreditCardProcessor(CreditCardProcessor.AMEX + CreditCardProcessor.VISA);</code>
 * </pre>
 *
 * <p>
 * configures the validator to only pass American Express and Visa cards.
 * If a card type is not directly supported by this class, you can create an
 * instance of the {@link CodeValidator} class and pass it to a {@link CreditCardProcessor}
 * constructor along with any existing validators. For example:
 * </p>
 *
 * <pre>
 * <code>CreditCardProcessor ccv = new CreditCardProcessor(
 *     new CodeValidator[] {
 *         CreditCardProcessor.AMEX_VALIDATOR,
 *         CreditCardProcessor.VISA_VALIDATOR,
 *         new CodeValidator("^(4)(\\d{12,18})$", LUHN_VALIDATOR) // add VPAY
 * };</code>
 * </pre>
 *
 * <p>
 * Alternatively you can define a validator using the {@link CreditCardRange} class.
 * For example:
 * </p>
 *
 * <pre>
 * <code>CreditCardProcessor ccv = new CreditCardProcessor(
 *    new CreditCardRange[]{
 *        new CreditCardRange("300", "305", 14, 14), // Diners
 *        new CreditCardRange("3095", null, 14, 14), // Diners
 *        new CreditCardRange("36",   null, 14, 14), // Diners
 *        new CreditCardRange("38",   "39", 14, 14), // Diners
 *        new CreditCardRange("4",    null, new int[] {13, 16}), // VISA
 *    }
 * );
 * </code>
 * </pre>
 * <p>
 * This can be combined with a list of {@code CodeValidator}s
 * </p>
 * <p>
 * More information can be found in Michael Gilleland's essay
 * <a href="http://web.archive.org/web/20120614072656/http://www.merriampark.com/anatomycc.htm">Anatomy of Credit Card Numbers</a>.
 * </p>
 *
 * @version $Revision: 1782740 $
 * @since Validator 1.4
 */
@Data
@EqualsAndHashCode
@ToString
public class CreditCardProcessor implements GenericProcessor<String, Object, InvalidFormatException> {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 4040573184175518080L;

    /**
     * Class that represents a credit card range.
     *
     * @since 1.6
     */
    @Data
    public static class CreditCardRange {
        private final String low; // e.g. 34 or 644
        private final String high; // e.g. 34 or 65
        private final int minLen; // e.g. 16 or -1
        private final int maxLen; // e.g. 19 or -1
        private final int lengths[]; // e.g. 16,18,19

        /**
         * Create a credit card range specifier for use in validation
         * of the number syntax including the IIN range.
         * <p>
         * The low and high parameters may be shorter than the length
         * of an IIN (currently 6 digits) in which case subsequent digits
         * are ignored and may range from 0-9.
         * <br>
         * The low and high parameters may be different lengths.
         * e.g. Discover "644" and "65".
         * </p>
         *
         * @param low    the low digits of the IIN range
         * @param high   the high digits of the IIN range
         * @param minLen the minimum length of the entire number
         * @param maxLen the maximum length of the entire number
         */
        public CreditCardRange(final String low, final String high, int minLen, int maxLen) {
            this.low = low;
            this.high = high;
            this.minLen = minLen;
            this.maxLen = maxLen;
            this.lengths = null;
        }

        /**
         * Create a credit card range specifier for use in validation
         * of the number syntax including the IIN range.
         * <p>
         * The low and high parameters may be shorter than the length
         * of an IIN (currently 6 digits) in which case subsequent digits
         * are ignored and may range from 0-9.
         * <br>
         * The low and high parameters may be different lengths.
         * e.g. Discover "644" and "65".
         * </p>
         *
         * @param low     the low digits of the IIN range
         * @param high    the high digits of the IIN range
         * @param lengths array of valid lengths
         */
        public CreditCardRange(final String low, final String high, final int[] lengths) {
            this.low = low;
            this.high = high;
            this.minLen = -1;
            this.maxLen = -1;
            this.lengths = lengths.clone();
        }
    }

    /**
     * The CreditCardTypes that are allowed to pass validation.
     */
    private final List<CodeValidator> cardTypes = new ArrayList<>();

    /**
     * Create a new CreditCardProcessor with default options.
     * The default options are:
     * AMEX, VISA, MASTERCARD and DISCOVER
     */
    public CreditCardProcessor() {
        this(AMEX + VISA + MASTERCARD + DISCOVER);
    }

    /**
     * Create a new CreditCardProcessor with the specified options.
     *
     * @param options Pass in
     *                CreditCardProcessor.VISA + CreditCardProcessor.AMEX to specify that
     *                those are the only valid card types.
     */
    public CreditCardProcessor(long options) {
        if (this.isOn(options, VISA)) {
            this.cardTypes.add(VISA_VALIDATOR);
        }

        if (this.isOn(options, VPAY)) {
            this.cardTypes.add(VPAY_VALIDATOR);
        }

        if (this.isOn(options, AMEX)) {
            this.cardTypes.add(AMEX_VALIDATOR);
        }

        if (this.isOn(options, MASTERCARD)) {
            this.cardTypes.add(MASTERCARD_VALIDATOR);
        }

        if (this.isOn(options, MASTERCARD_PRE_OCT2016)) {
            this.cardTypes.add(MASTERCARD_VALIDATOR_PRE_OCT2016);
        }

        if (this.isOn(options, DISCOVER)) {
            this.cardTypes.add(DISCOVER_VALIDATOR);
        }

        if (this.isOn(options, DINERS)) {
            this.cardTypes.add(DINERS_VALIDATOR);
        }
    }

    /**
     * Create a new CreditCardProcessor with the specified {@link CodeValidator}s.
     *
     * @param creditCardValidators Set of valid code validators
     */
    public CreditCardProcessor(final CodeValidator[] creditCardValidators) {
        ValidationUtils.notNull(creditCardValidators, "Credit card validators should not be null");
        Collections.addAll(this.cardTypes, creditCardValidators);
    }

    /**
     * Create a new CreditCardProcessor with the specified {@link CreditCardRange}s.
     *
     * @param creditCardRanges Set of valid code validators
     * @since 1.6
     */
    public CreditCardProcessor(final CreditCardRange[] creditCardRanges) {
        ValidationUtils.notNull(creditCardRanges, "Credit card ranges should not be null");
        Collections.addAll(this.cardTypes, createRangeValidator(creditCardRanges, LUHN_VALIDATOR));
    }

    /**
     * Create a new CreditCardProcessor with the specified {@link CodeValidator}s
     * and {@link CreditCardRange}s.
     * <p>
     * This can be used to combine predefined validators such as {@link #MASTERCARD_VALIDATOR}
     * with additional validators using the simpler {@link CreditCardRange}s.
     *
     * @param creditCardValidators Set of valid code validators
     * @param creditCardRanges     Set of valid code validators
     * @since 1.6
     */
    public CreditCardProcessor(final CodeValidator[] creditCardValidators, final CreditCardRange[] creditCardRanges) {
        ValidationUtils.notNull(creditCardValidators, "Credit card validator should not be null");
        ValidationUtils.notNull(creditCardRanges, "Credit card ranges should not be null");

        Collections.addAll(this.cardTypes, creditCardValidators);
        Collections.addAll(this.cardTypes, createRangeValidator(creditCardRanges, LUHN_VALIDATOR));
    }

    /**
     * Checks if the field is a valid credit card number.
     *
     * @param card The card number to validate.
     * @return The card number if valid or <code>null</code>
     * if invalid.
     */
    public Object processOrThrow(final String card) {
        if (Objects.isNull(card) || card.length() == 0) {
            return null;
        }
        for (final CodeValidator cardType : this.cardTypes) {
            final Object result = cardType.validate(card);
            if (Objects.nonNull(result)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Tests whether the given flag is on.  If the flag is not a power of 2
     * (ie. 3) this tests whether the combination of flags is on.
     *
     * @param options The options specified.
     * @param flag    Flag value to check.
     * @return whether the specified flag value is on.
     */
    private boolean isOn(long options, long flag) {
        return (options & flag) > 0;
    }
}
