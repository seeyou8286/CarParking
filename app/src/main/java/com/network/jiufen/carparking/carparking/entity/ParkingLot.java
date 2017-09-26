package com.network.jiufen.carparking.carparking.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by summer on 2017/5/5.
 */
public class ParkingLot implements Serializable {
    private static final long serialVersionUID = -3258839839160856613L;
    private String cityName;
    private List<String> airportName;
    private List<String> placeName;

    public ParkingLot() {
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public List<String> getAirportName() {
        return airportName;
    }

    public void setAirportName(List<String> airportName) {
        this.airportName = airportName;
    }

    public List<String> getPlaceName() {
        return placeName;
    }

    public void setPlaceName(List<String> placeName) {
        this.placeName = placeName;
    }
}
