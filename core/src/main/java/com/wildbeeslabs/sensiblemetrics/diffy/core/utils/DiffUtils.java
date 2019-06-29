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
package com.wildbeeslabs.sensiblemetrics.diffy.core.utils;

import com.wildbeeslabs.sensiblemetrics.diffy.common.entry.iface.Delta;
import com.wildbeeslabs.sensiblemetrics.diffy.common.entry.iface.Patch;
import com.wildbeeslabs.sensiblemetrics.diffy.common.entry.impl.DefaultChunk;
import com.wildbeeslabs.sensiblemetrics.diffy.common.entry.impl.DefaultPatch;
import com.wildbeeslabs.sensiblemetrics.diffy.common.utils.ValidationUtils;
import com.wildbeeslabs.sensiblemetrics.diffy.core.entry.delta.ChangeDelta;
import com.wildbeeslabs.sensiblemetrics.diffy.core.entry.utils.BinaryDiffResult;
import com.wildbeeslabs.sensiblemetrics.diffy.core.interfaces.DiffAlgorithm;
import com.wildbeeslabs.sensiblemetrics.diffy.core.service.DiffAlgorithmService;
import lombok.experimental.UtilityClass;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Diff utilities implementation
 */
@UtilityClass
public class DiffUtils {

    /**
     * Default unified diff chunk regex
     */
    private static final Pattern DEFAULT_DIFF_CHUNK_REGEX = Pattern.compile("^@@\\s+-(?:(\\d+)(?:,(\\d+))?)\\s+\\+(?:(\\d+)(?:,(\\d+))?)\\s+@@$");

    /**
     * Computes the difference between the original and revised list of elements
     * with default diff algorithm
     *
     * @param <T>      the type of elements.
     * @param original The original text. Must not be {@code null}.
     * @param revised  The revised text. Must not be {@code null}.
     * @return The patch describing the difference between the original and
     * revised sequences. Never {@code null}.
     */
    public static <T> Patch<T> diff(final List<T> original, final List<T> revised) {
        return diff(original, revised, new DiffAlgorithmService<>());
    }

    /**
     * Computes the difference between the original and revised list of elements
     * with default diff algorithm
     *
     * @param <T>       the type of elements.
     * @param original  The original text. Must not be {@code null}.
     * @param revised   The revised text. Must not be {@code null}.
     * @param algorithm The diff algorithm. Must not be {@code null}.
     * @return The patch describing the difference between the original and
     * revised sequences. Never {@code null}.
     * @throws IllegalArgumentException if original is {@code null}
     * @throws IllegalArgumentException if revised is {@code null}
     * @throws IllegalArgumentException if algorithm is {@code null}
     */
    public static <T> Patch<T> diff(final List<T> original, final List<T> revised, final DiffAlgorithm<T> algorithm) {
        ValidationUtils.notNull(original, "Original list should not be null");
        ValidationUtils.notNull(revised, "Revised list should not be null");
        ValidationUtils.notNull(algorithm, "Difference algorithm should not be null");

        return algorithm.diff(original, revised);
    }

    /**
     * DefaultPatch the original text with given patch
     *
     * @param <T>      the type of elements.
     * @param original the original text
     * @param patch    the given patch
     * @return the revised text
     * @throws IllegalStateException if can't apply patch
     */
    public static <T> Iterable<T> patch(final Iterable<T> original, final DefaultPatch<T> patch) {
        return patch.applyTo(original);
    }

