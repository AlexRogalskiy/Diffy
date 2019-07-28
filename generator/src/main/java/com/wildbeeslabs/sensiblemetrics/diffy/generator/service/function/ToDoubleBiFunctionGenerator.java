package com.wildbeeslabs.sensiblemetrics.diffy.generator.service.function;

import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.GenerationStatus;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.ComponentizedGenerator;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.SourceOfRandomness;

import java.util.function.ToDoubleBiFunction;

import static com.wildbeeslabs.sensiblemetrics.diffy.generator.utils.Lambdas.makeLambda;

/**
 * Produces values of type {@link ToDoubleBiFunction}.
 *
 * @param <T> type of first parameter of produced function
 * @param <U> type of second parameter of produced function
 */
public class ToDoubleBiFunctionGenerator<T, U> extends ComponentizedGenerator<ToDoubleBiFunction> {
    public ToDoubleBiFunctionGenerator() {
        super(ToDoubleBiFunction.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public ToDoubleBiFunction<T, U> generate(SourceOfRandomness random, GenerationStatus status) {
        return makeLambda(ToDoubleBiFunction.class, gen().type(double.class), status);
    }

    @Override
    public int numberOfNeededComponents() {
        return 2;
    }
}
