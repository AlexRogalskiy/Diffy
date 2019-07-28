package com.wildbeeslabs.sensiblemetrics.diffy.generator.service.function;

import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.GenerationStatus;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.ComponentizedGenerator;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.SourceOfRandomness;

import java.util.function.ToDoubleFunction;

import static com.wildbeeslabs.sensiblemetrics.diffy.generator.utils.Lambdas.makeLambda;

/**
 * Produces values of type {@link ToDoubleFunction}.
 *
 * @param <T> type of parameter of produced function
 */
public class ToDoubleFunctionGenerator<T> extends ComponentizedGenerator<ToDoubleFunction> {
    public ToDoubleFunctionGenerator() {
        super(ToDoubleFunction.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public ToDoubleFunction<T> generate(SourceOfRandomness random, GenerationStatus status) {
        return makeLambda(ToDoubleFunction.class, gen().type(double.class), status);
    }

    @Override
    public int numberOfNeededComponents() {
        return 1;
    }
}
