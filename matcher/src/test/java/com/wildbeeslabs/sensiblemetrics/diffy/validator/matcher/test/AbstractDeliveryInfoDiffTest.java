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
package com.wildbeeslabs.sensiblemetrics.diffy.validator.matcher.test;

import com.wildbeeslabs.sensiblemetrics.diffy.examples.model.AddressInfo;
import com.wildbeeslabs.sensiblemetrics.diffy.examples.model.DeliveryInfo;
import com.wildbeeslabs.sensiblemetrics.diffy.utility.ComparatorUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import net.andreinc.mockneat.abstraction.MockUnit;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static net.andreinc.mockneat.unit.objects.Filler.filler;
import static net.andreinc.mockneat.unit.types.Ints.ints;

/**
 * Abstract delivery info difference unit test
 *
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public abstract class AbstractDeliveryInfoDiffTest extends AbstractDiffTest {
    /**
     * Default number of {@link AddressInfo} items
     */
    public static final int DEFAULT_ADDRESS_INFO_COUNT = 5;
    /**
     * Default {@link DeliveryInfo.DeliveryStatus} collection {@link List}
     */
    public static final List<DeliveryInfo.DeliveryStatus> DELIVERY_STATUS_LIST = Collections.unmodifiableList(Arrays.asList(DeliveryInfo.DeliveryStatus.values()));

    /**
     * Default address info {@link Comparator}
     */
    public static final Comparator<? super AddressInfo> DEFAULT_ADDRESS_INFO_COMPARATOR =
        Comparator.comparing(AddressInfo::getId)
            .thenComparing(AddressInfo::getCity)
            .thenComparing(AddressInfo::getCountry)
            .thenComparing(AddressInfo::getPostalCode)
            .thenComparing(AddressInfo::getStateOrProvince)
            .thenComparing(AddressInfo::getStreet);
    /**
     * Default address info list {@link Comparator}
     */
    public static final Comparator<? super List<AddressInfo>> DEFAULT_ADDRESS_INFO_COLLECTION_COMPARATOR = ComparatorUtils.getIterableComparator(DEFAULT_ADDRESS_INFO_COMPARATOR, false);
    /**
     * Default code array {@link Comparator}
     */
    public static final Comparator<? super Integer[]> DEFAULT_CODE_COMPARATOR = new ComparatorUtils.DefaultNullSafeArrayComparator<>();
    /**
     * Default delivery info {@link Comparator}
     */
    public static final Comparator<? super DeliveryInfo> DEFAULT_DELIVERY_INFO_COMPARATOR =
        Comparator.comparing(DeliveryInfo::getId)
            .thenComparingInt(DeliveryInfo::getType)
            .thenComparing(DeliveryInfo::getGid)
            .thenComparing(DeliveryInfo::getDescription)
            .thenComparing(DeliveryInfo::getCreatedAt)
            .thenComparing(DeliveryInfo::getUpdatedAt)
            .thenComparingDouble(DeliveryInfo::getBalance)
            .thenComparing(DeliveryInfo::getCodes, DEFAULT_CODE_COMPARATOR)
            .thenComparing(DeliveryInfo::getStatus)
            .thenComparing(DeliveryInfo::getDiscount)
            .thenComparing(DeliveryInfo::getAddresses, DEFAULT_ADDRESS_INFO_COLLECTION_COMPARATOR);

    /**
     * Default {@link DeliveryInfo} {@link MockUnit}
     *
     * @return {@link DeliveryInfo} {@link MockUnit}
     */
    protected MockUnit<DeliveryInfo> getDeliveryInfoMock() {
        return this.getDeliveryInfoMock(DEFAULT_ADDRESS_INFO_COUNT);
    }

    /**
     * Default delivery info mock unit instance {@link MockUnit}
     *
     * @return delivery info mock unit instance {@link MockUnit}
     */
    protected MockUnit<DeliveryInfo> getDeliveryInfoMock(int addressInfoCount) {
        return filler(() -> new DeliveryInfo())
            .setter(DeliveryInfo::setId, getLongMock())
            .setter(DeliveryInfo::setType, getIntMock())
            .setter(DeliveryInfo::setGid, getAlphaNumericStringMock())
            .setter(DeliveryInfo::setDescription, getLettersStringMock())
            .setter(DeliveryInfo::setCreatedAt, getLocalDateMock().toUtilDate())
            .setter(DeliveryInfo::setUpdatedAt, getLocalDateMock().toUtilDate())
            .setter(DeliveryInfo::setBalance, getDoubleMock())
            .setter(DeliveryInfo::setStatus, generateStatus())
            .setter(DeliveryInfo::setDiscount, generateDiscount(1.5, 20.5))
            .setter(DeliveryInfo::setCodes, generateInts(10, 100, 200))
            .setter(DeliveryInfo::setAddresses, getAddressInfoMock().list(addressInfoCount));
    }

    /**
     * Returns {@link DeliveryInfo.DeliveryStatus} {@link MockUnit}
     *
     * @return {@link DeliveryInfo.DeliveryStatus} {@link MockUnit}
     */
    protected MockUnit<DeliveryInfo.DeliveryStatus> generateStatus() {
        return ints().bound(DELIVERY_STATUS_LIST.size()).map(value -> DELIVERY_STATUS_LIST.get(value));
    }

    /**
     * Returns delivery info discount {@link MockUnit} by initial input range (lower / upper bounds)
     *
     * @return delivery info discount {@link MockUnit}
     */
    protected MockUnit<BigDecimal> generateDiscount(double lowerBound, double upperBound) {
        return getMock().doubles().range(lowerBound, upperBound).map(BigDecimal::valueOf);
    }

    /**
     * Default {@link AddressInfo} {@link MockUnit}
     *
     * @return {@link AddressInfo} {@link MockUnit}
     */
    protected MockUnit<AddressInfo> getAddressInfoMock() {
        return filler(() -> new AddressInfo())
            .setter(AddressInfo::setId, getLongMock())
            .setter(AddressInfo::setCity, getLettersStringMock())
            .setter(AddressInfo::setCountry, getLettersStringMock())
            .setter(AddressInfo::setPostalCode, getAlphaNumericStringMock())
            .setter(AddressInfo::setStateOrProvince, getLettersStringMock())
            .setter(AddressInfo::setStreet, getLettersStringMock());
    }
}
