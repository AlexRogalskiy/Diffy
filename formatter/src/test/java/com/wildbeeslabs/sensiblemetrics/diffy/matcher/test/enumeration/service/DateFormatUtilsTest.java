package com.wildbeeslabs.sensiblemetrics.diffy.matcher.test.enumeration.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.testng.annotations.Test;

import java.util.TimeZone;

@Slf4j
public class DateFormatUtilsTest {

    @Test
    public void test_formatUTC() {
        log.debug(DateFormatUtils.formatUTC(System.currentTimeMillis(), "yyyy-MM-dd hh:mm"));
    }

    @Test
    public void test_formatTimezone() {
        log.debug(DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM-dd HH:mm", TimeZone.getTimeZone("America/Detroit")));
    }

    @Test
    public void test_formatTimezone2() {
        log.debug(DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM-dd HH:mm", TimeZone.getTimeZone("Europe/Tallinn")));
    }

    @Test
    public void test_formatTimezone3() {
        log.debug(DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM-dd HH:mm", TimeZone.getTimeZone("Asia/Singapore")));
    }
}
