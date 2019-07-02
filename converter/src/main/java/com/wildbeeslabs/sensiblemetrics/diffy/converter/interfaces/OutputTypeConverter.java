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
package com.wildbeeslabs.sensiblemetrics.diffy.converter.interfaces;

import com.wildbeeslabs.sensiblemetrics.diffy.common.exception.InvalidOperationException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import java.util.Objects;

/**
 * Defines the output type for a screenshot.
 *
 * @param <T> type of output item
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
public interface OutputTypeConverter<T> {
    /**
     * Obtain the screenshot as base64 data.
     */
    OutputTypeConverter<String> BASE64 = new OutputTypeConverter<String>() {
        @Override
        public String convertFromBase64Png(final String base64Png) {
            return base64Png;
        }

        @Override
        public String convertFromPngBytes(final byte[] png) {
            return Base64.getEncoder().encodeToString(png);
        }

        @Override
        public String toString() {
            return "OutputTypeConverter.BASE64";
        }
    };

    /**
     * Obtain the screenshot as raw bytes.
     */
    OutputTypeConverter<byte[]> BYTES = new OutputTypeConverter<byte[]>() {
        @Override
        public byte[] convertFromBase64Png(final String base64Png) {
            return Base64.getMimeDecoder().decode(base64Png);
        }

        @Override
        public byte[] convertFromPngBytes(final byte[] png) {
            return png;
        }

        @Override
        public String toString() {
            return "OutputTypeConverter.BYTES";
        }
    };

    /**
     * Obtain the screenshot into a temporary file that will be deleted once the JVM exits. It is up
     * to users to make a copy of this file.
     */
    OutputTypeConverter<File> FILE = new OutputTypeConverter<File>() {
        @Override
        public File convertFromBase64Png(final String base64Png) {
            return this.save(BYTES.convertFromBase64Png(base64Png));
        }

        @Override
        public File convertFromPngBytes(final byte[] data) {
            return this.save(data);
        }

        private File save(final byte[] data) {
            OutputStream stream = null;
            try {
                final File tmpFile = File.createTempFile("screenshot", ".png");
                tmpFile.deleteOnExit();
                stream = new FileOutputStream(tmpFile);
                stream.write(data);
                return tmpFile;
            } catch (IOException e) {
                throw new InvalidOperationException(e);
            } finally {
                if (Objects.nonNull(stream)) {
                    try {
                        stream.close();
                    } catch (IOException e) {
                    }
                }
            }
        }

        @Override
        public String toString() {
            return "OutputTypeConverter.FILE";
        }
    };

    /**
     * Convert the given base64 png to a requested format.
     *
     * @param base64Png base64 encoded png.
     * @return png encoded into requested format.
     */
    T convertFromBase64Png(final String base64Png);

    /**
     * Convert the given png to a requested format.
     *
     * @param png an array of bytes forming a png file.
     * @return png encoded into requested format.
     */
    T convertFromPngBytes(final byte[] png);
}
