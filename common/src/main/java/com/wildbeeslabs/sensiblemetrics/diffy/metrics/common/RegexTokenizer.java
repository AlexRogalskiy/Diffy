package com.wildbeeslabs.sensiblemetrics.diffy.metrics.common;

import com.wildbeeslabs.sensiblemetrics.diffy.metrics.iface.Tokenizer;
import com.wildbeeslabs.sensiblemetrics.diffy.utility.ValidationUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Regex {@link Tokenizer} implementation
 */
public class RegexTokenizer implements Tokenizer<CharSequence, CharSequence> {

    /**
     * {@inheritDoc}
     *
     * @throws IllegalArgumentException if the input text is blank
     */
    @Override
    public CharSequence[] tokenize(final CharSequence text) {
        ValidationUtils.isTrue(StringUtils.isNotBlank(text), "Invalid text");
        final Pattern pattern = Pattern.compile("(\\w)+");
        final Matcher matcher = pattern.matcher(text.toString());
        final List<String> tokens = new ArrayList<>();
        while (matcher.find()) {
            tokens.add(matcher.group(0));
        }
        return tokens.toArray(new String[0]);
    }
}
