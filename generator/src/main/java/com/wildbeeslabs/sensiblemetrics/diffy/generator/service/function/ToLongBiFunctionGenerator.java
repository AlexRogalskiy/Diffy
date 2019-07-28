package com.wildbeeslabs.sensiblemetrics.diffy.generator.service.function;

import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.GenerationStatus;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.ComponentizedGenerator;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.SourceOfRandomness;

import java.util.function.ToLongBiFunction;

import static com.wildbeeslabs.sensiblemetrics.diffy.generator.utils.Lambdas.makeLambda;

/**
 * Produces values of type {@link ToLongBiFunction}.
 *
 * @param <T> type of first parameter of produced function
 * @param <U> type of second parameter of produced function
 */
public class ToLongBiFunctionGenerator<T, U> extends ComponentizedGenerator<ToLongBiFunction> {
    public ToLongBiFunctionGenerator() {
        super(ToLongBiFunction.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public ToLongBiFunction<T, U> generate(SourceOfRandomness random, GenerationStatus status) {
        return makeLambda(ToLongBiFunction.class, gen().type(long.class), status);
    }

    @Override
    public int numberOfNeededComponents() {
        return 2;
    }
}
