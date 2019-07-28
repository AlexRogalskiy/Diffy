package com.wildbeeslabs.sensiblemetrics.diffy.generator.service.function;

import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.GenerationStatus;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.ComponentizedGenerator;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.SourceOfRandomness;

import java.util.function.ToIntFunction;

import static com.wildbeeslabs.sensiblemetrics.diffy.generator.utils.Lambdas.makeLambda;

/**
 * Produces values of type {@link ToIntFunction}.
 *
 * @param <T> type of parameter of produced function
 */
public class ToIntFunctionGenerator<T> extends ComponentizedGenerator<ToIntFunction> {
    public ToIntFunctionGenerator() {
        super(ToIntFunction.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public ToIntFunction<T> generate(SourceOfRandomness random, GenerationStatus status) {
        return makeLambda(ToIntFunction.class, gen().type(int.class), status);
    }

    @Override
    public int numberOfNeededComponents() {
        return 1;
    }
}
