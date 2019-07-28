package com.wildbeeslabs.sensiblemetrics.diffy.generator.service.function;

import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.GenerationStatus;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.ComponentizedGenerator;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.SourceOfRandomness;

import java.util.function.BinaryOperator;

import static com.wildbeeslabs.sensiblemetrics.diffy.generator.utils.Lambdas.makeLambda;

/**
 * Produces values of type {@link BinaryOperator}.
 *
 * @param <T> parameters type and return type of produced operator
 */
public class BinaryOperatorGenerator<T> extends ComponentizedGenerator<BinaryOperator> {
    public BinaryOperatorGenerator() {
        super(BinaryOperator.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public BinaryOperator<T> generate(SourceOfRandomness random, GenerationStatus status) {
        return makeLambda(BinaryOperator.class, componentGenerators().get(0), status);
    }

    @Override
    public int numberOfNeededComponents() {
        return 1;
    }
}
