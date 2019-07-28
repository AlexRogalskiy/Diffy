package com.wildbeeslabs.sensiblemetrics.diffy.generator.utils;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static java.math.RoundingMode.HALF_UP;

@UtilityClass
public class Sequences {

    public static Iterable<BigInteger> halvingIntegral(
        BigInteger max,
        BigInteger start) {

        return () -> new BigIntegerHalvingIterator(start, max);
    }

    public static Iterable<BigDecimal> halvingDecimal(
        BigDecimal max,
        BigDecimal start) {

        return () -> new BigDecimalHalvingIterator(start, max);
    }

    public static Iterable<Integer> halving(int start) {
        return () -> new IntegerHalvingIterator(start);
    }

    private static final class BigIntegerHalvingIterator
        implements Iterator<BigInteger> {

        private final BigInteger max;

        private boolean done;
        private BigInteger next;

        BigIntegerHalvingIterator(BigInteger start, BigInteger max) {
            this.max = max;
            next = start;
        }

        @Override
        public boolean hasNext() {
            return !done;
        }

        @Override
        public BigInteger next() {
            if (!hasNext())
                throw new NoSuchElementException();

            next = peek();
            done = next.equals(peek());
            return next;
        }

        private BigInteger peek() {
            return next.add(
                max.subtract(next)
                    .divide(BigInteger.valueOf(2)));
        }
    }

    private static final class BigDecimalHalvingIterator
        implements Iterator<BigDecimal> {

        private final BigDecimal max;

        private boolean done;
        private BigDecimal next;

        BigDecimalHalvingIterator(BigDecimal start, BigDecimal max) {
            this.max = max;
            next = start;
        }

        @Override
        public boolean hasNext() {
            return !done;
        }

        @Override
        public BigDecimal next() {
            if (!hasNext())
                throw new NoSuchElementException();

            next = peek();
            done = next.equals(peek());
            return next;
        }

        private BigDecimal peek() {
            return next.add(
                max.subtract(next)
                    .divide(BigDecimal.valueOf(2), HALF_UP));
        }
    }

    private static final class IntegerHalvingIterator implements Iterator<Integer> {

        private boolean done;
        private int next;

        IntegerHalvingIterator(int start) {
            next = start;
        }

        @Override
        public boolean hasNext() {
            return !done;
        }

        @Override
        public Integer next() {
            if (!hasNext())
                throw new NoSuchElementException();

            int result = next;
            next = peek();
            done = next == 0;
            return result;
        }

        private int peek() {
            return next / 2;
        }
    }
}
