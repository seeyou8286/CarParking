package com.network.jiufen.carparking.carparking.util;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by asus on 2017/10/19.
 */

public class DateUtil {
    public static final String DefautDateFormat = "yyyy-MM-dd HH:mm";

    public static DateTime convertFromStringToDateTime(String dateTime) {
        DateTimeFormatter sdf = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
        return sdf.parseDateTime(dateTime).withZoneRetainFields(DateTimeZone.forID("Asia/Shanghai"));

    }

}
