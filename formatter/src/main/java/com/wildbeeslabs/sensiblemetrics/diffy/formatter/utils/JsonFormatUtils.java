/*
 * The MIT License
 *
 * Copyright 2018 WildBees Labs.
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

import lombok.experimental.UtilityClass;

/**
 * Json formatter utilities implementation
 *
 * @author alexander.rogalskiy
 * @version 1.0
 * @since 2017-12-12
 */
@UtilityClass
public class JsonFormatUtils {

    private static final String INDENT = "   ";

    private static final String NEW_LINE = System.getProperty("line.separator");

    private static final int MODE_SINGLE = 100;
    private static final int MODE_DOUBLE = 101;
    private static final int MODE_ESCAPE_SINGLE = 102;
    private static final int MODE_ESCAPE_DOUBLE = 103;
    private static final int MODE_BETWEEN = 104;

    private static void appendIndent(StringBuilder sb, int count) {
        for (; count > 0; --count) sb.append(INDENT);
    }

    public static String prettyPrint(String input) {

        input = input.replaceAll("[\\r\\n]", "");

        StringBuilder output = new StringBuilder(input.length() * 2);
        int mode = MODE_BETWEEN;
        int depth = 0;

        for (int i = 0; i < input.length(); ++i) {
            char ch = input.charAt(i);

            switch (mode) {
                case MODE_BETWEEN:
                    switch (ch) {
                        case '{':
                        case '[':
                            output.append(ch);
                            output.append(NEW_LINE);
                            appendIndent(output, ++depth);
                            break;
                        case '}':
                        case ']':
                            output.append(NEW_LINE);
                            appendIndent(output, --depth);
                            output.append(ch);
                            break;
                        case ',':
                            output.append(ch);
                            output.append(NEW_LINE);
                            appendIndent(output, depth);
                            break;
                        case ':':
                            output.append(" : ");
                            break;
                        case '\'':
                            output.append(ch);
                            mode = MODE_SINGLE;
                            break;
                        case '"':
                            output.append(ch);
                            mode = MODE_DOUBLE;
                            break;
                        case ' ':
                            break;
                        default:
                            output.append(ch);
                            break;
                    }
                    break;
                case MODE_ESCAPE_SINGLE:
                    output.append(ch);
                    mode = MODE_SINGLE;
                    break;
                case MODE_ESCAPE_DOUBLE:
                    output.append(ch);
                    mode = MODE_DOUBLE;
                    break;
                case MODE_SINGLE:
                    output.append(ch);
                    switch (ch) {
                        case '\'':
                            mode = MODE_BETWEEN;
                            break;
                        case '\\':
                            mode = MODE_ESCAPE_SINGLE;
                            break;
                    }
                    break;
                case MODE_DOUBLE:
                    output.append(ch);
                    switch (ch) {
                        case '"':
                            mode = MODE_BETWEEN;
                            break;
                        case '\\':
                            mode = MODE_ESCAPE_DOUBLE;
                            break;
                    }
                    break;
            }
        }
        return output.toString();
    }
}
