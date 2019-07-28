package com.wildbeeslabs.sensiblemetrics.diffy.generator.service;

import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.GenerationStatus;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.InRange;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.utils.Comparables;

import java.math.BigDecimal;
import java.util.List;

import static com.wildbeeslabs.sensiblemetrics.diffy.generator.utils.Reflection.defaultValueOf;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

/**
 * Produces values of type {@code char} or {@link Character}.
 */
public class CharacterGenerator extends Generator<Character> {
    private char min = (Character) defaultValueOf(InRange.class, "minChar");
    private char max = (Character) defaultValueOf(InRange.class, "maxChar");

    @SuppressWarnings("unchecked")
    public CharacterGenerator() {
        super(asList(Character.class, char.class));
    }

    /**
     * Tells this generator to produce values within a specified minimum and/or
     * maximum, inclusive, with uniform distribution.
     * <p>
     * {@link InRange#min} and {@link InRange#max} take precedence over
     * {@link InRange#minChar()} and {@link InRange#maxChar()}, if non-empty.
     *
     * @param range annotation that gives the range's constraints
     */
    public void configure(InRange range) {
        min = range.min().isEmpty() ? range.minChar() : range.min().charAt(0);
        max = range.max().isEmpty() ? range.maxChar() : range.max().charAt(0);
    }

    @Override
    public Character generate(SourceOfRandomness random, GenerationStatus status) {
        return random.nextChar(min, max);
    }

    @Override
    public List<Character> doShrink(SourceOfRandomness random, Character larger) {
        return new CodePointShrink(cp -> cp >= min && cp <= max)
            .shrink(random, (int) larger)
            .stream()
            .map((Integer cp) -> (char) cp.intValue())
            .filter(this::inRange)
            .collect(toList());
    }

    @Override
    public BigDecimal magnitude(Object value) {
        return BigDecimal.valueOf(narrow(value));
    }

    private boolean inRange(Character value) {
        return Comparables.inRange(min, max).test(value);
    }
}
