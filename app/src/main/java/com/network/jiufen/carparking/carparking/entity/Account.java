package com.network.jiufen.carparking.carparking.entity;

import java.io.Serializable;

public class Account implements Serializable {
    private static final long serialVersionUID = -3258839839160856613L;
    private String phoneNumber;
    private String password;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Account() {
    }

    public Account(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Account(String phoneNumber, String password) {
        this.phoneNumber = phoneNumber;
        this.password = password;
    }
}
