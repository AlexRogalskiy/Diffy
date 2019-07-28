package com.wildbeeslabs.sensiblemetrics.diffy.generator.service.function;

import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.GenerationStatus;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.ComponentizedGenerator;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.SourceOfRandomness;

import java.util.function.DoubleFunction;

import static com.wildbeeslabs.sensiblemetrics.diffy.generator.utils.Lambdas.makeLambda;

/**
 * Produces values of type {@link DoubleFunction}.
 *
 * @param <R> return type of produced function
 */
public class DoubleFunctionGenerator<R> extends ComponentizedGenerator<DoubleFunction> {
    public DoubleFunctionGenerator() {
        super(DoubleFunction.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public DoubleFunction<R> generate(SourceOfRandomness random, GenerationStatus status) {
        return makeLambda(DoubleFunction.class, componentGenerators().get(0), status);
    }

    @Override
    public int numberOfNeededComponents() {
        return 1;
    }
}
