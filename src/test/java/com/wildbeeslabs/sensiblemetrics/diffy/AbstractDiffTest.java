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
import java.util.ArrayList;
import java.util.Date;
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
     * Default {@link MockUnitLong}
     */
    protected final MockUnitLong longMock = longs().bound(10000000);
    protected final MockUnitLong longRangeMock = longs().range(100000, 500000);
    /**
     * Default {@link MockUnitDouble}
     */
    protected final MockUnitDouble doubleMock = doubles().bound(50000);
    /**
     * Default {@link MockUnitFloat}
     */
    protected final MockUnitFloat floatMock = floats().bound(5000);
    /**
     * Default {@link MockUnitLocalDate}
     */
    protected final MockUnitLocalDate localDateMock = localDates();
    protected final MockUnitLocalDate yearLocalDateMock = localDates().thisYear();
    protected final MockUnitLocalDate pastLocalDateMock = localDates().past(LocalDate.now().minusYears(3));
    protected final MockUnitLocalDate futureLocalDateMock = localDates().future(LocalDate.now().plusYears(3));
    /**
     * Default {@link MockUnitString}
     */
    protected final MockUnitString alphaNumericStringMock = strings().types(ALPHA_NUMERIC);
    protected final MockUnitString lettersStringMock = strings().types(LETTERS);
    protected final MockUnitString numbersStringMock = strings().types(NUMBERS);
    /**
     * Default {@link MockUnitString}
     */
    protected final MockUnitString ipv4ClassACMock = getMock().ipv4s().types(CLASS_A, CLASS_C);
    protected final MockUnitString ipv6Mock = getMock().iPv6s().mapToString();
    protected final MockUnitString uuidMock = getMock().uuids().mapToString();
    protected final MockUnitString countryNameMock = getMock().countries().names();
    protected final MockUnitString cityNameMock = getMock().cities().capitals();
    protected final MockUnitString currencyNameMock = getMock().currencies().name();
    protected final MockUnitString creditCardNameMock = getMock().creditCards().names();
    protected final MockUnitString emailMock = getMock().emails().mapToString();
    protected final MockUnitString domainMock = getMock().domains().all();
    protected final MockUnitString departmentMock = getMock().departments().mapToString();
    protected final MockUnitString genderMock = getMock().genders().letter();
    protected final MockUnitString firstNameMock = getMock().names().first();
    protected final MockUnitString lastNameMock = getMock().names().last();
    protected final MockUnitString quotationMock = getMock().naughtyStrings().quotations();
    protected final MockUnitString dayNameMock = getMock().days().mapToString();
    protected final MockUnitString monthNameMock = getMock().months().mapToString();

    /**
     * Returns {@link MockUnit} to produce array of {@link Float} by initial input array size and range (lower / upper bounds)
     *
     * @param size       - initial input array size
     * @param lowerBound - initial input lower bound
     * @param upperBound - initial input upper bound
     * @return {@link MockUnit} to produce array of {@link Float}
     */
    protected MockUnit<Float[]> generateFloats(int size, float lowerBound, float upperBound) {
        return getMock().floats()
            .range(lowerBound, upperBound)
            .array(size);
    }

    /**
     * Returns {@link MockUnit} to produce array of {@link Double} by initial input array size and range (lower / upper bounds)
     *
     * @param size       - initial input array size
     * @param lowerBound - initial input lower bound
     * @param upperBound - initial input upper bound
     * @return {@link MockUnit} to produce array of {@link Double}
     */
    protected MockUnit<Double[]> generateDoubles(int size, double lowerBound, double upperBound) {
        return getMock().doubles()
            .range(lowerBound, upperBound)
            .array(size);
    }

    /**
     * Returns {@link MockUnit} to produce array of {@link Integer} by initial input array size and range (lower / upper bounds)
     *
     * @param size       - initial input array size
     * @param lowerBound - initial input lower value bound
     * @param upperBound - initial input upper value bound
     * @return {@link MockUnit} to produce array of {@link Integer}
     */
    protected MockUnit<Integer[]> generateInts(int size, int lowerBound, int upperBound) {
        return getMock().ints()
            .range(lowerBound, upperBound)
            .array(size);
    }

    /**
     * Returns {@link MockUnit} to produce array of {@link Long} by initial input array size and range (lower / upper bounds)
     *
     * @param size       - initial input array size
     * @param lowerBound - initial input lower value bound
     * @param upperBound - initial input value value bound
     * @return {@link MockUnit} to produce array of {@link Long}
     */
    protected MockUnit<Long[]> generateLongs(int size, long lowerBound, long upperBound) {
        return getMock().longs()
            .range(lowerBound, upperBound)
            .array(size);
    }

    /**
     * Returns mock unit {@link MockUnit} to produce {@link List} of {@link Integer} by initial input list size and bound
     *
     * @param size  - initial input {@link List} size
     * @param bound - initial input value bound
     * @return {@link MockUnit} to produce {@link List} of {@link Integer}
     */
    protected MockUnit<List<Integer>> generateInts(int size, int bound) {
        return getMock().ints()
            .bound(bound)
            .list(LinkedList.class, size);
    }

    /**
     * Returns {@link MockUnit} to produce list {@link List} of {@link String} by initial input list size
     *
     * @param size - initial input {@link List} size
     * @return {@link MockUnit} to produce list {@link List} of {@link String}
     */
    protected MockUnit<List<String>> generateStrings(int size) {
        final MockUnitInt num = getMock().probabilites(Integer.class)
            .add(0.3, getMock().ints().range(0, 10))
            .add(0.7, getMock().ints().range(10, 20))
            .mapToInt(Integer::intValue);

        return getMock().fmt("#{first} #{last} #{num}")
            .param("first", getMock().names().first().format(LOWER_CASE))
            .param("last", getMock().names().last().format(UPPER_CASE))
            .param("num", num)
            .list(size);
    }

    /**
     * Returns {@link MockUnit} to produce {@link List} of {@link Boolean} by initial input probability and size values
     *
     * @param probability - initial input probability value
     * @param size        - initial input {@link List} size
     * @return {@link MockUnit} to produce {@link List} of {@link Boolean}
     */
    protected MockUnit<List<Boolean>> generateBools(double probability, int size) {
        return getMock().bools()
            .probability(probability)
            .list(size);
    }

    /**
     * Returns {@link MockUnit} to produce {@link List} of {@link String} by range (lower / upper bounds)
     *
     * @param lowerBound - initial input lower value bound
     * @param upperBound - initial input value value bound
     * @return {@link MockUnit} to produce {@link List} of {@link Boolean}
     */
    protected MockUnit<List<String>> generateStrings(int lowerBound, int upperBound) {
        final MockUnitInt sizeGenerator = getMock().ints().range(lowerBound, upperBound);
        return getMock().strings()
            .list(() -> new ArrayList<>(), sizeGenerator);
    }

    /**
     * Returns {@link MockUnit} to produce {@link List} of {@link String} by range (lower / upper bounds)
     *
     * @param dateStart - initial input date start value {@link LocalDate}
     * @param dateEnd   - initial input date end value {@link LocalDate}
     * @return {@link MockUnit} to produce {@link Date}
     */
    protected MockUnit<Date> generateDates(final LocalDate dateStart, final LocalDate dateEnd) {
        return getMock().localDates()
            .between(dateStart, dateEnd)
            .toUtilDate();
    }
}

