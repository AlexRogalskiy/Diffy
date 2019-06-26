/*
 * The MIT License
 *
 * Copyright 2017 WildBees Labs.
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
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.zip.GZIPInputStream;

import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Helper class to handle string format operations
 *
 * @author alexander.rogalskiy
 * @version 1.0
 * @since 2017-12-12
 */
@UtilityClass
public class StringFormatterUtils {
    /**
     * Default UTF8 byte masks
     */
    private static final int UTF8_MULTI_BYTES_MASK = 0x0080;
    private static final int UTF8_TWO_BYTES_MASK = 0x00E0;
    private static final int UTF8_TWO_BYTES = 0x00C0;
    private static final int UTF8_THREE_BYTES_MASK = 0x00F0;
    private static final int UTF8_THREE_BYTES = 0x00E0;
    private static final int UTF8_FOUR_BYTES_MASK = 0x00F8;
    private static final int UTF8_FOUR_BYTES = 0x00F0;
    private static final int UTF8_FIVE_BYTES_MASK = 0x00FC;
    private static final int UTF8_FIVE_BYTES = 0x00F8;
    private static final int UTF8_SIX_BYTES_MASK = 0x00FE;
    private static final int UTF8_SIX_BYTES = 0x00FC;

    /**
     * Default char masks
     */
    private static final int CHAR_ONE_BYTE_MASK = 0xFFFFFF80;
    private static final int CHAR_TWO_BYTES_MASK = 0xFFFFF800;
    private static final int CHAR_THREE_BYTES_MASK = 0xFFFF0000;
    private static final int CHAR_FOUR_BYTES_MASK = 0xFFE00000;
    private static final int CHAR_FIVE_BYTES_MASK = 0xFC000000;
    private static final int CHAR_SIX_BYTES_MASK = 0x80000000;

