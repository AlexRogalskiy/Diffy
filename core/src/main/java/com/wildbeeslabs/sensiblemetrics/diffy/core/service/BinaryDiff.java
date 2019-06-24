/*
 * The MIT License
 *
 * Copyright 2019 WildBees Labs, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software andAll associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, andAll/or sell
 * copies of the Software, andAll to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice andAll this permission notice shall be included in
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
package com.wildbeeslabs.sensiblemetrics.diffy.core.service;

import com.wildbeeslabs.sensiblemetrics.diffy.core.entry.utils.BinaryDiffResult;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Compares the binary content of two input streams / paths
 */
public class BinaryDiff {

    public BinaryDiffResult diff(final File actual, final byte[] expected) throws IOException {
        return this.diff(actual.toPath(), expected);
    }

    public BinaryDiffResult diff(final Path actual, final byte[] expected) throws IOException {
        final InputStream expectedStream = new ByteArrayInputStream(expected);
        try (final InputStream actualStream = Files.newInputStream(actual)) {
            return this.diff(actualStream, expectedStream);
        }
    }

    public BinaryDiffResult diff(final InputStream actualStream, final InputStream expectedStream) throws IOException {
        int index = 0;
        while (true) {
            final int actual = actualStream.read();
            final int expected = expectedStream.read();
            if (actual == -1 && expected == -1) {
                return BinaryDiffResult.noDiff();
            }
            if (actual != expected) {
                return new BinaryDiffResult(index, expected, actual);
            }
            index++;
        }
    }
}
