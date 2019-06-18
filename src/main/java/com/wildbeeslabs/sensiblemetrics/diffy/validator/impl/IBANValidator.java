package com.wildbeeslabs.sensiblemetrics.diffy.validator.impl;

import com.wildbeeslabs.sensiblemetrics.diffy.validator.iface.Validator;
import org.apache.commons.validator.routines.RegexValidator;
import org.apache.commons.validator.routines.checkdigit.IBANCheckDigit;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * IBAN {@link Validator} implementation
 *
 * @since 1.5.0
 */
public class IBANValidator implements Validator<String> {

    private final Map<String, ValidatorEntry> formatValidators;

    /**
     * The validation class
     */
    public static class ValidatorEntry {
        /*
         * The minimum length does not appear to be defined by the standard.
         * Norway is currently the shortest at 15.
         *
         * There is no standard for BBANs; they vary between countries.
         * But a BBAN must consist of a branch id and account number.
         * Each of these must be at least 2 chars (generally more) so an absolute minimum is
         * 4 characters for the BBAN and 8 for the IBAN.
         */
        private static final int MIN_LEN = 8;
        private static final int MAX_LEN = 34; // defined by [3]
        final String countryCode;
        final RegexValidator validator;
        final int lengthOfIBAN; // used to avoid unnecessary regex matching

        /**
         * Creates the validator
         *
         * @param cc     the country code
         * @param len    the length of the IBAN
         * @param format the regex to use to check the format
         */
        public ValidatorEntry(final String cc, int len, final String format) {
            if (!(cc.length() == 2 && Character.isUpperCase(cc.charAt(0)) && Character.isUpperCase(cc.charAt(1)))) {
                throw new IllegalArgumentException("Invalid country Code; must be exactly 2 upper-case characters");
            }
            if (len > MAX_LEN || len < MIN_LEN) {
                throw new IllegalArgumentException("Invalid length parameter, must be in range " + MIN_LEN + " to " + MAX_LEN + " inclusive: " + len);
            }
            if (!format.startsWith(cc)) {
                throw new IllegalArgumentException("countryCode '" + cc + "' does not agree with format: " + format);
            }
            this.countryCode = cc;
            this.lengthOfIBAN = len;
            this.validator = new RegexValidator(format);
        }
    }

    /*
     * Wikipedia [1] says that only uppercase is allowed.
     * The SWIFT PDF file [2] implies that lower case is allowed.
     * However there are no examples using lower-case.
     * Unfortunately the relevant ISO documents (ISO 13616-1) are not available for free.
     * The IBANCheckDigit code treats upper and lower case the same,
     * so any case validation has to be done in this class.
     *
     * Note: the European Payments council has a document [3] which includes a description
     * of the IBAN. Section 5 clearly states that only upper case is allowed.
     * Also the maximum length is 34 characters (including the country code),
     * and the length is fixed for each country.
     *
     * It looks like lower-case is permitted in BBANs, but they must be converted to
     * upper case for IBANs.
     *
     * [1] https://en.wikipedia.org/wiki/International_Bank_Account_Number
     * [2] http://www.swift.com/dsp/resources/documents/IBAN_Registry.pdf
     * [3] http://www.europeanpaymentscouncil.eu/documents/ECBS%20IBAN%20standard%20EBS204_V3.2.pdf
     */

