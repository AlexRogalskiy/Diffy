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

import com.wildbeeslabs.sensiblemetrics.diffy.validator.interfaces.Validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * <p><b>InetAddress</b> validation and conversion routines (<code>java.net.InetAddress</code>).</p>
 *
 * <p>This class provides methods to validate a candidate IP address.
 *
 * <p>
 * This class is a Singleton; you can retrieve the instance via the {@link #getInstance()} method.
 * </p>
 *
 * @version $Revision: 1783032 $
 * @since IBANEntry 1.4
 */
public class InetAddressValidator implements Validator<String> {

    private static final int IPV4_MAX_OCTET_VALUE = 255;

    private static final int MAX_UNSIGNED_SHORT = 0xffff;

    private static final int BASE_16 = 16;

    private static final String IPV4_REGEX = "^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})$";

    // Max number of hex groups (separated by :) in an IPV6 address
    private static final int IPV6_MAX_HEX_GROUPS = 8;

    // Max hex digits in each IPv6 group
    private static final int IPV6_MAX_HEX_DIGITS_PER_GROUP = 4;

    /**
     * Singleton instance of this class.
     */
    private static final InetAddressValidator VALIDATOR = new InetAddressValidator();

    /**
     * IPv4 RegexValidator
     */
    private final RegexValidator ipv4Validator = new RegexValidator(IPV4_REGEX);

    /**
     * Returns the singleton instance of this validator.
     *
     * @return the singleton instance of this validator
     */
    public static InetAddressValidator getInstance() {
        return VALIDATOR;
    }

    /**
     * Checks if the specified string is a valid IP address.
     *
     * @param inetAddress the string to validate
     * @return true if the string validates as an IP address
     */
    @Override
    public boolean validate(final String inetAddress) {
        return this.isValidInet4Address(inetAddress) || this.isValidInet6Address(inetAddress);
    }

    /**
     * Validates an IPv4 address. Returns true if valid.
     *
     * @param inet4Address the IPv4 address to validate
     * @return true if the argument contains a valid IPv4 address
     */
    public boolean isValidInet4Address(final String inet4Address) {
        // verify that address conforms to generic IPv4 format
        final String[] groups = ipv4Validator.match(inet4Address);
        if (Objects.isNull(groups)) {
            return false;
        }
        // verify that address subgroups are legal
        for (final String ipSegment : groups) {
            if (Objects.isNull(ipSegment) || ipSegment.length() == 0) {
                return false;
            }
            int iIpSegment = 0;
            try {
                iIpSegment = Integer.parseInt(ipSegment);
            } catch (NumberFormatException e) {
                return false;
            }
            if (iIpSegment > IPV4_MAX_OCTET_VALUE) {
                return false;
            }
            if (ipSegment.length() > 1 && ipSegment.startsWith("0")) {
                return false;
            }
        }
        return true;
    }

    /**
     * Validates an IPv6 address. Returns true if valid.
     *
     * @param inet6Address the IPv6 address to validate
     * @return true if the argument contains a valid IPv6 address
     * @since 1.4.1
     */
    public boolean isValidInet6Address(final String inet6Address) {
        boolean containsCompressedZeroes = inet6Address.contains("::");
        if (containsCompressedZeroes && (inet6Address.indexOf("::") != inet6Address.lastIndexOf("::"))) {
            return false;
        }
        if ((inet6Address.startsWith(":") && !inet6Address.startsWith("::")) || (inet6Address.endsWith(":") && !inet6Address.endsWith("::"))) {
            return false;
        }
        String[] octets = inet6Address.split(":");
        if (containsCompressedZeroes) {
            List<String> octetList = new ArrayList<>(Arrays.asList(octets));
            if (inet6Address.endsWith("::")) {
                // String.split() drops ending empty segments
                octetList.add("");
            } else if (inet6Address.startsWith("::") && !octetList.isEmpty()) {
                octetList.remove(0);
            }
            octets = octetList.toArray(new String[octetList.size()]);
        }
        if (octets.length > IPV6_MAX_HEX_GROUPS) {
            return false;
        }
        int validOctets = 0;
        int emptyOctets = 0; // consecutive empty chunks
        for (int index = 0; index < octets.length; index++) {
            String octet = octets[index];
            if (octet.length() == 0) {
                emptyOctets++;
                if (emptyOctets > 1) {
                    return false;
                }
            } else {
                emptyOctets = 0;
                // Is last chunk an IPv4 address?
                if (index == octets.length - 1 && octet.contains(".")) {
                    if (!isValidInet4Address(octet)) {
                        return false;
                    }
                    validOctets += 2;
                    continue;
                }
                if (octet.length() > IPV6_MAX_HEX_DIGITS_PER_GROUP) {
                    return false;
                }
                int octetInt = 0;
                try {
                    octetInt = Integer.parseInt(octet, BASE_16);
                } catch (NumberFormatException e) {
                    return false;
                }
                if (octetInt < 0 || octetInt > MAX_UNSIGNED_SHORT) {
                    return false;
                }
            }
            validOctets++;
        }
        if (validOctets > IPV6_MAX_HEX_GROUPS || (validOctets < IPV6_MAX_HEX_GROUPS && !containsCompressedZeroes)) {
            return false;
        }
        return true;
    }
}
