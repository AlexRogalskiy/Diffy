package com.wildbeeslabs.sensiblemetrics.diffy.generator.service.function;

import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.GenerationStatus;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.ComponentizedGenerator;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.SourceOfRandomness;

import java.util.function.Function;

import static com.wildbeeslabs.sensiblemetrics.diffy.generator.utils.Lambdas.makeLambda;

/**
 * Produces values of type {@link Function}.
 *
 * @param <T> type of parameter of produced function
 * @param <R> return type of produced function
 */
public class FunctionGenerator<T, R> extends ComponentizedGenerator<Function> {
    public FunctionGenerator() {
        super(Function.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Function<T, R> generate(SourceOfRandomness random, GenerationStatus status) {
        return makeLambda(Function.class, componentGenerators().get(1), status);
    }

    @Override
    public int numberOfNeededComponents() {
        return 2;
    }
}
