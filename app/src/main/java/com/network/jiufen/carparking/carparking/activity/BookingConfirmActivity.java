package com.network.jiufen.carparking.carparking.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.network.jiufen.carparking.carparking.R;

import butterknife.BindView;

import static com.network.jiufen.carparking.carparking.util.HttpUtil.WEB_SERVICE_HOST;

public class BookingConfirmActivity extends AppCompatActivity implements View.OnClickListener {

    private String url = WEB_SERVICE_HOST + "/booking/save";

    /**
     * Called when the activity is first created.
     */
    @BindView(R.id.parkingLotName)
    TextView parkingLotName;
    @BindView(R.id.categoryValue)
    TextView categoryValue;
    @BindView(R.id.startTimeValue)
    TextView startTimeValue;
    @BindView(R.id.endTimeValue)
    TextView endTimeValue;
    @BindView(R.id.plateValue)
    TextView plateValue;
    @BindView(R.id.confirmBooking)
    Button confirmBookingButton;

    private String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_booking_content);
        Intent intent = this.getIntent();

        String placeName = intent.getStringExtra("parkingLotName");
        phoneNumber = intent.getStringExtra("phoneNumber");
        String startTime = intent.getStringExtra("startTime");
        String endTime = intent.getStringExtra("endTime");
        String plateNumber = intent.getStringExtra("plateNumber");
        Integer carCounts = intent.getIntExtra("carCounts", 0);

        parkingLotName.setText(placeName);

        //TODO
        categoryValue.setText("室内停车");
        startTimeValue.setText(startTime);
        endTimeValue.setText(endTime);
        plateValue.setText(plateNumber);

        confirmBookingButton.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        Intent intent = new Intent(BookingConfirmActivity.this, PaymentSuccessActivity.class);
        intent.putExtra("phoneNumber",phoneNumber);
        startActivity(intent);
    }
}
