package com.wildbeeslabs.sensiblemetrics.diffy.common.helpers.impl;

import java.util.Optional;
import java.util.function.Consumer;

public class OptionalConsumer<T> {
    private final Optional<T> optional;

    private OptionalConsumer(final Optional<T> optional) {
        this.optional = optional;
    }

    public static <T> OptionalConsumer<T> of(final Optional<T> optional) {
        return new OptionalConsumer<>(optional);
    }

    public static <T> OptionalConsumer<T> of(final T value) {
        return new OptionalConsumer<>(Optional.ofNullable(value));
    }

    public OptionalConsumer<T> ifPresent(final Consumer<T> c) {
        this.optional.ifPresent(c);
        return this;
    }

    public OptionalConsumer<T> ifNotPresent(final Runnable runnable) {
        if (!this.optional.isPresent()) {
            runnable.run();
        }
        return this;
    }
}
/*
Optional<Any> o = Optional.of(...);
OptionalConsumer.of(o).ifPresent(s ->System.out.println("isPresent "+s))
            .ifNotPresent(() -> System.out.println("! isPresent"));
 */
/*
public class OptionalConsumer<T> implements Consumer<Optional<T>> {
private final Consumer<T> c;
private final Runnable r;

public OptionalConsumer(Consumer<T> c, Runnable r) {
    super();
    this.c = c;
    this.r = r;
}

public static <T> OptionalConsumer<T> of(Consumer<T> c, Runnable r) {
    return new OptionalConsumer(c, r);
}

@Override
public void accept(Optional<T> t) {
    if (t.isPresent()) {
        c.accept(t.get());
    }
    else {
        r.run();
    }
}

Consumer<Optional<Integer>> c=OptionalConsumer.of(System.out::println, ()->{System.out.println("Not fit");});
IntStream.range(0, 100).boxed().map(i->Optional.of(i).filter(j->j%2==0)).forEach(c);
 */