    /**
     * Parse the given text in unified format and creates the list of deltas for
     * it.
     *
     * @param diff the text in unified format
     * @return the patch with deltas.
     */
    public static DefaultPatch<String> parseUnifiedDiff(final List<String> diff) {
        boolean inPrelude = true;
        final List<String[]> rawChunk = new ArrayList<>();
        DefaultPatch<String> patch = new DefaultPatch<>();

        int old_ln = 0;
        int new_ln = 0;
        String tag;
        String rest;
        for (final String line : diff) {
            if (inPrelude) {
                if (line.startsWith("+++")) {
                    inPrelude = false;
                }
                continue;
            }
            final Matcher m = DEFAULT_DIFF_CHUNK_REGEX.matcher(line);
            if (m.find()) {
                // Process the lines in the previous chunk
                if (!rawChunk.isEmpty()) {
                    List<String> oldChunkLines = new ArrayList<>();
                    List<String> newChunkLines = new ArrayList<>();

                    for (String[] raw_line : rawChunk) {
                        tag = raw_line[0];
                        rest = raw_line[1];
                        if (tag.equals(" ") || tag.equals("-")) {
                            oldChunkLines.add(rest);
                        }
                        if (tag.equals(" ") || tag.equals("+")) {
                            newChunkLines.add(rest);
                        }
                    }
                    patch.addDelta(new ChangeDelta<>(new DefaultChunk<>(old_ln - 1, oldChunkLines), new DefaultChunk<>(new_ln - 1, newChunkLines)));
                    rawChunk.clear();
                }
                old_ln = m.group(1) == null ? 1 : Integer.parseInt(m.group(1));
                new_ln = m.group(3) == null ? 1 : Integer.parseInt(m.group(3));

                if (old_ln == 0) {
                    old_ln += 1;
                }
                if (new_ln == 0) {
                    new_ln += 1;
                }
            } else {
                if (line.length() > 0) {
                    tag = line.substring(0, 1);
                    rest = line.substring(1);
                    if (tag.equals(" ") || tag.equals("+") || tag.equals("-")) {
                        rawChunk.add(new String[]{tag, rest});
                    }
                } else {
                    rawChunk.add(new String[]{" ", ""});
                }
            }
        }

        if (!rawChunk.isEmpty()) {
            final List<String> oldChunkLines = new ArrayList<>();
            final List<String> newChunkLines = new ArrayList<>();
            for (String[] raw_line : rawChunk) {
                tag = raw_line[0];
                rest = raw_line[1];
                if (tag.equals(" ") || tag.equals("-")) {
                    oldChunkLines.add(rest);
                }
                if (tag.equals(" ") || tag.equals("+")) {
                    newChunkLines.add(rest);
                }
            }
            patch.addDelta(new ChangeDelta<>(new DefaultChunk<>(old_ln - 1, oldChunkLines), new DefaultChunk<>(new_ln - 1, newChunkLines)));
            rawChunk.clear();
        }
        return patch;
    }

    /**
     * generateUnifiedDiff takes a DefaultPatch and some other arguments, returning the
     * Unified Diff format text representing the DefaultPatch.
     *
     * @param original      Filename of the original (unrevised file)
     * @param revised       Filename of the revised file
     * @param originalLines Lines of the original file
     * @param patch         DefaultPatch created by the diff() function
     * @param contextSize   number of lines of context output around each difference
     *                      in the file.
     * @return List of strings representing the Unified Diff representation of
     * the DefaultPatch argument.
     */
    public static List<String> generateUnifiedDiff(final String original, final String revised, final List<String> originalLines, final DefaultPatch<String> patch, int contextSize) {
        if (!patch.getDeltas().isEmpty()) {
            final List<String> ret = new ArrayList<>();
            ret.add("--- " + original);
            ret.add("+++ " + revised);

            final List<Delta<String>> patchDeltas = new ArrayList<>(patch.getDeltas());
            final List<Delta<String>> deltas = new ArrayList<>();
            Delta<String> delta = patchDeltas.get(0);
            deltas.add(delta);
            if (patchDeltas.size() > 1) {
                for (int i = 1; i < patchDeltas.size(); i++) {
                    int position = delta.getOriginal().getPosition();
                    final Delta<String> nextDelta = patchDeltas.get(i);
                    if ((position + delta.getOriginal().size() + contextSize) >= (nextDelta.getOriginal().getPosition() - contextSize)) {
                        deltas.add(nextDelta);
                    } else {
                        final List<String> curBlock = processDeltas(originalLines, deltas, contextSize);
                        ret.addAll(curBlock);
                        deltas.clear();
                        deltas.add(nextDelta);
                    }
                    delta = nextDelta;
                }
            }
            final List<String> curBlock = processDeltas(originalLines, deltas, contextSize);
            ret.addAll(curBlock);
            return ret;
        }
        return Collections.emptyList();
    }

