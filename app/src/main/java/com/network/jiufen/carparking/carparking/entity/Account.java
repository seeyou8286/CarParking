package com.network.jiufen.carparking.carparking.entity;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Account implements Serializable {
    private static final long serialVersionUID = -3258839839160856613L;
    private String phoneNumber;
    private String password;

    public Account(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Account(String phoneNumber, String password) {
        this.phoneNumber = phoneNumber;
        this.password = password;
    }
}
