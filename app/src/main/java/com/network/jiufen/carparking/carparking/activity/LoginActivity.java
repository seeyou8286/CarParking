package com.network.jiufen.carparking.carparking.activity;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.network.jiufen.carparking.carparking.R;
import com.network.jiufen.carparking.carparking.util.SMSender;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private String appId;
    private String accessKeySecret;
    private String verificationCode;

    private EditText phone;
    private EditText cord;
    private TextView hintmessage;
    private Button getCord;
    private Button saveCord;
    private String iPhone;
    private String iCord;
    private int time = 60;
    private boolean flag = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init() {
        initRetrieveMetaData();
        phone = (EditText) findViewById(R.id.phone);
        cord = (EditText) findViewById(R.id.cord);
        hintmessage = (TextView) findViewById(R.id.hintmessage);
        getCord = (Button) findViewById(R.id.getcord);
        saveCord = (Button) findViewById(R.id.savecord);
        getCord.setOnClickListener(this);
        saveCord.setOnClickListener(this);
    }


    private void initRetrieveMetaData() {
        try {
            ApplicationInfo appInfo = this.getPackageManager()
                    .getApplicationInfo(this.getPackageName(), PackageManager.GET_META_DATA);
            appId = appInfo.metaData.getString("Mob-AppKey");
            accessKeySecret = appInfo.metaData.getString("Mob-AppSecret");
        } catch (PackageManager.NameNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.getcord:
                if (!TextUtils.isEmpty(phone.getText().toString().trim())) {
                    if (phone.getText().toString().trim().length() == 11) {
                        iPhone = phone.getText().toString().trim();
                        verificationCode = (int) (Math.random() * 9000 + 1000) + "";
                        SMSender smSender = new SMSender(iPhone, verificationCode, appId, accessKeySecret);
                        Thread thread = new Thread(smSender);
                        thread.start();
                        cord.requestFocus();
                        getCord.setVisibility(View.GONE);
                        reminderText();
                    } else {
                        Toast.makeText(LoginActivity.this, "请输入完整电话号码", Toast.LENGTH_LONG).show();
                        phone.requestFocus();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "请输入您的电话号码", Toast.LENGTH_LONG).show();
                    phone.requestFocus();
                }
                break;
            case R.id.savecord:
                if (!TextUtils.isEmpty(cord.getText().toString().trim())) {
                    if (cord.getText().toString().trim().length() == 4) {
                        iCord = cord.getText().toString().trim();
                        if (verificationCode.equals(iCord)) {
                            Toast.makeText(getApplicationContext(), "验证码校验成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, ContentActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "请输入完整验证码", Toast.LENGTH_LONG).show();
                        cord.requestFocus();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "请输入验证码", Toast.LENGTH_LONG).show();
                    cord.requestFocus();
                }
                break;
            default:
                break;
        }
    }



    //验证码送成功后提示文字
    private void reminderText() {
        hintmessage.setVisibility(View.VISIBLE);
        handlerText.sendEmptyMessageDelayed(1, 1000);
    }

    Handler handlerText = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                if (time > 0) {
                    hintmessage.setText("验证码已发送" + time + "秒");
                    time--;
                    handlerText.sendEmptyMessageDelayed(1, 1000);
                } else {
                    hintmessage.setText("提示信息");
                    time = 60;
                    hintmessage.setVisibility(View.GONE);
                    getCord.setVisibility(View.VISIBLE);
                }
            } else {
                cord.setText("");
                hintmessage.setText("提示信息");
                time = 60;
                hintmessage.setVisibility(View.GONE);
                getCord.setVisibility(View.VISIBLE);
            }
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
