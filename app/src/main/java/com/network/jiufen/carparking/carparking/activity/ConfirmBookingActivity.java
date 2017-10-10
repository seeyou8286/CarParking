package com.network.jiufen.carparking.carparking.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.network.jiufen.carparking.carparking.R;
import com.network.jiufen.carparking.carparking.widget.CustomDatePicker;

import static com.network.jiufen.carparking.carparking.util.HttpUtil.WEB_SERVICE_HOST;

public class ConfirmBookingActivity extends AppCompatActivity implements View.OnClickListener {

    private String url = WEB_SERVICE_HOST + "/booking/save";

    /**
     * Called when the activity is first created.
     */
    private TextView parkingLotName;
    private TextView categoryValue;
    private TextView startTimeValue;
    private TextView endTimeValue;
    private TextView plateValue;

    private CustomDatePicker startTimeDatePicker;
    private CustomDatePicker endTimeDatePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_booking_content);
        Intent intent = this.getIntent();

        String placeName = intent.getStringExtra("placeName");
        String phoneNumber = intent.getStringExtra("phoneNumber");
        String startTime = intent.getStringExtra("startTime");
        String endTime = intent.getStringExtra("endTime");
        String plateNumber = intent.getStringExtra("plateNumber");
        Integer carCounts = intent.getIntExtra("carCounts", 0);

        parkingLotName = (TextView) findViewById(R.id.parkingLotName);
        categoryValue = (TextView) findViewById(R.id.categoryValue);
        startTimeValue = (TextView) findViewById(R.id.startTimeValue);
        endTimeValue = (TextView) findViewById(R.id.endTimeValue);
        plateValue = (TextView) findViewById(R.id.plateValue);
        parkingLotName.setText(placeName);
        categoryValue.setText("室内停车");
        startTimeValue.setText(startTime);
        endTimeValue.setText(endTime);
        plateValue.setText(plateNumber);
    }


    @Override
    public void onClick(View view) {

    }
}