    private static final ValidatorEntry[] DEFAULT_FORMATS = {
        new ValidatorEntry("AD", 24, "AD\\d{10}[A-Z0-9]{12}"), // Andorra
        new ValidatorEntry("AE", 23, "AE\\d{21}"), // United Arab Emirates
        new ValidatorEntry("AL", 28, "AL\\d{10}[A-Z0-9]{16}"), // Albania
        new ValidatorEntry("AT", 20, "AT\\d{18}"), // Austria
        new ValidatorEntry("AZ", 28, "AZ\\d{2}[A-Z]{4}[A-Z0-9]{20}"), // Republic of Azerbaijan
        new ValidatorEntry("BA", 20, "BA\\d{18}"), // Bosnia and Herzegovina
        new ValidatorEntry("BE", 16, "BE\\d{14}"), // Belgium
        new ValidatorEntry("BG", 22, "BG\\d{2}[A-Z]{4}\\d{6}[A-Z0-9]{8}"), // Bulgaria
        new ValidatorEntry("BH", 22, "BH\\d{2}[A-Z]{4}[A-Z0-9]{14}"), // Bahrain (Kingdom of)
        new ValidatorEntry("BR", 29, "BR\\d{25}[A-Z]{1}[A-Z0-9]{1}"), // Brazil
        new ValidatorEntry("BY", 28, "BY\\d{2}[A-Z0-9]{4}\\d{4}[A-Z0-9]{16}"), // Republic of Belarus
        new ValidatorEntry("CH", 21, "CH\\d{7}[A-Z0-9]{12}"), // Switzerland
        new ValidatorEntry("CR", 22, "CR\\d{20}"), // Costa Rica
        new ValidatorEntry("CY", 28, "CY\\d{10}[A-Z0-9]{16}"), // Cyprus
        new ValidatorEntry("CZ", 24, "CZ\\d{22}"), // Czech Republic
        new ValidatorEntry("DE", 22, "DE\\d{20}"), // Germany
        new ValidatorEntry("DK", 18, "DK\\d{16}"), // Denmark
        new ValidatorEntry("DO", 28, "DO\\d{2}[A-Z0-9]{4}\\d{20}"), // Dominican Republic
        new ValidatorEntry("EE", 20, "EE\\d{18}"), // Estonia
        new ValidatorEntry("ES", 24, "ES\\d{22}"), // Spain
        new ValidatorEntry("FI", 18, "FI\\d{16}"), // Finland
        new ValidatorEntry("FO", 18, "FO\\d{16}"), // Denmark (Faroes)
        new ValidatorEntry("FR", 27, "FR\\d{12}[A-Z0-9]{11}\\d{2}"), // France
        new ValidatorEntry("GB", 22, "GB\\d{2}[A-Z]{4}\\d{14}"), // United Kingdom
        new ValidatorEntry("GE", 22, "GE\\d{2}[A-Z]{2}\\d{16}"), // Georgia
        new ValidatorEntry("GI", 23, "GI\\d{2}[A-Z]{4}[A-Z0-9]{15}"), // Gibraltar
        new ValidatorEntry("GL", 18, "GL\\d{16}"), // Denmark (Greenland)
        new ValidatorEntry("GR", 27, "GR\\d{9}[A-Z0-9]{16}"), // Greece
        new ValidatorEntry("GT", 28, "GT\\d{2}[A-Z0-9]{24}"), // Guatemala
        new ValidatorEntry("HR", 21, "HR\\d{19}"), // Croatia
        new ValidatorEntry("HU", 28, "HU\\d{26}"), // Hungary
        new ValidatorEntry("IE", 22, "IE\\d{2}[A-Z]{4}\\d{14}"), // Ireland
        new ValidatorEntry("IL", 23, "IL\\d{21}"), // Israel
        new ValidatorEntry("IS", 26, "IS\\d{24}"), // Iceland
        new ValidatorEntry("IT", 27, "IT\\d{2}[A-Z]{1}\\d{10}[A-Z0-9]{12}"), // Italy
        new ValidatorEntry("IQ", 23, "IQ\\d{2}[A-Z]{4}\\d{15}"), // Iraq
        new ValidatorEntry("JO", 30, "JO\\d{2}[A-Z]{4}\\d{4}[A-Z0-9]{18}"), // Jordan
        new ValidatorEntry("KW", 30, "KW\\d{2}[A-Z]{4}[A-Z0-9]{22}"), // Kuwait
        new ValidatorEntry("KZ", 20, "KZ\\d{5}[A-Z0-9]{13}"), // Kazakhstan
        new ValidatorEntry("LB", 28, "LB\\d{6}[A-Z0-9]{20}"), // Lebanon
        new ValidatorEntry("LC", 32, "LC\\d{2}[A-Z]{4}[A-Z0-9]{24}"), // Saint Lucia
        new ValidatorEntry("LI", 21, "LI\\d{7}[A-Z0-9]{12}"), // Liechtenstein (Principality of)
        new ValidatorEntry("LT", 20, "LT\\d{18}"), // Lithuania
        new ValidatorEntry("LU", 20, "LU\\d{5}[A-Z0-9]{13}"), // Luxembourg
        new ValidatorEntry("LV", 21, "LV\\d{2}[A-Z]{4}[A-Z0-9]{13}"), // Latvia
        new ValidatorEntry("MC", 27, "MC\\d{12}[A-Z0-9]{11}\\d{2}"), // Monaco
        new ValidatorEntry("MD", 24, "MD\\d{2}[A-Z0-9]{20}"), // Moldova
        new ValidatorEntry("ME", 22, "ME\\d{20}"), // Montenegro
        new ValidatorEntry("MK", 19, "MK\\d{5}[A-Z0-9]{10}\\d{2}"), // Macedonia, Former Yugoslav Republic of
        new ValidatorEntry("MR", 27, "MR\\d{25}"), // Mauritania
        new ValidatorEntry("MT", 31, "MT\\d{2}[A-Z]{4}\\d{5}[A-Z0-9]{18}"), // Malta
        new ValidatorEntry("MU", 30, "MU\\d{2}[A-Z]{4}\\d{19}[A-Z]{3}"), // Mauritius
        new ValidatorEntry("NL", 18, "NL\\d{2}[A-Z]{4}\\d{10}"), // The Netherlands
        new ValidatorEntry("NO", 15, "NO\\d{13}"), // Norway
        new ValidatorEntry("PK", 24, "PK\\d{2}[A-Z]{4}[A-Z0-9]{16}"), // Pakistan
        new ValidatorEntry("PL", 28, "PL\\d{26}"), // Poland
        new ValidatorEntry("PS", 29, "PS\\d{2}[A-Z]{4}[A-Z0-9]{21}"), // Palestine, State of
        new ValidatorEntry("PT", 25, "PT\\d{23}"), // Portugal
        new ValidatorEntry("QA", 29, "QA\\d{2}[A-Z]{4}[A-Z0-9]{21}"), // Qatar
        new ValidatorEntry("RO", 24, "RO\\d{2}[A-Z]{4}[A-Z0-9]{16}"), // Romania
        new ValidatorEntry("RS", 22, "RS\\d{20}"), // Serbia
        new ValidatorEntry("SA", 24, "SA\\d{4}[A-Z0-9]{18}"), // Saudi Arabia
        new ValidatorEntry("SC", 31, "SC\\d{2}[A-Z]{4}\\d{20}[A-Z]{3}"), // Seychelles
        new ValidatorEntry("SE", 24, "SE\\d{22}"), // Sweden
        new ValidatorEntry("SI", 19, "SI\\d{17}"), // Slovenia
        new ValidatorEntry("SK", 24, "SK\\d{22}"), // Slovak Republic
        new ValidatorEntry("SM", 27, "SM\\d{2}[A-Z]{1}\\d{10}[A-Z0-9]{12}"), // San Marino
        new ValidatorEntry("ST", 25, "ST\\d{23}"), // Sao Tome and Principe
        new ValidatorEntry("TL", 23, "TL\\d{21}"), // Timor-Leste
        new ValidatorEntry("TN", 24, "TN\\d{22}"), // Tunisia
        new ValidatorEntry("TR", 26, "TR\\d{8}[A-Z0-9]{16}"), // Turkey
        new ValidatorEntry("UA", 29, "UA\\d{8}[A-Z0-9]{19}"), // Ukraine
        new ValidatorEntry("VG", 24, "VG\\d{2}[A-Z]{4}\\d{16}"), // Virgin Islands, British
        new ValidatorEntry("XK", 20, "XK\\d{18}"), // Republic of Kosovo
    };

