package com.network.jiufen.carparking.carparking.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by asus on 2017/9/25.
 */

public class DictionaryUtil {
    public static String PARKING_LOT_NAME = "parkingLotName";
    public static String BOOKING_DETAIL = "bookingDetail";
    public static String PARKING_LOT = "parkingLot";

    public static Map<String,String> dictionary = new HashMap<>();

    //To avoid initialization
    private DictionaryUtil() {
    }

    public static final String APP_KEY="Mob-AppKey";
    public static final String APP_SECRET="Mob-AppSecret";

    public static final String SUCCESS = "success";
    public static final String ERROR = "error";

    public static final String FIRST_OPEN = "first_open";

}
