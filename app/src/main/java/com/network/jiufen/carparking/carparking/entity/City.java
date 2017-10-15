package com.network.jiufen.carparking.carparking.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class City implements Serializable {
    private static final long serialVersionUID = -3258839839160856613L;
    private String name;
    private String firstLetter;

    public City(String name, String firstLetter) {
        this.name = name;
        this.firstLetter = firstLetter;
    }
}
