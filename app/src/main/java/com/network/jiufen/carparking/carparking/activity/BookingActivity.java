package com.network.jiufen.carparking.carparking.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.network.jiufen.carparking.carparking.R;
import com.network.jiufen.carparking.carparking.util.DateTimePickDialogUtil;

public class BookingActivity extends AppCompatActivity {
    /** Called when the activity is first created. */
    private EditText startDateTime;
    private EditText endDateTime;

    private String initStartDateTime = "2013年9月3日 14:44"; // 初始化开始时间
    private String initEndDateTime = "2014年8月23日 17:44"; // 初始化结束时间

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_content);

        startDateTime = (EditText) findViewById(R.id.startDialog);
        endDateTime = (EditText) findViewById(R.id.endDialog);

        startDateTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
                        BookingActivity.this, initStartDateTime);
                dateTimePicKDialog.dateTimePicKDialog(startDateTime);

            }
        });

        endDateTime.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
                        BookingActivity.this, initEndDateTime);
                dateTimePicKDialog.dateTimePicKDialog(endDateTime);
            }
        });
    }



}