    /**
     * The singleton instance which uses the default formats
     */
    public static final IBANValidator DEFAULT_IBAN_VALIDATOR = new IBANValidator();

    /**
     * Return a singleton instance of the IBAN validator using the default formats
     *
     * @return A singleton instance of the ISBN validator
     */
    public static IBANValidator getInstance() {
        return DEFAULT_IBAN_VALIDATOR;
    }

    /**
     * Create a default IBAN validator.
     */
    public IBANValidator() {
        this(DEFAULT_FORMATS);
    }

    /**
     * Create an IBAN validator from the specified map of IBAN formats.
     *
     * @param formatMap map of IBAN formats
     */
    public IBANValidator(final ValidatorEntry[] formatMap) {
        this.formatValidators = createValidators(formatMap);
    }

    private Map<String, ValidatorEntry> createValidators(final ValidatorEntry[] formatMap) {
        final Map<String, ValidatorEntry> m = new ConcurrentHashMap<>();
        for (final ValidatorEntry v : formatMap) {
            m.put(v.countryCode, v);
        }
        return m;
    }

    /**
     * Validate an IBAN Code
     *
     * @param code The value validation is being performed on
     * @return <code>true</code> if the value is valid
     */
    @Override
    public boolean validate(final String code) {
        ValidatorEntry formatValidator = getValidator(code);
        if (Objects.isNull(formatValidator) || code.length() != formatValidator.lengthOfIBAN || !formatValidator.validator.isValid(code)) {
            return false;
        }
        return IBANCheckDigit.IBAN_CHECK_DIGIT.isValid(code);
    }

