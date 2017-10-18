package com.network.jiufen.carparking.carparking.util;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

/**
 * Created by asus on 2017/10/19.
 */
public class DateUtilTest {

    @Test
    public void testConvertToString() {
        DateTime dateTime = new DateTime("2020-01-01T00:00:00.000Z", DateTimeZone.UTC);
        DateTimeFormatter sdf = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
        System.out.println(dateTime.toString("yyyy-MM-dd HH:mm"));
    }

}