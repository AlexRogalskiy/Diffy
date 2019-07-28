package com.wildbeeslabs.sensiblemetrics.diffy.generator.service;

import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.GenerationStatus;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.utils.Lists;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.wildbeeslabs.sensiblemetrics.diffy.generator.utils.Lists.shrinksOfOneItem;
import static com.wildbeeslabs.sensiblemetrics.diffy.generator.utils.Sequences.halving;
import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;

/**
 * <p>Base class for generators of values of type {@link String}.</p>
 *
 * <p>The generated values will have {@linkplain String#length()} decided by
 * {@link GenerationStatus#size()}.</p>
 */
public abstract class AbstractStringGenerator extends Generator<String> {
    protected AbstractStringGenerator() {
        super(String.class);
    }

    @Override
    public String generate(SourceOfRandomness random, GenerationStatus status) {
        int[] codePoints = new int[status.size()];

        for (int i = 0; i < codePoints.length; ++i)
            codePoints[i] = nextCodePoint(random);

        return new String(codePoints, 0, codePoints.length);
    }

    @Override
    public boolean canShrink(Object larger) {
        return super.canShrink(larger) && codePointsInRange((String) larger);
    }

    @Override
    public List<String> doShrink(SourceOfRandomness random, String larger) {
        List<String> shrinks = new ArrayList<>();

        List<Integer> codePoints = larger.codePoints().boxed().collect(toList());
        shrinks.addAll(removals(codePoints));

        List<List<Integer>> oneItemShrinks = shrinksOfOneItem(random, codePoints, new CodePointShrink(this::codePointInRange));
        shrinks.addAll(oneItemShrinks.stream()
            .map(this::convert)
            .filter(this::codePointsInRange)
            .collect(toList()));

        return shrinks;
    }

    @Override
    public BigDecimal magnitude(Object value) {
        return BigDecimal.valueOf(narrow(value).length());
    }

    protected abstract int nextCodePoint(SourceOfRandomness random);

    protected abstract boolean codePointInRange(int codePoint);

    private boolean codePointsInRange(String s) {
        return s.codePoints().allMatch(this::codePointInRange);
    }

    private List<String> removals(List<Integer> codePoints) {
        return stream(halving(codePoints.size()).spliterator(), false)
            .map(i -> Lists.removeFrom(codePoints, i))
            .flatMap(Collection::stream)
            .map(this::convert)
            .collect(toList());
    }

    private String convert(List<Integer> codePoints) {
        StringBuilder s = new StringBuilder();
        codePoints.forEach(s::appendCodePoint);
        return s.toString();
    }
}
