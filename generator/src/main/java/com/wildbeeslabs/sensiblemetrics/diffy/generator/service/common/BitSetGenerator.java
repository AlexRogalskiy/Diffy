package com.wildbeeslabs.sensiblemetrics.diffy.generator.service.common;

import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.GenerationStatus;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.Generator;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.SourceOfRandomness;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

/**
 * Produces values of type {@link BitSet}.
 */
public class BitSetGenerator extends Generator<BitSet> {
    public BitSetGenerator() {
        super(BitSet.class);
    }

    @Override
    public BitSet generate(SourceOfRandomness random, GenerationStatus status) {
        int size = status.size();

        BitSet bits = new BitSet(size);
        for (int i = 0; i < size; ++i)
            bits.set(i, random.nextBoolean());

        return bits;
    }

    @Override
    public List<BitSet> doShrink(SourceOfRandomness random, BitSet larger) {
        if (larger.length() == 0)
            return emptyList();

        List<BitSet> shrinks = new ArrayList<>();
        shrinks.addAll(larger.stream()
            .mapToObj(i -> larger.get(0, i))
            .collect(toList()));
        shrinks.addAll(larger.stream()
            .mapToObj(i -> {
                BitSet smaller = (BitSet) larger.clone();
                smaller.clear(i);
                return smaller;
            })
            .collect(toList()));

        return shrinks;
    }

    @Override
    public BigDecimal magnitude(Object value) {
        return BigDecimal.valueOf(narrow(value).size());
    }
}
