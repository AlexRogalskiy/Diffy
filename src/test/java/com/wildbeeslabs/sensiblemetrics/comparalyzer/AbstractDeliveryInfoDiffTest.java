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

import com.wildbeeslabs.sensiblemetrics.comparalyzer.examples.model.AddressInfo;
import com.wildbeeslabs.sensiblemetrics.comparalyzer.examples.model.DeliveryInfo;
import com.wildbeeslabs.sensiblemetrics.comparalyzer.utils.ComparatorUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import net.andreinc.mockneat.abstraction.MockUnit;

import java.util.Comparator;
import java.util.List;

import static net.andreinc.mockneat.unit.objects.Filler.filler;

/**
 * Abstract delivery info difference unit test
 *
 * @author Alexander Rogalskiy
 * @version %I%, %G%
 * @since 1.0
 */
@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public abstract class AbstractDeliveryInfoDiffTest extends AbstractDiffTest {

    /**
     * Default address info list size
     */
    private static final int DEFAULT_ADDRESS_INFO_LIST_SIZE = 5;

    /**
     * Default address info comparator instance {@link Comparator}
     */
    protected static final Comparator<? super AddressInfo> DEFAULT_ADDRESS_INFO_COMPARATOR =
            Comparator.comparing(AddressInfo::getId)
                    .thenComparing(AddressInfo::getCity)
                    .thenComparing(AddressInfo::getCountry)
                    .thenComparing(AddressInfo::getPostalCode)
                    .thenComparing(AddressInfo::getStateOrProvince)
                    .thenComparing(AddressInfo::getStreet);

    /**
     * Default address info list comparator instance {@link Comparator}
     */
    protected static final Comparator<? super List<AddressInfo>> DEFAULT_ADDRESS_INFO_COLLECTION_COMPARATOR = ComparatorUtils.getDefaultIterableComparator(DEFAULT_ADDRESS_INFO_COMPARATOR);

    /**
     * Default delivery info comparator instance {@link Comparator}
     */
    protected static final Comparator<? super DeliveryInfo> DEFAULT_DELIVERY_INFO_COMPARATOR =
            Comparator.comparing(DeliveryInfo::getId)
                    .thenComparing(DeliveryInfo::getType)
                    .thenComparing(DeliveryInfo::getGid)
                    .thenComparing(DeliveryInfo::getDescription)
                    .thenComparing(DeliveryInfo::getCreatedAt)
                    .thenComparing(DeliveryInfo::getUpdatedAt)
                    .thenComparing(DeliveryInfo::getBalance)
                    .thenComparing(DeliveryInfo::getAddresses, DEFAULT_ADDRESS_INFO_COLLECTION_COMPARATOR);

    /**
     * Default delivery info mock unit instance {@link MockUnit}
     *
     * @return delivery info mock unit instance {@link MockUnit}
     */
    protected MockUnit<DeliveryInfo> getDeliveryInfoUnit() {
        return getDeliveryInfoUnit(DEFAULT_ADDRESS_INFO_LIST_SIZE);
    }

    /**
     * Default delivery info mock unit instance {@link MockUnit}
     *
     * @return delivery info mock unit instance {@link MockUnit}
     */
    protected MockUnit<DeliveryInfo> getDeliveryInfoUnit(int addressListSize) {
        return filler(() -> new DeliveryInfo())
                .setter(DeliveryInfo::setId, getLongMock())
                .setter(DeliveryInfo::setType, getIntMock())
                .setter(DeliveryInfo::setGid, getAlphaNumericStringMock())
                .setter(DeliveryInfo::setDescription, getLettersStringMock())
                .setter(DeliveryInfo::setCreatedAt, getLocalDateMock().toUtilDate())
                .setter(DeliveryInfo::setUpdatedAt, getLocalDateMock().toUtilDate())
                .setter(DeliveryInfo::setBalance, getDoubleMock())
                .setter(DeliveryInfo::setAddresses, getAddressInfoUnit().list(addressListSize));
    }

    /**
     * Default address info unit {@link MockUnit}
     *
     * @return address info unit {@link MockUnit}
     */
    protected MockUnit<AddressInfo> getAddressInfoUnit() {
        return filler(() -> new AddressInfo())
                .setter(AddressInfo::setId, getLongMock())
                .setter(AddressInfo::setCity, getLettersStringMock())
                .setter(AddressInfo::setCountry, getLettersStringMock())
                .setter(AddressInfo::setPostalCode, getAlphaNumericStringMock())
                .setter(AddressInfo::setStateOrProvince, getLettersStringMock())
                .setter(AddressInfo::setStreet, getLettersStringMock());
    }
}
