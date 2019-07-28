package com.wildbeeslabs.sensiblemetrics.diffy.generator.service;

import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.GenerationStatus;

public class EnumGenerator extends Generator<Enum> {
    private final Class<?> enumType;

    private Boolean turnOffRandomness;

    public EnumGenerator(Class<?> enumType) {
        super(Enum.class);

        this.enumType = enumType;
    }

    public void configure(final Boolean flag) {
        this.turnOffRandomness = flag;
    }

    @Override
    public Enum<?> generate(SourceOfRandomness random, GenerationStatus status) {
        Object[] values = enumType.getEnumConstants();
        int index = turnOffRandomness == null
            ? random.nextInt(0, values.length - 1)
            : status.attempts() % values.length;
        return (Enum<?>) values[index];
    }

    @Override
    public boolean canShrink(Object larger) {
        return enumType.isInstance(larger);
    }
}
