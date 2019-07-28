package com.wildbeeslabs.sensiblemetrics.diffy.generator.service;

import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.GenerationStatus;

import java.math.BigDecimal;
import java.util.List;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

/**
 * Produces values of type {@code boolean} or {@link Boolean}.
 */
public class BooleanGenerator extends Generator<Boolean> {
    private Boolean turnOffRandomness;

    @SuppressWarnings("unchecked")
    public BooleanGenerator() {
        super(asList(Boolean.class, boolean.class));
    }

    /**
     * <p>Tells this generator to generate the values {@code true} and
     * {@code false} on alternating requests.</p>
     *
     * <p>Without this configuration, {@code true} and {@code false} are
     * generated with approximately equal probability.</p>
     *
     * @param flag annotation to turn off random generation and replace it
     *             with alternating values
     */
    public void configure(final Boolean flag) {
        turnOffRandomness = flag;
    }

    @Override
    public Boolean generate(SourceOfRandomness random, GenerationStatus status) {
        return turnOffRandomness == null ? random.nextBoolean() : status.attempts() % 2 != 0;
    }

    @Override
    public List<Boolean> doShrink(SourceOfRandomness random, Boolean larger) {
        return larger ? singletonList(false) : emptyList();
    }

    @Override
    public BigDecimal magnitude(Object value) {
        return narrow(value) ? ONE : ZERO;
    }
}
