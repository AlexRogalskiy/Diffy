package com.wildbeeslabs.sensiblemetrics.diffy.generator.utils;

import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.Shrink;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.SourceOfRandomness;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

public final class Lists {
    private Lists() {
        throw new UnsupportedOperationException();
    }

    public static <T> List<List<T>> removeFrom(List<T> target, int howMany) {
        if (howMany < 0)
            throw new IllegalArgumentException("Can't remove " + howMany + " elements from a list");
        if (howMany == 0)
            return singletonList(target);
        if (howMany > target.size())
            return emptyList();

        List<T> left = target.subList(0, howMany);
        List<T> right = target.subList(howMany, target.size());
        if (right.isEmpty())
            return singletonList(emptyList());

        List<List<T>> removals = new ArrayList<>();
        removals.add(right);
        removals.addAll(removeFrom(right, howMany)
            .stream()
            .map(r -> {
                List<T> items = new ArrayList<>(left);
                items.addAll(r);
                return items;
            })
            .collect(toList()));
        return removals;
    }

    public static <T> List<List<T>> shrinksOfOneItem(
        SourceOfRandomness random,
        List<T> target,
        Shrink<T> shrink) {

        if (target.isEmpty())
            return new ArrayList<>();

        T head = target.get(0);
        List<T> tail = target.subList(1, target.size());

        List<List<T>> shrinks = new ArrayList<>();
        shrinks.addAll(
            shrink.shrink(random, head)
                .stream()
                .map(i -> {
                    List<T> items = new ArrayList<>();
                    items.add(i);
                    items.addAll(tail);
                    return items;
                })
                .collect(toList()));
        shrinks.addAll(
            shrinksOfOneItem(random, tail, shrink)
                .stream()
                .map(s -> {
                    List<T> items = new ArrayList<>();
                    items.add(head);
                    items.addAll(s);
                    return items;
                })
                .collect(toList()));

        return shrinks;
    }

    public static <T> boolean isDistinct(List<T> target) {
        return new HashSet<>(target).size() == target.size();
    }
}
