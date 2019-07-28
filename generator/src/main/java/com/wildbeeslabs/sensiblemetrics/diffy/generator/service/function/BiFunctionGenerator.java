package com.wildbeeslabs.sensiblemetrics.diffy.generator.service.function;

import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.GenerationStatus;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.ComponentizedGenerator;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.SourceOfRandomness;

import java.util.function.BiFunction;

import static com.wildbeeslabs.sensiblemetrics.diffy.generator.utils.Lambdas.makeLambda;

/**
 * Produces values of type {@link BiFunction}.
 *
 * @param <T> type of first parameter of produced function
 * @param <U> type of second parameter of produced function
 * @param <R> return type of produced function
 */
public class BiFunctionGenerator<T, U, R> extends ComponentizedGenerator<BiFunction> {
    public BiFunctionGenerator() {
        super(BiFunction.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public BiFunction<T, U, R> generate(SourceOfRandomness random, GenerationStatus status) {
        return makeLambda(BiFunction.class, componentGenerators().get(2), status);
    }

    @Override
    public int numberOfNeededComponents() {
        return 3;
    }
}
