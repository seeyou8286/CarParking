package com.network.jiufen.carparking.carparking.activity;

import android.content.Intent;
import android.os.Bundle;
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
import com.network.jiufen.carparking.carparking.R;
import com.network.jiufen.carparking.carparking.util.DictionaryUtil;
import com.network.jiufen.carparking.carparking.util.IdGenerator;
import com.network.jiufen.carparking.carparking.util.MySingleton;
import com.network.jiufen.carparking.carparking.util.SharedPrefsUtil;
import com.network.jiufen.carparking.carparking.widget.CustomDatePicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;

import static com.network.jiufen.carparking.carparking.util.HttpUtil.WEB_SERVICE_HOST;

public class BookingPrepareActivity extends AppCompatActivity  implements View.OnClickListener {

    private String url = WEB_SERVICE_HOST+"/booking/save";

    /**
     * Called when the activity is first created.
     */
    @BindView(R.id.startDialog)
    private TextView startDateTime;
    @BindView(R.id.endDialog)
    private TextView endDateTime;
    @BindView(R.id.confirmBooking)
    private Button confirmBooking;
    @BindView(R.id.numbers)
    private TextView numberPicker;
    @BindView(R.id.carPlate)
    private EditText carPlate;
    @BindView(R.id.parkingLotName)
    private TextView placeName;
    @BindView(R.id.orderFee)
    private TextView orderFee;
    @BindView(R.id.numberAdd)
    private Button numberAdd;
    @BindView(R.id.numberMinus)
    private Button numberMinus;

    private CustomDatePicker startTimeDatePicker;
    private CustomDatePicker endTimeDatePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_content);

        String parkingLotName = this.getIntent().getStringExtra("parkingLotName");
        placeName.setText(parkingLotName);
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
                String startTime = startDateTime.getText().toString().trim();
                String endTime = endDateTime.getText().toString().trim();
                Integer number = Integer.valueOf(numberPicker.getText().toString().trim());
                String parkingLotName = this.getIntent().getStringExtra("parkingLotName");
                String plate = carPlate.getText().toString().trim();
                String phoneNumber = SharedPrefsUtil.getValue(getApplicationContext(),"phone","");
                Map map = new HashMap<>();
                map.put("phoneNumber",phoneNumber);
                map.put("startTime",startTime);
                map.put("endTime",endTime);
                map.put("plateNumber",plate);
                map.put("carCounts",number);
                map.put("parkingLotName",parkingLotName);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
                map.put("bookingTime",sdf.format(new Date()));
                map.put("id",IdGenerator.INSTANCE.nextId());
                Intent intent = new Intent(BookingPrepareActivity.this,BookingConfirmActivity.class);
                intent.putExtra("phoneNumber",phoneNumber);
                intent.putExtra("startTime",startTime);
                intent.putExtra("endTime",endTime);
                intent.putExtra("plateNumber",plate);
                intent.putExtra("carCounts",number);
                intent.putExtra("parkingLotName",parkingLotName);
                JSONObject params = new JSONObject(map);
                saveBookingInfo(params);
                startActivity(intent);
                break;
        }
    }




    public void saveBookingInfo(JSONObject jsonObject)
    {
        JsonObjectRequest objRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
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
