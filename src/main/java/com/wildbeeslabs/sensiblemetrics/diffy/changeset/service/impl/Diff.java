package com.wildbeeslabs.sensiblemetrics.diffy.changeset.service.impl;

import com.wildbeeslabs.sensiblemetrics.diffy.common.entry.iface.Delta;
import com.wildbeeslabs.sensiblemetrics.diffy.common.entry.iface.Patch;
import com.wildbeeslabs.sensiblemetrics.diffy.changeset.utils.DiffUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static com.wildbeeslabs.sensiblemetrics.diffy.utility.ServiceUtils.closeQuietly;
import static java.nio.file.Files.newBufferedReader;
import static org.apache.commons.collections.ListUtils.unmodifiableList;

public class Diff {

    public List<Delta<String>> diff(InputStream actual, InputStream expected) throws IOException {
        return diff(readerFor(actual), readerFor(expected));
    }

    public List<Delta<String>> diff(File actual, Charset actualCharset, File expected, Charset expectedCharset) throws IOException {
        return diff(actual.toPath(), actualCharset, expected.toPath(), expectedCharset);
    }

    public List<Delta<String>> diff(Path actual, Charset actualCharset, Path expected, Charset expectedCharset) throws IOException {
        return diff(newBufferedReader(actual, actualCharset), newBufferedReader(expected, expectedCharset));
    }

    public List<Delta<String>> diff(File actual, String expected, Charset charset) throws IOException {
        return diff(actual.toPath(), expected, charset);
    }

    public List<Delta<String>> diff(Path actual, String expected, Charset charset) throws IOException {
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

    private List<String> linesFromBufferedReader(BufferedReader reader) throws IOException {
        String line;
        List<String> lines = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        return lines;
    }
}