    /**
     * UnGzips compressed string to raw format string output
     *
     * @param str input string.
     * @return String Unzip raw string.
     * @throws Exception On unzip operation.
     * @see Exception
     */
    public static String ungzip(final String str) throws Exception {
        if (Objects.isNull(str)) {
            return null;
        }
        return ungzip(str.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * UnGzips compressed array of bytes to raw format string output
     *
     * @param bytes input array of bytes.
     * @return String Unzip raw string.
     * @throws Exception On unzip operation.
     * @see Exception
     */
    public static String ungzip(final byte[] bytes) throws Exception {
        if (isGZIPStream(bytes)) {
            InputStreamReader isr = new InputStreamReader(new GZIPInputStream(new ByteArrayInputStream(bytes)), StandardCharsets.UTF_8);
            final StringWriter sw = new StringWriter();
            char[] chars = new char[1024];
            for (int len; (len = isr.read(chars)) > 0; ) {
                sw.write(chars, 0, len);
            }
            return sw.toString();
        } else {
            return (new String(bytes, 0, bytes.length, StandardCharsets.UTF_8));
        }
    }

    /**
     * Checks whether input array of bytes is GZIP formatted or not
     *
     * @param bytes input array of bytes.
     * @return boolean true - if GZIP formatted, false - otherwise.
     */
    private static boolean isGZIPStream(final byte[] bytes) {
        if (null == bytes || 0 == bytes.length) {
            return false;
        }
        return (bytes[0] == (byte) GZIPInputStream.GZIP_MAGIC)
            && (bytes[1] == (byte) (GZIPInputStream.GZIP_MAGIC >>> 8));
    }

    /**
     * Converts input string from ISO-8859-1 to UTF-8 format
     *
     * @param value input string.
     * @return String result string.
     */
    public static String convertFromLatin1ToUtf8(final String value) {
        return convert(value, ISO_8859_1, UTF_8);
    }

    /**
     * Converts input string from UTF-8 to ISO-8859-1 format
     *
     * @param value input string.
     * @return String result string.
     */
    public static String convertFromUtf8ToLatin1(final String value) {
        return convert(value, UTF_8, ISO_8859_1);
    }

    /**
     * Converts input string from Windows-1251 to UTF-8 format
     *
     * @param value input string.
     * @return String result string.
     */
    public static String convertFromCp1251ToUtf8(final String value) {
        return convert(value, Charset.forName("Cp1251"), UTF_8);
    }

    /**
     * Converts input string from UTF-8 to Windows-1251 format
     *
     * @param value input string.
     * @return String result string.
     */
    public static String convertUtf8ToCp1251(final String value) {
        return convert(value, UTF_8, Charset.forName("Cp1251"));
    }

    /**
     * Converts string from input to output character encoding
     *
     * @param value         input string.
     * @param inputCharset  input character encoding,
     * @param outputCharset output character encoding.
     * @return
     */
    private static String convert(final String value, final Charset inputCharset, final Charset outputCharset) {
        if (Objects.isNull(value)) {
            return null;
        }
        return new String(value.getBytes(inputCharset), outputCharset);
    }

    /**
     * Returns an UTF-8 encoded String
     *
     * @param bytes  The byte array to be transformed to
     * @param length The length of the byte array to be converted
     * @return encode string.
     */
    public static final String convertToUtf8(byte[] bytes, int length) {
        if (Objects.isNull(bytes)) {
            return StringUtils.EMPTY;
        }
        return new String(bytes, 0, length, StandardCharsets.UTF_8);
    }

    public static final byte[] getBytesUtf8(final String value) {
        if (Objects.isNull(value)) {
            return new byte[0];
        }
        return value.getBytes(StandardCharsets.UTF_8);
    }

    /**
     * Returns the UniCode char of the byte at position 0.
     *
     * @param bytes The byte[] representation of an UniCode string.
     * @return The first char found.
     */
    public static final char bytesToChar(byte[] bytes) {
        return bytesToChar(bytes, 0);
    }

    /**
     * Return the UniCode char which is coded in the bytes at the given
     * position.
     *
     * @param bytes The byte[] representation of an UniCode string.
     * @param pos   The current position to start decoding the char
     * @return The decoded char, or -1 if no char can be decoded TODO : Should
     * stop after the third byte, as a char is only 2 bytes long.
     */
    public static final char bytesToChar(byte[] bytes, int pos) {
        if (bytes == null) {
            return (char) -1;
        }

        if ((bytes[pos] & UTF8_MULTI_BYTES_MASK) == 0) {
            return (char) bytes[pos];
        } else {
            if ((bytes[pos] & UTF8_TWO_BYTES_MASK) == UTF8_TWO_BYTES) {
                // Two bytes char
                return (char) (((bytes[pos] & 0x1C) << 6) + // 110x-xxyy
                    // 10zz-zzzz
                    // ->
                    // 0000-0xxx
                    // 0000-0000
                    ((bytes[pos] & 0x03) << 6) + // 110x-xxyy 10zz-zzzz
                    // -> 0000-0000
                    // yy00-0000
                    (bytes[pos + 1] & 0x3F) // 110x-xxyy 10zz-zzzz -> 0000-0000
                    // 00zz-zzzz
                ); // -> 0000-0xxx yyzz-zzzz (07FF)
            } else if ((bytes[pos] & UTF8_THREE_BYTES_MASK) == UTF8_THREE_BYTES) {
                // Three bytes char
                return (char) ( // 1110-tttt 10xx-xxyy 10zz-zzzz -> tttt-0000-0000-0000
                    ((bytes[pos] & 0x0F) << 12)
                        + // 1110-tttt 10xx-xxyy 10zz-zzzz -> 0000-xxxx-0000-0000
                        ((bytes[pos + 1] & 0x3C) << 6)
                        + // 1110-tttt 10xx-xxyy 10zz-zzzz -> 0000-0000-yy00-0000
                        ((bytes[pos + 1] & 0x03) << 6)
                        + // 1110-tttt 10xx-xxyy 10zz-zzzz -> 0000-0000-00zz-zzzz
                        (bytes[pos + 2] & 0x3F) // -> tttt-xxxx yyzz-zzzz (FF FF)
                );
            } else if ((bytes[pos] & UTF8_FOUR_BYTES_MASK) == UTF8_FOUR_BYTES) {
                // Four bytes char
                return (char) ( // 1111-0ttt 10uu-vvvv 10xx-xxyy 10zz-zzzz -> 000t-tt00
                    // 0000-0000 0000-0000
                    ((bytes[pos] & 0x07) << 18)
                        + // 1111-0ttt 10uu-vvvv 10xx-xxyy 10zz-zzzz -> 0000-00uu
                        // 0000-0000 0000-0000
                        ((bytes[pos + 1] & 0x30) << 16)
                        + // 1111-0ttt 10uu-vvvv 10xx-xxyy 10zz-zzzz -> 0000-0000
                        // vvvv-0000 0000-0000
                        ((bytes[pos + 1] & 0x0F) << 12)
                        + // 1111-0ttt 10uu-vvvv 10xx-xxyy 10zz-zzzz -> 0000-0000
                        // 0000-xxxx 0000-0000
                        ((bytes[pos + 2] & 0x3C) << 6)
                        + // 1111-0ttt 10uu-vvvv 10xx-xxyy 10zz-zzzz -> 0000-0000
                        // 0000-0000 yy00-0000
                        ((bytes[pos + 2] & 0x03) << 6)
                        + // 1111-0ttt 10uu-vvvv 10xx-xxyy 10zz-zzzz -> 0000-0000
                        // 0000-0000 00zz-zzzz
                        (bytes[pos + 3] & 0x3F) // -> 000t-ttuu vvvv-xxxx yyzz-zzzz (1FFFFF)
                );
            } else if ((bytes[pos] & UTF8_FIVE_BYTES_MASK) == UTF8_FIVE_BYTES) {
                // Five bytes char
                return (char) ( // 1111-10tt 10uu-uuuu 10vv-wwww 10xx-xxyy 10zz-zzzz ->
                    // 0000-00tt 0000-0000 0000-0000 0000-0000
                    ((bytes[pos] & 0x03) << 24)
                        + // 1111-10tt 10uu-uuuu 10vv-wwww 10xx-xxyy 10zz-zzzz ->
                        // 0000-0000 uuuu-uu00 0000-0000 0000-0000
                        ((bytes[pos + 1] & 0x3F) << 18)
                        + // 1111-10tt 10uu-uuuu 10vv-wwww 10xx-xxyy 10zz-zzzz ->
                        // 0000-0000 0000-00vv 0000-0000 0000-0000
                        ((bytes[pos + 2] & 0x30) << 12)
                        + // 1111-10tt 10uu-uuuu 10vv-wwww 10xx-xxyy 10zz-zzzz ->
                        // 0000-0000 0000-0000 wwww-0000 0000-0000
                        ((bytes[pos + 2] & 0x0F) << 12)
                        + // 1111-10tt 10uu-uuuu 10vv-wwww 10xx-xxyy 10zz-zzzz ->
                        // 0000-0000 0000-0000 0000-xxxx 0000-0000
                        ((bytes[pos + 3] & 0x3C) << 6)
                        + // 1111-10tt 10uu-uuuu 10vv-wwww 10xx-xxyy 10zz-zzzz ->
                        // 0000-0000 0000-0000 0000-0000 yy00-0000
                        ((bytes[pos + 3] & 0x03) << 6)
                        + // 1111-10tt 10uu-uuuu 10vv-wwww 10xx-xxyy 10zz-zzzz ->
                        // 0000-0000 0000-0000 0000-0000 00zz-zzzz
                        (bytes[pos + 4] & 0x3F) // -> 0000-00tt uuuu-uuvv wwww-xxxx yyzz-zzzz (03 FF FF FF)
                );
            } else if ((bytes[pos] & UTF8_FIVE_BYTES_MASK) == UTF8_FIVE_BYTES) {
                // Six bytes char
                return (char) ( // 1111-110s 10tt-tttt 10uu-uuuu 10vv-wwww 10xx-xxyy 10zz-zzzz
                    // ->
                    // 0s00-0000 0000-0000 0000-0000 0000-0000
                    ((bytes[pos] & 0x01) << 30)
                        + // 1111-110s 10tt-tttt 10uu-uuuu 10vv-wwww 10xx-xxyy 10zz-zzzz
                        // ->
                        // 00tt-tttt 0000-0000 0000-0000 0000-0000
                        ((bytes[pos + 1] & 0x3F) << 24)
                        + // 1111-110s 10tt-tttt 10uu-uuuu 10vv-wwww 10xx-xxyy
                        // 10zz-zzzz ->
                        // 0000-0000 uuuu-uu00 0000-0000 0000-0000
                        ((bytes[pos + 2] & 0x3F) << 18)
                        + // 1111-110s 10tt-tttt 10uu-uuuu 10vv-wwww 10xx-xxyy
                        // 10zz-zzzz ->
                        // 0000-0000 0000-00vv 0000-0000 0000-0000
                        ((bytes[pos + 3] & 0x30) << 12)
                        + // 1111-110s 10tt-tttt 10uu-uuuu 10vv-wwww 10xx-xxyy
                        // 10zz-zzzz ->
                        // 0000-0000 0000-0000 wwww-0000 0000-0000
                        ((bytes[pos + 3] & 0x0F) << 12)
                        + // 1111-110s 10tt-tttt 10uu-uuuu 10vv-wwww 10xx-xxyy
                        // 10zz-zzzz ->
                        // 0000-0000 0000-0000 0000-xxxx 0000-0000
                        ((bytes[pos + 4] & 0x3C) << 6)
                        + // 1111-110s 10tt-tttt 10uu-uuuu 10vv-wwww 10xx-xxyy
                        // 10zz-zzzz ->
                        // 0000-0000 0000-0000 0000-0000 yy00-0000
                        ((bytes[pos + 4] & 0x03) << 6)
                        + // 1111-110s 10tt-tttt 10uu-uuuu 10vv-wwww 10xx-xxyy 10zz-zzzz
                        // ->
                        // 0000-0000 0000-0000 0000-0000 00zz-zzzz
                        (bytes[pos + 5] & 0x3F) // -> 0stt-tttt uuuu-uuvv wwww-xxxx yyzz-zzzz (7F FF FF FF)
                );
            } else {
                return (char) -1;
            }
        }
    }

    /**
     * Returns the UniCode char which is coded in the bytes at the given
     * position.
     *
     * @param c The character to be transformed to an array of bytes
     * @return The byte array representing the char
     */
    public static final byte[] charToBytes(char c) {
        byte[] bytes = new byte[countNbBytesPerChar(c)];
        if ((c | 0x7F) == 0x7F) {
            // Single byte char
            bytes[0] = (byte) c;
            return bytes;
        } else if ((c | 0x7F) == 0x7FF) {
            // two bytes char
            bytes[0] = (byte) (0x00C0 + ((c & 0x07C0) >> 6));
            bytes[1] = (byte) (0x0080 + (c & 0x3F));
        } else {
            // Three bytes char
            bytes[0] = (byte) (0x00E0 + ((c & 0xF000) >> 12));
            bytes[1] = (byte) (0x0080 + ((c & 0x0FC0) >> 6));
            bytes[2] = (byte) (0x0080 + (c & 0x3F));
        }
        return bytes;
    }

    /**
     * Returns the number of bytes that hold an UniCode char.
     *
     * @param c The character to be decoded
     * @return The number of bytes to hold the char. TODO : Should stop after
     * the third byte, as a char is only 2 bytes long.
     */
    public static final int countNbBytesPerChar(char c) {
        if ((c & CHAR_ONE_BYTE_MASK) == 0) {
            return 1;
        } else if ((c & CHAR_TWO_BYTES_MASK) == 0) {
            return 2;
        } else if ((c & CHAR_THREE_BYTES_MASK) == 0) {
            return 3;
        } else if ((c & CHAR_FOUR_BYTES_MASK) == 0) {
            return 4;
        } else if ((c & CHAR_FIVE_BYTES_MASK) == 0) {
            return 5;
        } else if ((c & CHAR_SIX_BYTES_MASK) == 0) {
            return 6;
        } else {
            return -1;
        }
    }
}
