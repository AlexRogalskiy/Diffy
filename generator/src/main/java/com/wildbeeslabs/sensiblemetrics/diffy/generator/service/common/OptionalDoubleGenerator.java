package com.wildbeeslabs.sensiblemetrics.diffy.generator.service.common;

import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.GenerationStatus;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.InRange;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.DoubleGenerator;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.Generator;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.SourceOfRandomness;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

import static java.math.BigDecimal.ZERO;
import static java.util.stream.Collectors.toList;

/**
 * Produces values of type {@link OptionalDouble}.
 */
public class OptionalDoubleGenerator extends Generator<OptionalDouble> {
    private final DoubleGenerator doubleGenerator = new DoubleGenerator();

    public OptionalDoubleGenerator() {
        super(OptionalDouble.class);
    }

    /**
     * Tells this generator to produce values, when
     * {@link OptionalDouble#isPresent() present}, within a specified minimum
     * (inclusive) and/or maximum (exclusive) with uniform distribution.
     * <p>
     * {@link InRange#min} and {@link InRange#max} take precedence over
     * {@link InRange#minDouble()} and {@link InRange#maxDouble()},
     * if non-empty.
     *
     * @param range annotation that gives the range's constraints
     */
    public void configure(InRange range) {
        doubleGenerator.configure(range);
    }

    @Override
    public OptionalDouble generate(SourceOfRandomness random, GenerationStatus status) {
        double trial = random.nextDouble();
        return trial < 0.25
            ? OptionalDouble.empty()
            : OptionalDouble.of(doubleGenerator.generate(random, status));
    }

    @Override
    public List<OptionalDouble> doShrink(
        SourceOfRandomness random,
        OptionalDouble larger) {

        if (!larger.isPresent())
            return new ArrayList<>();

        List<OptionalDouble> shrinks = new ArrayList<>();
        shrinks.add(OptionalDouble.empty());
        shrinks.addAll(
            doubleGenerator.shrink(random, larger.getAsDouble())
                .stream()
                .map(OptionalDouble::of)
                .collect(toList()));
        return shrinks;
    }

    @Override
    public BigDecimal magnitude(Object value) {
        OptionalDouble narrowed = narrow(value);

        return narrowed.isPresent()
            ? doubleGenerator.magnitude(narrowed.getAsDouble())
            : ZERO;
    }
}