    /**
     * processDeltas takes a list of Deltas and outputs them together in a
     * single block of Unified-Diff-format text.
     *
     * @param origLines   the lines of the original file
     * @param deltas      the Deltas to be output as a single block
     * @param contextSize the number of lines of context to place around block
     */
    private static List<String> processDeltas(final List<String> origLines, final List<Delta<String>> deltas, int contextSize) {
        final List<String> buffer = new ArrayList<>();
        int origTotal = 0;
        int revTotal = 0;
        int line;

        Delta<String> curDelta = deltas.get(0);
        int origStart = curDelta.getOriginal().getPosition() + 1 - contextSize;
        if (origStart < 1) {
            origStart = 1;
        }
        int revStart = curDelta.getRevised().getPosition() + 1 - contextSize;
        if (revStart < 1) {
            revStart = 1;
        }
        int contextStart = curDelta.getOriginal().getPosition() - contextSize;
        if (contextStart < 0) {
            contextStart = 0;
        }

        for (line = contextStart; line < curDelta.getOriginal().getPosition(); line++) {
            buffer.add(" " + origLines.get(line));
            origTotal++;
            revTotal++;
        }

        buffer.addAll(getDeltaText(curDelta));
        origTotal += curDelta.getOriginal().getLines().size();
        revTotal += curDelta.getRevised().getLines().size();

        int deltaIndex = 1;
        while (deltaIndex < deltas.size()) {
            Delta<String> nextDelta = deltas.get(deltaIndex);
            int intermediateStart = curDelta.getOriginal().getPosition()
                + curDelta.getOriginal().getLines().size();
            for (line = intermediateStart; line < nextDelta.getOriginal()
                .getPosition(); line++) {
                buffer.add(" " + origLines.get(line));
                origTotal++;
                revTotal++;
            }
            buffer.addAll(getDeltaText(nextDelta));
            origTotal += nextDelta.getOriginal().getLines().size();
            revTotal += nextDelta.getRevised().getLines().size();
            curDelta = nextDelta;
            deltaIndex++;
        }

        contextStart = curDelta.getOriginal().getPosition()
            + curDelta.getOriginal().getLines().size();
        for (line = contextStart; (line < (contextStart + contextSize))
            && (line < origLines.size()); line++) {
            buffer.add(" " + origLines.get(line));
            origTotal++;
            revTotal++;
        }

        final String header = "@@ -" + origStart + "," + origTotal + " +" + revStart + "," + revTotal + " @@";
        buffer.add(0, header);
        return buffer;
    }

    /**
     * getDeltaText returns the lines to be added to the Unified Diff text from
     * the Delta parameter
     *
     * @param delta the Delta to output
     * @return list of String lines of code.
     */
    private static List<String> getDeltaText(final Delta<String> delta) {
        final List<String> buffer = new ArrayList<>();
        for (final String original : delta.getOriginal().getLines()) {
            buffer.add("-" + original);
        }
        for (final String original : delta.getRevised().getLines()) {
            buffer.add("+" + original);
        }
        return buffer;
    }

    public static BinaryDiffResult diff(final File actual, final byte[] expected) throws IOException {
        return diff(actual.toPath(), expected);
    }

    public static BinaryDiffResult diff(final Path actual, final byte[] expected) throws IOException {
        final InputStream expectedStream = new ByteArrayInputStream(expected);
        try (final InputStream actualStream = Files.newInputStream(actual)) {
            return diff(actualStream, expectedStream);
        }
    }

    public static BinaryDiffResult diff(final InputStream actualStream, final InputStream expectedStream) throws IOException {
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
