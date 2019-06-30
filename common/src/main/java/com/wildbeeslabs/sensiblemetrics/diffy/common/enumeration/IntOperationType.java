/*
 * The MIT License
 *
 * Copyright 2018 WildBees Labs.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.wildbeeslabs.sensiblemetrics.diffy.common.enumeration;

import com.wildbeeslabs.sensiblemetrics.diffy.common.helpers.impl.IntWrapper;
import com.wildbeeslabs.sensiblemetrics.diffy.common.interfaces.Operation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Integer {@link Operation} {@link Enum} implementation
 */
@Getter
@RequiredArgsConstructor
public enum IntOperationType implements Operation<IntWrapper, IntWrapper> {
    PLUS("+") {
        @Override
        public IntWrapper apply(final IntWrapper x, final IntWrapper y) {
            return IntWrapper.of(x.getValue() + y.getValue());
        }
    },
    MINUS("-") {
        @Override
        public IntWrapper apply(final IntWrapper x, final IntWrapper y) {
            return IntWrapper.of(x.getValue() - y.getValue());
        }
    },
    TIMES("*") {
        @Override
        public IntWrapper apply(final IntWrapper x, final IntWrapper y) {
            return IntWrapper.of(x.getValue() * y.getValue());
        }
    },
    DIVIDE("/") {
        @Override
        public IntWrapper apply(final IntWrapper x, final IntWrapper y) {
            return IntWrapper.of(x.getValue() / y.getValue());
        }
    },
    EXP("^") {
        @Override
        public IntWrapper apply(final IntWrapper x, final IntWrapper y) {
            return IntWrapper.of((int) Math.pow(x.getValue(), y.getValue()));
        }
    },
    REMAINDER("%") {
        @Override
        public IntWrapper apply(final IntWrapper x, final IntWrapper y) {
            return IntWrapper.of(x.getValue() % y.getValue());
        }
    };

    private final String symbol;
}

//    public static void main(final String[] args) {
//        double x = Double.parseDouble(args[0]);
//        double y = Double.parseDouble(args[1]);
//        test(ExtendedOperation.class, x, y);
//        test2(Arrays.asList(ExtendedOperation.values()), x, y);
//    }

//    private static <T extends Enum<T> & Operation> void test(final Class<T> opSet, double x, double y) {
//        for (final Operation op : opSet.getEnumConstants())
//            System.out.printf("%f %s %f = %f%n", x, op, y, op.apply(x, y));
//    }

//    private static void test2(final Collection<? extends Operation> opSet, double x, double y) {
//        for (final Operation op : opSet)
//            System.out.printf("%f %s %f = %f%n", x, op, y, op.apply(x, y));
//    }
