package com.wildbeeslabs.sensiblemetrics.diffy.common.helpers.iface;

import java.util.function.Function;

@FunctionalInterface
public interface Fkt<T, R> extends Function<T, R> {

    default R elseApply(final T t) {
        return this.apply(t);
    }
}
