package com.wildbeeslabs.sensiblemetrics.diffy.generator.service.function;

import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.GenerationStatus;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.ComponentizedGenerator;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.SourceOfRandomness;

import java.util.function.LongFunction;

import static com.wildbeeslabs.sensiblemetrics.diffy.generator.utils.Lambdas.makeLambda;

/**
 * Produces values of type {@link LongFunction}.
 *
 * @param <R> return type of produced function
 */
public class LongFunctionGenerator<R> extends ComponentizedGenerator<LongFunction> {
    public LongFunctionGenerator() {
        super(LongFunction.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public LongFunction<R> generate(SourceOfRandomness random, GenerationStatus status) {
        return makeLambda(LongFunction.class, componentGenerators().get(0), status);
    }

    @Override
    public int numberOfNeededComponents() {
        return 1;
    }
}
