package com.network.jiufen.carparking.carparking.entity;


import java.io.Serializable;

/**
 * Created by chachen on 9/26/2017.
 */
public class BookingDetail implements Serializable {
    private static final long serialVersionUID = -3258839839160856613L;
    private String phoneNumber; //Compulsory
//    private DateTime startTime; //Compulsory
//    private DateTime endTime;   //Compulsory
    private String plateNumber; //Compulsory
    private Integer peopleCounts;
    private String carCounts;
    private String placeName;  //Compulsory

    public BookingDetail() {
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public Integer getPeopleCounts() {
        return peopleCounts;
    }

    public void setPeopleCounts(Integer peopleCounts) {
        this.peopleCounts = peopleCounts;
    }

    public String getCarCounts() {
        return carCounts;
    }

    public void setCarCounts(String carCounts) {
        this.carCounts = carCounts;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }
}
