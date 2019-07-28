package com.wildbeeslabs.sensiblemetrics.diffy.generator.service;

import com.google.common.base.Optional;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.GenerationStatus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.math.BigDecimal.ZERO;
import static java.util.stream.Collectors.toList;

/**
 * Produces values of type {@link Optional}.
 */
public class OptionalGenerator extends ComponentizedGenerator<Optional> {
    public OptionalGenerator() {
        super(Optional.class);
    }

    @Override
    public Optional<?> generate(SourceOfRandomness random, GenerationStatus status) {
        double trial = random.nextDouble();
        return trial < 0.25
            ? Optional.absent()
            : Optional.fromNullable(componentGenerators().get(0).generate(random, status));
    }

    @Override
    public List<Optional> doShrink(
        SourceOfRandomness random,
        Optional larger) {

        if (!larger.isPresent())
            return new ArrayList<>();

        List<Optional> shrinks = new ArrayList<>();
        shrinks.add(Optional.absent());
        shrinks.addAll(
            componentGenerators().get(0)
                .shrink(random, larger.get())
                .stream()
                .map(Optional::fromNullable)
                .collect(toList()));
        return shrinks;
    }

    @Override
    public int numberOfNeededComponents() {
        return 1;
    }

    @Override
    public BigDecimal magnitude(Object value) {
        Optional<?> narrowed = narrow(value);

        return narrowed.toJavaUtil()
            .map(componentGenerators().get(0)::magnitude)
            .orElse(ZERO);
    }
}
