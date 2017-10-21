package com.network.jiufen.carparking.carparking.enumeration;

public enum BookingStatusEnum {
    Booked("已预订"), CheckedIn("已入场"),CheckedOut("已出场"),Canceled("已取消");

    private final  String value;

    private  BookingStatusEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
