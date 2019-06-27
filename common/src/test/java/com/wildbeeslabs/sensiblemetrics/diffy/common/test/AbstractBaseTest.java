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
package com.wildbeeslabs.sensiblemetrics.diffy.common.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wildbeeslabs.sensiblemetrics.diffy.common.exception.BadOperationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Abstract base test implementation
 */
@Slf4j
@SuppressWarnings("unchecked")
public abstract class AbstractBaseTest {
    /**
     * Default charset
     */
    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    protected String asString(final Object value) {
        final ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(value);
        } catch (JsonProcessingException ex) {
            log.error("Cannot serialize entity = {}, message = {}", value, ex.getMessage());
        }
        return null;
    }

    protected InputStream getInputStream(final String fileName) {
        InputStream targetStream = null;
        try {
            final File initialFile = new File(fileName);
            targetStream = new FileInputStream(initialFile);
        } catch (IOException e) {
            BadOperationException.throwError(String.format("ERROR: cannot operate on file target = {%s}, message = {%s}", fileName, e.getMessage()), e);
        }
        return targetStream;
    }

    protected String getFileAsString(final String fileName) {
        return this.getFileAsString(fileName, DEFAULT_CHARSET);
    }

    protected String getFileAsString(final String fileName, final Charset charset) {
        String fileContent = null;
        try {
            final File file = new File(fileName);
            fileContent = FileUtils.readFileToString(file, charset);
        } catch (IOException e) {
            BadOperationException.throwError(String.format("ERROR: cannot operate on file target = {%s} with charset = {%s}, message = {%s}", fileName, charset, e.getMessage()), e);
        }
        return fileContent;
    }
}
