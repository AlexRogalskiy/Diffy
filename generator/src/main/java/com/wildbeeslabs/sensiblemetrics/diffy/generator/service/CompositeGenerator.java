package com.wildbeeslabs.sensiblemetrics.diffy.generator.service;

import com.wildbeeslabs.sensiblemetrics.diffy.generator.exception.GeneratorConfigurationException;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.GenerationStatus;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.Generators;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.utils.Items;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.AnnotatedType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class CompositeGenerator extends Generator<Object> {
    private final List<Weighted<Generator<?>>> composed;

    public CompositeGenerator(List<Weighted<Generator<?>>> composed) {
        super(Object.class);

        this.composed = new ArrayList<>(composed);
    }

    @Override
    public Object generate(SourceOfRandomness random, GenerationStatus status) {
        Generator<?> choice = Items.chooseWeighted(composed, random);
        return choice.generate(random, status);
    }

    @Override
    public boolean canShrink(Object larger) {
        return composed.stream()
            .map(w -> w.item)
            .anyMatch(g -> g.canShrink(larger));
    }

    @Override
    public List<Object> doShrink(SourceOfRandomness random, Object larger) {
        List<Weighted<Generator<?>>> shrinkers =
            composed.stream()
                .filter(w -> w.item.canShrink(larger))
                .collect(toList());

        Generator<?> choice = Items.chooseWeighted(shrinkers, random);
        return new ArrayList<>(choice.shrink(random, larger));
    }

    public Generator<?> composed(int index) {
        return composed.get(index).item;
    }

    public int numberOfComposedGenerators() {
        return composed.size();
    }

    @Override
    public void provide(Generators provided) {
        super.provide(provided);

        for (Weighted<Generator<?>> each : composed)
            each.item.provide(provided);
    }

    @Override
    public BigDecimal magnitude(Object value) {
        List<Weighted<Generator<?>>> shrinkers =
            composed.stream()
                .filter(w -> w.item.canShrink(value))
                .collect(toList());

        return shrinkers.get(0).item.magnitude(value);
    }

    @Override
    public void configure(AnnotatedType annotatedType) {
        List<Weighted<Generator<?>>> candidates = new ArrayList<>(composed);

        for (Iterator<Weighted<Generator<?>>> it = candidates.iterator(); it.hasNext(); ) {
            try {
                it.next().item.configure(annotatedType);
            } catch (GeneratorConfigurationException e) {
                it.remove();
            }
        }

        installCandidates(candidates, annotatedType);
    }

    @Override
    public void configure(AnnotatedElement element) {
        List<Weighted<Generator<?>>> candidates = new ArrayList<>(composed);

        for (Iterator<Weighted<Generator<?>>> it = candidates.iterator(); it.hasNext(); ) {
            try {
                it.next().item.configure(element);
            } catch (GeneratorConfigurationException e) {
                it.remove();
            }
        }

        installCandidates(candidates, element);
    }

    @Override
    public void addComponentGenerators(List<Generator<?>> newComponents) {
        for (Weighted<Generator<?>> each : composed)
            each.item.addComponentGenerators(newComponents);
    }

    private void installCandidates(
        List<Weighted<Generator<?>>> candidates,
        AnnotatedElement element) {

        if (candidates.isEmpty()) {
            throw new GeneratorConfigurationException(
                String.format(
                    "None of the candidate generators %s"
                        + " understands all of the configuration annotations %s",
                    candidateGeneratorDescriptions(),
                    configurationAnnotationNames(element)));
        }

        composed.clear();
        composed.addAll(candidates);
    }

    private String candidateGeneratorDescriptions() {
        return composed.stream()
            .map(w -> w.item.getClass().getName())
            .collect(toList())
            .toString();
    }

    private static List<String> configurationAnnotationNames(AnnotatedElement element) {
        return configurationAnnotationsOn(element).stream()
            .map(a -> a.annotationType().getName())
            .collect(toList());
    }
}
