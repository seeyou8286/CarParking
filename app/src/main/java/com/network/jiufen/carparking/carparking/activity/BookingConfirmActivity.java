package com.network.jiufen.carparking.carparking.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.network.jiufen.carparking.carparking.R;
import com.network.jiufen.carparking.carparking.entity.BookingDetail;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.network.jiufen.carparking.carparking.util.DateUtil.DefautDateFormat;
import static com.network.jiufen.carparking.carparking.util.HttpUtil.WEB_SERVICE_HOST;

public class BookingConfirmActivity extends AppCompatActivity implements View.OnClickListener {

    private String url = WEB_SERVICE_HOST + "/booking/save";

    /**
     * Called when the activity is first created.
     */
    @BindView(R.id.parkingLotName)
    TextView parkingLotName;
//    @BindView(R.id.categoryValue)
//    TextView categoryValue;
    @BindView(R.id.plannedCheckInTime)
    TextView startTimeValue;
    @BindView(R.id.plannedCheckOutTime)
    TextView endTimeValue;
    @BindView(R.id.plateValue)
    TextView plateValue;
    @BindView(R.id.confirmBooking)
    Button confirmBookingButton;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.parkingDays)
    TextView parkingDays;
    @BindView(R.id.bookingStatus)
    TextView bookingStatus;
    @BindView(R.id.totalPrice)
    TextView totalPrice;
    @BindView(R.id.leftPrice)
    TextView leftPrice;
    @BindView(R.id.bookingFee)
    TextView bookingFee;

    private String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_booking_content);
        ButterKnife.bind(this);

        Intent intent = this.getIntent();
        BookingDetail bookingDetail = (BookingDetail)intent.getSerializableExtra("bookingDetail");


        parkingLotName.setText(bookingDetail.getParkingLotName());
        //TODO
//        categoryValue.setText("室内停车");
        startTimeValue.setText(bookingDetail.getPlanedCheckInTime().toString(DefautDateFormat));
        endTimeValue.setText(bookingDetail.getPlanedCheckOutTime().toString(DefautDateFormat));
        plateValue.setText(bookingDetail.getPlateNumber());
        address.setText(bookingDetail.getParkingLotAddress());
        parkingDays.setText(bookingDetail.getParkingDays().toString());
        bookingStatus.setText(bookingDetail.getBookingStatus().toString());
        totalPrice.setText(bookingDetail.getTotalPrice().toString());
        Integer left = bookingDetail.getTotalPrice() - bookingDetail.getBookingFee();
        leftPrice.setText(left.toString());
        bookingFee.setText(bookingDetail.getBookingFee().toString()  );
        confirmBookingButton.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        Intent intent = new Intent(BookingConfirmActivity.this, PaymentSuccessActivity.class);
        intent.putExtra("phoneNumber",phoneNumber);
        startActivity(intent);
    }
}
