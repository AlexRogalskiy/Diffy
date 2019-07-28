package com.wildbeeslabs.sensiblemetrics.diffy.generator.service;

import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.Shrink;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.lang.Character.isUpperCase;
import static java.util.Collections.addAll;
import static java.util.Collections.reverse;
import static java.util.Comparator.comparing;
import static java.util.Comparator.naturalOrder;
import static java.util.stream.Collectors.toList;

class CodePointShrink implements Shrink<Integer> {
    private final Predicate<? super Integer> filter;

    CodePointShrink(Predicate<? super Integer> filter) {
        this.filter = filter;
    }

    @Override
    public List<Integer> shrink(SourceOfRandomness random, Object larger) {
        int codePoint = (Integer) larger;

        List<Integer> shrinks = new ArrayList<>();
        addAll(shrinks, (int) 'a', (int) 'b', (int) 'c');
        if (isUpperCase(codePoint))
            shrinks.add(Character.toLowerCase(codePoint));
        addAll(shrinks, (int) 'A', (int) 'B', (int) 'C',
            (int) '1', (int) '2', (int) '3',
            (int) ' ', (int) '\n');
        reverse(shrinks);

        Comparator<Integer> comparator =
            comparing((Function<Integer, Boolean>) Character::isLowerCase)
                .thenComparing((Function<Integer, Boolean>) Character::isUpperCase)
                .thenComparing((Function<Integer, Boolean>) Character::isDigit)
                .thenComparing(cp -> Integer.valueOf(' ').equals(cp))
                .thenComparing((Function<Integer, Boolean>) Character::isSpaceChar)
                .thenComparing(naturalOrder());
        return shrinks.stream()
            .filter(filter)
            .filter(cp -> comparator.compare(cp, codePoint) < 0)
            .distinct()
            .collect(toList());
    }
}
