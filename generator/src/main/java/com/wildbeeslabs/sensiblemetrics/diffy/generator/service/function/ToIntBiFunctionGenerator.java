package com.wildbeeslabs.sensiblemetrics.diffy.generator.service.function;

import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.GenerationStatus;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.ComponentizedGenerator;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.SourceOfRandomness;

import java.util.function.ToIntBiFunction;

import static com.wildbeeslabs.sensiblemetrics.diffy.generator.utils.Lambdas.makeLambda;

/**
 * Produces values of type {@link ToIntBiFunction}.
 *
 * @param <T> type of first parameter of produced function
 * @param <U> type of second parameter of produced function
 */
public class ToIntBiFunctionGenerator<T, U> extends ComponentizedGenerator<ToIntBiFunction> {
    public ToIntBiFunctionGenerator() {
        super(ToIntBiFunction.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public ToIntBiFunction<T, U> generate(SourceOfRandomness random, GenerationStatus status) {
        return makeLambda(ToIntBiFunction.class, gen().type(int.class), status);
    }

    @Override
    public int numberOfNeededComponents() {
        return 2;
    }
}
