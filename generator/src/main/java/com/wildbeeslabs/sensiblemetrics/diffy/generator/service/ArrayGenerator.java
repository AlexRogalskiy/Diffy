package com.wildbeeslabs.sensiblemetrics.diffy.generator.service;

import com.wildbeeslabs.sensiblemetrics.diffy.generator.annotation.Distinct;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.annotation.Size;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.GenerationStatus;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.Generators;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.Shrink;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.utils.Lists;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.wildbeeslabs.sensiblemetrics.diffy.generator.utils.Lists.removeFrom;
import static com.wildbeeslabs.sensiblemetrics.diffy.generator.utils.Lists.shrinksOfOneItem;
import static com.wildbeeslabs.sensiblemetrics.diffy.generator.utils.Ranges.Type.INTEGRAL;
import static com.wildbeeslabs.sensiblemetrics.diffy.generator.utils.Ranges.checkRange;
import static com.wildbeeslabs.sensiblemetrics.diffy.generator.utils.Reflection.annotatedComponentTypes;
import static com.wildbeeslabs.sensiblemetrics.diffy.generator.utils.Sequences.halving;
import static java.math.BigDecimal.ZERO;
import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;

public class ArrayGenerator extends Generator<Object> {
    private final Class<?> componentType;
    private final Generator<?> component;

    private Size lengthRange;
    private boolean distinct;

    ArrayGenerator(Class<?> componentType, Generator<?> component) {
        super(Object.class);

        this.componentType = componentType;
        this.component = component;
    }

    /**
     * Tells this generator to produce values with a length within a specified
     * minimum and/or maximum, inclusive, chosen with uniform distribution.
     *
     * @param size annotation that gives the length constraints
     */
    public void configure(Size size) {
        this.lengthRange = size;
        checkRange(INTEGRAL, size.min(), size.max());
    }

    /**
     * Tells this generator to produce values which are distinct from each
     * other.
     *
     * @param distinct Generated values will be distinct if this param is not
     *                 null.
     */
    public void configure(Distinct distinct) {
        this.distinct = distinct != null;
    }

    @Override
    public Object generate(
        SourceOfRandomness random,
        GenerationStatus status) {

        int length = length(random, status);
        Object array = Array.newInstance(componentType, length);

        Stream<?> items =
            Stream.generate(() -> component.generate(random, status))
                .sequential();
        if (distinct)
            items = items.distinct();

        Iterator<?> iterator = items.iterator();
        for (int i = 0; i < length; ++i)
            Array.set(array, i, iterator.next());

        return array;
    }

    @Override
    public boolean canShrink(Object larger) {
        return larger.getClass().getComponentType() == componentType;
    }

    @Override
    public List<Object> doShrink(SourceOfRandomness random, Object larger) {
        int length = Array.getLength(larger);
        List<Object> asList = new ArrayList<>();
        for (int i = 0; i < length; ++i)
            asList.add(Array.get(larger, i));

        List<Object> shrinks = new ArrayList<>(removals(asList));

        @SuppressWarnings("unchecked")
        Stream<List<Object>> oneItemShrinks =
            shrinksOfOneItem(random, asList, (Shrink<Object>) component)
                .stream();
        if (distinct)
            oneItemShrinks = oneItemShrinks.filter(Lists::isDistinct);

        shrinks.addAll(
            oneItemShrinks
                .map(this::convert)
                .filter(this::inLengthRange)
                .collect(toList()));

        return shrinks;
    }

    @Override
    public void provide(Generators provided) {
        super.provide(provided);

        component.provide(provided);
    }

    @Override
    public BigDecimal magnitude(Object value) {
        int length = Array.getLength(value);
        if (length == 0)
            return ZERO;

        BigDecimal elementsMagnitude =
            IntStream.range(0, length)
                .mapToObj(i -> component.magnitude(Array.get(value, i)))
                .reduce(ZERO, BigDecimal::add);
        return BigDecimal.valueOf(length).multiply(elementsMagnitude);
    }

    @Override
    public void configure(AnnotatedType annotatedType) {
        super.configure(annotatedType);

        List<AnnotatedType> annotated = annotatedComponentTypes(annotatedType);
        if (!annotated.isEmpty())
            component.configure(annotated.get(0));
    }

    private int length(SourceOfRandomness random, GenerationStatus status) {
        return lengthRange != null
            ? random.nextInt(lengthRange.min(), lengthRange.max())
            : status.size();
    }

    private boolean inLengthRange(Object items) {
        int length = Array.getLength(items);
        return lengthRange == null
            || (length >= lengthRange.min() && length <= lengthRange.max());
    }

    private List<Object> removals(List<?> items) {
        return stream(halving(items.size()).spliterator(), false)
            .map(i -> removeFrom(items, i))
            .flatMap(Collection::stream)
            .map(this::convert)
            .filter(this::inLengthRange)
            .collect(toList());
    }

    private Object convert(List<?> items) {
        Object array = Array.newInstance(componentType, items.size());
        for (int i = 0; i < items.size(); ++i)
            Array.set(array, i, items.get(i));
        return array;
    }
}
