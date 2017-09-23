package com.network.jiufen.carparking.carparking.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.network.jiufen.carparking.carparking.R;
import com.network.jiufen.carparking.carparking.adapter.CityDetailAdapter;
import com.network.jiufen.carparking.carparking.domain.CarParkDetail;

import java.util.ArrayList;
import java.util.List;

public class BriefActivity extends AppCompatActivity{
    private List<CarParkDetail> details = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.brief_list);
        initDetails();
        CityDetailAdapter cityDetailAdapter = new CityDetailAdapter(BriefActivity.this,R.layout.brief_info,details);
        ListView listView = (ListView)findViewById(R.id.brief_list);
        listView.setAdapter(cityDetailAdapter);

    }


    private void initDetails()
    {
        CarParkDetail carParkDetail = new CarParkDetail("深圳机场",R.drawable.car_park1,"4.8分","距机场直线距离3公里","月销2000单","$18.0均");
        details.add(carParkDetail);
    }

}
