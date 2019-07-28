package com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces;

import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.Generator;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.SourceOfRandomness;

import java.lang.reflect.Field;
import java.lang.reflect.Parameter;

/**
 * An access point for available generators.
 */
public interface Generators {
    /**
     * <p>Gives a generator that can produce instances of one of a given set of
     * related types on every generation.</p>
     *
     * <p>On each generation, one of the available generators that can produce
     * one of the named types will be chosen with (approximately) equal
     * probability.</p>
     *
     * @param <T>   type of objects produced by the resulting generator
     * @param first first type of generated value
     * @param rest  other (related) types of generated values
     * @return generator that can produce values of the given types
     */
    <T> Generator<T> oneOf(final Class<? extends T> first, final Class<? extends T>... rest);

    /**
     * <p>Gives a generator that can produce instances of one of a given set of
     * related types on every generation.</p>
     *
     * <p>On each generation, one of the given generators will be called upon
     * with (approximately) equal probability.</p>
     *
     * @param <T>   type of objects produced by the resulting generator
     * @param first first generator
     * @param rest  other generators
     * @return generator that can produce values using the given generators
     */
    <T> Generator<T> oneOf(final Generator<? extends T> first, final Generator<? extends T>... rest);

    /**
     * <p>Gives a generator that can produce an instance of the type described
     * by the field with the given name and containing type.</p>
     *
     * <p>If the field is marked with an annotation that influences the
     * generation of a given kind of value, that annotation will be applied to
     * the resulting generator's values.</p>
     *
     * @param type      containing type for a field
     * @param fieldName name of a field
     * @return generator that can produce values of the field's type
     */
    Generator<?> field(final Class<?> type, final String fieldName);

    /**
     * <p>Gives a generator that can produce an instance of the type described
     * by a constructor of a given type that accepts the given types of
     * arguments. It will generate values for the parameters of the given
     * constructor, and then invoke the constructor.</p>
     *
     * <p>If the constructor's parameters are marked with annotations that
     * influence the generation of a given kind of value, those annotations
     * will be applied to the generators that will produce values to be used
     * as arguments to the constructor.</p>
     *
     * @param <T>           type of objects produced by the resulting generator
     * @param type          containing type for a constructor
     * @param argumentTypes types of arguments to the constructor
     * @return generator that can produce values using the constructor
     */
    <T> Generator<T> constructor(final Class<T> type, final Class<?>... argumentTypes);

    /**
     * <p>Gives a generator that can produce an instance of the given type
     * by reflecting the class's fields on up its class hierarchy and
     * generating random values for them.</p>
     *
     * <p>The given type must have an accessible zero-arg constructor.</p>
     *
     * <p>If a field of the given type is marked with an annotation that
     * influences the generation of a given kind of value, that annotation
     * will be applied to the generation of values for that field.</p>
     *
     * @param <T>  type of objects produced by the resulting generator
     * @param type a type
     * @return generator that can produce values of that type
     */
    <T> Generator<T> fieldsOf(final Class<T> type);

    /**
     * <p>Gives a generator that can produce values of the given type,
     * parameterized by the given "component" types.</p>
     *
     * @param <T>            type of objects produced by the resulting generator
     * @param type           a type
     * @param componentTypes types for the "components" of the type, if any
     * @return generator that can produce values of that type
     */
    <T> Generator<T> type(final Class<T> type, final Class<?>... componentTypes);

    /**
     * <p>Gives a generator that can produce instances of the type of the
     * given reflected method parameter.</p>
     *
     * <p>If the parameter is marked with an annotation that influences the
     * generation of its value, that annotation will be applied to the
     * generation of values for that parameter's type.</p>
     *
     * @param parameter a reflected method parameter
     * @return generator that can produce values of the parameter's type
     */
    Generator<?> parameter(final Parameter parameter);

    /**
     * <p>Gives a generator that can produce instances of the type of the
     * given reflected field.</p>
     *
     * <p>If the field is marked with an annotation that influences the
     * generation of its value, that annotation will be applied to the
     * generation of values for that field's type.</p>
     *
     * @param field a reflected field
     * @return generator that can produce values of the field's type
     */
    Generator<?> field(final Field field);

    /**
     * <p>Makes a new generator of the given type, provides it with access to
     * other available generators,
     * {@linkplain Generator#configure(java.lang.reflect.AnnotatedType)
     * configures} it with any configuration annotations present on the
     * generator type, and supplies it any
     * {@linkplain Generator#hasComponents() component generators} it needs.
     *
     * @param <T>                 type of objects produced by the resulting generator
     * @param genType             type of generator to create
     * @param componentGenerators any generators for components of the type
     * @return a generator for producing values
     */
    <T extends Generator<?>> T make(final Class<T> genType, final Generator<?>... componentGenerators);

    /**
     * <p>Makes a generator access point just like the receiver, but which
     * uses the given source of randomness for making random choices.</p>
     *
     * <p>Intended for use by junit-quickcheck.</p>
     *
     * @param random a source of randomness
     * @return a generator access point that has the source of randomness
     * available to it
     */
    Generators withRandom(final SourceOfRandomness random);
}
