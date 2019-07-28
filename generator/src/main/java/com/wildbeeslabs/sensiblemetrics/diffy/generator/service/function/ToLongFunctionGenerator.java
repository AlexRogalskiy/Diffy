package com.wildbeeslabs.sensiblemetrics.diffy.generator.service.function;

import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.GenerationStatus;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.ComponentizedGenerator;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.SourceOfRandomness;

import java.util.function.ToLongFunction;

import static com.wildbeeslabs.sensiblemetrics.diffy.generator.utils.Lambdas.makeLambda;

/**
 * Produces values of type {@link ToLongFunction}.
 *
 * @param <T> type of parameter of produced function
 */
public class ToLongFunctionGenerator<T> extends ComponentizedGenerator<ToLongFunction> {
    public ToLongFunctionGenerator() {
        super(ToLongFunction.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public ToLongFunction<T> generate(SourceOfRandomness random, GenerationStatus status) {
        return makeLambda(ToLongFunction.class, gen().type(long.class), status);
    }

    @Override
    public int numberOfNeededComponents() {
        return 1;
    }
}
