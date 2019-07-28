package com.wildbeeslabs.sensiblemetrics.diffy.generator.service;

import com.google.common.reflect.TypeParameter;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.annotation.NullAllowed;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.GenerationStatus;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.Generators;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.AnnotatedType;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.wildbeeslabs.sensiblemetrics.diffy.generator.utils.Reflection.defaultValueOf;

class NullableGenerator<T> extends Generator<T> {
    private final Generator<T> delegate;
    private float probabilityOfNull = (Float) defaultValueOf(NullAllowed.class, "probability");

    NullableGenerator(Generator<T> delegate) {
        super(delegate.types());
        this.delegate = delegate;
    }

    @Override
    public T generate(SourceOfRandomness random, GenerationStatus status) {
        if (random.nextFloat(0.0f, 1.0f) < probabilityOfNull) {
            return null;
        } else {
            return delegate.generate(random, status);
        }
    }

    private void configure(NullAllowed nullAllowed) {
        if (nullAllowed.probability() >= 0.0f && nullAllowed.probability() <= 1.0f) {
            this.probabilityOfNull = nullAllowed.probability();
        } else {
            throw new IllegalArgumentException("NullAllowed probability must be in the [0,1] range");
        }
    }

    @Override
    public boolean canRegisterAsType(Class<?> type) {
        return delegate.canRegisterAsType(type);
    }

    @Override
    public boolean hasComponents() {
        return delegate.hasComponents();
    }

    @Override
    public int numberOfNeededComponents() {
        return delegate.numberOfNeededComponents();
    }

    @Override
    public void addComponentGenerators(List<Generator<?>> newComponents) {
        delegate.addComponentGenerators(newComponents);
    }

    @Override
    public boolean canGenerateForParametersOfTypes(List<TypeParameter<?>> typeParameters) {
        return delegate.canGenerateForParametersOfTypes(typeParameters);
    }

    @Override
    public void configure(AnnotatedType annotatedType) {
        Optional.ofNullable(annotatedType.getAnnotation(NullAllowed.class)).ifPresent(this::configure);
        delegate.configure(annotatedType);
    }

    @Override
    public void configure(AnnotatedElement element) {
        delegate.configure(element);
    }

    @Override
    public void provide(Generators provided) {
        delegate.provide(provided);
    }

    @Override
    public boolean canShrink(Object larger) {
        return delegate.canShrink(larger);
    }

    @Override
    public List<T> doShrink(SourceOfRandomness random, T larger) {
        return delegate.doShrink(random, larger);
    }

    @Override
    public BigDecimal magnitude(Object value) {
        return delegate.magnitude(value);
    }
}
