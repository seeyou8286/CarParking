package com.network.jiufen.carparking.carparking.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.network.jiufen.carparking.carparking.R;
import com.network.jiufen.carparking.carparking.widget.CustomDatePicker;

import static com.network.jiufen.carparking.carparking.util.HttpUtil.WEB_SERVICE_HOST;

public class BookingConfirmActivity extends AppCompatActivity implements View.OnClickListener {

    private String url = WEB_SERVICE_HOST + "/booking/save";

    /**
     * Called when the activity is first created.
     */
    private TextView parkingLotName;
    private TextView categoryValue;
    private TextView startTimeValue;
    private TextView endTimeValue;
    private TextView plateValue;
    private Button confirmBookingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_booking_content);
        Intent intent = this.getIntent();

        String placeName = intent.getStringExtra("parkingLotName");
        String phoneNumber = intent.getStringExtra("phoneNumber");
        String startTime = intent.getStringExtra("startTime");
        String endTime = intent.getStringExtra("endTime");
        String plateNumber = intent.getStringExtra("plateNumber");
        Integer carCounts = intent.getIntExtra("carCounts", 0);

        confirmBookingButton = (Button)findViewById(R.id.confirmBooking);
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

        confirmBookingButton.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        Intent intent = new Intent(BookingConfirmActivity.this, PaymentSuccessActivity.class);
        startActivity(intent);
    }
}
