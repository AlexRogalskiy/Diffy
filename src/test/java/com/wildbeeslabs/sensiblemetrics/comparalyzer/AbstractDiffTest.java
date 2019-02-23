/*
 * The MIT License
 *
 * Copyright 2019 WildBees Labs, Inc.
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
package com.wildbeeslabs.sensiblemetrics.comparalyzer;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import net.andreinc.mockneat.MockNeat;
import net.andreinc.mockneat.abstraction.*;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import static net.andreinc.mockneat.types.enums.IPv4Type.CLASS_A;
import static net.andreinc.mockneat.types.enums.IPv4Type.CLASS_C;
import static net.andreinc.mockneat.types.enums.StringType.*;
import static net.andreinc.mockneat.unit.text.Strings.strings;
import static net.andreinc.mockneat.unit.time.LocalDates.localDates;
import static net.andreinc.mockneat.unit.types.Doubles.doubles;
import static net.andreinc.mockneat.unit.types.Floats.floats;
import static net.andreinc.mockneat.unit.types.Ints.ints;
import static net.andreinc.mockneat.unit.types.Longs.longs;

/**
 * Abstract difference unit test
 *
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@Slf4j
@Data
@EqualsAndHashCode
@ToString
public abstract class AbstractDiffTest {

    /**
     * Default mock instance (using threadlocal implementation)
     */
    protected final MockNeat mock = MockNeat.threadLocal();
    /**
     * Default numeric mock instances
     */
    protected final MockUnitInt thousandMock = ints().range(1, 999);
    protected final MockUnitInt monthMock = ints().range(1, 12);
    protected final MockUnitInt codeMock = ints().bound(10);
    protected final MockUnitInt intMock = ints().bound(1000000);
    protected final MockUnitLong longMock = longs().bound(10000000);
    protected final MockUnitInt byteMock = ints().bound(255);
    protected final MockUnitInt shortMock = ints().bound(15000);
    protected final MockUnitInt intRangeMock = ints().range(1000, 5000);
    protected final MockUnitLong longRangeMock = longs().range(100000, 500000);
    protected final MockUnitDouble doubleMock = doubles().bound(50000);
    protected final MockUnitFloat floatMock = floats().bound(5000);
    /**
     * Default local date mock instances
     */
    protected final MockUnitLocalDate localDateMock = localDates();
    protected final MockUnitLocalDate yearLocalDateMock = localDates().thisYear();
    protected final MockUnitLocalDate pastLocalDateMock = localDates().past(LocalDate.now().minusYears(3));
    protected final MockUnitLocalDate futureLocalDateMock = localDates().future(LocalDate.now().plusYears(3));
    /**
     * Default string mock instances
     */
    protected final MockUnitString alphaNumericStringMock = strings().types(ALPHA_NUMERIC);
    protected final MockUnitString lettersStringMock = strings().types(LETTERS);
    protected final MockUnitString numbersStringMock = strings().types(NUMBERS);
    /**
     * Default ip mock instances
     */
    protected final MockUnitString ipv4ClassA = this.mock.ipv4s().types(CLASS_A, CLASS_C);
    protected final MockUnitString ipv6ClassA = this.mock.iPv6s().mapToString();

    /**
     * Returns mock unit {@link MockUnit} of {@link Float} array by initial size, lower and upper bounds
     *
     * @param size       - initial input array size
     * @param lowerBound - initial input lower bound
     * @param upperBound - initial input upper bound
     * @return mock unit {@link MockUnit} of {@link Float} array
     */
    protected MockUnit<Float[]> generateFloats(int size, float lowerBound, float upperBound) {
        return this.mock.floats()
            .range(lowerBound, upperBound)
            .array(size);
    }

    /**
     * Returns mock unit {@link MockUnit} of {@link Double} array by initial size, lower and upper bounds
     *
     * @param size       - initial input array size
     * @param lowerBound - initial input lower bound
     * @param upperBound - initial input upper bound
     * @return mock unit {@link MockUnit} of {@link Double} array
     */
    protected MockUnit<Double[]> generateDoubles(int size, double lowerBound, double upperBound) {
        return this.mock.doubles()
            .range(lowerBound, upperBound)
            .array(size);
    }

    /**
     * Returns mock unit {@link MockUnit} of {@link Integer} array by initial size, lower and upper bounds
     *
     * @param size       - initial input array size
     * @param lowerBound - initial input lower bound
     * @param upperBound - initial input upper bound
     * @return mock unit {@link MockUnit} of {@link Integer} array
     */
    protected MockUnit<Integer[]> generateInts(int size, int lowerBound, int upperBound) {
        return this.mock.ints()
            .range(lowerBound, upperBound)
            .array(size);
    }

    /**
     * Returns mock unit {@link MockUnit} of {@link Long} array by initial size, lower and upper bounds
     *
     * @param size       - initial input array size
     * @param lowerBound - initial input lower bound
     * @param upperBound - initial input upper bound
     * @return mock unit {@link MockUnit} of {@link Long} array
     */
    protected MockUnit<Long[]> generateInts(int size, long lowerBound, long upperBound) {
        return this.mock.longs()
            .range(lowerBound, upperBound)
            .array(size);
    }

    /**
     * Returns mock unit {@link MockUnit} of {@link List} integers by initial size and bound
     *
     * @param size  - initial input array size
     * @param bound - initial input bound
     * @return mock unit {@link MockUnit} of {@link List} integers
     */
    protected MockUnit<List<Integer>> generateInts(int size, int bound) {
        return this.mock.ints()
            .bound(bound)
            .list(LinkedList.class, size);
    }
}

