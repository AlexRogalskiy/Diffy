package com.wildbeeslabs.sensiblemetrics.diffy.generator.service.function;

import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.GenerationStatus;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.ComponentizedGenerator;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.SourceOfRandomness;

import java.util.function.BiPredicate;

import static com.wildbeeslabs.sensiblemetrics.diffy.generator.utils.Lambdas.makeLambda;

/**
 * Produces values of type {@link BiPredicate}.
 *
 * @param <T> type of first parameter of produced predicate
 * @param <U> type of second parameter of produced predicate
 */
public class BiPredicateGenerator<T, U> extends ComponentizedGenerator<BiPredicate> {
    public BiPredicateGenerator() {
        super(BiPredicate.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public BiPredicate<T, U> generate(SourceOfRandomness random, GenerationStatus status) {
        return makeLambda(BiPredicate.class, gen().type(boolean.class), status);
    }

    @Override
    public int numberOfNeededComponents() {
        return 2;
    }
}
