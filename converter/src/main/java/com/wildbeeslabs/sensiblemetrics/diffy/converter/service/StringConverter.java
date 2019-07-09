package com.wildbeeslabs.sensiblemetrics.diffy.converter.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringConverter extends AbstractConverter<String, List<String>> {

    @Override
    protected List<String> valueOf(final String value) {
        List<String> lines = Arrays.asList(value.split("[\\r\\n]"));
        // documents must be empty line terminated
        if (!lines.get(lines.size() - 1).trim().isEmpty()) {
            lines = new ArrayList<>(lines);
            lines.add("");
        }
        return lines;
    }
}
