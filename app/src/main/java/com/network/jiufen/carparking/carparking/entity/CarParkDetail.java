package com.network.jiufen.carparking.carparking.entity;

/**
 * Created by asus on 2017/9/23.
 */

public class CarParkDetail {
    private String name;
    private int imageId;
    private String points;
    private String distance;
    private String monthlySoldOut;
    private String price;

    public CarParkDetail(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    public CarParkDetail(String name, int imageId, String points, String distance, String monthlySoldOut, String price) {
        this.name = name;
        this.imageId = imageId;
        this.points = points;
        this.distance = distance;
        this.monthlySoldOut = monthlySoldOut;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getMonthlySoldOut() {
        return monthlySoldOut;
    }

    public void setMonthlySoldOut(String monthlySoldOut) {
        this.monthlySoldOut = monthlySoldOut;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
