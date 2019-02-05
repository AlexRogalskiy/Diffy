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

import static net.andreinc.mockneat.types.enums.StringType.*;
import static net.andreinc.mockneat.unit.text.Strings.strings;
import static net.andreinc.mockneat.unit.time.LocalDates.localDates;
import static net.andreinc.mockneat.unit.types.Doubles.doubles;
import static net.andreinc.mockneat.unit.types.Floats.floats;
import static net.andreinc.mockneat.unit.types.Ints.ints;
import static net.andreinc.mockneat.unit.types.Longs.longs;

/**
 * Abstract difference unit test
 */
@Slf4j
@Data
@EqualsAndHashCode
@ToString
public abstract class AbstractDiffTest {

    /**
     * Default mockNeat instance
     */
    private final MockNeat mockNeat = MockNeat.threadLocal();
    /**
     * Default mockNeat unit numeric instances
     */
    protected final MockUnitInt mockUnitThousand = ints().range(1, 999);
    protected final MockUnitInt mockUnitMonth = ints().range(1, 12);
    protected final MockUnitInt mockUnitCode = ints().bound(10);
    protected final MockUnitInt mockUnitInt = ints().bound(1000000);
    protected final MockUnitLong mockUnitLong = longs().bound(10000000);
    protected final MockUnitInt mockUnitByte = ints().bound(255);
    protected final MockUnitInt mockUnitShort = ints().bound(15000);
    protected final MockUnitInt rangeMockUnitInt = ints().range(1000, 5000);
    protected final MockUnitLong rangeMockUnitLong = longs().range(100000, 500000);
    protected final MockUnitDouble mockUnitDouble = doubles().bound(50000);
    protected final MockUnitFloat mockUnitFloat = floats().bound(5000);
    /**
     * Default mockNeat unit localdate instances
     */
    protected final MockUnitLocalDate mockUnitLocalDate = localDates();
    protected final MockUnitLocalDate yearMockUnitLocalDate = localDates().thisYear();
    protected final MockUnitLocalDate pastMockUnitLocalDate = localDates().past(LocalDate.now().minusYears(3));
    protected final MockUnitLocalDate futureMockUnitLocalDate = localDates().future(LocalDate.now().plusYears(3));
    /**
     * Default mockNeat unit string instances
     */
    protected final MockUnitString alphaNumericMockUnitString = strings().types(ALPHA_NUMERIC);
    protected final MockUnitString lettersMockUnitString = strings().types(LETTERS);
    protected final MockUnitString numbersMockUnitString = strings().types(NUMBERS);
}

