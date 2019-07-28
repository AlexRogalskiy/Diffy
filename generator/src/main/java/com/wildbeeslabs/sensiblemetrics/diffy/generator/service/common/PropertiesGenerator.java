package com.wildbeeslabs.sensiblemetrics.diffy.generator.service.common;

import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.GenerationStatus;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.*;

import java.math.BigDecimal;
import java.util.*;

import static java.math.BigDecimal.ZERO;
import static java.util.Arrays.asList;

/**
 * Produces values of type {@link Properties}.
 */
public class PropertiesGenerator extends Generator<Properties> {
    private AbstractStringGenerator stringGenerator = new StringGenerator();

    public PropertiesGenerator() {
        super(Properties.class);
    }

    public void configure(Encoded.InCharset charset) {
        Encoded encoded = new Encoded();
        encoded.configure(charset);
        stringGenerator = encoded;
    }

    @Override
    public Properties generate(SourceOfRandomness random, GenerationStatus status) {
        int size = status.size();

        Properties properties = new Properties();
        for (int i = 0; i < size; ++i) {
            properties.setProperty(
                stringGenerator.generate(random, status),
                stringGenerator.generate(random, status));
        }

        return properties;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean canRegisterAsType(Class<?> type) {
        Set<Class<?>> exclusions =
            new HashSet<>(
                asList(
                    Object.class,
                    Hashtable.class,
                    Map.class,
                    Dictionary.class));
        return !exclusions.contains(type);
    }

    @Override
    public BigDecimal magnitude(Object value) {
        Properties narrowed = narrow(value);

        if (narrowed.isEmpty())
            return ZERO;

        BigDecimal keysMagnitude =
            narrowed.keySet().stream()
                .map(e -> stringGenerator.magnitude(e))
                .reduce(ZERO, BigDecimal::add);
        BigDecimal valuesMagnitude =
            narrowed.values().stream()
                .map(e -> stringGenerator.magnitude(e))
                .reduce(ZERO, BigDecimal::add);
        return BigDecimal.valueOf(narrowed.size())
            .multiply(keysMagnitude)
            .add(valuesMagnitude);
    }
}
