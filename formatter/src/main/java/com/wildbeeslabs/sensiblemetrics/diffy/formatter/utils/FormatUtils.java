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
package com.wildbeeslabs.sensiblemetrics.diffy.formatter.utils;

import com.wildbeeslabs.sensiblemetrics.diffy.formatter.exception.ExcessiveOrMissingFormatArgumentException;
import com.wildbeeslabs.sensiblemetrics.diffy.formatter.exception.IllegalFormatConversionCategoryException;
import com.wildbeeslabs.sensiblemetrics.diffy.formatter.enumeration.ConversionCategory;
import lombok.experimental.UtilityClass;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class provides a collection of utilities to ease working with format strings.
 */
@UtilityClass
public class FormatUtils {

    private static class Conversion {
        private final int index;
        private final ConversionCategory cath;

        public Conversion(char c, int index) {
            this.index = index;
            this.cath = ConversionCategory.fromConversionChar(c);
        }

        int index() {
            return index;
        }

        ConversionCategory category() {
            return cath;
        }
    }

    /**
     * Returns if the format string is satisfiable, and if the format's parameters match the passed
     * {@link ConversionCategory}s. Otherwise an {@link Error} is thrown.
     *
     * <p>TODO introduce more such functions, see RegexUtil for examples
     */
    public static String asFormat(String format, ConversionCategory... cc)
        throws IllegalFormatException {
        ConversionCategory[] fcc = formatParameterCategories(format);
        if (fcc.length != cc.length) {
            throw new ExcessiveOrMissingFormatArgumentException(cc.length, fcc.length);
        }

        for (int i = 0; i < cc.length; i++) {
            if (cc[i] != fcc[i]) {
                throw new IllegalFormatConversionCategoryException(cc[i], fcc[i]);
            }
        }

        return format;
    }

    /**
     * Throws an exception if the format is not syntactically valid.
     */
    public static void tryFormatSatisfiability(String format) throws IllegalFormatException {
        String.format(format, (Object[]) null);
    }

    /**
     * Returns a {@link ConversionCategory} for every conversion found in the format string.
     *
     * <p>Throws an exception if the format is not syntactically valid.
     */
    public static ConversionCategory[] formatParameterCategories(String format) throws IllegalFormatException {
        tryFormatSatisfiability(format);

        int last = -1; // index of last argument referenced
        int lasto = -1; // last ordinary index
        int maxindex = -1;

        final Conversion[] cs = parse(format);
        final Map<Integer, ConversionCategory> conv = new HashMap<>();

        for (final Conversion c : cs) {
            int index = c.index();
            switch (index) {
                case -1: // relative index
                    break;
                case 0: // ordinary index
                    lasto++;
                    last = lasto;
                    break;
                default: // explicit index
                    last = index - 1;
                    break;
            }
            maxindex = Math.max(maxindex, last);
            conv.put(
                last,
                ConversionCategory.intersect(
                    conv.containsKey(last) ? conv.get(last) : ConversionCategory.UNUSED,
                    c.category()));
        }

        final ConversionCategory[] res = new ConversionCategory[maxindex + 1];
        for (int i = 0; i <= maxindex; ++i) {
            res[i] = conv.containsKey(i) ? conv.get(i) : ConversionCategory.UNUSED;
        }
        return res;
    }

    // %[argument_index$][flags][width][.precision][t]conversion
    private static final String formatSpecifier = "%(\\d+\\$)?([-#+ 0,(\\<]*)?(\\d+)?(\\.\\d+)?([tT])?([a-zA-Z%])";

    private static Pattern fsPattern = Pattern.compile(formatSpecifier);

    private static int indexFromFormat(final Matcher m) {
        int index;
        String s = m.group(1);
        if (Objects.nonNull(s)) {
            index = Integer.parseInt(s.substring(0, s.length() - 1));
        } else {
            if (m.group(2) != null && m.group(2).contains(String.valueOf('<'))) {
                index = -1;
            } else {
                index = 0;
            }
        }
        return index;
    }

    private static char conversionCharFromFormat(final Matcher m) {
        String dt = m.group(5);
        if (Objects.isNull(dt)) {
            return m.group(6).charAt(0);
        }
        return dt.charAt(0);
    }

    private static Conversion[] parse(final String format) {
        ArrayList<Conversion> cs = new ArrayList<>();
        Matcher m = fsPattern.matcher(format);
        while (m.find()) {
            char c = conversionCharFromFormat(m);
            switch (c) {
                case '%':
                case 'n':
                    break;
                default:
                    cs.add(new Conversion(c, indexFromFormat(m)));
            }
        }
        return cs.toArray(new Conversion[cs.size()]);
    }
}
