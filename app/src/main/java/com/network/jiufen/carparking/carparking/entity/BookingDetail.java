package com.network.jiufen.carparking.carparking.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.network.jiufen.carparking.carparking.enumeration.BookingStatusEnum;

import org.joda.time.DateTime;

import java.io.Serializable;

import lombok.AllArgsConstructor;
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
    @JsonFormat(pattern="yyyy-MM-dd HH:mm", timezone="GMT+8")
    private DateTime bookingTime; //Compulsory
    @JsonFormat(pattern="yyyy-MM-dd HH:mm", timezone="GMT+8")
    private DateTime planedCheckInTime; //Compulsory
    @JsonFormat(pattern="yyyy-MM-dd HH:mm", timezone="GMT+8")
    private DateTime planedCheckOutTime;   //Compulsory
    @JsonFormat(pattern="yyyy-MM-dd HH:mm", timezone="GMT+8")
    private DateTime actualCheckInTime; //Compulsory
    @JsonFormat(pattern="yyyy-MM-dd HH:mm", timezone="GMT+8")
    private DateTime actualCheckOutTime;   //Compulsory
    private Integer parkingDays;
    private String plateNumber; //Compulsory
    private Integer peopleCounts;
    private String carCounts;
    private String parkingLotName;  //Compulsory
    private String parkingLotAddress; //Compulsory
    private Integer totalPrice;
    private Integer bookingFee;
    private BookingStatusEnum bookingStatus;

}
