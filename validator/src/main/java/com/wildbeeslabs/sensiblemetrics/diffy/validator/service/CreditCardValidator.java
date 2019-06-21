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
package com.wildbeeslabs.sensiblemetrics.diffy.validator.service;

import com.wildbeeslabs.sensiblemetrics.diffy.processor.impl.CreditCardProcessor;
import com.wildbeeslabs.sensiblemetrics.diffy.processor.impl.RegexProcessor;
import com.wildbeeslabs.sensiblemetrics.diffy.processor.utility.CreditCardRange;
import com.wildbeeslabs.sensiblemetrics.diffy.validator.digits.iface.DigitValidator;
import com.wildbeeslabs.sensiblemetrics.diffy.validator.iface.Validator;
import lombok.NonNull;

import java.util.Objects;

import static com.wildbeeslabs.sensiblemetrics.diffy.processor.impl.CreditCardProcessor.*;

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
 * instance of the {@link CodeValidator} class and pass it to a {@link CreditCardValidator}
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
public class CreditCardValidator implements Validator<String> {

    /**
     * Default {@link CreditCardProcessor} instance
     */
    private final CreditCardProcessor processor;

    /**
     * Create a new CreditCardProcessor with default options.
     * The default options are:
     * AMEX, VISA, MASTERCARD and DISCOVER
     */
    public CreditCardValidator() {
        this.processor = new CreditCardProcessor();
    }

    /**
     * Create a new CreditCardProcessor with the specified options.
     *
     * @param options Pass in
     *                CreditCardProcessor.VISA + CreditCardProcessor.AMEX to specify that
     *                those are the only valid card types.
     */
    public CreditCardValidator(long options) {
        this.processor = new CreditCardProcessor(options);
    }

    /**
     * Create a new CreditCardProcessor with the specified {@link CodeValidator}s.
     *
     * @param creditCardValidators Set of valid code validators
     */
    public CreditCardValidator(final CodeValidator[] creditCardValidators) {
        this.processor = new CreditCardProcessor(creditCardValidators);
    }

    /**
     * Create a new CreditCardProcessor with the specified {@link CreditCardRange}s.
     *
     * @param creditCardRanges Set of valid code validators
     */
    public CreditCardValidator(final CreditCardRange[] creditCardRanges) {
        this.processor = new CreditCardProcessor(creditCardRanges);
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
     */
    public CreditCardValidator(final CodeValidator[] creditCardValidators, final CreditCardRange[] creditCardRanges) {
        this.processor = new CreditCardProcessor(creditCardValidators, creditCardRanges);
    }

    /**
     * Create a new generic CreditCardProcessor which validates the syntax and check digit only.
     * Does not check the Issuer Identification Number (IIN)
     *
     * @param minLen minimum allowed length
     * @param maxLen maximum allowed length
     * @return the validator
     */
    @NonNull
    public static CreditCardValidator genericCreditCardValidator(int minLen, int maxLen) {
        return new CreditCardValidator(new CodeValidator[]{new CodeValidator("(\\d+)", minLen, maxLen, LUHN_VALIDATOR)});
    }

    /**
     * Create a new generic CreditCardProcessor which validates the syntax and check digit only.
     * Does not check the Issuer Identification Number (IIN)
     *
     * @param length exact length
     * @return the validator
     */
    @NonNull
    public static CreditCardValidator genericCreditCardValidator(int length) {
        return genericCreditCardValidator(length, length);
    }

    /**
     * Create a new generic CreditCardProcessor which validates the syntax and check digit only.
     * Does not check the Issuer Identification Number (IIN)
     *
     * @return the validator
     */
    @NonNull
    public static CreditCardValidator genericCreditCardValidator() {
        return genericCreditCardValidator(MIN_CC_LENGTH, MAX_CC_LENGTH);
    }

    /**
     * Checks if the field is a valid credit card number.
     *
     * @param card The card number to validate.
     * @return Whether the card number is valid.
     */
    @Override
    public boolean validate(final String card) {
        return Objects.nonNull(this.processor.processOrThrow(card));
    }

    public static boolean validLength(int valueLength, final CreditCardRange range) {
        if (Objects.nonNull(range.getLengths())) {
            for (final int length : range.getLengths()) {
                if (valueLength == length) {
                    return true;
                }
            }
            return false;
        }
        return valueLength >= range.getMinLen() && valueLength <= range.getMaxLen();
    }

    public static CodeValidator createRangeValidator(final CreditCardRange[] creditCardRanges, final DigitValidator validator) {
        return new CodeValidator(new RegexProcessor("(\\d+)") {
            /**
             * Default array of {@link CreditCardRange}s
             */
            private CreditCardRange[] ccr = creditCardRanges.clone();

            @Override
            public String processOrThrow(final String value) {
                if (Objects.nonNull(super.match(value))) {
                    int length = value.length();
                    for (final CreditCardRange range : ccr) {
                        if (validLength(length, range)) {
                            if (Objects.isNull(range.getHigh())) {
                                if (value.startsWith(range.getLow())) {
                                    return value;
                                }
                            } else if (range.getLow().compareTo(value) <= 0 && range.getHigh().compareTo(value.substring(0, range.getHigh().length())) >= 0) {
                                return value;
                            }
                        }
                    }
                }
                return null;
            }

            @Override
            public String[] match(final String value) {
                return new String[]{this.processOrThrow(value)};
            }
        }, validator);
    }
}
