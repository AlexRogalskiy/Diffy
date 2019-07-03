package com.wildbeeslabs.sensiblemetrics.diffy.common.helpers.impl;

import com.wildbeeslabs.sensiblemetrics.diffy.common.helpers.iface.Fkt;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class Opt<T> {

    private Optional<T> optional;

    private Opt(final Optional<T> optional) {
        this.optional = optional;
    }

    public static <T> Opt<T> of(final T value) {
        return new Opt<>(Optional.of(value));
    }

    public static <T> Opt<T> of(final Optional<T> optional) {
        return new Opt<>(optional);
    }

    public static <T> Opt<T> ofNullable(final T value) {
        return new Opt<>(Optional.ofNullable(value));
    }

    public static <T> Opt<T> empty() {
        return new Opt<>(Optional.empty());
    }

    private final BiFunction<Consumer<T>, Runnable, Void> ifPresent = (present, notPresent) -> {
        if (this.optional.isPresent()) {
            present.accept(this.optional.get());
        } else {
            notPresent.run();
        }
        return null;
    };

    private final BiFunction<Runnable, Consumer<T>, Void> ifNotPresent = (notPresent, present) -> {
        if (!this.optional.isPresent()) {
            notPresent.run();
        } else {
            present.accept(this.optional.get());
        }
        return null;
    };

    public Fkt<Consumer<T>, Fkt<Runnable, Void>> ifPresent() {
        return Opt.curry(this.ifPresent);
    }

    public Fkt<Runnable, Fkt<Consumer<T>, Void>> ifNotPresent() {
        return Opt.curry(this.ifNotPresent);
    }

    private static <X, Y, Z> Fkt<X, Fkt<Y, Z>> curry(final BiFunction<X, Y, Z> function) {
        return (final X x) -> (final Y y) -> function.apply(x, y);
    }
}

/*
    final Opt<String> opt = Opt.of("I'm a cool text");
    opt.ifPresent()
        .apply(s -> System.out.printf("Text is: %s\n", s))
        .elseApply(() -> System.out.println("no text available"));

    final Opt<String> opt = Opt.of("This is the text");
    opt.ifNotPresent()
        .apply(() -> System.out.println("Not present"))
        .elseApply(t -> );
 */
