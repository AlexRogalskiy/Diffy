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

import com.wildbeeslabs.sensiblemetrics.diffy.common.entry.iface.Delta;
import com.wildbeeslabs.sensiblemetrics.diffy.common.entry.iface.Patch;
import com.wildbeeslabs.sensiblemetrics.diffy.core.utils.DiffUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static com.wildbeeslabs.sensiblemetrics.diffy.common.utils.ServiceUtils.closeQuietly;
import static java.nio.file.Files.newBufferedReader;
import static org.apache.commons.collections.ListUtils.unmodifiableList;

@SuppressWarnings("unchecked")
public class Diff {

    public List<Delta<String>> diff(final InputStream actual, final InputStream expected) throws IOException {
        return diff(readerFor(actual), readerFor(expected));
    }

    public List<Delta<String>> diff(final File actual, final Charset actualCharset, final File expected, final Charset expectedCharset) throws IOException {
        return diff(actual.toPath(), actualCharset, expected.toPath(), expectedCharset);
    }

    public List<Delta<String>> diff(final Path actual, final Charset actualCharset, final Path expected, final Charset expectedCharset) throws IOException {
        return diff(newBufferedReader(actual, actualCharset), newBufferedReader(expected, expectedCharset));
    }

    public List<Delta<String>> diff(final File actual, final String expected, final Charset charset) throws IOException {
        return diff(actual.toPath(), expected, charset);
    }

    public List<Delta<String>> diff(final Path actual, final String expected, final Charset charset) throws IOException {
        return diff(newBufferedReader(actual, charset), readerFor(expected));
    }

    private BufferedReader readerFor(final InputStream stream) {
        return new BufferedReader(new InputStreamReader(stream, Charset.defaultCharset()));
    }

    private BufferedReader readerFor(String string) {
        return new BufferedReader(new StringReader(string));
    }

    private List<Delta<String>> diff(final BufferedReader actual, final BufferedReader expected) throws IOException {
        try {
            final List<String> actualLines = linesFromBufferedReader(actual);
            final List<String> expectedLines = linesFromBufferedReader(expected);

            final Patch<String> patch = DiffUtils.diff(expectedLines, actualLines);
            return unmodifiableList(patch.getDeltas());
        } finally {
            closeQuietly(actual, expected);
        }
    }

    private List<String> linesFromBufferedReader(final BufferedReader reader) throws IOException {
        String line;
        List<String> lines = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        return lines;
    }
}
