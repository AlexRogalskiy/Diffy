//package com.wildbeeslabs.sensiblemetrics.diffy.generator.service.common;
//
//import com.wildbeeslabs.sensiblemetrics.diffy.generator.annotation.Distinct;
//import com.wildbeeslabs.sensiblemetrics.diffy.generator.annotation.Size;
//import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.GenerationStatus;
//import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.Shrink;
//import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.ComponentizedGenerator;
//import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.Generator;
//import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.SourceOfRandomness;
//import com.wildbeeslabs.sensiblemetrics.diffy.generator.utils.Lists;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//import java.util.stream.Stream;
//
//import static com.wildbeeslabs.sensiblemetrics.diffy.generator.utils.Lists.removeFrom;
//import static com.wildbeeslabs.sensiblemetrics.diffy.generator.utils.Lists.shrinksOfOneItem;
//import static com.wildbeeslabs.sensiblemetrics.diffy.generator.utils.Ranges.Type.INTEGRAL;
//import static com.wildbeeslabs.sensiblemetrics.diffy.generator.utils.Ranges.checkRange;
//import static com.wildbeeslabs.sensiblemetrics.diffy.generator.utils.Sequences.halving;
//import static java.math.BigDecimal.ZERO;
//import static java.util.stream.Collectors.toList;
//import static java.util.stream.StreamSupport.stream;
//
///**
// * <p>Base class for generators of {@link Collection}s.</p>
// *
// * <p>The generated collection has a number of elements limited by
// * {@link GenerationStatus#size()}, or else by the attributes of a {@link Size}
// * marking. The individual elements will have a type corresponding to the
// * collection's type argument.</p>
// *
// * @param <T> the type of collection generated
// */
//public abstract class CollectionGenerator<T extends Collection>
//    extends ComponentizedGenerator<T> {
//
//    private Size sizeRange;
//    private boolean distinct;
//
//    protected CollectionGenerator(Class<T> type) {
//        super(type);
//    }
//
//    /**
//     * <p>Tells this generator to add elements to the generated collection
//     * a number of times within a specified minimum and/or maximum, inclusive,
//     * chosen with uniform distribution.</p>
//     *
//     * <p>Note that some kinds of collections disallow duplicates, so the
//     * number of elements added may not be equal to the collection's
//     * {@link Collection#size()}.</p>
//     *
//     * @param size annotation that gives the size constraints
//     */
//    public void configure(Size size) {
//        this.sizeRange = size;
//        checkRange(INTEGRAL, size.min(), size.max());
//    }
//
//    /**
//     * Tells this generator to add elements which are distinct from each other.
//     *
//     * @param distinct Generated elements will be distinct if this param is
//     *                 not null
//     */
//    public void configure(Distinct distinct) {
//        this.distinct = distinct != null;
//    }
//
//    @SuppressWarnings("unchecked")
//    @Override
//    public T generate(
//        SourceOfRandomness random,
//        GenerationStatus status) {
//
//        int size = size(random, status);
//
//        Generator<?> generator = componentGenerators().get(0);
//        Stream<?> itemStream =
//            Stream.generate(() -> generator.generate(random, status))
//                .sequential();
//        if (distinct)
//            itemStream = itemStream.distinct();
//
//        T items = empty();
//        itemStream.limit(size).forEach(items::add);
//        return items;
//    }
//
//    @Override
//    public List<T> doShrink(SourceOfRandomness random, T larger) {
//        @SuppressWarnings("unchecked")
//        List<Object> asList = new ArrayList<>(larger);
//
//        List<T> shrinks = new ArrayList<>(removals(asList));
//
//        @SuppressWarnings("unchecked")
//        Shrink<Object> generator = (Shrink<Object>) componentGenerators().get(0);
//
//        Stream<List<Object>> oneItemShrinks =
//            shrinksOfOneItem(random, asList, generator)
//                .stream();
//        if (distinct)
//            oneItemShrinks = oneItemShrinks.filter(Lists::isDistinct);
//
//        shrinks.addAll(
//            oneItemShrinks
//                .map(this::convert)
//                .filter(this::inSizeRange)
//                .collect(toList()));
//
//        return shrinks;
//    }
//
//    @Override
//    public int numberOfNeededComponents() {
//        return 1;
//    }
//
//    @Override
//    public BigDecimal magnitude(Object value) {
//        Collection<?> narrowed = narrow(value);
//
//        if (narrowed.isEmpty())
//            return ZERO;
//
//        BigDecimal elementsMagnitude =
//            narrowed.stream()
//                .map(e -> componentGenerators().get(0).magnitude(e))
//                .reduce(ZERO, BigDecimal::add);
//        return BigDecimal.valueOf(narrowed.size()).multiply(elementsMagnitude);
//    }
//
////    protected final T empty() {
////        return instantiate(findConstructor(types().get(0)));
////    }
//
//    private boolean inSizeRange(T items) {
//        return sizeRange == null
//            || (items.size() >= sizeRange.min() && items.size() <= sizeRange.max());
//    }
//
//    private int size(SourceOfRandomness random, GenerationStatus status) {
//        return sizeRange != null
//            ? random.nextInt(sizeRange.min(), sizeRange.max())
//            : status.size();
//    }
//
//    private List<T> removals(List<?> items) {
//        return stream(halving(items.size()).spliterator(), false)
//            .map(i -> removeFrom(items, i))
//            .flatMap(Collection::stream)
//            .map(this::convert)
//            .filter(this::inSizeRange)
//            .collect(toList());
//    }
//
//    @SuppressWarnings("unchecked")
//    private T convert(List<?> items) {
//        T converted = empty();
//        converted.addAll(items);
//        return converted;
//    }
//}
