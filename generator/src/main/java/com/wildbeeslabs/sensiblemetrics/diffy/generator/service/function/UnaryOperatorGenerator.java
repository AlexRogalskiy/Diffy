package com.wildbeeslabs.sensiblemetrics.diffy.generator.service.function;

import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.GenerationStatus;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.ComponentizedGenerator;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.SourceOfRandomness;

import java.util.function.UnaryOperator;

import static com.wildbeeslabs.sensiblemetrics.diffy.generator.utils.Lambdas.makeLambda;

/**
 * Produces values of type {@link UnaryOperator}.
 *
 * @param <T> type of parameter and return type of produced operator
 */
public class UnaryOperatorGenerator<T> extends ComponentizedGenerator<UnaryOperator> {
    public UnaryOperatorGenerator() {
        super(UnaryOperator.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public UnaryOperator<T> generate(SourceOfRandomness random, GenerationStatus status) {
        return makeLambda(UnaryOperator.class, componentGenerators().get(0), status);
    }

    @Override
    public int numberOfNeededComponents() {
        return 1;
    }
}
