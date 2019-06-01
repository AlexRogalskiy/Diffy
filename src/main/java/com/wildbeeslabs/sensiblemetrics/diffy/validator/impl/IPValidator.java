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

import com.google.common.collect.ImmutableSet;
import com.wildbeeslabs.sensiblemetrics.diffy.validator.iface.Validator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * IP {@link Validator} implementation
 */
@Slf4j
public class IPValidator implements Validator<String> {

    private static final Set<String> whiteListedIPs = ImmutableSet.of("127.0.0.1", "0:0:0:0:0:0:0:1");
    private List<String[]> masks = new ArrayList<>();

    public IPValidator(final String source) {
        Objects.requireNonNull(source, "IP source should not be null");
        final String[] parts = source.split("[,;]");
        for (final String part : parts) {
            final String m = part.trim();
            String[] mask = this.ipv4(m);
            if (Objects.isNull(mask)) {
                mask = this.ipv6(m);
                if (Objects.isNull(mask)) {
                    log.warn("Invalid IP mask: '{}'", m);
                    continue;
                }
            }
            this.masks.add(mask);
        }
    }

    public boolean validate(final String value) {
        if (StringUtils.isBlank(value) || this.whiteListedIPs.contains(value)) {
            return true;
        }
        if (this.masks.isEmpty()) {
            return true;
        }

        String[] ipv = ipv4(value);
        if (Objects.isNull(ipv)) {
            ipv = ipv6(value);
            if (Objects.isNull(ipv)) {
                log.warn("IP format not supported: '{}'", value);
                return true;
            }
        }

        for (final String[] mask : masks) {
            if (match(mask, ipv)) {
                return true;
            }
        }
        return false;
    }

    private boolean match(final String[] mask, final String[] ip) {
        if (mask.length != ip.length) {
            return false;
        }
        for (int j = 0; j < mask.length; j++) {
            final String mp = mask[j];
            if (!mp.equals("*") && !ip[j].equals(mp)) {
                return false;
            }
        }
        return true;
    }

    private static String[] ipv4(final String ip) {
        final String[] ipp = ip.split("\\.");
        if (ipp.length != 4) {
            return null;
        }
        return ipp;
    }

    private static String[] ipv6(final String ip) {
        final String[] ipp = ip.split(":");
        if (ipp.length != 8) {
            return null;
        }
        return ipp;
    }
}
