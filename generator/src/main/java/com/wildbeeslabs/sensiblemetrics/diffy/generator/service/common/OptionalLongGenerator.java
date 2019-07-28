package com.wildbeeslabs.sensiblemetrics.diffy.generator.service.common;

import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.GenerationStatus;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.InRange;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.Generator;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.LongGenerator;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.SourceOfRandomness;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalLong;

import static java.math.BigDecimal.ZERO;
import static java.util.stream.Collectors.toList;

/**
 * Produces values of type {@link OptionalLong}.
 */
public class OptionalLongGenerator extends Generator<OptionalLong> {
    private final LongGenerator longGenerator = new LongGenerator();

    public OptionalLongGenerator() {
        super(OptionalLong.class);
    }

    /**
     * Tells this generator to produce values, when
     * {@link OptionalLong#isPresent() present}, within a specified minimum
     * and/or maximum, inclusive, with uniform distribution.
     * <p>
     * {@link InRange#min} and {@link InRange#max} take precedence over
     * {@link InRange#minLong()} and {@link InRange#maxLong()}, if non-empty.
     *
     * @param range annotation that gives the range's constraints
     */
    public void configure(InRange range) {
        longGenerator.configure(range);
    }

    @Override
    public OptionalLong generate(
        SourceOfRandomness random,
        GenerationStatus status) {

        double trial = random.nextDouble();
        return trial < 0.25
            ? OptionalLong.empty()
            : OptionalLong.of(longGenerator.generate(random, status));
    }

    @Override
    public List<OptionalLong> doShrink(
        SourceOfRandomness random,
        OptionalLong larger) {

        if (!larger.isPresent())
            return new ArrayList<>();

        List<OptionalLong> shrinks = new ArrayList<>();
        shrinks.add(OptionalLong.empty());
        shrinks.addAll(
            longGenerator.shrink(random, larger.getAsLong())
                .stream()
                .map(OptionalLong::of)
                .collect(toList()));
        return shrinks;
    }

    @Override
    public BigDecimal magnitude(Object value) {
        OptionalLong narrowed = narrow(value);

        return narrowed.isPresent()
            ? BigDecimal.valueOf(narrowed.getAsLong())
            : ZERO;
    }
}
