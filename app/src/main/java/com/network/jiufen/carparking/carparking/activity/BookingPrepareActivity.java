package com.network.jiufen.carparking.carparking.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.math.MathUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.network.jiufen.carparking.carparking.R;
import com.network.jiufen.carparking.carparking.entity.BookingDetail;
import com.network.jiufen.carparking.carparking.entity.ParkingLot;
import com.network.jiufen.carparking.carparking.enumeration.BookingStatusEnum;
import com.network.jiufen.carparking.carparking.util.CustomJsonObjectRequest;
import com.network.jiufen.carparking.carparking.util.DateUtil;
import com.network.jiufen.carparking.carparking.util.DictionaryUtil;
import com.network.jiufen.carparking.carparking.util.IdGenerator;
import com.network.jiufen.carparking.carparking.util.JsonUtil;
import com.network.jiufen.carparking.carparking.util.MySingleton;
import com.network.jiufen.carparking.carparking.util.SharedPrefsUtil;
import com.network.jiufen.carparking.carparking.widget.CustomDatePicker;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.network.jiufen.carparking.carparking.util.DictionaryUtil.BOOKING_DETAIL;
import static com.network.jiufen.carparking.carparking.util.DictionaryUtil.PARKING_LOT;
import static com.network.jiufen.carparking.carparking.util.DictionaryUtil.PARKING_LOT_NAME;
import static com.network.jiufen.carparking.carparking.util.HttpUtil.WEB_SERVICE_HOST;

public class BookingPrepareActivity extends AppCompatActivity  implements View.OnClickListener {

    private String url = WEB_SERVICE_HOST+"/booking/save";

    /**
     * Called when the activity is first created.
     */
    @BindView(R.id.startDialog)
    TextView startDateTime;
    @BindView(R.id.endDialog)
    TextView endDateTime;
    @BindView(R.id.confirmBooking)
    Button confirmBooking;
    @BindView(R.id.numbers)
    TextView numberPicker;
    @BindView(R.id.carPlate)
    EditText carPlate;
    @BindView(R.id.parkingLotName)
    TextView placeName;
    @BindView(R.id.orderFee)
    TextView orderFee;
    @BindView(R.id.numberAdd)
    Button numberAdd;
    @BindView(R.id.numberMinus)
    Button numberMinus;

    private CustomDatePicker startTimeDatePicker;
    private CustomDatePicker endTimeDatePicker;
    private ParkingLot parkingLot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_content);

        ButterKnife.bind(this);
        parkingLot = (ParkingLot) this.getIntent().getSerializableExtra(PARKING_LOT);
        placeName.setText(parkingLot.getName());
        startDateTime.setOnClickListener(this);
        endDateTime.setOnClickListener(this);
        confirmBooking.setOnClickListener(this);
        numberAdd.setOnClickListener(this);
        numberMinus.setOnClickListener(this);

        initDatePicker();
    }

    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
        startDateTime.setText(now);
        endDateTime.setText(now);
        startTimeDatePicker = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                startDateTime.setText(time);
            }
        }, now, "2020-01-01 00:00"); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        startTimeDatePicker.showSpecificTime(true); // 显示时和分
        startTimeDatePicker.setIsLoop(true); // 允许循环滚动

        endTimeDatePicker = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                endDateTime.setText(time);
            }
        }, now, "2020-01-01 00:00"); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        endTimeDatePicker.showSpecificTime(true); // 显示时和分
        endTimeDatePicker.setIsLoop(true); // 允许循环滚动
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.startDialog:
                // 日期格式为yyyy-MM-dd
                startTimeDatePicker.show(startDateTime.getText().toString());
                break;

            case R.id.endDialog:
                // 日期格式为yyyy-MM-dd HH:mm
                endTimeDatePicker.show(endDateTime.getText().toString());
                confirmBooking.setEnabled(true);
                break;
            case R.id.numberMinus:
                Integer currentValue = Integer.valueOf(numberPicker.getText().toString().trim());
                currentValue--;
                numberPicker.setText(currentValue.toString());
                orderFee.setText(currentValue*20+"");
                if(currentValue==1)
                {
                    numberMinus.setEnabled(false);
                }else if(currentValue>1)
                {
                    numberMinus.setEnabled(true);
                }
                break;
            case R.id.numberAdd:
                Integer currentValue2 = Integer.valueOf(numberPicker.getText().toString().trim());
                currentValue2++;
                numberPicker.setText(currentValue2.toString());
                orderFee.setText(currentValue2*20+"");
                if(currentValue2>1)
                {
                    numberMinus.setEnabled(true);
                }
                break;
            case R.id.confirmBooking:
                DateTime planedCheckInTime = DateUtil.convertFromStringToDateTime(startDateTime.getText().toString());
                DateTime plannedCheckOutTime = DateUtil.convertFromStringToDateTime(endDateTime.getText().toString());

                int parkingDays = Math.abs(planedCheckInTime.getDayOfYear()-plannedCheckOutTime.getDayOfYear())+1;
                Integer carCount = Integer.valueOf(numberPicker.getText().toString().trim());
                String parkingLotName = parkingLot.getName();
                String plateNumber = carPlate.getText().toString().trim();
                String phoneNumber = SharedPrefsUtil.getValue(getApplicationContext(),"phone","");
                BookingDetail bookingDetail = new BookingDetail();
                bookingDetail.setPlanedCheckInTime(planedCheckInTime);
                bookingDetail.setPlanedCheckOutTime(plannedCheckOutTime);
                bookingDetail.setCarCounts(carCount);
                bookingDetail.setParkingLotName(parkingLotName);
                bookingDetail.setPlateNumber(plateNumber);
                bookingDetail.setPhoneNumber(phoneNumber);
                bookingDetail.setId(IdGenerator.INSTANCE.nextId());
                bookingDetail.setParkingDays(parkingDays);
                bookingDetail.setBookingFee(20*carCount);
                bookingDetail.setTotalPrice(parkingDays*parkingLot.getDayPrice());
                bookingDetail.setParkingLotAddress(parkingLot.getAddress());
                //To record the current time
                bookingDetail.setBookingTime(new DateTime().withZone(DateTimeZone.forID("Asia/Shanghai")));
                //To set the initial status to Booked
                bookingDetail.setBookingStatus(BookingStatusEnum.Booked);
                Intent intent = new Intent(BookingPrepareActivity.this,BookingConfirmActivity.class);
                intent.putExtra(BOOKING_DETAIL, bookingDetail);
                try {
                    String jsonString = JsonUtil.toJson(bookingDetail);
                    saveBookingInfo(jsonString);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    Toast.makeText(BookingPrepareActivity.this, "预订失败", Toast.LENGTH_LONG).show();
                }
                startActivity(intent);
                break;
        }
    }




    public void saveBookingInfo(String jsonString)
    {
        CustomJsonObjectRequest objRequest = new CustomJsonObjectRequest(Request.Method.POST, url, jsonString,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String status = response.getString("status");
                            if(status.equals(DictionaryUtil.SUCCESS))
                            {
                                Toast.makeText(BookingPrepareActivity.this, "预订成功", Toast.LENGTH_LONG).show();
                            }else
                            {
                                Toast.makeText(BookingPrepareActivity.this, "预订失败", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(BookingPrepareActivity.this, "预订失败", Toast.LENGTH_LONG).show();
                            return;
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BookingPrepareActivity.this, "预订失败", Toast.LENGTH_LONG).show();
            }
        });
        MySingleton.getInstance(BookingPrepareActivity.this).addToRequestQueue(objRequest);
    }


}
