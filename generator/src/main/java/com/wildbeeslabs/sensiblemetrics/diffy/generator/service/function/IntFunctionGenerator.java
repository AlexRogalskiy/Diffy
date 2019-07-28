package com.wildbeeslabs.sensiblemetrics.diffy.generator.service.function;

import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.GenerationStatus;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.ComponentizedGenerator;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.SourceOfRandomness;

import java.util.function.IntFunction;

import static com.wildbeeslabs.sensiblemetrics.diffy.generator.utils.Lambdas.makeLambda;

/**
 * Produces values of type {@link IntFunction}.
 *
 * @param <R> return type of produced function
 */
public class IntFunctionGenerator<R> extends ComponentizedGenerator<IntFunction> {
    public IntFunctionGenerator() {
        super(IntFunction.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public IntFunction<R> generate(SourceOfRandomness random, GenerationStatus status) {
        return makeLambda(IntFunction.class, componentGenerators().get(0), status);
    }

    @Override
    public int numberOfNeededComponents() {
        return 1;
    }
}