    /**
     * Does the class have the required validator?
     *
     * @param code the code to check
     * @return true if there is a validator
     */
    public boolean hasValidator(final String code) {
        return Objects.nonNull(this.getValidator(code));
    }

    /**
     * Gets a copy of the default Validators.
     *
     * @return a copy of the default ValidatorEntry array
     */
    public ValidatorEntry[] getDefaultValidators() {
        return Arrays.copyOf(DEFAULT_FORMATS, DEFAULT_FORMATS.length);
    }

    /**
     * Get the ValidatorEntry for a given IBAN
     *
     * @param code a string starting with the ISO country code (e.g. an IBAN)
     * @return the validator or {@code null} if there is not one registered.
     */
    public ValidatorEntry getValidator(final String code) {
        if (Objects.isNull(code) || code.length() < 2) { // ensure we can extract the code
            return null;
        }
        final String key = code.substring(0, 2);
        return this.formatValidators.get(key);
    }

    /**
     * Installs a validator.
     * Will replace any existing entry which has the same countryCode
     *
     * @param validator the instance to install.
     * @return the previous ValidatorEntry, or {@code null} if there was none
     * @throws IllegalStateException if an attempt is made to modify the singleton validator
     */
    public ValidatorEntry setValidator(final ValidatorEntry validator) {
        if (this == DEFAULT_IBAN_VALIDATOR) {
            throw new IllegalStateException("The singleton validator cannot be modified");
        }
        return this.formatValidators.put(validator.countryCode, validator);
    }

    /**
     * Installs a validator.
     * Will replace any existing entry which has the same countryCode.
     *
     * @param countryCode the country code
     * @param length      the length of the IBAN. Must be &ge; 8 and &le; 32.
     *                    If the length is &lt; 0, the validator is removed, and the format is not used.
     * @param format      the format of the IBAN (as a regular expression)
     * @return the previous ValidatorEntry, or {@code null} if there was none
     * @throws IllegalArgumentException if there is a problem
     * @throws IllegalStateException    if an attempt is made to modify the singleton validator
     */
    public ValidatorEntry setValidator(final String countryCode, int length, final String format) {
        if (this == DEFAULT_IBAN_VALIDATOR) {
            throw new IllegalStateException("The singleton validator cannot be modified");
        }
        if (length < 0) {
            return this.formatValidators.remove(countryCode);
        }
        return setValidator(new ValidatorEntry(countryCode, length, format));
    }
}
