package com.network.jiufen.carparking.carparking.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.network.jiufen.carparking.carparking.R;
import com.network.jiufen.carparking.carparking.adapter.CityDetailAdapter;
import com.network.jiufen.carparking.carparking.domain.CarParkDetail;

import java.util.ArrayList;
import java.util.List;

public class BookingActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_content);

    }

}
