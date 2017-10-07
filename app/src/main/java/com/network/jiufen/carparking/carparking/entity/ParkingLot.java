package com.network.jiufen.carparking.carparking.entity;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ParkingLot implements Serializable {
    private static final long serialVersionUID = -3258839839160856613L;
    private String name;
    private String distance;
    private String rate;
    private Integer dayPrice;
    private String airportName;
    private String cityName;
    private int imageId;

    public ParkingLot(String name, String distance, String rate, Integer dayPrice, String airportName, String cityName) {
        this.name = name;
        this.distance = distance;
        this.rate = rate;
        this.dayPrice = dayPrice;
        this.airportName = airportName;
        this.cityName = cityName;
    }
}
