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

import com.wildbeeslabs.sensiblemetrics.comparalyzer.examples.model.DeliveryInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import net.andreinc.mockneat.abstraction.MockUnit;

import java.util.Comparator;

import static net.andreinc.mockneat.unit.objects.Filler.filler;

/**
 * Abstract difference unit test
 */
@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public abstract class AbstractDeliveryInfoDiffTest extends AbstractDiffTest {

    /**
     * Default delivery info comparator instance {@link Comparator}
     */
    protected static final Comparator<? super DeliveryInfo> DEFAULT_COMPARATOR = Comparator
            .comparing(DeliveryInfo::getId)
            .thenComparing(DeliveryInfo::getType)
            .thenComparing(DeliveryInfo::getDescription)
            .thenComparing(DeliveryInfo::getCreatedAt)
            .thenComparing(DeliveryInfo::getUpdatedAt)
            .thenComparing(DeliveryInfo::getBalance);

    /**
     * Default delivery info unit {@link MockUnit}
     *
     * @return delivery info unit {@link MockUnit}
     */
    protected MockUnit<DeliveryInfo> getDeliveryInfoUnit() {
        return filler(() -> new DeliveryInfo())
                .setter(DeliveryInfo::setId, mockUnitLong)
                .setter(DeliveryInfo::setType, mockUnitInt)
                .setter(DeliveryInfo::setDescription, lettersMockUnitString)
                .setter(DeliveryInfo::setCreatedAt, mockUnitLocalDate.toUtilDate())
                .setter(DeliveryInfo::setUpdatedAt, mockUnitLocalDate.toUtilDate())
                .setter(DeliveryInfo::setBalance, mockUnitDouble);
    }
}
