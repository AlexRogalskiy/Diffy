package com.wildbeeslabs.sensiblemetrics.diffy.generator.service.function;

import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.GenerationStatus;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.ComponentizedGenerator;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.SourceOfRandomness;

import java.util.function.Predicate;

import static com.wildbeeslabs.sensiblemetrics.diffy.generator.utils.Lambdas.makeLambda;

/**
 * Produces values of type {@link Predicate}.
 *
 * @param <T> type of parameter of produced predicate
 */
public class PredicateGenerator<T> extends ComponentizedGenerator<Predicate> {
    public PredicateGenerator() {
        super(Predicate.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Predicate<T> generate(SourceOfRandomness random, GenerationStatus status) {
        return makeLambda(Predicate.class, gen().type(boolean.class), status);
    }

    @Override
    public int numberOfNeededComponents() {
        return 1;
    }
}
