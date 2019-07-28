package com.wildbeeslabs.sensiblemetrics.diffy.generator.utils;

import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.SourceOfRandomness;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.Weighted;

import java.util.Collection;

public final class Items {
    private Items() {
        throw new UnsupportedOperationException();
    }

    @SuppressWarnings("unchecked")
    public static <T> T choose(final Collection<T> items, final SourceOfRandomness random) {
        final Object[] asArray = items.toArray(new Object[items.size()]);
        return (T) asArray[random.nextInt(items.size())];
    }

    public static <T> T chooseWeighted(Collection<Weighted<T>> items, SourceOfRandomness random) {
        if (items.size() == 1) {
            return items.iterator().next().item;
        }
        int range = items.stream().mapToInt(i -> i.weight).sum();
        int sample = random.nextInt(range);

        int threshold = 0;
        for (final Weighted<T> each : items) {
            threshold += each.weight;
            if (sample < threshold)
                return each.item;
        }
        throw new AssertionError(String.format("sample = %d, range = %d", sample, range));
    }
}
