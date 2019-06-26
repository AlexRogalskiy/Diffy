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
import org.w3c.dom.Document;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Format an XML String with indent = 2 space.
 * <p>
 * Very much inspired by http://stackoverflow.com/questions/139076/how-to-pretty-print-xml-from-java and
 * http://pastebin.com/XL7932aC
 * </p>
 */
@UtilityClass
public class XmlFormatterUtils {
    /**
     * Default format error message
     */
    private static final String FORMAT_ERROR = "Unable to format XML string";

    public static String xmlPrettyFormat(final String xmlStringToFormat) {
        checkArgument(xmlStringToFormat != null, "Expecting XML String not to be null");
        return prettyFormat(toXmlDocument(xmlStringToFormat), xmlStringToFormat.startsWith("<?xml"));
    }

    private static String prettyFormat(final Document document, boolean keepXmlDeclaration) {
        try {
            DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
            DOMImplementationLS domImplementation = (DOMImplementationLS) registry.getDOMImplementation("LS");
            Writer stringWriter = new StringWriter();
            LSOutput formattedOutput = domImplementation.createLSOutput();
            formattedOutput.setCharacterStream(stringWriter);
            LSSerializer domSerializer = domImplementation.createLSSerializer();
            domSerializer.getDomConfig().setParameter("format-pretty-print", true);
            // Set this to true if the declaration is needed to be in the output.
            domSerializer.getDomConfig().setParameter("xml-declaration", keepXmlDeclaration);
            domSerializer.write(document, formattedOutput);
            return stringWriter.toString();
        } catch (Exception e) {
            throw new RuntimeException(FORMAT_ERROR, e);
        }
    }

    private static Document toXmlDocument(final String xmlString) {
        try {
            InputSource xmlInputSource = new InputSource(new StringReader(xmlString));
            DocumentBuilder xmlDocumentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            return xmlDocumentBuilder.parse(xmlInputSource);
        } catch (Exception e) {
            throw new RuntimeException(FORMAT_ERROR, e);
        }
    }
}
