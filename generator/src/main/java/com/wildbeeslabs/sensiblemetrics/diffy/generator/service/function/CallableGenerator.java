package com.wildbeeslabs.sensiblemetrics.diffy.generator.service.function;

import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.GenerationStatus;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.ComponentizedGenerator;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.SourceOfRandomness;

import java.util.concurrent.Callable;

import static com.wildbeeslabs.sensiblemetrics.diffy.generator.utils.Lambdas.makeLambda;

/**
 * Produces values of type {@code Callable}.
 *
 * @param <V> the type of the values produced by the generated instances
 */
public class CallableGenerator<V> extends ComponentizedGenerator<Callable> {
    public CallableGenerator() {
        super(Callable.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Callable<V> generate(SourceOfRandomness random, GenerationStatus status) {
        return makeLambda(Callable.class, componentGenerators().get(0), status);
    }

    @Override
    public int numberOfNeededComponents() {
        return 1;
    }
}
