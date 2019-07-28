package com.wildbeeslabs.sensiblemetrics.diffy.generator.service;

import com.google.common.base.Function;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.GenerationStatus;

import static com.wildbeeslabs.sensiblemetrics.diffy.generator.utils.Lambdas.makeLambda;

/**
 * Produces values of type {@code Function}.
 *
 * @param <F> parameter type of the generated functions
 * @param <T> return type of the generated functions
 */
public class FunctionGenerator<F, T> extends ComponentizedGenerator<Function> {
    public FunctionGenerator() {
        super(Function.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Function<F, T> generate(SourceOfRandomness random, GenerationStatus status) {
        return makeLambda(Function.class, componentGenerators().get(1), status);
    }

    @Override
    public int numberOfNeededComponents() {
        return 2;
    }
}
