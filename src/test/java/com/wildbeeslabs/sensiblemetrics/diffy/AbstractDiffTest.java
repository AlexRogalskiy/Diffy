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
package com.wildbeeslabs.sensiblemetrics.diffy;

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
import static net.andreinc.mockneat.types.enums.StringFormatType.LOWER_CASE;
import static net.andreinc.mockneat.types.enums.StringFormatType.UPPER_CASE;
import static net.andreinc.mockneat.types.enums.StringType.*;
import static net.andreinc.mockneat.unit.text.Strings.strings;
import static net.andreinc.mockneat.unit.time.LocalDates.localDates;
import static net.andreinc.mockneat.unit.types.Doubles.doubles;
import static net.andreinc.mockneat.unit.types.Floats.floats;
import static net.andreinc.mockneat.unit.types.Ints.ints;
import static net.andreinc.mockneat.unit.types.Longs.longs;

/**
 * Abstract difference mock unit test
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
     * Default thread local mock instance {@link MockNeat}
     */
    protected final MockNeat mock = MockNeat.threadLocal();
    /**
     * Default integer mock unit instances {@link MockUnitInt}
     */
    protected final MockUnitInt thousandMock = ints().range(1, 999);
    protected final MockUnitInt monthMock = ints().range(1, 12);
    protected final MockUnitInt codeMock = ints().bound(10);
    protected final MockUnitInt intMock = ints().bound(1000000);
    protected final MockUnitInt byteMock = ints().bound(255);
    protected final MockUnitInt shortMock = ints().bound(15000);
    protected final MockUnitInt intRangeMock = ints().range(1000, 5000);
    /**
     * Default long mock unit instances {@link MockUnitLong}
     */
    protected final MockUnitLong longMock = longs().bound(10000000);
    protected final MockUnitLong longRangeMock = longs().range(100000, 500000);
    /**
     * Default double mock unit instances {@link MockUnitDouble}
     */
    protected final MockUnitDouble doubleMock = doubles().bound(50000);
    /**
     * Default float mock unit instances {@link MockUnitFloat}
     */
    protected final MockUnitFloat floatMock = floats().bound(5000);
    /**
     * Default local date mock unit instances {@link MockUnitLocalDate}
     */
    protected final MockUnitLocalDate localDateMock = localDates();
    protected final MockUnitLocalDate yearLocalDateMock = localDates().thisYear();
    protected final MockUnitLocalDate pastLocalDateMock = localDates().past(LocalDate.now().minusYears(3));
    protected final MockUnitLocalDate futureLocalDateMock = localDates().future(LocalDate.now().plusYears(3));
    /**
     * Default string mock unit instances {@link MockUnitString}
     */
    protected final MockUnitString alphaNumericStringMock = strings().types(ALPHA_NUMERIC);
    protected final MockUnitString lettersStringMock = strings().types(LETTERS);
    protected final MockUnitString numbersStringMock = strings().types(NUMBERS);
    /**
     * Default string mock unit instances {@link MockUnitString}
     */
    protected final MockUnitString ipv4ClassACMock = this.mock.ipv4s().types(CLASS_A, CLASS_C);
    protected final MockUnitString ipv6Mock = this.mock.iPv6s().mapToString();
    protected final MockUnitString uuidMock = this.mock.uuids().mapToString();
    protected final MockUnitString countryNameMock = this.mock.countries().names();
    protected final MockUnitString cityNameMock = this.mock.cities().capitals();
    protected final MockUnitString currencyNameMock = this.mock.currencies().name();
    protected final MockUnitString creditCardNameMock = this.mock.creditCards().names();
    protected final MockUnitString emailMock = this.mock.emails().mapToString();
    protected final MockUnitString domainMock = this.mock.domains().all();
    protected final MockUnitString departmentMock = this.mock.departments().mapToString();
    protected final MockUnitString genderMock = this.mock.genders().letter();
    protected final MockUnitString firstNameMock = this.mock.names().first();
    protected final MockUnitString lastNameMock = this.mock.names().last();
    protected final MockUnitString quotationMock = this.mock.naughtyStrings().quotations();

    /**
     * Returns mock unit instance {@link MockUnit} to produce array of {@link Float} by initial array size, lower / upper value bounds
     *
     * @param size       - initial input array size
     * @param lowerBound - initial input lower bound
     * @param upperBound - initial input upper bound
     * @return mock unit {@link MockUnit} to produce array of {@link Float}
     */
    protected MockUnit<Float[]> generateFloats(int size, float lowerBound, float upperBound) {
        return this.mock.floats()
            .range(lowerBound, upperBound)
            .array(size);
    }

    /**
     * Returns mock unit instance {@link MockUnit} to produce array of {@link Double} by initial array size, lower / upper value bounds
     *
     * @param size       - initial input array size
     * @param lowerBound - initial input lower bound
     * @param upperBound - initial input upper bound
     * @return mock unit instance {@link MockUnit} to produce array of {@link Double}
     */
    protected MockUnit<Double[]> generateDoubles(int size, double lowerBound, double upperBound) {
        return this.mock.doubles()
            .range(lowerBound, upperBound)
            .array(size);
    }

    /**
     * Returns mock unit instance {@link MockUnit} to produce array of {@link Integer} by initial array size, lower / upper value bounds
     *
     * @param size       - initial input array size
     * @param lowerBound - initial input lower value bound
     * @param upperBound - initial input upper value bound
     * @return mock unit {@link MockUnit} to produce array of {@link Integer}
     */
    protected MockUnit<Integer[]> generateInts(int size, int lowerBound, int upperBound) {
        return this.mock.ints()
            .range(lowerBound, upperBound)
            .array(size);
    }

    /**
     * Returns mock unit instance {@link MockUnit} to produce array of {@link Long} by initial array size, lower and upper value bounds
     *
     * @param size       - initial input array size
     * @param lowerBound - initial input lower value bound
     * @param upperBound - initial input value value bound
     * @return mock unit instance {@link MockUnit} to produce array of {@link Long}
     */
    protected MockUnit<Long[]> generateLongs(int size, long lowerBound, long upperBound) {
        return this.mock.longs()
            .range(lowerBound, upperBound)
            .array(size);
    }

    /**
     * Returns mock unit {@link MockUnit} to produce {@link List} of {@link Integer} by initial list size and value bound
     *
     * @param size  - initial input list {@link List} size
     * @param bound - initial input value bound
     * @return mock unit instance {@link MockUnit} to produce {@link List} of {@link Integer}
     */
    protected MockUnit<List<Integer>> generateInts(int size, int bound) {
        return this.mock.ints()
            .bound(bound)
            .list(LinkedList.class, size);
    }

    /**
     * Returns mock unit instance {@link MockUnit} to produce list {@link List} of {@link String} by initial list size
     *
     * @param size - initial input list {@link List} size
     * @return mock unit instance {@link MockUnit} to produce list {@link List} of {@link String}
     */
    protected MockUnit<List<String>> generateStrings(int size) {
        final MockUnitInt num = this.mock.probabilites(Integer.class)
            .add(0.3, this.mock.ints().range(0, 10))
            .add(0.7, this.mock.ints().range(10, 20))
            .mapToInt(Integer::intValue);

        return this.mock.fmt("#{first} #{last} #{num}")
            .param("first", this.mock.names().first().format(LOWER_CASE))
            .param("last", this.mock.names().last().format(UPPER_CASE))
            .param("num", num)
            .list(size);
    }
}
