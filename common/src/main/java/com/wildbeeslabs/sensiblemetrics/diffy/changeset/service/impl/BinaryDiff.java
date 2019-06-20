package com.wildbeeslabs.sensiblemetrics.diffy.changeset.service.impl;

import com.wildbeeslabs.sensiblemetrics.diffy.stream.entry.BinaryDiffResult;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Compares the binary content of two inputStreams/paths.
 */
public class BinaryDiff {

    public BinaryDiffResult diff(final File actual, final byte[] expected) throws IOException {
        return this.diff(actual.toPath(), expected);
    }

    public BinaryDiffResult diff(final Path actual, final byte[] expected) throws IOException {
        InputStream expectedStream = new ByteArrayInputStream(expected);
        InputStream actualStream = null;
        boolean threw = true;
        try {
            actualStream = Files.newInputStream(actual);
            BinaryDiffResult result = this.diff(actualStream, expectedStream);
            threw = false;
            return result;
        } finally {
            try {
                if (actualStream != null) actualStream.close();
            } catch (IOException e) {
                // Only rethrow if it doesn't shadow an exception thrown from the inner try block
                if (!threw) throw e;
            }
        }
    }

    public BinaryDiffResult diff(final InputStream actualStream, final InputStream expectedStream) throws IOException {
        int index = 0;
        while (true) {
            int actual = actualStream.read();
            int expected = expectedStream.read();
            if (actual == -1 && expected == -1) return BinaryDiffResult.noDiff();
            if (actual != expected) return new BinaryDiffResult(index, expected, actual);
            index += 1;
        }
    }
}
