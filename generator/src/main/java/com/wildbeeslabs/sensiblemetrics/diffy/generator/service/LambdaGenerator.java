package com.wildbeeslabs.sensiblemetrics.diffy.generator.service;

import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.GenerationStatus;

import static com.wildbeeslabs.sensiblemetrics.diffy.generator.utils.Lambdas.makeLambda;

public class LambdaGenerator<T, U> extends Generator<T> {
    private final Class<T> lambdaType;
    private final Generator<U> returnValueGenerator;

    public LambdaGenerator(Class<T> lambdaType, Generator<U> returnValueGenerator) {
        super(lambdaType);

        this.lambdaType = lambdaType;
        this.returnValueGenerator = returnValueGenerator;
    }

    @Override
    public T generate(SourceOfRandomness random, GenerationStatus status) {
        return makeLambda(lambdaType, returnValueGenerator, status);
    }
}
