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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.google.common.collect.Lists;
import com.wildbeeslabs.sensiblemetrics.diffy.AbstractDeliveryInfoDiffTest;
import com.wildbeeslabs.sensiblemetrics.diffy.comparator.impl.DefaultDiffComparator;
import com.wildbeeslabs.sensiblemetrics.diffy.entry.impl.DefaultDiffEntry;
import com.wildbeeslabs.sensiblemetrics.diffy.entry.view.DefaultDiffEntryView;
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
import java.util.Map;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertNull;
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
     * Default {@link DeliveryInfo} models
     */
    private DeliveryInfo deliveryInfoFirst;
    private DeliveryInfo deliveryInfoLast;

    @Before
    public void setUp() {
        this.deliveryInfoFirst = getDeliveryInfoMock().val();
        this.deliveryInfoLast = getDeliveryInfoMock().val();
    }

    @Test
    @DisplayName("Test serialize difference entries by default mapper and external view class")
    public void testSerializeDiffEntriesByDefaultMapperAndExternalView() throws IOException {
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
        String jsonString = MapperUtils.toJson(valueChangeList.get(0), DefaultDiffEntryView.External.class);
        final DefaultDiffEntry entry = MapperUtils.map(jsonString, DefaultDiffEntry.class, DefaultDiffEntryView.External.class);

        // then
        assertThat(entry.getFirst(), IsEqual.equalTo(valueChangeList.get(0).getFirst()));
        assertThat(entry.getLast(), IsEqual.equalTo(valueChangeList.get(0).getLast()));

        // when
        jsonString = MapperUtils.toJson(valueChangeList, DefaultDiffEntryView.External.class);
        final List<DefaultDiffEntry> entries = MapperUtils.toList(jsonString);

        // then
        assertThat(entries, is(not(empty())));
        assertThat(entries.size(), IsEqual.equalTo(valueChangeList.size()));
    }

    @Test
    @DisplayName("Test serialize difference entry by default mapper and external view class")
    public void testSerializeDiffEntryByDefaultMapperAndExternalView() throws IOException {
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
        String jsonString = MapperUtils.toJson(valueChangeList.get(0), DefaultDiffEntryView.External.class);
        final DefaultDiffEntry entry = MapperUtils.map(jsonString, DefaultDiffEntry.class, DefaultDiffEntryView.External.class);

        // then
        assertThat(entry.getFirst(), IsEqual.equalTo(valueChangeList.get(0).getFirst()));
        assertThat(entry.getLast(), IsEqual.equalTo(valueChangeList.get(0).getLast()));

        // when
        jsonString = MapperUtils.toJson(valueChangeList, DefaultDiffEntryView.External.class);
        final List<Map<String, DefaultDiffEntry>> entries = MapperUtils.toList(jsonString);

        // then
        assertThat(entries, is(not(empty())));
        assertThat(entries.size(), IsEqual.equalTo(valueChangeList.size()));
    }

    @Test
    @DisplayName("Test deserialize collection of difference entries by default mapper")
    public void testDeserializeDiffEntriesByDefaultMapperAndExternalView() throws IOException {
        // given
        final String jsonString = "[{\"property\":\"description\",\"first\":\"oeffPyZQcxoaOMFrKOdbrMURgasTaQUbRlAwPztMeUptxWehkROMStfwgbFAPVhl\",\"last\":\"boHdMLIhLCnAPjXZOclxTWMflYdGmZDVewXYfqjIDFSIPAqsElGjOEOgNInznuhb\"},{\"property\":\"id\",\"first\":5814988,\"last\":8258751},{\"property\":\"type\",\"first\":425402,\"last\":620131}]";

        // when
        final List<Map<String, ?>> entries = MapperUtils.toList(jsonString);

        // then
        assertThat(entries, is(not(empty())));
        assertThat(entries.size(), IsEqual.equalTo(3));

        // then
        assertThat(entries.get(0).get("property"), IsEqual.equalTo("description"));
        assertThat(entries.get(0).get("first"), IsEqual.equalTo("oeffPyZQcxoaOMFrKOdbrMURgasTaQUbRlAwPztMeUptxWehkROMStfwgbFAPVhl"));
        assertThat(entries.get(0).get("last"), IsEqual.equalTo("boHdMLIhLCnAPjXZOclxTWMflYdGmZDVewXYfqjIDFSIPAqsElGjOEOgNInznuhb"));

        assertThat(entries.get(1).get("property"), IsEqual.equalTo("id"));
        assertThat(entries.get(1).get("first"), IsEqual.equalTo(5814988));
        assertThat(entries.get(1).get("last"), IsEqual.equalTo(8258751));

        assertThat(entries.get(2).get("property"), IsEqual.equalTo("type"));
        assertThat(entries.get(2).get("first"), IsEqual.equalTo(425402));
        assertThat(entries.get(2).get("last"), IsEqual.equalTo(620131));
    }

    @Test
    @DisplayName("Test deserialize single difference entry by default mapper")
    public void testDeserializeDiffEntryByDefaultMapperAndEmptyView() throws IOException {
        // given
        final String jsonString = "{\"property\":\"description\",\"first\":\"qJuhKeFPtekjZMfsHNntujjnmNbFBKhQPFIVdsEWsWfcJHkbTYnTNdchFGsdPjTp\",\"last\":\"SklOvcvqlEoXlAJspQyIVCjvzBQFsjrwYifJIAhJuZpLstBKYgYjZawqNTvXZkkG\"}";

        // when
        final DefaultDiffEntry entry = MapperUtils.map(jsonString, DefaultDiffEntry.class);

        // then
        assertThat(entry.getPropertyName(), IsEqual.equalTo("description"));
        assertThat(entry.getFirst(), IsEqual.equalTo("qJuhKeFPtekjZMfsHNntujjnmNbFBKhQPFIVdsEWsWfcJHkbTYnTNdchFGsdPjTp"));
        assertThat(entry.getLast(), IsEqual.equalTo("SklOvcvqlEoXlAJspQyIVCjvzBQFsjrwYifJIAhJuZpLstBKYgYjZawqNTvXZkkG"));
    }

    @Test
    @DisplayName("Test deserialize single difference entry by default mapper and external view class")
    public void testDeserializeDiffEntryByDefaultMapperAndExternalView() throws IOException {
        // given
        final String jsonString = "{\"property\":\"description\",\"first\":\"qJuhKeFPtekjZMfsHNntujjnmNbFBKhQPFIVdsEWsWfcJHkbTYnTNdchFGsdPjTp\",\"last\":\"SklOvcvqlEoXlAJspQyIVCjvzBQFsjrwYifJIAhJuZpLstBKYgYjZawqNTvXZkkG\"}";

        // when
        final DefaultDiffEntry entry = MapperUtils.map(jsonString, DefaultDiffEntry.class, DefaultDiffEntryView.External.class);

        // then
        assertThat(entry.getPropertyName(), IsEqual.equalTo("description"));
        assertThat(entry.getFirst(), IsEqual.equalTo("qJuhKeFPtekjZMfsHNntujjnmNbFBKhQPFIVdsEWsWfcJHkbTYnTNdchFGsdPjTp"));
        assertThat(entry.getLast(), IsEqual.equalTo("SklOvcvqlEoXlAJspQyIVCjvzBQFsjrwYifJIAhJuZpLstBKYgYjZawqNTvXZkkG"));
    }

    @Test
    @DisplayName("Test deserialize single difference entry by default mapper and internal view class")
    public void testDeserializeDiffEntryByDefaultMapperAndInternalView() throws IOException {
        // given
        final String jsonString = "{\"id\":\"1\",\"property\":\"description\",\"first\":\"qJuhKeFPtekjZMfsHNntujjnmNbFBKhQPFIVdsEWsWfcJHkbTYnTNdchFGsdPjTp\",\"last\":\"SklOvcvqlEoXlAJspQyIVCjvzBQFsjrwYifJIAhJuZpLstBKYgYjZawqNTvXZkkG\"}";

        // when
        final DefaultDiffEntry entry = MapperUtils.map(jsonString, DefaultDiffEntry.class, DefaultDiffEntryView.Internal.class);

        // then
        assertThat(entry.getId(), IsEqual.equalTo("1"));
        assertThat(entry.getPropertyName(), IsEqual.equalTo("description"));
        assertThat(entry.getFirst(), IsEqual.equalTo("qJuhKeFPtekjZMfsHNntujjnmNbFBKhQPFIVdsEWsWfcJHkbTYnTNdchFGsdPjTp"));
        assertThat(entry.getLast(), IsEqual.equalTo("SklOvcvqlEoXlAJspQyIVCjvzBQFsjrwYifJIAhJuZpLstBKYgYjZawqNTvXZkkG"));
    }

    @Test
    @DisplayName("Test deserialize single difference entry with mismatch properties by default mapper and external view class")
    public void testDeserializeDiffEntryWithMismatchPropertiesByDefaultMapperAndExternalView() throws IOException {
        // given
        final String jsonString = "{\"property\":\"description\",\"entityFirst\":\"cGSWCwEawsEvMrAPghayOBoGhUYURCHTqxhHWboiopHSgiQJlphvkHNvpWigpkaJ\",\"entityLast\":\"eYqOeXZUihWmoLlhjTxWkEpuObQJMuMGHqOwLlGkLBfEbdWvtlIDdoYKbTUaNgmL\"}";

        // when
        final DefaultDiffEntry entry = MapperUtils.map(jsonString, DefaultDiffEntry.class, DefaultDiffEntryView.External.class);

        // then
        assertThat(entry.getPropertyName(), IsEqual.equalTo("description"));
        assertNull(entry.getFirst());
        assertNull(entry.getLast());
    }

    @Test
    @DisplayName("Test deserialize single empty difference entry by default mapper and external view class")
    public void testDeserializeEmptyDiffEntryByDefaultMapperAndExternalView() throws IOException {
        // given
        final String jsonString = "{}";

        // when
        final DefaultDiffEntry entry = MapperUtils.map(jsonString, DefaultDiffEntry.class, DefaultDiffEntryView.External.class);

        // then
        assertNull(entry.getPropertyName());
        assertNull(entry.getFirst());
        assertNull(entry.getLast());
    }

    @Test(expected = MismatchedInputException.class)
    @DisplayName("Test deserialize invalid difference entry by default mapper and external view class")
    public void testDeserializeInvalidDiffEntryByDefaultMapperAndExternalView() throws IOException {
        // given
        final String jsonString = "[]";

        // when
        MapperUtils.map(jsonString, DefaultDiffEntry.class, DefaultDiffEntryView.External.class);
    }

    @Test(expected = JsonParseException.class)
    @DisplayName("Test deserialize unquoted difference entry by default mapper and external view class")
    public void testDeserializeUnquotedDiffEntryByDefaultMapperAndExternalView() throws IOException {
        // given
        final String jsonString = "{[]}";

        // when
        MapperUtils.map(jsonString, DefaultDiffEntry.class, DefaultDiffEntryView.External.class);
    }

    @Test
    @DisplayName("Test serialize custom difference entries by default mapper and external view class")
    public void testSerializeCustomDiffEntriesByDefaultMapperAndExternalView() throws IOException {
        // given
        final String expectedJsonString = "[{\"property\":\"codes\",\"first\":[1,2,3],\"last\":[1,2]},{\"property\":\"gid\",\"first\":\"Test 01\",\"last\":\"Test 02\"},{\"property\":\"description\",\"first\":\"Description 01\",\"last\":\"Description 02\"},{\"property\":\"discount\",\"first\":10,\"last\":1},{\"property\":\"id\",\"first\":1,\"last\":2},{\"property\":\"status\",\"first\":\"DELIVERED\",\"last\":\"PENDING\"}]";
        final DeliveryInfo deliveryInfoFirst = createDeliveryInfo(1L, 10, "Test 01", 0.68, "Description 01", DeliveryInfo.DeliveryStatus.DELIVERED, BigDecimal.TEN, 1, 2, 3);
        final DeliveryInfo deliveryInfoLast = createDeliveryInfo(2L, 10, "Test 02", 0.68, "Description 02", DeliveryInfo.DeliveryStatus.PENDING, BigDecimal.ONE, 1, 2);

        final DefaultDiffComparator<DeliveryInfo> diffComparator = DefaultDiffComparatorFactory.create(DeliveryInfo.class);

        // when
        final Iterable<DefaultDiffEntry> iterable = diffComparator.diffCompare(deliveryInfoFirst, deliveryInfoLast);
        final List<DefaultDiffEntry> valueChangeList = Lists.newArrayList(iterable);

        // then
        assertThat(valueChangeList, is(not(empty())));
        assertThat(valueChangeList.size(), IsEqual.equalTo(6));

        // when
        String jsonString = MapperUtils.toJson(valueChangeList, DefaultDiffEntryView.External.class);

        // then
        assertThat(jsonString, IsEqual.equalTo(expectedJsonString));
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
