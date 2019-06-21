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
package com.wildbeeslabs.sensiblemetrics.diffy.processor.service;

import com.wildbeeslabs.sensiblemetrics.diffy.exception.InvalidFormatException;
import com.wildbeeslabs.sensiblemetrics.diffy.processor.iface.GenericProcessor;
import com.wildbeeslabs.sensiblemetrics.diffy.processor.utility.CreditCardRange;
import com.wildbeeslabs.sensiblemetrics.diffy.utility.ValidationUtils;
import com.wildbeeslabs.sensiblemetrics.diffy.validator.digits.iface.DigitValidator;
import com.wildbeeslabs.sensiblemetrics.diffy.validator.digits.impl.LuhnDigitValidator;
import com.wildbeeslabs.sensiblemetrics.diffy.validator.impl.CodeValidator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.wildbeeslabs.sensiblemetrics.diffy.validator.impl.CreditCardValidator.createRangeValidator;

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
     * Default min credit card length
     */
    public static final int MIN_CC_LENGTH = 12; // minimum allowed length

    /**
     * Default max credit card length
     */
    public static final int MAX_CC_LENGTH = 19; // maximum allowed length

    /**
     * Option specifying that no cards are allowed.  This is useful if
     * you want only custom card types to validate so you turn off the
     * default cards with this option.
     *
     * <pre>
     * <code>
     * CreditCardProcessor v = new CreditCardProcessor(CreditCardProcessor.NONE);
     * v.addAllowedCardType(customType);
     * v.isValid(aCardNumber);
     * </code>
     * </pre>
     */
    public static final long NONE = 0;

    /**
     * Option specifying that American Express cards are allowed.
     */
    public static final long AMEX = 1 << 0;

    /**
     * Option specifying that Visa cards are allowed.
     */
    public static final long VISA = 1 << 1;

    /**
     * Option specifying that Mastercard cards are allowed.
     */
    public static final long MASTERCARD = 1 << 2;

    /**
     * Option specifying that Discover cards are allowed.
     */
    public static final long DISCOVER = 1 << 3;

    /**
     * Option specifying that Diners cards are allowed.
     */
    public static final long DINERS = 1 << 4;

    /**
     * Option specifying that VPay (Visa) cards are allowed.
     *
     * @since 1.5.0
     */
    public static final long VPAY = 1 << 5;

    /**
     * Option specifying that Mastercard cards (pre Oct 2016 only) are allowed.
     */
    public static final long MASTERCARD_PRE_OCT2016 = 1 << 6;

    /**
     * Luhn checkdigit validator for the card numbers.
     */
    public static final DigitValidator LUHN_VALIDATOR = LuhnDigitValidator.getInstance();

    /**
     * American Express (Amex) Card Validator
     * <p>
     * 34xxxx (15) <br>
     * 37xxxx (15) <br>
     */
    public static final CodeValidator AMEX_VALIDATOR = new CodeValidator("^(3[47]\\d{13})$", LUHN_VALIDATOR);

    /**
     * Diners Card Validator
     * <p>
     * 300xxx - 305xxx (14) <br>
     * 3095xx (14) <br>
     * 36xxxx (14) <br>
     * 38xxxx (14) <br>
     * 39xxxx (14) <br>
     */
    public static final CodeValidator DINERS_VALIDATOR = new CodeValidator("^(30[0-5]\\d{11}|3095\\d{10}|36\\d{12}|3[8-9]\\d{12})$", LUHN_VALIDATOR);

    /**
     * Discover Card regular expressions
     * <p>
     * 6011xx (16) <br>
     * 644xxx - 65xxxx (16) <br>
     */
    private static final RegexProcessor DISCOVER_REGEX = new RegexProcessor(new String[]{"^(6011\\d{12})$", "^(64[4-9]\\d{13})$", "^(65\\d{14})$"});

    /**
     * Discover Card Validator
     */
    public static final CodeValidator DISCOVER_VALIDATOR = new CodeValidator(DISCOVER_REGEX, LUHN_VALIDATOR);

    /**
     * Mastercard regular expressions
     * <p>
     * 2221xx - 2720xx (16) <br>
     * 51xxx - 55xxx (16) <br>
     */
    private static final RegexProcessor MASTERCARD_PROCESSOR = new RegexProcessor(
        new String[]{
            "^(5[1-5]\\d{14})$",  // 51 - 55 (pre Oct 2016)
            // valid from October 2016
            "^(2221\\d{12})$",    // 222100 - 222199
            "^(222[2-9]\\d{12})$",// 222200 - 222999
            "^(22[3-9]\\d{13})$", // 223000 - 229999
            "^(2[3-6]\\d{14})$",  // 230000 - 269999
            "^(27[01]\\d{13})$",  // 270000 - 271999
            "^(2720\\d{12})$",    // 272000 - 272099
        });

    /**
     * Mastercard Card Validator
     */
    public static final CodeValidator MASTERCARD_VALIDATOR = new CodeValidator(MASTERCARD_PROCESSOR, LUHN_VALIDATOR);

    /**
     * Mastercard Card Validator (pre Oct 2016)
     */
    public static final CodeValidator MASTERCARD_VALIDATOR_PRE_OCT2016 = new CodeValidator("^(5[1-5]\\d{14})$", LUHN_VALIDATOR);

    /**
     * Visa Card Validator
     * <p>
     * 4xxxxx (13 or 16)
     */
    public static final CodeValidator VISA_VALIDATOR = new CodeValidator("^(4)(\\d{12}|\\d{15})$", LUHN_VALIDATOR);

    /**
     * VPay (Visa) Card Validator
     * <p>
     * 4xxxxx (13-19)
     *
     * @since 1.5.0
     */
    public static final CodeValidator VPAY_VALIDATOR = new CodeValidator("^(4)(\\d{12,18})$", LUHN_VALIDATOR);

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
