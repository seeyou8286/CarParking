package com.network.jiufen.carparking.carparking.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.network.jiufen.carparking.carparking.R;
import com.network.jiufen.carparking.carparking.widget.CustomDatePicker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BookingActivity extends AppCompatActivity  implements View.OnClickListener {
    /**
     * Called when the activity is first created.
     */
    private TextView startDateTime;
    private TextView endDateTime;

    private CustomDatePicker startTimeDatePicker;
    private CustomDatePicker endTimeDatePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_content);

        startDateTime = (TextView) findViewById(R.id.startDialog);
        endDateTime = (TextView) findViewById(R.id.endDialog);
        startDateTime.setOnClickListener(this);
        endDateTime.setOnClickListener(this);
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
        }, "2010-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        startTimeDatePicker.showSpecificTime(true); // 显示时和分
        startTimeDatePicker.setIsLoop(true); // 允许循环滚动

        endTimeDatePicker = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                endDateTime.setText(time);
            }
        }, "2010-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
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
                break;
        }
    }
}
