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
package com.wildbeeslabs.sensiblemetrics.diffy.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.common.collect.Lists;
import com.wildbeeslabs.sensiblemetrics.diffy.AbstractDeliveryInfoDiffTest;
import com.wildbeeslabs.sensiblemetrics.diffy.comparator.DiffComparator;
import com.wildbeeslabs.sensiblemetrics.diffy.comparator.impl.DefaultDiffComparator;
import com.wildbeeslabs.sensiblemetrics.diffy.entry.impl.DefaultDiffEntry;
import com.wildbeeslabs.sensiblemetrics.diffy.entry.view.EntryView;
import com.wildbeeslabs.sensiblemetrics.diffy.examples.model.DeliveryInfo;
import com.wildbeeslabs.sensiblemetrics.diffy.factory.DefaultDiffComparatorFactory;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.core.IsEqual;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * Mapper utils unit test
 *
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MapperUtilsTest extends AbstractDeliveryInfoDiffTest {

    /**
     * Default delivery info models {@link DeliveryInfo}
     */
    private DeliveryInfo deliveryInfoFirst;
    private DeliveryInfo deliveryInfoLast;

    @Before
    public void setUp() {
        this.deliveryInfoFirst = getDeliveryInfoMock().val();
        this.deliveryInfoLast = getDeliveryInfoMock().val();
    }

    @Test
    @DisplayName("Test serializing objects by custom mapper")
    public void testDeliveryInfoByDiffComparator() throws IOException {
        // given
        final List<String> includedProperties = Arrays.asList("id", "type", "description");
        final DefaultDiffComparator<DeliveryInfo> diffComparator = DefaultDiffComparatorFactory.create(DeliveryInfo.class);
        diffComparator.includeProperties(includedProperties);

        // when
        final Iterable<DefaultDiffEntry> iterable = diffComparator.diffCompare(getDeliveryInfoFirst(), getDeliveryInfoLast());
        final List<DefaultDiffEntry> valueChangeList = Lists.newArrayList(iterable);

        // then
        assertThat(valueChangeList, is(not(empty())));
        assertThat(valueChangeList.size(), is(greaterThanOrEqualTo(0)));
        assertThat(valueChangeList.size(), is(lessThanOrEqualTo(includedProperties.size())));

        // when
        final String jsonString = MapperUtils.mapToJson(valueChangeList.get(0), EntryView.External.class);
        final DefaultDiffEntry entry = MapperUtils.mapFromJson(jsonString, DefaultDiffEntry.class, EntryView.External.class);

        // then
        assertEquals(entry.getFirst(), valueChangeList.get(0).getFirst());
        assertEquals(entry.getLast(), valueChangeList.get(0).getLast());
    }

    @Test
    @DisplayName("Test serializing objects by custom mapper")
    public void testObjectsByDefaultComparator() throws JsonProcessingException {
        // given
        final String expectedJsonResult = "[{\"id\":null,\"propertyName\":\"codes\",\"first\":[107,111,189,110,150,134,157,139,119,143],\"last\":[143,152,177,136,188,149,176,158,195,147]},{\"id\":null,\"propertyName\":\"createdAt\",\"first\":200782800000,\"last\":521582400000},{\"id\":null,\"propertyName\":\"addresses\",\"first\":[{\"id\":9183259,\"city\":\"MezczcTcrDVhRDuATvOFHzSJFUgDLnZTDpMPvaTABAjYRWQjrshfcUwoTQDRmUia\",\"country\":\"fEoYMHKZaBLltuXTuPjGBnmNBVnAwqIYwxwuiuovmgAOjrsiqKWbNeENVgdtWQFp\",\"stateOrProvince\":\"iRqPAMYvHZozHDJJgyhlBqYdZMVYVvLMrTqvLEkmcybodAZuxQAWzZitrnKEBzrV\",\"postalCode\":\"nvbQ7ypk19PZ01lK1NhDw1nr2vs0ecMaE25qHlVHA6ETgpnlGJnpYdOe6OsHebTF\",\"street\":\"WbfREgYMaKojtILnJOaqobTdCONMpxjIQGUpTTggnuBJUmBwuJORQvAfkMVjMrrK\"},{\"id\":1274888,\"city\":\"TkCFDjwjuMwZqjqmaDnLOVLrilXAUjpkoruIIQiiQbvuxzNbOHBFpLFxBcPUXWWG\",\"country\":\"sNABMPcxStDZbVgJIIonJXLEOQClbVpMxmNwRLaxjhUkrKVmMHVNyTOwXipjKGcG\",\"stateOrProvince\":\"PJAmdpfOmwWCuojnsadcIHgoxVPTabRFuFuekdNRDsoHenQqLEHAFLCkQPEOPvlc\",\"postalCode\":\"sgceyD3BzGXLXu2W9vqeC0FihjoBGQ6ZDcVsRiSuivX0Sgr5Li0ycsR8o5C78bV2\",\"street\":\"sPAtGENYElSYTWlbiyoboepKNusqerRPeBLXTRSxjWPDWWzLpibrXoepWXjtQAQS\"},{\"id\":6035643,\"city\":\"YMNIDJRyDTUjDKYvjPTtuQGnYUBYkTxQhVTtLVpQoobORYiycpYfYExgDNUcqUOk\",\"country\":\"PhxmcCTjOabeYnTarObduNKaQBTgWofHWIfCwrlvBtdXgIhCSThxszoPKmDciCwK\",\"stateOrProvince\":\"iZysRWDBmmlwqcLTVnDqVoWktuhfRsiYClDLsMscirECMclTumpquecaFrcfPwak\",\"postalCode\":\"I3nWWPu1WddHKM8dXzrGVrCJetWQ4MAKmzy13pUhs8xQe5Giffo49Yf4tQm6TZp7\",\"street\":\"JnNFbWULDNsTlbexMQIUpASzXNrhnJyZQYKVMpYwdISVCjKypXmtFtEAvOlaOLfb\"},{\"id\":5739158,\"city\":\"zEqrZYFbBHMbRROfQpxnBiMYTdUNWJkwvYxODEHxaFxFMiwbabSQfAJklpCqGWAa\",\"country\":\"bVdTlexsRwEcsLqDbpPDiFYSdrtJcJekLSofBFpNGLqPgOjRTnOFNoNbhhhbZJLb\",\"stateOrProvince\":\"PEmkDRIddQTlEDRyhpgUCnMggOEJOpqsGjeQVJKpkfmLDbjFLMRlYgbrJzOWSKrP\",\"postalCode\":\"8xrsrjXRZj1Rtg1T062qqJsks9Lc9uMJTrllV4QALtxJKJjCD8cOE9LO6usBFYrx\",\"street\":\"sNXvpBRmlJAQYJaeaSQZVlhgfTeyaqqyAZZhSCpFaMUyfyBMTOYrUUPeoNsxOIeF\"},{\"id\":874071,\"city\":\"YyvUiDauIeWwzDWBSotUvPrcgTWvhkzYULAQsrOKUJhocHMNIKIzFsEhjAXYKKKG\",\"country\":\"xKiSzBTskIQRJOQngBrlfNnmWzVTckdqWXYcaDSbWaXJtRpKkzkPnNxETXNqmTLc\",\"stateOrProvince\":\"NUqGxzHSnBDwiqNFCHOdilIaEyrNzxvgxbBoERjsOeDuTZJtgetXFmsPgkiikYdJ\",\"postalCode\":\"PUqY8tLubBo6A6etMURuuxPCZs2j86qBLpu7BnpRHqszahL2BRcgumS8DapLdgfQ\",\"street\":\"hjnPphsepyVAHVeVBIVCOvQOwdWDIChONtbQWRPRmiBEWRRGGGDXUiDrIrBYFnwS\"}],\"last\":[{\"id\":7341564,\"city\":\"HqRSLRsEwoGEHLfaHoSIzLCpPYqIvGvEMbDVTKkblQuXIhJuFSKuNrJfBWtfoUIb\",\"country\":\"nmSwrgBrvzNNUONhRxdmYvHAeUKgHRnAdlzPMMrsmJJAlLwhcrLFTvdzdgbPzLFk\",\"stateOrProvince\":\"meJriFKKkMKKqBOHjFYjGuzycbCdYOvAmEmVDPzAAZxRJELphCQdXVbFnPEuWpnO\",\"postalCode\":\"n35rwdQtXrqLyHA34KLMluzfhozF7mew7T8Yrxo8jX9J3ouyjEbvDibCS8O1PlB7\",\"street\":\"bPKoCuZClbeEfkwaDrDBQLaoPfLJkcDoEjZXsoVhEAuslMKVAavVCplYBQcxuGiv\"},{\"id\":7723550,\"city\":\"ShwHnNHJywMUIdRaRsLdLDYchgbcQDCIRvbrCoWRjkMrwSnQJDaCgTgkeiHgrzIV\",\"country\":\"BQNybFAHeKkiXnedmThqmqAuKjEplNsMGfEzYdTObaErhMZyfvKOnrBdhuLHWgnN\",\"stateOrProvince\":\"fLTjOkEAAAgsQcTXXLRXNrliykqRcDuVwlYdutmlTfgUTaTpjCNSHMfIPcIKeQMO\",\"postalCode\":\"ZEu5DGwhcZ1uu0OXwGoQ63cmcv3Nz1AbY4XtUGFeuESIXLUjTsvGky3m09L96aHA\",\"street\":\"wfBKLkYaZxscCBNUoRDpoaWCfNXeNvHyychJFDBfXnpxdliMJybPxGPVtUeJOzBp\"},{\"id\":7458037,\"city\":\"HeIuZItWafxqnHmLfKpZMEoLvAWRETnlzuZbVCDsoXMxulhOZYQaDmdoTTGxyAbd\",\"country\":\"DLebNjiEgfaohmtWNrTrZndnSpbSqkLNFmMmhUbbosOYRBXOYFdVmLnnOHbNGqyN\",\"stateOrProvince\":\"gkGBaAyszZBpioMcEaWOIpYctiubwlWqBGRPIKIdHYZSpjFJGOgMoTbhEfOZyFhc\",\"postalCode\":\"sWk9iz4ZO1q6WQuh99vO24QT9Vj9lUl2wLJhpJCcRqYy7Dld9qbcqPUcztcx3uCa\",\"street\":\"DxuSBGlHKyxbmqrXqCmetekCvsxRdwJVzdzUPJIuueYurhyAIwCmddFLITHgvdAF\"},{\"id\":5529635,\"city\":\"JFZJyWTbykaPPbUiOijqKfZBVbSwHKjBXcVhKfaXbJiLnUplZFIqyZlHMBQcWOPm\",\"country\":\"vHvGmvQmfkUfYkbQZvJcnTokQDtmFjkLIyqGrlPppECVrGdMwjVVcaFTsUBQgfrp\",\"stateOrProvince\":\"VPopAxVmPhmvZweKTwYkjbFGOxbhCPXihDndEyquRKPocagsiOEOpRHOUciuvtIB\",\"postalCode\":\"kiq4ozAwmLC3GP8HFzjrYByAI2FhupV397LONpEtpXEuOjfYRO5BDjNVxb2JFyXn\",\"street\":\"XJTJXEnBDfgBoWbRbBGQPdaytWlIPCsglibupRIyOaerVFEdUUrMXEDMFxBPWYsw\"},{\"id\":8325831,\"city\":\"SUMROSJzmwDYquUcvAYUsrgyBOgBPJdAklvDhkaVTpXORovZhwypaqUpvcbBNEIG\",\"country\":\"xknNvROhqofgvKhuvrdhOQcKSWzMKxedXZpJzQzNalKkqUaTIbEvtwxvfOYqvVgF\",\"stateOrProvince\":\"MwuzGthomuOfeVjaqHXnLpWMaEUBxMkZxynxbLnqtLtechlropAeuzSBRUwFPeVX\",\"postalCode\":\"ZlQytTX2k8tGWDOx8fUCeppzsbWEO8F7mg2HOhIY2G8DN3ZpmUWm7FCALRIgr9Vi\",\"street\":\"jJmWMqsPoYrrCouPcfnwUqhGaPIxaFNhOkPvOvfPoQSmNQqMNMBMWHzHZvbVQxKZ\"}]},{\"id\":null,\"propertyName\":\"gid\",\"first\":\"tEZbDhUuT01ylAd9MccpK977X8OKM5s4Phry3wgtBxVa7BEGIsMlWsG63InZzv6X\",\"last\":\"Fz3EnTNtUaNExgAKSddREARnWHbkJpsYphs9BaBz83l5w8yr7LMHtDvfG9Enx4kW\"},{\"id\":null,\"propertyName\":\"balance\",\"first\":48353.200317735136,\"last\":1146.4539067625235},{\"id\":null,\"propertyName\":\"discount\",\"first\":7.86074363446051,\"last\":11.605950547072029},{\"id\":null,\"propertyName\":\"description\",\"first\":\"hrnbWJqDISXWPddxMFJvWgHfPNzdoyRhkFSsKLbntRrrhEzlBxDofyqltZJzIAky\",\"last\":\"bPbyJvfdKTqwcEDoUpzTXpitQxIDTHisHAMcxQRBbAVDLmsjqNvdixvrDbXMkAlp\"},{\"id\":null,\"propertyName\":\"id\",\"first\":8042080,\"last\":5108549},{\"id\":null,\"propertyName\":\"type\",\"first\":46109,\"last\":16002},{\"id\":null,\"propertyName\":\"status\",\"first\":\"PENDING\",\"last\":\"REJECTED\"},{\"id\":null,\"propertyName\":\"updatedAt\",\"first\":1319918400000,\"last\":1267563600000}]";
        final DiffComparator<DeliveryInfo> diffComparator = DefaultDiffComparatorFactory.create(DeliveryInfo.class);

        // when
        final Iterable<DefaultDiffEntry> iterable = diffComparator.diffCompare(getDeliveryInfoFirst(), getDeliveryInfoLast());
        final List<DefaultDiffEntry> valueChangeList = Lists.newArrayList(iterable);

        // when
        final String jsonResult = MapperUtils.mapToJson(valueChangeList);

        // then
        assertThat(jsonResult, IsEqual.equalTo(""));
    }

    @Test(expected = JsonMappingException.class)
    public void givenAbstractClass_whenDeserializing_thenException() throws IOException {
        final String jsonString = "{\"propertyName\":\"description\",\"first\":\"cGSWCwEawsEvMrAPghayOBoGhUYURCHTqxhHWboiopHSgiQJlphvkHNvpWigpkaJ\",\"last\":\"eYqOeXZUihWmoLlhjTxWkEpuObQJMuMGHqOwLlGkLBfEbdWvtlIDdoYKbTUaNgmL\"}";
        final DefaultDiffEntry entry = MapperUtils.mapFromJson(jsonString, DefaultDiffEntry.class, EntryView.External.class);
    }

    /**
     * Returns new {@link DeliveryInfo} instance by initial input parameters
     *
     * @param id          - initial input id value
     * @param type        - initial input type value
     * @param gid         - initial input global id value
     * @param balance     - initial input balance value
     * @param description - initial input description value
     * @param status      - initial input status value {@link DeliveryInfo.DeliveryStatus}
     * @param discount    - initial input discount value {@link BigDecimal}
     * @param codes       - initial input array of codes
     * @return new {@link DeliveryInfo} instance
     */
    protected DeliveryInfo createDeliveryInfo(
        final Long id,
        final Integer type,
        final String gid,
        double balance,
        final String description,
        final DeliveryInfo.DeliveryStatus status,
        final BigDecimal discount,
        final Integer... codes) {
        final DeliveryInfo deliveryInfo = new DeliveryInfo();
        deliveryInfo.setId(id);
        deliveryInfo.setType(type);
        deliveryInfo.setGid(gid);
        deliveryInfo.setBalance(balance);
        deliveryInfo.setDescription(description);
        deliveryInfo.setStatus(status);
        deliveryInfo.setDiscount(discount);
        deliveryInfo.setCodes(codes);
        return deliveryInfo;
    }
}
