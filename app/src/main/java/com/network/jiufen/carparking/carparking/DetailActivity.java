package com.network.jiufen.carparking.carparking;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.network.jiufen.carparking.carparking.adapter.CityDetailAdapter;
import com.network.jiufen.carparking.carparking.domain.CarParkDetail;

import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity{
    private List<CarParkDetail> details = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_list);
        initDetails();
        CityDetailAdapter cityDetailAdapter = new CityDetailAdapter(DetailActivity.this,R.layout.detail_info,details);
        ListView listView = (ListView)findViewById(R.id.detail_list);
        listView.setAdapter(cityDetailAdapter);

    }


    private void initDetails()
    {
        CarParkDetail carParkDetail = new CarParkDetail("深圳机场",R.drawable.car_park1);
        details.add(carParkDetail);
    }

}
