package com.wildbeeslabs.sensiblemetrics.diffy.generator.service.function;

import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.GenerationStatus;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.ComponentizedGenerator;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.SourceOfRandomness;

import java.util.function.Supplier;

import static com.wildbeeslabs.sensiblemetrics.diffy.generator.utils.Lambdas.makeLambda;

/**
 * Produces values of type {@code Supplier}.
 *
 * @param <T> the type of the values produced by the generated instances
 */
public class SupplierGenerator<T> extends ComponentizedGenerator<Supplier> {
    public SupplierGenerator() {
        super(Supplier.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Supplier<T> generate(SourceOfRandomness random, GenerationStatus status) {
        return makeLambda(Supplier.class, componentGenerators().get(0), status);
    }

    @Override
    public int numberOfNeededComponents() {
        return 1;
    }
}
