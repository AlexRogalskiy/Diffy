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

import com.wildbeeslabs.sensiblemetrics.comparalyzer.entity.DeliveryInfo;
import lombok.extern.slf4j.Slf4j;
import net.andreinc.mockneat.MockNeat;
import net.andreinc.mockneat.abstraction.*;
import net.andreinc.mockneat.types.enums.StringType;
import net.andreinc.mockneat.unit.objects.Reflect;

import java.time.LocalDate;
import java.util.Comparator;

import static com.wildbeeslabs.sensiblemetrics.comparalyzer.utils.DateUtils.toDate;

/**
 * Abstract delivery info unit test
 */
@Slf4j
public abstract class AbstractDeliveryInfoDiffComparatorTest {

    /**
     * Default mockNeat instance
     */
    private final MockNeat mockNeat = MockNeat.threadLocal();
    /**
     * Default mockNeat unit numeric instances
     */
    protected final MockUnitInt mockUnitThousand = getMockNeat().ints().range(1, 999);
    protected final MockUnitInt mockUnitMonth = getMockNeat().ints().range(1, 12);
    protected final MockUnitInt mockUnitCode = getMockNeat().ints().bound(10);
    protected final MockUnitInt mockUnitInt = getMockNeat().ints().bound(1000000);
    protected final MockUnitLong mockUnitLong = getMockNeat().longs().bound(10000000);
    protected final MockUnitInt mockUnitByte = getMockNeat().ints().bound(255);
    protected final MockUnitInt mockUnitShort = getMockNeat().ints().bound(15000);
    protected final MockUnitInt rangeMockUnitInt = getMockNeat().ints().range(1000, 5000);
    protected final MockUnitLong rangeMockUnitLong = getMockNeat().longs().range(100000, 500000);
    protected final MockUnitDouble mockUnitDouble = getMockNeat().doubles().bound(50000);
    protected final MockUnitFloat mockUnitFloat = getMockNeat().floats().bound(5000);
    /**
     * Default mockNeat unit localdate instances
     */
    protected final MockUnitLocalDate mockUnitLocalDate = getMockNeat().localDates();
    protected final MockUnitLocalDate yearMockUnitLocalDate = getMockNeat().localDates().thisYear();
    protected final MockUnitLocalDate pastMockUnitLocalDate = getMockNeat().localDates().past(LocalDate.now().minusYears(3));
    protected final MockUnitLocalDate futureMockUnitLocalDate = getMockNeat().localDates().future(LocalDate.now().plusYears(3));
    /**
     * Default mockNeat unit string instances
     */
    protected final MockUnitString alphaNumbericMockUnitString = getMockNeat().strings().types(StringType.ALPHA_NUMERIC);
    protected final MockUnitString lettersMockUnitString = getMockNeat().strings().types(StringType.LETTERS);
    protected final MockUnitString numbersMockUnitString = getMockNeat().strings().types(StringType.NUMBERS);

    /**
     * Default delivery info comparator
     */
    protected static final Comparator<? super DeliveryInfo> deliveryInfoComparator = Comparator
            .comparing(DeliveryInfo::getId)
            .thenComparing(DeliveryInfo::getType)
            .thenComparing(DeliveryInfo::getDescription)
            .thenComparing(DeliveryInfo::getCreatedAt)
            .thenComparing(DeliveryInfo::getUpdatedAt);

    protected Reflect<DeliveryInfo> getDeliveryInfoReflect() {
        return mockNeat.reflect(DeliveryInfo.class)
                .field("id", mockUnitLong.val())
                .field("type", mockUnitInt.val())
                .field("description", lettersMockUnitString.val())
                .field("createdAt", toDate(mockUnitLocalDate.val()))
                .field("updatedAt", toDate(mockUnitLocalDate.val()));
    }

    protected DeliveryInfo getDeliveryInfo() {
        final DeliveryInfo deliveryInfoComparable = new DeliveryInfo();
        deliveryInfoComparable.setId(mockNeat.longSeq().start(100).increment(100).val());
        deliveryInfoComparable.setType(mockUnitInt.val());
        deliveryInfoComparable.setDescription(lettersMockUnitString.val());
        deliveryInfoComparable.setCreatedAt(toDate(mockUnitLocalDate.val()));
        deliveryInfoComparable.setUpdatedAt(toDate(mockUnitLocalDate.val()));
        return deliveryInfoComparable;
    }

    protected MockNeat getMockNeat() {
        return this.mockNeat;
    }
}
