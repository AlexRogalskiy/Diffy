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
package com.wildbeeslabs.sensiblemetrics.diffy.validator.impl;

import com.wildbeeslabs.sensiblemetrics.diffy.common.utility.impl.Flags;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.impl.AbstractTypeSafeMatcher;
import com.wildbeeslabs.sensiblemetrics.diffy.validator.iface.Validator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import static com.wildbeeslabs.sensiblemetrics.diffy.processor.impl.CreditCardProcessor.*;
import static java.util.Arrays.asList;

/**
 * Credit card {@link Validator}
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
 * If a card type is not directly supported by this class, you can implement
 * the CreditCardType interface and pass an instance into the
 * <code>addAllowedCardType</code> method.
 * </p>
 *
 * <p>
 * For a similar implementation in Perl, reference Sean M. Burke's
 * <a href="http://www.speech.cs.cmu.edu/~sburke/pub/luhn_lib.html">script</a>.
 * More information is also available
 * <a href="http://www.merriampark.com/anatomycc.htm">here</a>.
 * </p>
 */
public class CreditCardValidator2 implements Validator<String> {

    /**
     * Default {@link CreditCardValidator2} instance
     */
    private static final CreditCardValidator2 CREDIT_CARD_VALIDATOR = new CreditCardValidator2();

    /**
     * The CreditCardTypes that are allowed to pass validation.
     */
    private final Collection<CreditCardType> cardTypes = new ArrayList<>();

    /**
     * Returns {@link CreditCardValidator2} instance
     *
     * @return {@link CreditCardValidator2} instance
     */
    public static CreditCardValidator2 getInstance() {
        return CREDIT_CARD_VALIDATOR;
    }

    /**
     * Create a new CreditCardValidator2 with default options.
     */
    public CreditCardValidator2() {
        this(AMEX + VISA + MASTERCARD + DISCOVER);
    }

    /**
     * Creates a new CreditCardValidator2 with the specified options.
     *
     * @param options Pass in
     *                CreditCardValidator2.VISA + CreditCardValidator2.AMEX to specify that
     *                those are the only valid card types.
     */
    public CreditCardValidator2(long options) {
        final Flags f = new Flags(options);
        if (f.isOn(VISA)) {
            this.cardTypes.add(new Visa());
        }
        if (f.isOn(AMEX)) {
            this.cardTypes.add(new Amex());
        }
        if (f.isOn(MASTERCARD)) {
            this.cardTypes.add(new Mastercard());
        }
        if (f.isOn(DISCOVER)) {
            this.cardTypes.add(new Discover());
        }
    }

    /**
     * Checks if the field is a valid credit card number.
     *
     * @param card The card number to validate.
     * @return Whether the card number is valid.
     */
    @Override
    public boolean validate(final String card) {
        if (Objects.isNull(card) || (card.length() < 13) || (card.length() > 19)) {
            return false;
        }
        if (!this.luhnCheck(card)) {
            return false;
        }
        for (final Object cardType : this.cardTypes) {
            final CreditCardType type = (CreditCardType) cardType;
            if (type.matches(card)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds an allowed CreditCardType that participates in the card
     * validation algorithm.
     *
     * @param type The type that is now allowed to pass validation.
     * @since Validator 1.1.2
     */
    public void addAllowedCardType(final CreditCardType type) {
        this.cardTypes.add(type);
    }

    /**
     * Checks for a valid credit card number.
     *
     * @param cardNumber Credit Card Number.
     * @return Whether the card number passes the luhnCheck.
     */
    protected boolean luhnCheck(final String cardNumber) {
        int digits = cardNumber.length();
        int oddOrEven = digits & 1;
        long sum = 0;
        for (int count = 0; count < digits; count++) {
            int digit = 0;
            try {
                digit = Integer.parseInt(String.valueOf(cardNumber.charAt(count)));
            } catch (NumberFormatException e) {
                return false;
            }

            if (((count & 1) ^ oddOrEven) == 0) {
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }
            sum += digit;
        }
        return (sum == 0) ? false : (sum % 10 == 0);
    }

    /**
     * Default credit card type marker interface
     */
    @Data
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    public static abstract class CreditCardType extends AbstractTypeSafeMatcher<String> {
    }

    /**
     * Change to support Visa Carte Blue used in France
     * has been removed - see Bug 35926
     */
    @Data
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    private static class Visa extends CreditCardType {
        /**
         * Default credit card {@link Collection} of {@link String} prefixes
         */
        private static final Collection<String> CARD_PREFIX = Collections.unmodifiableList(asList("4", "37"));

        @Override
        public boolean matchesSafe(final String card) {
            final String prefix2 = card.substring(0, 1);
            return (CARD_PREFIX.contains(prefix2) && (card.length() == 13 || card.length() == 16));
        }
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    private static class Amex extends CreditCardType {
        /**
         * Default credit card {@link Collection} of {@link String} prefixes
         */
        private static final Collection<String> CARD_PREFIX = Collections.unmodifiableList(asList("34", "37"));

        @Override
        public boolean matchesSafe(final String card) {
            final String prefix2 = card.substring(0, 2);
            return (CARD_PREFIX.contains(prefix2) && (card.length() == 15));
        }
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    private static class Discover extends CreditCardType {
        /**
         * Default credit card {@link Collection} of {@link String} prefixes
         */
        private static final Collection<String> CARD_PREFIX = Collections.unmodifiableList(asList("6011"));

        @Override
        public boolean matchesSafe(final String card) {
            final String prefix2 = card.substring(0, 4);
            return (CARD_PREFIX.contains(prefix2) && (card.length() == 16));
        }
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    private static class Mastercard extends CreditCardType {
        /**
         * Default credit card {@link Collection} of {@link String} prefixes
         */
        private static final Collection<String> CARD_PREFIX = Collections.unmodifiableList(asList("51", "52", "53", "54", "55"));

        @Override
        public boolean matchesSafe(final String card) {
            final String prefix2 = card.substring(0, 2);
            return (CARD_PREFIX.contains(prefix2) && (card.length() == 16));
        }
    }
}
