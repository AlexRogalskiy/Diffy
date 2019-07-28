package com.wildbeeslabs.sensiblemetrics.diffy.generator.service.common;

import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.GenerationStatus;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.InRange;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.Generator;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.IntegerGenerator;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.SourceOfRandomness;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;

import static java.math.BigDecimal.ZERO;
import static java.util.stream.Collectors.toList;

/**
 * Produces values of type {@link OptionalInt}.
 */
public class OptionalIntGenerator extends Generator<OptionalInt> {
    private final IntegerGenerator integerGenerator = new IntegerGenerator();

    public OptionalIntGenerator() {
        super(OptionalInt.class);
    }

    /**
     * Tells this generator to produce values, when
     * {@link OptionalInt#isPresent() present}, within a specified minimum
     * and/or maximum, inclusive, with uniform distribution.
     * <p>
     * {@link InRange#min} and {@link InRange#max} take precedence over
     * {@link InRange#minInt()} and {@link InRange#maxInt()}, if non-empty.
     *
     * @param range annotation that gives the range's constraints
     */
    public void configure(InRange range) {
        integerGenerator.configure(range);
    }

    @Override
    public OptionalInt generate(
        SourceOfRandomness random,
        GenerationStatus status) {

        double trial = random.nextDouble();
        return trial < 0.25
            ? OptionalInt.empty()
            : OptionalInt.of(integerGenerator.generate(random, status));
    }

    @Override
    public List<OptionalInt> doShrink(
        SourceOfRandomness random,
        OptionalInt larger) {

        if (!larger.isPresent())
            return new ArrayList<>();

        List<OptionalInt> shrinks = new ArrayList<>();
        shrinks.add(OptionalInt.empty());
        shrinks.addAll(
            integerGenerator.shrink(random, larger.getAsInt())
                .stream()
                .map(OptionalInt::of)
                .collect(toList()));
        return shrinks;
    }

    @Override
    public BigDecimal magnitude(Object value) {
        OptionalInt narrowed = narrow(value);

        return narrowed.isPresent()
            ? BigDecimal.valueOf(narrowed.getAsInt())
            : ZERO;
    }
}
