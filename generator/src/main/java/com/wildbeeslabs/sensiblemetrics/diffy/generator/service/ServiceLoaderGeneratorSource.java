package com.wildbeeslabs.sensiblemetrics.diffy.generator.service;

import java.util.*;

import static java.util.Collections.unmodifiableList;
import static java.util.Comparator.comparing;

public class ServiceLoaderGeneratorSource implements Iterable<Generator<?>> {
    @SuppressWarnings("rawtypes")
    private final ServiceLoader<Generator> loader;

    public ServiceLoaderGeneratorSource() {
        loader = ServiceLoader.load(Generator.class);
    }

    @Override
    public Iterator<Generator<?>> iterator() {
        List<Generator<?>> generators = new ArrayList<>();

        for (Generator<?> each : loader)
            generators.add(each);

        Collections.sort(
            generators,
            comparing(generator -> generator.getClass().getName()));

        return unmodifiableList(generators).iterator();
    }
}
