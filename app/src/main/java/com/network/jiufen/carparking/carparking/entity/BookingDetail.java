package com.network.jiufen.carparking.carparking.entity;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by chachen on 9/26/2017.
 */
@Data
@NoArgsConstructor
public class BookingDetail implements Serializable {
    private static final long serialVersionUID = -3258839839160856613L;
    private String id;
    private String phoneNumber; //Compulsory
    private String bookingTime;   //Compulsory
    private String startTime; //Compulsory
    private String endTime;   //Compulsory
    private String plateNumber; //Compulsory
    private Integer peopleCounts;
    private String carCounts;
    private String parkingLotName;  //Compulsory
    private Integer totalPrice;
    private Integer bookingFee;

    public BookingDetail(String id, String phoneNumber, String startTime, String endTime, String plateNumber, Integer peopleCounts, String carCounts, String parkingLotName, Integer totalPrice, Integer bookingFee) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.startTime = startTime;
        this.endTime = endTime;
        this.plateNumber = plateNumber;
        this.peopleCounts = peopleCounts;
        this.carCounts = carCounts;
        this.parkingLotName = parkingLotName;
        this.totalPrice = totalPrice;
        this.bookingFee = bookingFee;
    }
}
