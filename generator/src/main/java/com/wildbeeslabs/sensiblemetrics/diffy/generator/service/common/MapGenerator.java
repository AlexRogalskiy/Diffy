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
//import java.util.AbstractMap.SimpleEntry;
//import java.util.*;
//import java.util.Map.Entry;
//import java.util.stream.Stream;
//
//import static com.wildbeeslabs.sensiblemetrics.diffy.generator.utils.Lists.removeFrom;
//import static com.wildbeeslabs.sensiblemetrics.diffy.generator.utils.Lists.shrinksOfOneItem;
//import static com.wildbeeslabs.sensiblemetrics.diffy.generator.utils.Ranges.Type.INTEGRAL;
//import static com.wildbeeslabs.sensiblemetrics.diffy.generator.utils.Ranges.checkRange;
//import static com.wildbeeslabs.sensiblemetrics.diffy.generator.utils.Sequences.halving;
//import static java.util.stream.Collectors.toList;
//import static java.util.stream.StreamSupport.stream;
//
///**
// * <p>Base class for generators of {@link Map}s.</p>
// *
// * <p>The generated map has a number of entries limited by
// * {@link GenerationStatus#size()}, or else by the attributes of a {@link Size}
// * marking. The individual keys and values will have types corresponding to the
// * property parameter's type arguments.</p>
// *
// * @param <T> the type of map generated
// */
//public abstract class MapGenerator<T extends Map> extends ComponentizedGenerator<T> {
//    private Size sizeRange;
//    private boolean distinct;
//
//    protected MapGenerator(Class<T> type) {
//        super(type);
//    }
//
//    @Override
//    public void addComponentGenerators(List<Generator<?>> newComponents) {
//        super.addComponentGenerators(newComponents);
//    }
//
//    /**
//     * <p>Tells this generator to add key-value pairs to the generated map a
//     * number of times within a specified minimum and/or maximum, inclusive,
//     * chosen with uniform distribution.</p>
//     *
//     * <p>Note that maps disallow duplicate keys, so the number of pairs added
//     * may not be equal to the map's {@link Map#size()}.</p>
//     *
//     * @param size annotation that gives the size constraints
//     */
//    public void configure(Size size) {
//        this.sizeRange = size;
//        checkRange(INTEGRAL, size.min(), size.max());
//    }
//
//    /**
//     * Tells this generator to add entries whose keys are distinct from
//     * each other.
//     *
//     * @param distinct Keys of generated entries will be distinct if this
//     *                 param is not null
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
//        Generator<?> keyGenerator = componentGenerators().get(0);
//        Stream<?> keyStream =
//            Stream.generate(() -> keyGenerator.generate(random, status))
//                .sequential();
//        if (distinct)
//            keyStream = keyStream.distinct();
//
//        T items = empty();
//        Generator<?> valueGenerator = componentGenerators().get(1);
//        keyStream
//            .map(key -> new SimpleEntry(key, valueGenerator.generate(random, status)))
//            .filter(entry -> okToAdd(entry.getKey(), entry.getValue()))
//            .limit(size)
//            .forEach(entry -> items.put(entry.getKey(), entry.getValue()));
//
//        return items;
//    }
//
//    @Override
//    public List<T> doShrink(SourceOfRandomness random, T larger) {
//        @SuppressWarnings("unchecked")
//        List<Entry<?, ?>> entries = new ArrayList<>(larger.entrySet());
//
//        List<T> shrinks = new ArrayList<>(removals(entries));
//
//        @SuppressWarnings("unchecked")
//        Shrink<Entry<?, ?>> entryShrink = entryShrinker(
//            (Shrink<Object>) componentGenerators().get(0),
//            (Shrink<Object>) componentGenerators().get(1));
//
//        Stream<List<Entry<?, ?>>> oneEntryShrinks =
//            shrinksOfOneItem(random, entries, entryShrink)
//                .stream();
//        if (distinct)
//            oneEntryShrinks = oneEntryShrinks.filter(MapGenerator::isKeyDistinct);
//
//        shrinks.addAll(
//            oneEntryShrinks
//                .map(this::convert)
//                .filter(this::inSizeRange)
//                .collect(toList()));
//
//        return shrinks;
//    }
//
//    @Override
//    public int numberOfNeededComponents() {
//        return 2;
//    }
//
//    @Override
//    public BigDecimal magnitude(Object value) {
//        Map<?, ?> narrowed = narrow(value);
//
//        if (narrowed.isEmpty())
//            return BigDecimal.ZERO;
//
//        BigDecimal keysMagnitude =
//            narrowed.keySet().stream()
//                .map(e -> componentGenerators().get(0).magnitude(e))
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//        BigDecimal valuesMagnitude =
//            narrowed.values().stream()
//                .map(e -> componentGenerators().get(1).magnitude(e))
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//        return BigDecimal.valueOf(narrowed.size())
//            .multiply(keysMagnitude)
//            .add(valuesMagnitude);
//    }
//
////    protected final T empty() {
////        return instantiate(findConstructor(types().get(0)));
////    }
//
//    protected boolean okToAdd(Object key, Object value) {
//        return true;
//    }
//
//    private boolean inSizeRange(T target) {
//        return sizeRange == null
//            || (target.size() >= sizeRange.min() && target.size() <= sizeRange.max());
//    }
//
//    private int size(SourceOfRandomness random, GenerationStatus status) {
//        return sizeRange != null
//            ? random.nextInt(sizeRange.min(), sizeRange.max())
//            : status.size();
//    }
//
//    private List<T> removals(List<Entry<?, ?>> items) {
//        return stream(halving(items.size()).spliterator(), false)
//            .map(i -> removeFrom(items, i))
//            .flatMap(Collection::stream)
//            .map(this::convert)
//            .filter(this::inSizeRange)
//            .collect(toList());
//    }
//
//    @SuppressWarnings("unchecked")
//    private <K, V> Map<K, V> convert(List<Entry<K, V>> entries) {
//        final Map<K, V> converted = Collections.emptyMap();
//        for (final Entry<K, V> each : entries) {
//            converted.put(each.getKey(), each.getValue());
//        }
//        return converted;
//    }
//
//    private Shrink<Entry<?, ?>> entryShrinker(
//        Shrink<Object> keyShrinker,
//        Shrink<Object> valueShrinker) {
//
//        return (r, e) -> {
//            @SuppressWarnings("unchecked")
//            Entry<Object, Object> entry = (Entry<Object, Object>) e;
//
//            List<Object> keyShrinks = keyShrinker.shrink(r, entry.getKey());
//            List<Object> valueShrinks = valueShrinker.shrink(r, entry.getValue());
//            List<Entry<?, ?>> shrinks = new ArrayList<>();
//            shrinks.addAll(
//                keyShrinks.stream()
//                    .map(k -> new SimpleEntry<>(k, entry.getValue()))
//                    .collect(toList()));
//            shrinks.addAll(
//                valueShrinks.stream()
//                    .map(v -> new SimpleEntry<>(entry.getKey(), v))
//                    .collect(toList()));
//
//            return shrinks;
//        };
//    }
//
//    private static boolean isKeyDistinct(List<Entry<?, ?>> entries) {
//        return Lists.isDistinct(
//            entries.stream()
//                .map(Entry::getKey)
//                .collect(toList()));
//    }
//}
